package ua.pp.bizon.moneyholder;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;

import java.io.IOException;
import java.net.URISyntaxException;

public class Authentication extends Endpoint {
    private String prefix = super.prefix + "/authentication";

    public String session(String uuid) throws URISyntaxException, IOException {
        logger.info("/authentication/session?token=" + uuid );
        HttpRequestBase method = new HttpGet();
        URIBuilder uriBuilder = new URIBuilder().setScheme("http").setHost(host).setPort(port).setPath( prefix + "/session");
        uriBuilder.addParameter("token", uuid);
        method.setURI(uriBuilder.build());
        return execute(method);
    }
}
