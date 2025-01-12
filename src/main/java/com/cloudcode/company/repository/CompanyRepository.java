package com.cloudcode.company.repository;

import com.cloudcode.company.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CompanyRepository extends MongoRepository<Company, String> {
    Optional<Company> findByDomainName(String domainName);
    Optional<Company> findByUserId(String userId);
}
