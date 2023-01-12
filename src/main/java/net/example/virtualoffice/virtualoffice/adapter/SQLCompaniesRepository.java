package net.example.virtualoffice.virtualoffice.adapter;

import net.example.virtualoffice.virtualoffice.model.Company;
import net.example.virtualoffice.virtualoffice.repository.CompaniesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

interface SQLCompaniesRepository extends CompaniesRepository, JpaRepository<Company, Integer> {
    @Query(nativeQuery = true, value = "select company_name from companies where id=:id")
    String findCompanyNameById(int id);
    @Query(nativeQuery = true, value = "select if(count(company_name)> 0,TRUE ,FALSE) from companies where lower(company_name) like lower(:companyName)")
    Integer existsByCompanyName(String companyName);
    @Query(nativeQuery = true, value = "select * from companies where active=:active")
    List<Company> findCompanyByStatus(boolean active);



}
