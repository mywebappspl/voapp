package net.example.virtualoffice.virtualoffice.services;

import net.example.virtualoffice.virtualoffice.KeycloakRestClient.projection.KeycloakReadUserFromRestDTO;
import net.example.virtualoffice.virtualoffice.model.Agent;
import net.example.virtualoffice.virtualoffice.repository.AgentsRepository;
import org.springframework.stereotype.Service;

@Service
public class AgentService {
    private AgentsRepository agentsRepository;

    AgentService(final AgentsRepository agentsRepository) {
        this.agentsRepository = agentsRepository;
    }

    public void addAgent(KeycloakReadUserFromRestDTO newAgent) {
        Agent agent = new Agent();
        agent.setName(newAgent.getFirstName() + ' ' + newAgent.getLastName());
        agent.setUsername(newAgent.getUsername());
        agent.setKeycloakId(newAgent.getId());
        agentsRepository.save(agent);
    }
}
