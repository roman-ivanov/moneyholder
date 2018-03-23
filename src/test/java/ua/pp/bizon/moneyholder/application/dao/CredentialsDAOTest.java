package ua.pp.bizon.moneyholder.application.dao;

import org.junit.Test;

public class CredentialsDAOTest {

    @Test
    public void testEncode(){
        System.out.print(CredentialsDAO.encode("password"));
    }
}
