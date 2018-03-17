package ua.pp.bizon.moneyholder.application.Entity;

import javax.persistence.*;

@Entity()
public class UserEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name; // Not perfect!! Should be a proper object!
    protected UserEntity() {}
    public UserEntity(String name) {
        super();
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
