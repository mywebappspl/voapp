package net.example.virtualoffice.virtualoffice.KeycloakRestClient.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionMessages {
    AGENT_NOT_EXIST ("Agent not exist"),
    AGENTS_CANNOT_BE_FETCHED ("Agent cannot be fetched"),
    INTERNAL_SERVER_ERROR ("Unknown error"),
    STATUS_TOKEN_ERROR ("Cannot get access to Agent resource server"),
    AGENT_EMAIL_NOT_EMPTY_BAD_REQUEST ("Agent email cannot be empty. Incorrect request content."),
    AGENT_FIRSTNAME_NOT_EMPTY_BAD_REQUEST ("Agent firstname cannot be changed. Incorrect request content."),
    AGENT_LASTNME_NOT_EMPTY_REQUEST ("Agent lastname cannot be changed. Incorrect request content.");
    private final String message;
}
