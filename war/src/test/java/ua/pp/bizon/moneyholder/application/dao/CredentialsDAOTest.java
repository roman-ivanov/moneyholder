package ua.pp.bizon.moneyholder.application.dao;

import org.junit.Assert;
import org.junit.Test;

public class CredentialsDAOTest {

    @Test
    public void testEncode(){
        Assert.assertEquals("X03MO1qnZdYdgyfeuILPmQ==", CredentialsDAO.encode("password"));
    }
}
