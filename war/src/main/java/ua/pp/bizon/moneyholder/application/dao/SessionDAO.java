package ua.pp.bizon.moneyholder.application.dao;

import org.springframework.stereotype.Component;
import ua.pp.bizon.moneyholder.application.datasource.Session;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionDAO {

    private Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    public void add(Session session) {
        sessionMap.put(session.getUuid(), session);
    }

    public boolean exist(Session session){
        return sessionMap.get(session.getUuid()) == null ? false :
                sessionMap.get(session.getUuid()).equals(session);
    }

    public void remove(Session session) {
        sessionMap.remove(session.getUuid(), session);
    }

    public Session get(String token) {
        return sessionMap.get(token);
    }
}
