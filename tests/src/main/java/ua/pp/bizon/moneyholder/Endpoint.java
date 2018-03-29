package ua.pp.bizon.moneyholder;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ResourceBundle;

public abstract class Endpoint {

    protected Logger logger = LogManager.getLogger(getClass());

    protected String host;
    protected int port;
    protected String prefix;
    protected String scheme;

    protected CloseableHttpClient client;

    protected Endpoint(CloseableHttpClient client){
        this.client = client;
    }

    {
        ResourceBundle config = ResourceBundle.getBundle("application");
        host = config.getString("host");
        port = Integer.parseInt(config.getString("port"));
        prefix = config.getString("prefix");
        scheme = config.getString("scheme");

    }


    protected String execute(HttpRequestBase method) throws IOException {
        if (client == null){
            client = HttpClientBuilder.create().setDefaultCookieStore(new BasicCookieStore()).build();
        }

            try (CloseableHttpResponse response = client.execute(method)) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    String stringResponse = EntityUtils.toString(response.getEntity(), "utf-8");
                    if (logger.isTraceEnabled()) {
                        logger.trace("http call response: " + stringResponse);
                    }
                    for (Header h:response.getAllHeaders()) {
                        logger.trace("header:" + h);
                    }

                    return stringResponse;
                } else {
                    StringBuilder builderError = new StringBuilder();
                    builderError.append("http call: " + method + "\n");
                    if (method instanceof HttpPost) {
                        builderError.append("http call POST entity: " + ((HttpPost) method).getEntity() + "\n");
                    }
                    builderError.append("http call response error:" + response.getStatusLine().getStatusCode() + ":" + response.getStatusLine().getReasonPhrase());
                    if (response.getEntity().getContentLength() > 0) {
                        builderError.append(IOUtils.toString(response.getEntity().getContent(), "UTF-8")).append(":");
                    }
                    logger.error(builderError.toString());
                    throw new RuntimeException(builderError.toString());
                }
            }

    }

    protected URIBuilder getUriBuilder() {
        return new URIBuilder().setScheme(scheme).setHost(host).setPort(port);
    }
}
