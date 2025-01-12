package com.cloudcode.session.repository;

import com.cloudcode.session.model.Session;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SessionRepository extends MongoRepository<Session, String> {
    List<Session> findByUserId(String userId); // Add this method to retrieve sessions by user ID

    List<Session> findByProjectId(String projectId); // Add this method to retrieve sessions by project ID

    List<Session> findByCompanyId(String companyId); // Add this method to retrieve sessions by company ID
}
