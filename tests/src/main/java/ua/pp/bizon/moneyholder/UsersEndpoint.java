package ua.pp.bizon.moneyholder;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;

public class UsersEndpoint extends Endpoint {

    private Logger logger = LogManager.getLogger(UsersEndpoint.class);
    protected String prefix = super.prefix +  "/users";

    protected UsersEndpoint(CloseableHttpClient client) {
        super(client);
    }


    public String register(String username, String password) throws URISyntaxException, IOException {
        logger.info("users/register?username=" + username + "&password=" + password);
        HttpPost method = new HttpPost();
        URIBuilder uriBuilder = getUriBuilder().setPath(prefix + "/register");
        method.setEntity(EntityBuilder.create().setParameters(
                new BasicNameValuePair("username", username),
                new BasicNameValuePair("password", password),
                new BasicNameValuePair("submit", "Login")).build());

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
