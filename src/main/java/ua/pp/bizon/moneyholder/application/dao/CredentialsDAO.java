package ua.pp.bizon.moneyholder.application.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import ua.pp.bizon.moneyholder.application.entity.Credential;
import ua.pp.bizon.moneyholder.application.entity.ICredentialRepository;

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

    void setPassword(long userid, String password) {
        Credential c = repository.findByUserId(userid);

    }

    boolean checkPassword(long userid, String password) {
        return false;
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
}
