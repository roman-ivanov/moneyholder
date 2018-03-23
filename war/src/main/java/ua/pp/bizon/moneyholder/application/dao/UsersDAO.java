package ua.pp.bizon.moneyholder.application.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import ua.pp.bizon.moneyholder.application.entity.UserEntity;
import ua.pp.bizon.moneyholder.application.datasource.User;
import ua.pp.bizon.moneyholder.application.entity.IUserRepository;

import java.util.ArrayList;
import java.util.List;

@Controller
@Transactional
public class UsersDAO {


    @Autowired
    private IUserRepository repository;

    public User save(User user){
        UserEntity userEntity = new UserEntity(user.getUsername());
        repository.save(userEntity);
        user.setId(userEntity.getId() == null ? -1 : userEntity.getId());
        return user;
    }

    public List<User> getAll() {
        List<UserEntity> userEntities = repository.findAll();
        List<User> users = new ArrayList<>(userEntities.size());
        userEntities.stream().forEach(u -> users.add(new User(u)));
        return users;
    }
}
