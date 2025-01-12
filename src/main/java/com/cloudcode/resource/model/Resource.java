package com.cloudcode.resource.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Resource {

    @Id
    private String id;
    private String containerId;
    private String userId;
    private String projectId;
    private String companyId;
    private boolean isActive;
    private String editorUrl;
}
