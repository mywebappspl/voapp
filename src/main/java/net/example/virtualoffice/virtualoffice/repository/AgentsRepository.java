package net.example.virtualoffice.virtualoffice.repository;

import net.example.virtualoffice.virtualoffice.model.Agent;

public interface AgentsRepository {
    String findAgentUsernameById(int id);
    Agent save(Agent Entity);
}
