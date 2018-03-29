package ua.pp.bizon.moneyholder;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

public class ClientApplication extends  Endpoint {

    protected ClientApplication(CloseableHttpClient client) {
        super(client);
    }

    public ClientApplication() {
        this(HttpClientBuilder.create().setDefaultCookieStore(new BasicCookieStore()).build());
        users = new UsersEndpoint(client);
        authentication = new Authentication(client);
    }

    public UsersEndpoint users(){
        return users;
    }
    public Authentication authentication() { return authentication;}

    public UsersEndpoint users;
    public Authentication authentication;




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
