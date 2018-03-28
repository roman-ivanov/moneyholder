package ua.pp.bizon.moneyholder;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

public class ClientApplication extends  Endpoint {

    public UsersEndpoint users(){
        return new UsersEndpoint();
    }
    public Authentication authentication() { return new Authentication();}

    public String login(String username, String password) throws IOException, URISyntaxException {
        logger.info("login?username=" + username + "&password=" + password);
        HttpRequestBase method = new HttpGet();
        URIBuilder uriBuilder = new URIBuilder().setScheme("http").setHost(host).setPort(port).setPath( super.prefix + "/login");
        uriBuilder.addParameter("username", username);
        uriBuilder.addParameter("password", password);
        method.setURI(uriBuilder.build());
        return execute(method);
    }
}
