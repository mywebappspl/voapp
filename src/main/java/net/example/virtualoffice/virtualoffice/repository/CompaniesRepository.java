package net.example.virtualoffice.virtualoffice.repository;

import net.example.virtualoffice.virtualoffice.model.Company;

import java.util.List;
import java.util.Optional;

public interface CompaniesRepository {
    Company save(Company Entity);
    Optional<Company> findById(int id);
    void delete(Company company);
    List<Company> findAll();
    List<Company> findActiveById(int id);
    List<Company> findCompanyByStatus(boolean active);
    String findCompanyNameById(int id);
    Integer existsByCompanyName(String companyName);
    Boolean existsById(int id);

}
