package net.example.virtualoffice.virtualoffice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionMessages {
    COMPANY_WITH_ID_NOT_EXISTS ("Company with given id not exists"),
    EXPORT_CSV_TWO_DATES_REQUIRED ("This endpoint requires two dates"),
    EXPORT_CSV_FIRST_DATE_LATE ("Start date is higher than end date"),
    COMPANY_NAME_EXISTS ("Company with given name already exists"),
    NO_MESSAGES ("No messages found"),
    COMPANY_IS_INACTIVE ("Company is not active"),
    MEMBER_ALREADY_EXISTS ("Member already exists"),
    MEMBER_DOES_NOT_EXISTS ("Member with given Id not exists"),
    MEMBER_NOT_EXIST_IN_COMPANY ("Member with given Id not exists in this company"),
    INCORRECT_DATES ("Given dates are incorrect");
    private final String message;
}
