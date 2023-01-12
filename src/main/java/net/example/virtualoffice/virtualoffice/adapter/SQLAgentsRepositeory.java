package net.example.virtualoffice.virtualoffice.adapter;

import net.example.virtualoffice.virtualoffice.model.Agent;
import net.example.virtualoffice.virtualoffice.repository.AgentsRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface SQLAgentsRepositeory extends AgentsRepository, JpaRepository<Agent,Integer> {
    @Query(nativeQuery = true, value = "select username from agents where id=:id")
    String findAgentUsernameById(int id);

}
