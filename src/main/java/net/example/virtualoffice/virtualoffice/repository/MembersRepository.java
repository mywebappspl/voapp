package net.example.virtualoffice.virtualoffice.repository;

import net.example.virtualoffice.virtualoffice.model.Member;

import java.util.List;
import java.util.Optional;

public interface MembersRepository {
    Member save(Member Entity);
    String findMemberNameById(int id);
    Integer existsByName(String name, int companyId);
    Optional<Member> findById(int id);
    Optional<List<Integer>> findByCompanyId(int id);
}
