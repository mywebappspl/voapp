package net.example.virtualoffice.virtualoffice.Utils;

import net.example.virtualoffice.virtualoffice.exception.LogsExceptionHandler;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class voutils {
    static public boolean LogsDatesValidation(Date startDate, Date endDate) {
        if ((startDate != null && endDate == null) || (startDate == null && endDate != null)) {
            throw new LogsExceptionHandler(" This endpoint requires two dates");
        }
        if (startDate != null) {
            if (startDate.compareTo(endDate) > 0)
                throw new LogsExceptionHandler(" Start date is higher than end date");
            return true;
        } else {
            return false;
        }
    }

    static public Instant EndDateCalculation(Date endDate) {
        return endDate.toInstant().plus(1, ChronoUnit.DAYS);
    }

    static public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
