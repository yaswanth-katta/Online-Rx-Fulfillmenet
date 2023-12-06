package com.example.authorization.service;

import com.example.authorization.config.JwtUtil;
import com.example.authorization.exception.AuthException;
import com.example.authorization.model.Login;
import com.example.authorization.model.Member;
import com.example.authorization.repository.AuthRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private AuthRepo authRepo;

	@Autowired
	private JavaMailSender javaMailSender;

	public String login(Login loginDetails) throws AuthException {
		Optional<Member> memberDetails=authRepo.findByEmail(loginDetails.getUserName());
		if(memberDetails.isPresent() && memberDetails.get().getPassword().equals(loginDetails.getPassword())){
			return ("Bearer "+jwtUtil.generateToken(loginDetails));  //generate token
		}else throw new AuthException("Invalid Credentials");
	}
	public boolean validateToken(String tokenHeader){
		try {
			return !jwtUtil.extractUsername(tokenHeader.substring(7)).isEmpty();
		}catch (Exception e){

			return false;
		}
	}

	public Member registerMember(Member member) throws AuthException {
		if(!authRepo.findByEmail(member.getEmail()).isPresent()){
			return  authRepo.save(member);
		}else throw new AuthException("EmailId is already in use, Try again..!");
	}

	public Member getMemberByUserName(String userName) throws AuthException {
		Optional<Member> member=authRepo.findByEmail(userName);
		if(member.isPresent()){
			return member.get();
		}else throw new AuthException("There is no record with username : "+userName);

	}

	public String changePassword(Login login) throws AuthException {
		Optional<Member> member=authRepo.findByEmail(login.getUserName());
		if(member.isPresent()){
			member.get().setPassword(login.getPassword());
			authRepo.save(member.get());
			return "Password updated successfully";
		}else throw new AuthException("Invalid Username");
	}

	public String generateOtp(){
		return String.valueOf(1000+new Random().nextInt(9000));
	}

	public String sendOtpToEmail(String toEmail,String otp){
		SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
		simpleMailMessage.setTo(toEmail);
		simpleMailMessage.setFrom("onlineRxFulfillment@info.in");
		simpleMailMessage.setSubject("your OTP for Online RxFulfillment");
		simpleMailMessage.setText("Don't share your otp to Anyone..\n Your One code is: " + otp
		+"\n\nRegards\nOnline RxFulfillment");
		javaMailSender.send(simpleMailMessage);

		return otp;
	}

	public List<Member> getAllMembers(){
		return authRepo.findAll();
	}


}
