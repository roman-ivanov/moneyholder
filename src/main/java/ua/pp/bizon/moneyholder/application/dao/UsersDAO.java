package ua.pp.bizon.moneyholder.application.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.pp.bizon.moneyholder.application.Entity.UserEntity;
import ua.pp.bizon.moneyholder.application.Entity.UserRepository;
import ua.pp.bizon.moneyholder.application.datasource.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class UsersDAO {


    @Autowired
    private UserRepository repository;

    public User save(User user){
        UserEntity userEntity = new UserEntity(user.getUsername());
        repository.save(userEntity);
        user.setId(userEntity.getId());
        return user;
    }

    public List<User> getAll() {
        List<UserEntity> userEntities = repository.findAll();
        List<User> users = new ArrayList<>(userEntities.size());
        userEntities.stream().forEach(u -> users.add(new User(u)));
        return users;
    }
}
