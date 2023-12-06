package com.example.authorization.controller;

import com.example.authorization.model.Login;
import com.example.authorization.model.Member;
import com.example.authorization.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {AuthorizationController.class})
@ExtendWith(SpringExtension.class)
@WebMvcTest(AuthorizationController.class)
class AuthorizationControllerTest {
    @MockBean
    private AuthService authService;

    @Autowired
    private AuthorizationController authorizationController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testLogin() throws Exception {
        when(authService.login((Login) any())).thenReturn("Login");

        Login login = new Login();
        login.setPassword("iloveyou");
        login.setUserName("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(login);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(authorizationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Login"));
    }

    /**
     * Method under test: {@link AuthorizationController#validateToken(String)}
     */
    @Test
    void testValidateToken() throws Exception {
        when(authService.validateToken((String) any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/validateToken")
                .header("Authorization", "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==");
        ResultActions resultActions = MockMvcBuilders.standaloneSetup(authorizationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"));
        ContentResultMatchers contentResult = MockMvcResultMatchers.content();
        resultActions.andExpect(contentResult.string(Boolean.TRUE.toString()));
    }

    /**
     * Method under test: {@link AuthorizationController#registerMember(Member)}
     */
    @Test
    void testRegisterMember() throws Exception {
        Member member = new Member();
        member.setAddress("42 Main St");
        member.setAge(1);
        member.setContactNumber(1L);
        member.setEmail("jane.doe@example.org");
        member.setFullName("Dr Jane Doe");
        member.setGender("Gender");
        member.setMemberId(123L);
        member.setPassword("iloveyou");
        when(authService.registerMember((Member) any())).thenReturn(member);

        Member member1 = new Member();
        member1.setAddress("42 Main St");
        member1.setAge(1);
        member1.setContactNumber(1L);
        member1.setEmail("jane.doe@example.org");
        member1.setFullName("Dr Jane Doe");
        member1.setGender("Gender");
        member1.setMemberId(123L);
        member1.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(member1);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/registerMember")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(authorizationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"memberId\":123,\"email\":\"jane.doe@example.org\",\"fullName\":\"Dr Jane Doe\",\"gender\":\"Gender\",\"age\":1,"
                                        + "\"contactNumber\":1,\"password\":\"iloveyou\",\"address\":\"42 Main St\"}"));
    }

    /**
     * Method under test: {@link AuthorizationController#getMemberByUserName(String)}
     */
    @Test
    void testGetMemberByUserName() throws Exception {
        Member member = new Member();
        member.setAddress("42 Main St");
        member.setAge(1);
        member.setContactNumber(1L);
        member.setEmail("jane.doe@example.org");
        member.setFullName("Dr Jane Doe");
        member.setGender("Gender");
        member.setMemberId(123L);
        member.setPassword("iloveyou");
        when(authService.getMemberByUserName((String) any())).thenReturn(member);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getMemberByUserName/{userName}",
                "janedoe");
        MockMvcBuilders.standaloneSetup(authorizationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"memberId\":123,\"email\":\"jane.doe@example.org\",\"fullName\":\"Dr Jane Doe\",\"gender\":\"Gender\",\"age\":1,"
                                        + "\"contactNumber\":1,\"password\":\"iloveyou\",\"address\":\"42 Main St\"}"));
    }

    /**
     * Method under test: {@link AuthorizationController#getAllMembers()}
     */
    @Test
    void testGetAllMembers() throws Exception {
        when(authService.getAllMembers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getAllMembers");
        MockMvcBuilders.standaloneSetup(authorizationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link AuthorizationController#getAllMembers()}
     */
    @Test
    void testGetAllMembers2() throws Exception {
        when(authService.getAllMembers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/getAllMembers");
        getResult.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(authorizationController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }
}

