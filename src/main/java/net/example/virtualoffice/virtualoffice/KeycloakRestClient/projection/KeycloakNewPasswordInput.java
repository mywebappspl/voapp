package net.example.virtualoffice.virtualoffice.KeycloakRestClient.projection;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Component
@Getter
@Setter
public class KeycloakNewPasswordInput {
    @NotBlank
    @Size(min=8)
    private String value;
}
