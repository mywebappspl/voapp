package net.example.virtualoffice.virtualoffice.KeycloakRestClient;

import net.example.virtualoffice.virtualoffice.KeycloakRestClient.projection.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface KeycloakRestClient {
    List<KeycloakReadUserFromRestDTO> getAgentsService();
    KeycloakReadUserFromRestDTO getSingleAgentService(String id);
    ResponseEntity<String> addAgentService(KeycloakRestInputUserFromControllerDTO keycloakRestInputUserFromControllerDTO);
    ResponseEntity<String> changePasswordService(String uid, KeycloakNewPasswordInput keycloakNewPasswordInput);
    ResponseEntity<String> updateAgentService(String uid, KeycloakRestUpdateAgent keycloakRestUpdateAgent);
    ResponseEntity<String> agentStatusService(String uid, KeycloakRestSetStatus keycloakRestSetStatus);
    ResponseEntity<String> agentGroupUpdateService(String uid, AdminStateSetFromInput adminStateSetFromInput);
    AdminStateSetFromInput agentGroupFetchService(String uid);
}
