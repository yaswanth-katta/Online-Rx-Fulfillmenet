package com.example.authorization.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.authorization.config.JwtUtil;
import com.example.authorization.exception.AuthException;
import com.example.authorization.model.Login;
import com.example.authorization.model.Member;
import com.example.authorization.repository.AuthRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthService.class})
@ExtendWith(SpringExtension.class)
class AuthServiceTest {
    @MockBean
    private JavaMailSender javaMailSender;

    @MockBean
    private AuthRepo authRepo;

    @Autowired
    private AuthService authService;

    @MockBean
    private JwtUtil jwtUtil;


    @Test
    void testLogin() throws AuthException {
        Member member = new Member();
        member.setAddress("42 Main St");
        member.setAge(1);
        member.setContactNumber(1L);
        member.setEmail("jane.doe@example.org");
        member.setFullName("Dr Jane Doe");
        member.setGender("Gender");
        member.setMemberId(123L);
        member.setPassword("iloveyou");
        Optional<Member> ofResult = Optional.of(member);
        when(authRepo.findByEmail((String) any())).thenReturn(ofResult);
        when(jwtUtil.generateToken((Login) any())).thenReturn("ABC123");

        Login login = new Login();
        login.setPassword("iloveyou");
        login.setUserName("janedoe");
        assertEquals("Bearer ABC123", authService.login(login));
        verify(authRepo).findByEmail((String) any());
        verify(jwtUtil).generateToken((Login) any());
    }


    @Test
    void testLogin2() throws AuthException {
        Member member = mock(Member.class);
        when(member.getPassword()).thenReturn("foo");
        doNothing().when(member).setAddress((String) any());
        doNothing().when(member).setAge(anyInt());
        doNothing().when(member).setContactNumber(anyLong());
        doNothing().when(member).setEmail((String) any());
        doNothing().when(member).setFullName((String) any());
        doNothing().when(member).setGender((String) any());
        doNothing().when(member).setMemberId((Long) any());
        doNothing().when(member).setPassword((String) any());
        member.setAddress("42 Main St");
        member.setAge(1);
        member.setContactNumber(1L);
        member.setEmail("jane.doe@example.org");
        member.setFullName("Dr Jane Doe");
        member.setGender("Gender");
        member.setMemberId(123L);
        member.setPassword("iloveyou");
        Optional<Member> ofResult = Optional.of(member);
        when(authRepo.findByEmail((String) any())).thenReturn(ofResult);
        when(jwtUtil.generateToken((Login) any())).thenReturn("ABC123");

        Login login = new Login();
        login.setPassword("iloveyou");
        login.setUserName("janedoe");
        assertThrows(AuthException.class, () -> authService.login(login));
        verify(authRepo).findByEmail((String) any());
        verify(member).getPassword();
        verify(member).setAddress((String) any());
        verify(member).setAge(anyInt());
        verify(member).setContactNumber(anyLong());
        verify(member).setEmail((String) any());
        verify(member).setFullName((String) any());
        verify(member).setGender((String) any());
        verify(member).setMemberId((Long) any());
        verify(member).setPassword((String) any());
    }


    @Test
    void testLogin3() throws AuthException {
        when(authRepo.findByEmail((String) any())).thenReturn(Optional.empty());
        Member member = mock(Member.class);
        when(member.getPassword()).thenReturn("iloveyou");
        doNothing().when(member).setAddress((String) any());
        doNothing().when(member).setAge(anyInt());
        doNothing().when(member).setContactNumber(anyLong());
        doNothing().when(member).setEmail((String) any());
        doNothing().when(member).setFullName((String) any());
        doNothing().when(member).setGender((String) any());
        doNothing().when(member).setMemberId((Long) any());
        doNothing().when(member).setPassword((String) any());
        member.setAddress("42 Main St");
        member.setAge(1);
        member.setContactNumber(1L);
        member.setEmail("jane.doe@example.org");
        member.setFullName("Dr Jane Doe");
        member.setGender("Gender");
        member.setMemberId(123L);
        member.setPassword("iloveyou");
        when(jwtUtil.generateToken((Login) any())).thenReturn("ABC123");

        Login login = new Login();
        login.setPassword("iloveyou");
        login.setUserName("janedoe");
        assertThrows(AuthException.class, () -> authService.login(login));
        verify(authRepo).findByEmail((String) any());
        verify(member).setAddress((String) any());
        verify(member).setAge(anyInt());
        verify(member).setContactNumber(anyLong());
        verify(member).setEmail((String) any());
        verify(member).setFullName((String) any());
        verify(member).setGender((String) any());
        verify(member).setMemberId((Long) any());
        verify(member).setPassword((String) any());
    }


    @Test
    void testValidateToken() {
        assertFalse(authService.validateToken("ABC123"));
    }


    @Test
    void testValidateToken2() {
        when(jwtUtil.extractUsername((String) any())).thenReturn("janedoe");
        assertTrue(authService.validateToken("Token Header"));
        verify(jwtUtil).extractUsername((String) any());
    }


    @Test
    void testValidateToken3() {
        when(jwtUtil.extractUsername((String) any())).thenReturn("");
        assertFalse(authService.validateToken("Token Header"));
        verify(jwtUtil).extractUsername((String) any());
    }


    @Test
    void testRegisterMember() throws AuthException {
        Member member = new Member();
        member.setAddress("42 Main St");
        member.setAge(1);
        member.setContactNumber(1L);
        member.setEmail("jane.doe@example.org");
        member.setFullName("Dr Jane Doe");
        member.setGender("Gender");
        member.setMemberId(123L);
        member.setPassword("iloveyou");

        Member member1 = new Member();
        member1.setAddress("42 Main St");
        member1.setAge(1);
        member1.setContactNumber(1L);
        member1.setEmail("jane.doe@example.org");
        member1.setFullName("Dr Jane Doe");
        member1.setGender("Gender");
        member1.setMemberId(123L);
        member1.setPassword("iloveyou");
        Optional<Member> ofResult = Optional.of(member1);
        when(authRepo.save((Member) any())).thenReturn(member);
        when(authRepo.findByEmail((String) any())).thenReturn(ofResult);

        Member member2 = new Member();
        member2.setAddress("42 Main St");
        member2.setAge(1);
        member2.setContactNumber(1L);
        member2.setEmail("jane.doe@example.org");
        member2.setFullName("Dr Jane Doe");
        member2.setGender("Gender");
        member2.setMemberId(123L);
        member2.setPassword("iloveyou");
        assertThrows(AuthException.class, () -> authService.registerMember(member2));
        verify(authRepo).findByEmail((String) any());
    }


    @Test
    void testRegisterMember2() throws AuthException {
        Member member = new Member();
        member.setAddress("42 Main St");
        member.setAge(1);
        member.setContactNumber(1L);
        member.setEmail("jane.doe@example.org");
        member.setFullName("Dr Jane Doe");
        member.setGender("Gender");
        member.setMemberId(123L);
        member.setPassword("iloveyou");
        when(authRepo.save((Member) any())).thenReturn(member);
        when(authRepo.findByEmail((String) any())).thenReturn(Optional.empty());

        Member member1 = new Member();
        member1.setAddress("42 Main St");
        member1.setAge(1);
        member1.setContactNumber(1L);
        member1.setEmail("jane.doe@example.org");
        member1.setFullName("Dr Jane Doe");
        member1.setGender("Gender");
        member1.setMemberId(123L);
        member1.setPassword("iloveyou");
        assertSame(member, authService.registerMember(member1));
        verify(authRepo).save((Member) any());
        verify(authRepo).findByEmail((String) any());
    }


    @Test
    void testGetMemberByUserName() throws AuthException {
        Member member = new Member();
        member.setAddress("42 Main St");
        member.setAge(1);
        member.setContactNumber(1L);
        member.setEmail("jane.doe@example.org");
        member.setFullName("Dr Jane Doe");
        member.setGender("Gender");
        member.setMemberId(123L);
        member.setPassword("iloveyou");
        Optional<Member> ofResult = Optional.of(member);
        when(authRepo.findByEmail((String) any())).thenReturn(ofResult);
        assertSame(member, authService.getMemberByUserName("janedoe"));
        verify(authRepo).findByEmail((String) any());
    }


    @Test
    void testGetMemberByUserName2() throws AuthException {
        when(authRepo.findByEmail((String) any())).thenReturn(Optional.empty());
        assertThrows(AuthException.class, () -> authService.getMemberByUserName("janedoe"));
        verify(authRepo).findByEmail((String) any());
    }

    @Test
    void testGenerateOtp() {
        String generatedOtp=authService.generateOtp();

        assertNotNull(generatedOtp);
        assertEquals(4,generatedOtp.length());
    }

    @Test
    void testSendOtpToEmail() throws MailException {
        doNothing().when(javaMailSender).send((SimpleMailMessage) any());
        assertEquals("Otp", authService.sendOtpToEmail("jane.doe@example.org", "Otp"));
        verify(javaMailSender).send((SimpleMailMessage) any());
    }


    @Test
    void testGetAllMembers() {
        ArrayList<Member> memberList = new ArrayList<>();
        when(authRepo.findAll()).thenReturn(memberList);
        List<Member> actualAllMembers = authService.getAllMembers();
        assertSame(memberList, actualAllMembers);
        assertTrue(actualAllMembers.isEmpty());
        verify(authRepo).findAll();
    }
}

