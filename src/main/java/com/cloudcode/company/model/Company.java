package com.cloudcode.company.model;

import com.cloudcode.auth.Model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.security.Timestamp;
import java.util.List;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    private String id;
    private String name;
    private String email;
    private String userId;
    private String phone;
    private String domainName;
    private List<String> allocatedProjects;
    private int cpuCores;
    private int ram;
    private int storage;
    private long createdAt;
    private long updatedAt;

}