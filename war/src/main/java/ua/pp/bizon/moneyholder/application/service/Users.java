package ua.pp.bizon.moneyholder.application.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ua.pp.bizon.moneyholder.application.dao.CredentialsDAO;
import ua.pp.bizon.moneyholder.application.dao.UsersDAO;
import ua.pp.bizon.moneyholder.application.datasource.Error;
import ua.pp.bizon.moneyholder.application.datasource.OK;
import ua.pp.bizon.moneyholder.application.datasource.Response;
import ua.pp.bizon.moneyholder.application.datasource.User;
import ua.pp.bizon.moneyholder.application.entity.ICredentialRepository;
import ua.pp.bizon.moneyholder.application.entity.UserEntity;

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
    private UsersDAO usersDAO;

    @Autowired
    private CredentialsDAO credentialsDAO;



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
        user = usersDAO.save(user);
        credentialsDAO.setPassword(user, "");
        return user;
    }


    @RequestMapping("/setPassword")
    public @ResponseBody Response setPassword(@RequestParam(value="name") String name, @RequestParam(value="password") String password, @RequestParam(value="oldPassword") String oldPassword){
        User e = usersDAO.getByName(name);
        if (e != null && credentialsDAO.checkPassword(e, oldPassword)){
            credentialsDAO.setPassword(e, password);
            return new OK();
        }
        return new Error("wrong username or password");
    }

    /*
    all:
        register
        resetPassword
        deregister
    user:
        updatePassword
        updateInfo

    admin:
        list
        search
        block
        unblock
        resetPasswordForUser
    * */

    @RequestMapping("register")
    public ResponseEntity<Response> register(@RequestParam("username") String username, @RequestParam("password") String password){
        User e = usersDAO.getByName(username);
        if (e != null){
            ResponseEntity.badRequest().body(new Error("username \"" + username + "\" already exist"));
        }
        e = new User();
        e.setUsername(username);
        e = usersDAO.save(e);
        credentialsDAO.setPassword(e, password);
        return ResponseEntity.ok(new OK());
    }


    @RequestMapping("deregister")
    public ResponseEntity<Response> deregister(@RequestParam("username") String username, @RequestParam("password") String password){
        UserEntity e = usersDAO.getEntityByName(username);
        if (e == null){
            return ResponseEntity.badRequest().body(new Error("username \"" + username + "\" not exist"));
        } else if (credentialsDAO.checkPassword(e, password)){
            credentialsDAO.delete(e);
            usersDAO.delete(e);
            return ResponseEntity.ok(new OK());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Error("password not correct for user " + username));
        }
    }
}
