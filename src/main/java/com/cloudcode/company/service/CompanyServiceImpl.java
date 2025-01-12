package com.cloudcode.company.service;

import com.cloudcode.auth.Security.CurrentUser;
import com.cloudcode.company.model.Company;
import com.cloudcode.company.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CurrentUser currentUser;

    @Override
    public List<Company> getAllCompanies() {
        try {
            return companyRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Company getCompanyByDomainName(String domainName) {
        try {
            return companyRepository.findByDomainName(domainName).orElseThrow(() -> new RuntimeException("Company not found"));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Company getCompanyById(String id) {
        try {
            return companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Company createCompany(Company company) {
        try {
            company.setUserId(currentUser.getCurrentUser().getId());
            company.setCreatedAt(System.currentTimeMillis());
            return companyRepository.save(company);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Company updateCompany(String id, Company company) {
        try {
            Company existingCompany = companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
            existingCompany.setName(company.getName());
            existingCompany.setEmail(company.getEmail());
            existingCompany.setPhone(company.getPhone());
            existingCompany.setDomainName(company.getDomainName());
            existingCompany.setAllocatedProjects(company.getAllocatedProjects());
            existingCompany.setCpuCores(company.getCpuCores());
            existingCompany.setRam(company.getRam());
            existingCompany.setStorage(company.getStorage());
            existingCompany.setUpdatedAt(System.currentTimeMillis());
            return companyRepository.save(existingCompany);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteCompany(String id) {
        // First check if the company exists on this id or not
        try {
            companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
            companyRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Company getCompanyByUserId(String userId) {
        try {
            return companyRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Company not found"));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
