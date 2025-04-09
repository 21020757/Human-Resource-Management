package org.example.hrm.constant;

import java.math.BigDecimal;
import java.time.LocalTime;

public final class Constants {
    public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

    public static final String SYSTEM = "system";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final Long IN_MINUTES = 60 * 1000L;
    public static final Long IN_DAYS = 24 * 60 * 60 * 1000L;
    public static final LocalTime CHECK_IN_DEADLINE = LocalTime.of(8, 30);
    public static final LocalTime REGULAR_CHECK_OUT_TIME = LocalTime.of(17, 30);
    public static final Long LUNCH_BREAK = 60L;
    public static final BigDecimal MONTHLY_WORK_DAYS = BigDecimal.valueOf(22);
}
