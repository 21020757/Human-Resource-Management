package org.example.hrm.service.impl;

import org.example.hrm.constant.Constants;
import org.example.hrm.constant.WorkLocation;
import org.example.hrm.dto.AttendanceDto;
import org.example.hrm.dto.AttendanceRequest;
import org.example.hrm.dto.AttendanceResponse;
import org.example.hrm.dto.response.EmployeeAttendanceDto;
import org.example.hrm.exception.CoreErrorCode;
import org.example.hrm.exception.CoreException;
import org.example.hrm.model.Attendance;
import org.example.hrm.model.Employee;
import org.example.hrm.model.enumeration.AttendanceStatus;
import org.example.hrm.repository.AttendanceRepository;
import org.example.hrm.repository.EmployeeRepository;
import org.example.hrm.service.AttendanceService;
import org.example.hrm.util.CommonUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;
    private static final double EARTH_RADIUS_KM = 6371.0;
    private static final double ACCEPTABLE_DISTANCE = WorkLocation.ACCEPTABLE_DISTANCE;
    private static final double officeLat = WorkLocation.latitude;
    private static final double officeLng = WorkLocation.longitude;
    private static final LocalTime CHECK_IN_DEADLINE = Constants.CHECK_IN_DEADLINE;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository, EmployeeRepository employeeRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void updateAttendance(AttendanceDto attendanceDto) {
        Attendance attendance = attendanceRepository.findByEmployeeIdAndDate(
                attendanceDto.getEmployeeId(),
                attendanceDto.getDate()
        );
        CommonUtils.copyPropertiesIgnoreNull(attendanceDto, attendance);
        BigDecimal totalHours = calculateTotalHours(attendance.getCheckInTime(), attendanceDto.getCheckOutTime());
        attendance.setTotalWorkingTime(totalHours);
        attendance.setWorkDays(calculateWorkdays(totalHours));
        attendanceRepository.save(attendance);
    }

    @Override
    public AttendanceResponse recordAttendance(String email, AttendanceRequest request) {
        Attendance attendance = attendanceRepository
                .findAttendanceByEmployee_EmailAndDate(email, LocalDate.now());
        if (attendance.getCheckInTime() == null) {
            return checkIn(attendance, request);
        }
        return checkOut(attendance, request);
    }

    @Override
    public Page<EmployeeAttendanceDto> getAllAttendanceByEmployee(int month, int year, Pageable pageable) {

        LocalDate startDate = LocalDate.of(year, month, 1); // LocalDate.of(2025, 5, 1)
        LocalDate endDate = LocalDate.of(year, month, startDate.lengthOfMonth());

        // Lấy tất cả employee có tham gia chấm công trong tháng đó
        Page<Employee> employees = employeeRepository.findAllWithAttendanceInDateRange(startDate, endDate, pageable);

        List<EmployeeAttendanceDto> dtoList = employees.getContent().stream()
                .map(employee -> {
                    List<Attendance> attendances = attendanceRepository
                            .findAllByEmployeeAndDateBetween(employee, startDate, endDate);
                    return new EmployeeAttendanceDto(employee, attendances);
                })
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, employees.getTotalElements());
    }


    @Override
    public Page<Attendance> findByFilters(Long employeeId,
                                             int month, int year,
                                             AttendanceStatus status,
                                             final Pageable pageable) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = LocalDate.of(year, month, startDate.lengthOfMonth());
        return attendanceRepository
                .findByFilters(employeeId,
                startDate,
                endDate,
                status,
                pageable);
    }

    public AttendanceResponse checkIn(Attendance attendance, AttendanceRequest checkin) {
        double lat = checkin.getLatitude();
        double lng = checkin.getLongitude();
        LocalTime checkInTime = checkin.getTime();
        if (validateDistance(lat, lng, officeLat, officeLng, ACCEPTABLE_DISTANCE)) {
            throw new CoreException(CoreErrorCode.OUT_OF_RANGE);
        }
        attendance.setCheckInTime(checkInTime);
        AttendanceStatus attendanceStatus = Duration.between(checkInTime, CHECK_IN_DEADLINE).toMinutes() < 10 ?
                    AttendanceStatus.LATE : AttendanceStatus.ON_TIME;

        attendance.setStatus(attendanceStatus);
        attendanceRepository.save(attendance);

        return new AttendanceResponse(LocalDate.now(), checkInTime, lat, lng);
    }

    public AttendanceResponse checkOut(Attendance attendance, AttendanceRequest checkout) {
        double lat = checkout.getLatitude();
        double lng = checkout.getLongitude();
        LocalTime checkOutTime = checkout.getTime();
        BigDecimal totalHours = calculateTotalHours(attendance.getCheckInTime(), checkOutTime);
        if (validateDistance(lat, lng, officeLat, officeLng, ACCEPTABLE_DISTANCE)) {
            throw new RuntimeException("Check-out failed! You're out of acceptable distance!");
        }
        attendance.setCheckOutTime(checkOutTime);
        attendance.setTotalWorkingTime(totalHours);
        attendance.setWorkDays(calculateWorkdays(totalHours));

        attendanceRepository.save(attendance);
        return new AttendanceResponse(LocalDate.now(), checkOutTime, lat, lng);
    }

    public BigDecimal calculateTotalHours(LocalTime checkInTime, LocalTime checkOutTime) {
        if(checkOutTime.isAfter(Constants.REGULAR_CHECK_OUT_TIME)) {
            checkOutTime = Constants.REGULAR_CHECK_OUT_TIME;
        }
        double totalHours = (Duration.between(checkInTime, checkOutTime).toMinutes() - Constants.LUNCH_BREAK)/60.0;
        return BigDecimal.valueOf(totalHours > 8 ? 8 : totalHours);
    }

    public BigDecimal calculateWorkdays(BigDecimal totalHours) {
        return totalHours.divide(BigDecimal.valueOf(8), 2, RoundingMode.HALF_EVEN);
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }

    public boolean validateDistance(double userLat, double userLon, double officeLat, double officeLon, double maxDistanceKm) {
        double distance = calculateDistance(userLat, userLon, officeLat, officeLon);
        return !(distance <= maxDistanceKm);
    }
}
