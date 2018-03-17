package ua.pp.bizon.moneyholder.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.pp.bizon.moneyholder.application.dao.UsersDAO;
import ua.pp.bizon.moneyholder.application.datasource.User;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class Users {

    @Autowired
    UsersDAO usersDAO;

    @RequestMapping("/list")
    public List<User> list(){
        return usersDAO.getAll();
    }


    @RequestMapping("/create")
    public User create(@RequestParam(value="name") String name){
        User user = new User();
        user.setUsername(name);
        usersDAO.save(user);
        return user;
    }



}
