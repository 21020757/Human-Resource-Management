package org.example.hrm;

import jakarta.annotation.PostConstruct;
import org.example.hrm.cronjob.AttendanceScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class HrmApplication {
	@Autowired
	private AttendanceScheduler attendanceScheduler;

	public static void main(String[] args) {
		SpringApplication.run(HrmApplication.class, args);
	}

	@PostConstruct
	public void init() {
		attendanceScheduler.createDailyAttendance();
	}
}
