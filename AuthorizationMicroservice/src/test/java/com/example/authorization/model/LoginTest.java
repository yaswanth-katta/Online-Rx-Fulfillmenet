package com.example.authorization.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LoginTest {

    @Test
    void testConstructor() {
        Login actualLogin = new Login();
        actualLogin.setPassword("iloveyou");
        actualLogin.setUserName("janedoe");
        String actualToStringResult = actualLogin.toString();
        assertEquals("iloveyou", actualLogin.getPassword());
        assertEquals("janedoe", actualLogin.getUserName());

    }
}

