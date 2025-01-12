package com.cloudcode.company.service;

import com.cloudcode.company.model.Company;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();

    Company getCompanyByDomainName(String domainName);

    Company getCompanyById(String id);

    Company createCompany(Company company);

    Company updateCompany(String id, Company company);

    void deleteCompany(String id);
    Company getCompanyByUserId(String userId);
}
