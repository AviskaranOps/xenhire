package xenhire.controller;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.Context;

import xenhire.dto.UserDTO;
import xenhire.model.Role;
import xenhire.model.User;
import xenhire.request.AuthRequest;
import xenhire.request.OtpRequest;
import xenhire.request.PasswordRequest;
import xenhire.request.SignupRequest;
import xenhire.response.AuthResponse;
import xenhire.security.JwtUtil;
import xenhire.security.UserDetailServiceImpl;
import xenhire.service.EmailService;
import xenhire.service.UserService;

import jakarta.mail.MessagingException;


@RestController
public class AuthController {

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailServiceImpl userDetailsService;
    @Autowired
    UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailServiceImpl userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) throws Exception {
        try {
        	System.out.println("login request" + authenticationRequest);
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        

        final String jwt = jwtUtil.generateToken(userDetails);
        
        User user = userDetailsService.getUserDetails(authenticationRequest.getUsername());
        Role role = user.getRoles().stream().findFirst().get();
        return ResponseEntity.ok(new AuthResponse(user.getId(), user.getUsername(), user.getEmail(), jwt, "ROLE_"+role.getName()));
    }
    
    @GetMapping("/getEncryptedPass")
    public ResponseEntity<Object> getPass(@RequestParam("password") String password) {
    	
    	String pass = bCryptPasswordEncoder.encode(password);
    	return new ResponseEntity<>(pass, null, HttpStatus.OK);
    	
    }
    
    @PostMapping("/signup")
    public ResponseEntity<Object> userSignup(@RequestBody SignupRequest req){
    	try {
    		return userService.userSignup(req);
    	}
    	catch(Exception e) {
    		return new ResponseEntity<>(e.getMessage(), null, HttpStatus.OK);
    	}
    }
    
    @PostMapping("/verifyOTP")
    public ResponseEntity<Object> verifyOtp(@RequestBody OtpRequest req){
    	try {
    		return userService.verifyOTP(req);
    	}
    	catch(Exception e) {
    		return new ResponseEntity<>(e.getMessage(), null, HttpStatus.OK);
    	}
    }
    
    
    @PostMapping("/updatePassword")
    public ResponseEntity<Object> updatePassword(@RequestBody PasswordRequest req){
    	try {
    		return userService.updatePassword(req);
    	}
    	catch(Exception e) {
    		return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);	
    	}
    }
    
    @GetMapping("forgotPassword")
    public ResponseEntity<Object> forgotPassword(@RequestParam("emailId") String emailId){
    	try {
    		return userService.checkEmail(emailId);
    	}
    	catch(Exception e) {
    		return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);	
    	}
    }
    
    @GetMapping("/sendemail")
    public String sendEmail() throws MessagingException {
    	String to = "hemanth.k3399@gmail.com";
        String subject = "Test Subject";
        String body = "Hello,\n\nThis is the first line of the email body.\nThis is the second line.\nAnd this is the third line.\n\nRegards,\nYour Name";

        
        emailService.sendSimpleEmail(to, subject, body);	

        return "email-sent";
    }
}
