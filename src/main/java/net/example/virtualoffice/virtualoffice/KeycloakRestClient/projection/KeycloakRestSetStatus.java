package net.example.virtualoffice.virtualoffice.KeycloakRestClient.projection;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class KeycloakRestSetStatus {
    @NotNull
    private boolean enabled;
}
