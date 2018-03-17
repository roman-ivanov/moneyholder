package ua.pp.bizon.moneyholder.application.datasource;

import ua.pp.bizon.moneyholder.application.Entity.UserEntity;

public class User {
    private String username;
    private long id;

    public User() {};

    public User(UserEntity u) {
        username = u.getName();
        id = u.getId();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
