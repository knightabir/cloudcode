package com.cloudcode.session.service;

import com.cloudcode.auth.Security.CurrentUser;
import com.cloudcode.session.model.Session;
import com.cloudcode.session.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private CurrentUser currentUser;

    @Override
    public List<Session> getAllSessions() {
        try {
            return sessionRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Session getSessionById(String sessionId) {
        try {
            return sessionRepository.findById(sessionId).orElseThrow(() -> new RuntimeException("Session not found"));
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Session> getSessionByUserId(String userId) {
        try {
            return sessionRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Session> getSessionByProjectId(String projectId) {
        try {
            return sessionRepository.findByProjectId(projectId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Session createSession(Session session) {
        try {
            session.setStartTime(System.currentTimeMillis());
            session.setUserId(currentUser.getCurrentUser().getId());
            session.setCompanyId(currentUser.getCurrentUserCompany().getId());
            return sessionRepository.save(session);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Session endSession(String sessionId) {
        try {
            Session session = getSessionById(sessionId);
            session.setEndTime(System.currentTimeMillis());
            return sessionRepository.save(session);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteSession(String sessionId) {
        try {
            Session existingSession = sessionRepository.findById(sessionId).orElseThrow(() -> new RuntimeException("Session not found"));
            if (existingSession != null) {
                sessionRepository.delete(existingSession);
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
