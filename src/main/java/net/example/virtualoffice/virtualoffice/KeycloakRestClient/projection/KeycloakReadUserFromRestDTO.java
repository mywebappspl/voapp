package net.example.virtualoffice.virtualoffice.KeycloakRestClient.projection;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter

public class KeycloakReadUserFromRestDTO {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String enabled;

}
