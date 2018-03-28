package ua.pp.bizon.moneyholder.application.datasource;

import java.util.Objects;
import java.util.UUID;

public class Session implements Response {

    private String username;
    private String uuid;


    public Session(){
       uuid = UUID.randomUUID().toString();
    }

    public Session(String uuid){
        this.uuid = uuid;
    }

    public Session setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Session session = (Session) o;
        return Objects.equals(username, session.username) &&
                Objects.equals(uuid, session.uuid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(username, uuid);
    }
}
