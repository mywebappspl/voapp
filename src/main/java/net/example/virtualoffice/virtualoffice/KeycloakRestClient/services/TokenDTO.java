package net.example.virtualoffice.virtualoffice.KeycloakRestClient.services;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class TokenDTO {
    private String access_token;
    private int expires_in;
    private int refresh_expires_in;
    private String token_type;
    @JsonProperty("not-before-policy")
    private int not_before_policy;
    private String scope;
    private int statusCode;
    TokenDTO(){}
    TokenDTO(int status)
    {
        this.statusCode=status;
    }
}
