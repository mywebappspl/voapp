package net.example.virtualoffice.virtualoffice.KeycloakRestClient.projection;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class KeycloakRestUpdateAgent {

    @Email
    private String email;
    private String firstName;
    private String lastName;
}
