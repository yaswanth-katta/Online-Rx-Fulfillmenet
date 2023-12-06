package com.example.authorization.controller;

import com.example.authorization.exception.AuthException;
import com.example.authorization.model.Login;
import com.example.authorization.model.Member;
import com.example.authorization.service.AuthService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins="http://localhost:4200",allowedHeaders ="*")
@RestController
public class AuthorizationController {
	@Autowired
	private AuthService authService;

	@PostMapping(value = "/login")
	public String login(@RequestBody Login loginDetails) throws AuthException {
		return authService.login(loginDetails);
	}

	@GetMapping(value ="/validateToken" )
	public boolean validateToken(@RequestHeader("Authorization") String tokenHeader){
		return authService.validateToken(tokenHeader);
	}

	@PostMapping(value = "/registerMember")
	public Member  registerMember(@RequestBody Member member) throws AuthException {
		return authService.registerMember(member);
	}

	@GetMapping(value = "/getMemberByUserName/{userName}")
	public Member getMemberByUserName(@PathVariable String userName) throws AuthException {
		return authService.getMemberByUserName(userName);
	}

	@PutMapping(value = "/changePassword")
	public String changePassword(@RequestBody Login login) throws AuthException {
		return authService.changePassword(login);
	}

	@PostMapping(value = "/sendOtpToEmail/{toEmail}")
	public String sendOtpToEmail(@PathVariable String toEmail){
		return authService.sendOtpToEmail(toEmail, authService.generateOtp());
	}

	@GetMapping(value = "/getAllMembers")
	public List<Member> getAllMembers(){
		return authService.getAllMembers();
	}
}
