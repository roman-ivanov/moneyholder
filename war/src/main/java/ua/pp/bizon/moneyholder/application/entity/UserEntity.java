package ua.pp.bizon.moneyholder.application.entity;

import ua.pp.bizon.moneyholder.application.datasource.User;

import javax.persistence.*;

@Entity()
public class UserEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Not perfect!! Should be a proper object!
    protected UserEntity() {}
    public UserEntity(String name) {
        super();
        this.name = name;
    }

    public UserEntity(User user){
        super();
        this.name = user.getUsername();
        this.id = user.getId();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
