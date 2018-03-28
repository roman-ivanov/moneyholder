package ua.pp.bizon.moneyholder;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;

public class UsersEndpoint extends Endpoint {

    private Logger logger = LogManager.getLogger(UsersEndpoint.class);
    protected String prefix = super.prefix +  "/users";


    public String register(String username, String password) throws URISyntaxException, IOException {
        logger.info("users/register?username=" + username + "&password=" + password);
        HttpRequestBase method = new HttpGet();
        URIBuilder uriBuilder = getUriBuilder().setPath(prefix + "/register");
        uriBuilder.addParameter("username", username);
        uriBuilder.addParameter("password", password);
        method.setURI(uriBuilder.build());
        return execute(method);

    }


    public String deregister(String username, String password)throws URISyntaxException, IOException  {
        logger.info("users/deregister?username=" + username + "&password=" + password);
        HttpRequestBase method = new HttpGet();
        URIBuilder uriBuilder = getUriBuilder().setPath(prefix + "/deregister");
        uriBuilder.addParameter("username", username);
        uriBuilder.addParameter("password", password);
        method.setURI(uriBuilder.build());
        return execute(method);

    }
}
