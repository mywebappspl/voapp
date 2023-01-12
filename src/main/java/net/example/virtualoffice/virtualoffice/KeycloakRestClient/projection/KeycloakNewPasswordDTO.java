package net.example.virtualoffice.virtualoffice.KeycloakRestClient.projection;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class KeycloakNewPasswordDTO{
    private String type;
    private String value;
    private boolean temporary;
    public void ToSave(String value){
        this.type="password";
        this.value=value;
        this.temporary=false;
    }

}
