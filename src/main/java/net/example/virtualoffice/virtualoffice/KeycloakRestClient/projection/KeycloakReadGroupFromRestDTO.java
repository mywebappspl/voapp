package net.example.virtualoffice.virtualoffice.KeycloakRestClient.projection;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class KeycloakReadGroupFromRestDTO {
    private String id;
    private String name;
}
