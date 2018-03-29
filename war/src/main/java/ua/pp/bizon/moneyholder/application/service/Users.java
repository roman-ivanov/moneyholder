package ua.pp.bizon.moneyholder.application.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.pp.bizon.moneyholder.application.datasource.Error;
import ua.pp.bizon.moneyholder.application.datasource.OK;
import ua.pp.bizon.moneyholder.application.datasource.Response;


import java.util.*;

@RestController
@RequestMapping("/users")
public class Users {

    private Logger logger = LogManager.getLogger(getClass());

    public Users(){
        logger.info("Users started");
    }

    @Autowired
    private UserDetailsManager userDetailsManager;

    @RequestMapping("/hello")
    public String hello(){
        return "Hello! ";
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
        logger.info("register: username: " + username + ", password:" + password);
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority("all"));
        if (userDetailsManager.userExists(username)){
            return ResponseEntity.badRequest().body(new Error("username \"" + username + "\" already exist"));
        }
        userDetailsManager.createUser(new org.springframework.security.core.userdetails.User(username, password, authorities));
        return ResponseEntity.ok(new OK());
    }


    @RequestMapping("deregister")
    @Transactional
    public ResponseEntity<Response> deregister(@RequestParam("username") String username, @RequestParam("password") String password){
        logger.info("deregister: username: " + username + ", password:" + password);
        if (!userDetailsManager.userExists(username)){
            return ResponseEntity.badRequest().body(new Error("username \"" + username + "\" not exist"));
        } else if (userDetailsManager.loadUserByUsername(username).getPassword().equals(password)){
            userDetailsManager.deleteUser(username);
            return ResponseEntity.ok(new OK());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Error("password not correct for user " + username));
        }
    }
}
