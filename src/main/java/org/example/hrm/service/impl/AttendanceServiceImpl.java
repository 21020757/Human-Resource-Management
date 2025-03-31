package org.example.hrm.service.impl;

import org.example.hrm.constant.Constants;
import org.example.hrm.constant.WorkLocation;
import org.example.hrm.dto.AttendanceRequest;
import org.example.hrm.dto.AttendanceResponse;
import org.example.hrm.dto.CustomResponse;
import org.example.hrm.model.Attendance;
import org.example.hrm.model.enumeration.AttendanceStatus;
import org.example.hrm.repository.AttendanceRepository;
import org.example.hrm.service.AttendanceService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;


@Service
public class AttendanceServiceImpl implements AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private static final double EARTH_RADIUS_KM = 6371.0;
    private static final double ACCEPTABLE_DISTANCE = WorkLocation.ACCEPTABLE_DISTANCE;
    private static final double officeLat = WorkLocation.latitude;
    private static final double officeLng = WorkLocation.longitude;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public CustomResponse recordAttendance(String email, AttendanceRequest request) {
        Attendance attendance = attendanceRepository
                .findAttendanceByEmployee_EmailAndDate(email, LocalDate.now());
        if (attendance.getCheckInTime() == null) {
            return checkIn(attendance, request);
        }
        return checkOut(attendance, request);
    }

    public CustomResponse checkIn(Attendance attendance, AttendanceRequest checkin) {
        double lat = checkin.getLatitude();
        double lng = checkin.getLongitude();
        LocalTime checkInTime = checkin.getTime();
        if (validateDistance(lat, lng, officeLat, officeLng, ACCEPTABLE_DISTANCE)) {
            return new CustomResponse(false, "Check-in failed! You're out of acceptable distance!",
                    new AttendanceResponse(LocalDate.now(), checkInTime, lat, lng));
        }
        attendance.setCheckInTime(checkInTime);
        AttendanceStatus attendanceStatus = checkInTime.isAfter(Constants.CHECK_IN_DEADLINE) ?
                    AttendanceStatus.LATE : AttendanceStatus.ON_TIME;attendance.setStatus(attendanceStatus);

        attendanceRepository.save(attendance);

        return new CustomResponse(true, "Check-in successful!",
                new AttendanceResponse(LocalDate.now(), checkInTime, lat, lng));
    }

    public CustomResponse checkOut(Attendance attendance, AttendanceRequest checkout) {
        double lat = checkout.getLatitude();
        double lng = checkout.getLongitude();
        LocalTime checkOutTime = checkout.getTime();
        BigDecimal totalHours = calculateTotalHours(attendance.getCheckInTime(), checkOutTime);
        if (validateDistance(lat, lng, officeLat, officeLng, ACCEPTABLE_DISTANCE)) {
            return new CustomResponse(false, "Check-out failed! You're out of acceptable distance!",
                    new AttendanceResponse(LocalDate.now(), checkOutTime, lat, lng));
        }
        attendance.setCheckOutTime(checkOutTime);
        attendance.setTotalWorkingTime(totalHours);
        attendance.setWorkDays(calculateWorkdays(totalHours));

        attendanceRepository.save(attendance);
        return new CustomResponse(true, "Check-out successful!",
                new AttendanceResponse(LocalDate.now(), checkOutTime, lat, lng));
    }

    public BigDecimal calculateTotalHours(LocalTime checkInTime, LocalTime checkOutTime) {
        if(checkOutTime.isAfter(Constants.REGULAR_CHECK_OUT_TIME)) {
            checkOutTime = Constants.REGULAR_CHECK_OUT_TIME;
        }
        double totalHours = (Duration.between(checkInTime, checkOutTime).toMinutes() - Constants.LUNCH_BREAK)/60.0;
        return BigDecimal.valueOf(totalHours > 8 ? 8 : totalHours);
    }

    public BigDecimal calculateWorkdays(BigDecimal totalHours) {
        return totalHours.divide(BigDecimal.valueOf(8));
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
