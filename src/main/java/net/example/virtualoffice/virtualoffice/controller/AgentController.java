package net.example.virtualoffice.virtualoffice.controller;

import lombok.AllArgsConstructor;
import net.example.virtualoffice.virtualoffice.KeycloakRestClient.KeycloakRestClient;
import net.example.virtualoffice.virtualoffice.KeycloakRestClient.projection.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@AllArgsConstructor
public class AgentController {
    private final KeycloakRestClient keycloakRestClient;

    @GetMapping("/agents")
    public List<KeycloakReadUserFromRestDTO> showCustomers() {
        return keycloakRestClient.getAgentsService();
    }

    @GetMapping("/agents/{id}")
    public KeycloakReadUserFromRestDTO getSingleAgent(@PathVariable @Valid String id) {
        return keycloakRestClient.getSingleAgentService(id);
    }

    @PostMapping("/agents")
    public ResponseEntity<String> addUser(@RequestBody KeycloakRestInputUserFromControllerDTO keycloakRestInputUserFromControllerDTO) {
        return keycloakRestClient.addAgentService(keycloakRestInputUserFromControllerDTO);
    }

    @PutMapping("/agents/{uid}/password")
    public ResponseEntity<String> changePassword(@PathVariable String uid, @RequestBody @Valid KeycloakNewPasswordInput keycloakNewPasswordInput) {
        return keycloakRestClient.changePasswordService(uid, keycloakNewPasswordInput);
    }

    @PutMapping("/agents/{uid}")
    public ResponseEntity<String> updateAgent(@PathVariable String uid, @Valid @RequestBody KeycloakRestUpdateAgent keycloakRestUpdateAgent) {
        return keycloakRestClient.updateAgentService(uid, keycloakRestUpdateAgent);
    }

    @PutMapping("/agents/{uid}/status")
    public ResponseEntity<String> updateAgentStatus(@PathVariable String uid, @Valid @RequestBody KeycloakRestSetStatus keycloakRestSetStatus) {
        return keycloakRestClient.agentStatusService(uid, keycloakRestSetStatus);
    }

    @PutMapping("/agents/{uid}/admin")
    public ResponseEntity<String> updateAgentGroup(@PathVariable String uid, @RequestBody AdminStateSetFromInput adminStateSetFromInput) {
        return keycloakRestClient.agentGroupUpdateService(uid, adminStateSetFromInput);
    }

    @GetMapping("/agents/{uid}/admin")
    public AdminStateSetFromInput getAgentGroup(@PathVariable String uid) {
        return keycloakRestClient.agentGroupFetchService(uid);
    }
}
