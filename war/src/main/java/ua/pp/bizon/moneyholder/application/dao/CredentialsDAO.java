package ua.pp.bizon.moneyholder.application.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import ua.pp.bizon.moneyholder.application.datasource.User;
import ua.pp.bizon.moneyholder.application.entity.Credential;
import ua.pp.bizon.moneyholder.application.entity.ICredentialRepository;
import ua.pp.bizon.moneyholder.application.entity.UserEntity;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Controller
@Transactional
public class CredentialsDAO {

    @Autowired
    private ICredentialRepository repository;


    public void setPassword(User user, String password) {
            setPassword(new UserEntity(user), password);
    }

    public void setPassword(UserEntity user, String password) {
        Credential c = null;
        try {
            c = repository.findByUser(user);
        } catch (EntityNotFoundException ex){

        }
        if (c == null) {
            c = new Credential();
            c.setUser(user);
            c.setAlgorithm(1);
        }
        c.setPassword(encode(password));
        repository.saveAndFlush(c);
    }

    public boolean checkPassword(User user, String password) {
        return checkPassword(new UserEntity(user), password);
    }

   public boolean checkPassword(UserEntity user, String password) {
        Credential c = repository.findByUser(user);
        return c != null && (((c.getPassword().equals(encode(password))) && c.getAlgorithm() == 1) ||
                (password.equals(c.getPassword()) && c.getAlgorithm() == 0));
    }


    static String encode(String password) {
        try {
            byte[] bytesOfMessage = password == null ? new byte[0] : password.getBytes("UTF-8");
            return Base64.getEncoder().encodeToString(MessageDigest.getInstance("MD5").digest(bytesOfMessage));
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return password;
    }

    public void delete(UserEntity e) {
        Credential c = repository.findByUser(e);
        if (c!= null){
            repository.delete(c);
        }
    }
}
