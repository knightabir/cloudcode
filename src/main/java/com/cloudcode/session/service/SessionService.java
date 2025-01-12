package com.cloudcode.session.service;

import com.cloudcode.session.model.Session;

import java.util.List;

public interface SessionService {
    List<Session> getAllSessions();

    Session getSessionById(String sessionId);

    List<Session> getSessionByUserId(String userId);

    List<Session> getSessionByProjectId(String projectId);

    Session createSession(Session session);

    Session endSession(String sessionId);

    void deleteSession(String sessionId);
}
