package net.example.virtualoffice.virtualoffice.controller;

import net.example.virtualoffice.virtualoffice.KeycloakRestClient.KeycloakRestClient;
import net.example.virtualoffice.virtualoffice.KeycloakRestClient.projection.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.strefaphp.virtualoffice.virtualoffice.KeycloakRestClient.projection.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AgentController {
    private KeycloakRestClient keycloakRestClient;

    public AgentController(final KeycloakRestClient keycloakRestClient) {
        this.keycloakRestClient = keycloakRestClient;
    }

    @GetMapping("/agents")
    public List<KeycloakReadUserFromRestDTO> showCustomers() {
        return keycloakRestClient.GetAgents();
    }

    @GetMapping("/agents/{id}")
    public KeycloakReadUserFromRestDTO getSingleAgent(@PathVariable @Valid String id) {
        return keycloakRestClient.GetSingleAgent(id);
    }

    @PostMapping("/agents")
    public ResponseEntity<String> AddUser(@RequestBody KeycloakRestInputUserFromControllerDTO keycloakRestInputUserFromControllerDTO) {
        return keycloakRestClient.AddAgent(keycloakRestInputUserFromControllerDTO);
    }

    @PutMapping("/agents/{uid}/password")
    public ResponseEntity<String> changePassword(@PathVariable String uid, @RequestBody @Valid KeycloakNewPasswordInput keycloakNewPasswordInput) {
        return keycloakRestClient.ChangePassword(uid, keycloakNewPasswordInput);
    }

    @PutMapping("/agents/{uid}")
    public ResponseEntity<String> updateAgent(@PathVariable String uid, @Valid @RequestBody KeycloakRestUpdateAgent keycloakRestUpdateAgent) {
        return keycloakRestClient.UpdateAgent(uid, keycloakRestUpdateAgent);
    }

    @PutMapping("/agents/{uid}/status")
    public ResponseEntity<String> updateAgentStatus(@PathVariable String uid, @Valid @RequestBody KeycloakRestSetStatus keycloakRestSetStatus) {
        return keycloakRestClient.AgentStatus(uid, keycloakRestSetStatus);
    }

    @PutMapping("/agents/{uid}/admin")
    public ResponseEntity<String> updateAgentGroup(@PathVariable String uid, @RequestBody AdminStateSetFromInput adminStateSetFromInput) {
        return keycloakRestClient.AgentGroupUpdate(uid, adminStateSetFromInput);
    }

    @GetMapping("/agents/{uid}/admin")
    public AdminStateSetFromInput getAgentGroup(@PathVariable String uid) {
        return keycloakRestClient.AgentGroupFetch(uid);
    }
}
