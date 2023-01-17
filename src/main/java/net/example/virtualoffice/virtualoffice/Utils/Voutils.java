package net.example.virtualoffice.virtualoffice.Utils;

import net.example.virtualoffice.virtualoffice.exception.CustomExceptionHandler;
import net.example.virtualoffice.virtualoffice.exception.ExceptionMessages;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Voutils {
    static public boolean logsDatesValidation(Date startDate, Date endDate) {
        if ((startDate != null && endDate == null) || (startDate == null && endDate != null)) {
            throw new CustomExceptionHandler(ExceptionMessages.EXPORT_CSV_TWO_DATES_REQUIRED, HttpStatus.BAD_REQUEST);
        }
        if (startDate != null) {
            if (startDate.compareTo(endDate) > 0)
                throw new CustomExceptionHandler(ExceptionMessages.EXPORT_CSV_FIRST_DATE_LATE,HttpStatus.BAD_REQUEST);
            return true;
        } else {
            return false;
        }
    }

    static public Instant endDateCalculation(Date endDate) {
        return endDate.toInstant().plus(1, ChronoUnit.DAYS);
    }

    static public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
