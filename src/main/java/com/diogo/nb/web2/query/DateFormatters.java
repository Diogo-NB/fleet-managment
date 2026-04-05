package com.diogo.nb.web2.query;

import java.time.format.DateTimeFormatter;

public final class DateFormatters {

    public static final DateTimeFormatter DATE_TIME = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    public static final DateTimeFormatter DATE_TIME_VERBOSE = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm");

    private DateFormatters() {}
}
