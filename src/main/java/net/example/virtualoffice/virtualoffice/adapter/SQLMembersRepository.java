package net.example.virtualoffice.virtualoffice.adapter;

import net.example.virtualoffice.virtualoffice.model.Member;
import net.example.virtualoffice.virtualoffice.repository.MembersRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

interface SQLMembersRepository extends MembersRepository, JpaRepository<Member,Integer> {
    @Query(nativeQuery = true, value = "select name from Members where id=:id")
    String findMemberNameById(int id);

    @Query(nativeQuery = true, value = "select if(count(name)> 0,TRUE ,FALSE) from members where lower(name) like lower(:name) and company_id=:companyId")
    Integer existsByName(String name, int companyId);
    @Query(nativeQuery = true, value = "select id from members where members.active=1 and company_id=:id")
    Optional<List<Integer>> findByCompanyId(int id);
}
