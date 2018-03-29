package ua.pp.bizon.moneyholder.tests;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.pp.bizon.moneyholder.ClientApplication;

import java.io.IOException;
import java.net.URISyntaxException;

public class Session {

    protected Logger logger = LogManager.getLogger(getClass());


    protected static final String USERNAME = "vasya";
    protected static final String PASSWORD = "password";
    private ClientApplication clientApplication;

    @Before
    public void before(){
        clientApplication = new ClientApplication();
    }

    @Test
    public void testRegister() throws IOException, URISyntaxException {
        try {
            Assert.assertEquals("{\"ok\":\"OK\"}", clientApplication.users().register(USERNAME, PASSWORD));

        }finally {
            Assert.assertEquals("{\"ok\":\"OK\"}", clientApplication.users().deregister(USERNAME, PASSWORD));
        }

    }


    @Test
    public void testSession() throws IOException, URISyntaxException {
        try {
            Assert.assertEquals("{\"ok\":\"OK\"}", clientApplication.users().register(USERNAME, PASSWORD));
            String response = clientApplication.login(USERNAME, PASSWORD);
            logger.debug("response:" + response);
            String session = clientApplication.authentication().session();
            logger.debug("session:" + session);
        }finally {
            Assert.assertEquals("{\"ok\":\"OK\"}", clientApplication.users().deregister(USERNAME, PASSWORD));
        }
    }



    @Test(expected = java.lang.RuntimeException.class)
    public void testWrongCredentials() throws IOException, URISyntaxException {
        try {
            Assert.assertEquals("{\"ok\":\"OK\"}", clientApplication.users().register(USERNAME, PASSWORD));
            String response = clientApplication.login(USERNAME, PASSWORD + "233");
            logger.debug("response:" + response);
        }finally {
            Assert.assertEquals("{\"ok\":\"OK\"}", clientApplication.users().deregister(USERNAME, PASSWORD));
        }
    }
}
