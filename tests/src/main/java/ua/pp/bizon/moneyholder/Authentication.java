package ua.pp.bizon.moneyholder;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.net.URISyntaxException;

public class Authentication extends Endpoint {
    private String prefix = super.prefix + "/authentication";

    protected Authentication(CloseableHttpClient client) {
        super(client);
    }

    public String session() throws URISyntaxException, IOException {
        logger.info("/authentication/session" );
        HttpRequestBase method = new HttpGet();
        URIBuilder uriBuilder = new URIBuilder().setScheme("http").setHost(host).setPort(port).setPath( prefix + "/session");
        method.setURI(uriBuilder.build());
        return execute(method);
    }
}
