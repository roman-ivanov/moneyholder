package ua.pp.bizon.moneyholder.application.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ua.pp.bizon.moneyholder.application.dao.UsersDAO;
import ua.pp.bizon.moneyholder.application.datasource.User;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class Users {

    private Logger logger = LogManager.getLogger(Users.class);

    public Users(){
        logger.info("Users started");
    }

    @Autowired
    UsersDAO usersDAO;

    @RequestMapping("/hello")
    public String hello(){
        return "Hello! ";
    }


    @RequestMapping("/list")
    public @ResponseBody List<User> list(){
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
