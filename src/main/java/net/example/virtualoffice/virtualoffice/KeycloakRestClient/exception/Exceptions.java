package net.example.virtualoffice.virtualoffice.KeycloakRestClient.exception;

import lombok.Getter;

@Getter
public class Exceptions {
  public static final String AGENT_NOT_EXIST = "Agent cannot be fetched jjjjjj";      //404
  public static final String AGENTS_CANNOT_BE_FETCHED = "Agents cannot be fetched";   //404
  public static final String INTERNAL_SERVER_ERROR = "Unknown error"; //500
  public static final String STATUS_TOKEN_ERROR = "Cannot get access to Agent resource server"; //500
  public static final String AGENT_EMAIL_NOT_EMPTY_BAD_REQUEST = "Agent email cannot be empty. Incorrect request content."; //400
  public static final String AGENT_FIRSTNAME_NOT_EMPTY_BAD_REQUEST = "Agent firstname cannot be changed. Incorrect request content."; //400
  public static final String AGENT_LASTNME_NOT_EMPTY_REQUEST = "Agent lastname cannot be changed. Incorrect request content."; //400
}
