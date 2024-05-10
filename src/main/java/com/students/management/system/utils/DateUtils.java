package com.students.management.system.utils;

import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Locale;

@Slf4j
public class DateUtils {

    //DateTimeFormat
    public static final String DATE_TIME_FORMAT = "dd/MM/yyyy hh:mm a";
    public static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
    public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT, Locale.ENGLISH);
    DateUtils() {
    }

    public static boolean isValidDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        try {
            formatter.parse(dateTimeString);
            return true; // Parsing successful, format is valid
        } catch (DateTimeParseException e) {
            return false; // Parsing failed, format is invalid
        }
    }

    public static boolean isValidTimeZone(String timeZone) {
        try {
            ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of(timeZone));
            log.info(zonedDateTime.toOffsetDateTime().toString());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean checkAgeAboveEighteen(Long date, int age) {
        LocalDate ageDate = setDate(date);
        LocalDate todayDate = LocalDate.now();
        Period period = Period.between(ageDate, todayDate);
        return period.getYears() >= age;
    }

    public static LocalDate setDate(Long lDate) {
        Date dateLong = new Date(lDate);
        Instant instant = dateLong.toInstant();
        ZoneId defaultZoneId = ZoneId.systemDefault();
        return instant.atZone(defaultZoneId).toLocalDate();
    }

    public static Long convertLocalDateToLong() {
        LocalDate localDateTime = LocalDate.now();
        ZonedDateTime zdt = ZonedDateTime.of(localDateTime.atStartOfDay(), ZoneId.systemDefault());
        return zdt.toInstant().toEpochMilli();
    }

    public static Long convertLocalDateToLongDate(LocalDateTime date) {
        LocalDateTime localDate = LocalDateTime.from(date);
        return localDate.toEpochSecond(ZoneOffset.UTC) * 1000;
    }

    public static ZonedDateTime convertUTCToUserTimeZone(LocalDateTime dateTime, ZoneId userZoneId) {
        ZoneId utcTimeZone = ZoneId.of("UTC");
        ZonedDateTime userDateTime = ZonedDateTime.of(dateTime, utcTimeZone);
        return userDateTime.withZoneSameInstant(userZoneId);
    }

    public static ZonedDateTime formatAndAddTimeZoneToDate(String date, ZoneId timeZones) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT, Locale.ENGLISH);
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        return ZonedDateTime.of(localDateTime, timeZones);
    }

    public static String convertUTCToUserLocalTimeInString(LocalDateTime dateTime, ZoneId userZoneId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT, Locale.ENGLISH);
        ZoneId utcTimeZone = ZoneId.of("UTC");
        ZonedDateTime userDateTime = ZonedDateTime.of(dateTime, utcTimeZone);
        return (userDateTime.withZoneSameInstant(userZoneId).toLocalDateTime()).format(formatter);
    }

    public static String convertUTCToUserLocalTimeInStringFromMillis(long dateInMillis, ZoneId userZoneId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT, Locale.ENGLISH);
        ZoneId utcTimeZone = ZoneId.of("UTC");
        ZonedDateTime userDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(dateInMillis * 1000), utcTimeZone);
        return (userDateTime.withZoneSameInstant(userZoneId).toLocalDateTime()).format(formatter);
    }

    public int calculateAge(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(date, currentDate).getYears();
    }
}
