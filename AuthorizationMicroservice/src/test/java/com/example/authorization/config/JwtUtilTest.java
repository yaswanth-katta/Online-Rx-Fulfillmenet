package com.example.authorization.config;

import com.example.authorization.model.Login;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {JwtUtil.class})
@ExtendWith(SpringExtension.class)
class JwtUtilTest {
    @Autowired
    private JwtUtil jwtUtil;

    private static final String SECRETKEY = "${jwt.secret}";

    @Test
    void testGenerateToken() {
        Login login = mock(Login.class);
        when(login.getPassword()).thenReturn("iloveyou");
        doNothing().when(login).setPassword((String) any());
        doNothing().when(login).setUserName((String) any());
        login.setPassword("iloveyou");
        login.setUserName("janedoe");
        jwtUtil.generateToken(login);
        verify(login).getPassword();
        verify(login).setPassword((String) any());
        verify(login).setUserName((String) any());
    }

    @Test
    void testValidateToken() {
        assertFalse(jwtUtil.validateToken("ABC123"));
        assertFalse(jwtUtil.validateToken("${jwt.secret}"));
        assertFalse(jwtUtil.validateToken("com.example.authorization.config.JwtUtil"));
        assertFalse(jwtUtil.validateToken(""));
        assertFalse(jwtUtil.validateToken("${jwt.secret}${jwt.secret}"));
    }

//    @Test
//    void testExtractClaim() {
////        String token=jwtUtil.generateToken(new Login("abc@gmail.com","Abc@123"));
////        System.out.println("====== "+token+" =========");
//        String token="valid token";
//        when(Jwts.parser().setSigningKey(SECRETKEY).parseClaimsJws(token).getBody()).thenReturn(createClaims("testClaimValue"));
//        Function<Claims, String> claimsResolver = Claims::getSubject;
//        String claimValue = jwtUtil.extractClaim(token, claimsResolver);
//        assertEquals("testClaimValue", claimValue);
//    }
//
//    private Claims createClaims(String subject) {
//        return Jwts.claims().setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 15)));
//    }

}