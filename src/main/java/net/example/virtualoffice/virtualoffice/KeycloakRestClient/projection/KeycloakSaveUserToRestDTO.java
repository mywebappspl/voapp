package net.example.virtualoffice.virtualoffice.KeycloakRestClient.projection;

import lombok.Getter;
import lombok.Setter;
import org.keycloak.common.util.Time;
import org.springframework.stereotype.Component;

@Getter
@Setter
class Credentials {
    private boolean temporary;
    private String type;
    private String value;
    Credentials(String value)
    {
        this.temporary = false;
        this.type = "password";
        this.value = value;
    }
}
@Component
@Getter
@Setter
public class KeycloakSaveUserToRestDTO {
    private int createdTimestamp;
    private String username;
    private boolean enabled;
    private boolean emailVerified;
    private String firstName;
    private String lastName;
    private String email;
    private String[] groups;
    private Credentials[] credentials;
    public KeycloakSaveUserToRestDTO toSave(KeycloakRestInputUserFromControllerDTO keycloakRestInputUserFromControllerDTO)
    {
        this.createdTimestamp= Time.currentTime();
        this.username= keycloakRestInputUserFromControllerDTO.getUsername();
        this.enabled=true;
        this.firstName= keycloakRestInputUserFromControllerDTO.getFirstName();
        this.lastName= keycloakRestInputUserFromControllerDTO.getLastName();
        this.email= keycloakRestInputUserFromControllerDTO.getEmail();
        this.emailVerified=true;
        this.credentials=new Credentials[]{new Credentials(keycloakRestInputUserFromControllerDTO.getPassword())};
        this.groups = new String[]{"userg"};
        return this;
    }

}
