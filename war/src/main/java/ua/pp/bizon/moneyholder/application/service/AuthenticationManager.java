package ua.pp.bizon.moneyholder.application.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import ua.pp.bizon.moneyholder.application.dao.CredentialsDAO;
import ua.pp.bizon.moneyholder.application.dao.SessionDAO;
import ua.pp.bizon.moneyholder.application.dao.UsersDAO;
import ua.pp.bizon.moneyholder.application.datasource.Error;
import ua.pp.bizon.moneyholder.application.datasource.Response;
import ua.pp.bizon.moneyholder.application.datasource.Session;
import ua.pp.bizon.moneyholder.application.datasource.User;
import ua.pp.bizon.moneyholder.application.entity.UserEntity;

@RestController
public class AuthenticationManager {

    @Autowired
    private UsersDAO users;
    @Autowired
    private CredentialsDAO credentials;
    @Autowired
    private SessionDAO session;

    @RequestMapping("/login")
    public Response login(@RequestParam(value="username")String user, @RequestParam(value="password") String password){
        UserEntity u = users.getEntityByName(user);
        if (u != null) {
            if (credentials.checkPassword(u, password)){
                Session session = new Session();
                session.setUsername(user);
                this.session.add(session);
                return session;
            }
        }
        return new Error("wrong username or password");
    }


    @RequestMapping("/logout")
    public void logout(@RequestParam(value="username")String user, @RequestParam(value="token") String token){
            session.remove(new Session(token).setUsername(user));
    }


    @RequestMapping("/authentication/session")
    public @ResponseBody Response session(@RequestParam(value="token") String token){
        Session session = this.session.get(token);
        return session == null ? new Error("no active session") : session;
    }
}
