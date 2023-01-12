package net.example.virtualoffice.virtualoffice.KeycloakRestClient;

import net.example.virtualoffice.virtualoffice.KeycloakRestClient.projection.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import pl.strefaphp.virtualoffice.virtualoffice.KeycloakRestClient.projection.*;

import java.util.List;
@Component
public interface KeycloakRestClient {
    List<KeycloakReadUserFromRestDTO> GetAgents();
    KeycloakReadUserFromRestDTO GetSingleAgent(String id);
    ResponseEntity<String> AddAgent(KeycloakRestInputUserFromControllerDTO keycloakRestInputUserFromControllerDTO);
    ResponseEntity<String> ChangePassword(String uid, KeycloakNewPasswordInput keycloakNewPasswordInput);
    ResponseEntity<String> UpdateAgent(String uid, KeycloakRestUpdateAgent keycloakRestUpdateAgent);
    ResponseEntity<String> AgentStatus(String uid, KeycloakRestSetStatus keycloakRestSetStatus);
    ResponseEntity<String> AgentGroupUpdate(String uid, AdminStateSetFromInput adminStateSetFromInput);
    AdminStateSetFromInput AgentGroupFetch(String uid);
}
