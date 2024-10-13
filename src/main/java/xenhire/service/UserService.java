package xenhire.service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import xenhire.model.Role;
import xenhire.model.User;
import xenhire.repository.RoleRepository;
import xenhire.repository.UserRepository;
import xenhire.request.AuthRequest;
import xenhire.request.OtpRequest;
import xenhire.request.PasswordRequest;
import xenhire.request.SignupRequest;

import jakarta.mail.MessagingException;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	EmailService emailService;
	
	private static final SecureRandom secureRandom = new SecureRandom();
    private static final int OTP_LENGTH = 4;
	
	
	public ResponseEntity<Object> userSignup(SignupRequest req) throws MessagingException{
		System.out.println(req);
		User u = userRepository.findByEmail(req.getEmail());
		if(u != null) {
			return new ResponseEntity<>("user already existed", null, HttpStatus.BAD_REQUEST);
		}
		Role r = roleRepository.findByName("CANDIDATE");
		Set s = new HashSet();
		s.add(r);
		int otp = secureRandom.nextInt((int) Math.pow(10, OTP_LENGTH));
		
		int count = 0;
		int currentNum = Math.abs(otp);

		  while (currentNum > 0) {
		    currentNum /= 10; 
		    count++;
		  }
		  
		  if(count < 4) otp = otp * 10;
		
		u = User.builder()
				.username(req.getUsername())
				.email(req.getEmail())
				.password(bCryptPasswordEncoder.encode(req.getPassword()))
				.roles(s)
				.otp(otp)
				.otpVerified(false)
				.build();
		userRepository.save(u);
		
		String body = "One Time Password to verify your account in Xenflexer! " + otp;
		
		emailService.sendSimpleEmail(req.getEmail(), "verify your account", body);	
		return new ResponseEntity<>("saved", null, HttpStatus.CREATED);
	}
	
	
	
	public ResponseEntity<Object> verifyOTP(OtpRequest req) throws MessagingException{
		
		Optional<User> u = userRepository.findByEmailAndOtp(req.getEmail(), Long.parseLong(req.getOtp()));
		if(u.isPresent()) {
			User user = u.get();
			user.setOtpVerified(true);
			userRepository.save(user);
			String body = "Hi " + user.getUsername() + "\n"
					+ "\n"
					+ "Thanks for signing up with Xenflexer! We're excited to have you on board.\n"
					+ "We're confident that Xenflexer will help you simplify your contracting career with our All-In-One Employer of Record, Payroll & Other Services – Focus on Your Projects, Not Paperwork!.\n"
					+ "In the meantime, you can explore our website https://www.xenflexer.com to learn more about Xenflexer's features and functionality.\n"
					+ "If you have any questions or run into any issues, please don't hesitate to contact our support team at recruitment@xenspire.com.\n"
					+ "We look forward to having you as part of the Xenflexer community!\n"
					+ " \n"
					+ "Best regards,\n"
					+ "Ram Konduru";
			
			emailService.sendSimpleEmail(user.getEmail(), "Welcome to Xenflexer!", body);
			
			return new ResponseEntity<>("updated successfully", null, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("user not found", null, HttpStatus.BAD_REQUEST);
		}	

		
	}
	
	
	public ResponseEntity<Object> updatePassword(PasswordRequest req) {
		System.out.println("req = " + req);
		User u = userRepository.findByEmail(req.getEmail());
		if(u != null) {
			String npass = bCryptPasswordEncoder.encode(req.getPassword());
			u.setPassword(npass);
			userRepository.save(u);
			return new ResponseEntity<>("updated successfully", null, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("user not found", null, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	public String getUsername() {
        // Get the authentication object from SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            // Get the username of the authenticated user
            String username = authentication.getName();
            
            // You can also access other details like authorities, etc.
            // List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();

            // Now you can do whatever you need with the username or other user details
            System.out.println("Authenticated user: " + username);
            return username;
        } else {
            // Handle the case where no user is authenticated
            System.out.println("No authenticated user");
            return "";
        }
    }



	public ResponseEntity<Object> checkEmail(String emailId) throws MessagingException {
		// TODO Auto-generated method stub
		User u = userRepository.findByEmail(emailId);
		if(u != null) {
			int otp = secureRandom.nextInt((int) Math.pow(10, OTP_LENGTH));
			u.setOtp(otp);
			userRepository.save(u);
			String body = "One Time Password to update your xenflexer account password " + otp;
			emailService.sendSimpleEmail(emailId, "verify your account", body);	
			return new ResponseEntity<>("user found", null, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>("user not found", null, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	public ResponseEntity<Object> uploadResume(MultipartFile file, long candidateId) throws IOException{
		Optional<User> uOpt = userRepository.findById(candidateId);
		User u = uOpt.get();
		u.setResume(file.getBytes());
		u.setResumeName(file.getOriginalFilename());
		userRepository.save(u);
		return new ResponseEntity<>("saved", null, HttpStatus.OK);
	}

}
