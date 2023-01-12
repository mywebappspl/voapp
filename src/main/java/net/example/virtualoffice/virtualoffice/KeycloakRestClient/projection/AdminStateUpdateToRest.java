package net.example.virtualoffice.virtualoffice.KeycloakRestClient.projection;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class AdminStateUpdateToRest {
    private String[] groups;
    public AdminStateUpdateToRest ToRest(String group)
    {
        this.groups=new String[]{group};
        return this;
    }

}
