package com.learn.shirologin.session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Serializable;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class UserSessionRepository extends CachingSessionDAO {

    private final JdbcTemplate jdbcTemplate;

    @Override
    protected void doUpdate(Session session) {
        if(session instanceof ValidatingSession && !((ValidatingSession)session).isValid()) {
            return;
        }
        String sql = "update sessions set session=? where id=?";
        jdbcTemplate.update(sql, SessionSerializable.serialize(session), session.getId());
    }

    @Override
    protected void doDelete(Session session) {
        String sql = "delete from sessions where id=?";
        jdbcTemplate.update(sql, session.getId());
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = generateSessionId(session);
        assignSessionId(session, sessionId);
        String sql = "insert into sessions(id, session) values(?,?)";
        jdbcTemplate.update(sql, sessionId, SessionSerializable.serialize(session));
        return session.getId();
    }

    @Override
    protected Session doReadSession(Serializable serializable) {
        String sql = "select session from sessions where id=?";
        List<String> sessionStrList = jdbcTemplate.queryForList(sql, String.class, serializable);
        if(sessionStrList.size() == 0) return null;
        return SessionSerializable.deserialize(sessionStrList.get(0));
    }
}
