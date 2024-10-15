package xenhire.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import xenhire.model.CandidateDTPReportData;
import xenhire.model.CandidateDtpAccess;
import xenhire.model.Client;
import xenhire.model.User;
import xenhire.repository.CandidateDTPReportDataRepository;
import xenhire.repository.CandidateDtpAccessRepository;
import xenhire.repository.ClientRepository;
import xenhire.repository.UserRepository;
import xenhire.request.DtpAccessRequest;

@Service
public class DtpAccessService {
	
	@Autowired
	CandidateDtpAccessRepository candidateDtpAccessRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	ClientRepository clientRepository;
	
	@Autowired
	CandidateDTPReportDataRepository candidateDTPReportDataRepository;

	

	public ResponseEntity<Object> declineDtpAccess(long candidateId, DtpAccessRequest req) {
		// TODO Auto-generated method stub
		if(req.isDeclined() == true) req.setAuthorized(false);
		CandidateDtpAccess access = candidateDtpAccessRepository.findByCandidateIdAndClientId(candidateId, req.getClientId());
		access.setAuthorized(req.isAuthorized());
		access.setDeclined(req.isDeclined());
		access.setDeclinedAt(LocalDateTime.now());
		access.setDtpStatus("Show DTP");
		candidateDtpAccessRepository.save(access);
		return new ResponseEntity<>("saved", null, HttpStatus.OK);
	}
	
	
	public ResponseEntity<Object> authorizeDtpAccess(long candidateId, DtpAccessRequest req) {
		// TODO Auto-generated method stub
		if(req.isAuthorized() == true) req.setDeclined(false);
		CandidateDtpAccess access = candidateDtpAccessRepository.findByCandidateIdAndClientId(candidateId, req.getClientId());
		if(access == null) access = new CandidateDtpAccess();
		CandidateDTPReportData dtpData = candidateDTPReportDataRepository.getRecentRecord(candidateId);
		access.setClientId(req.getClientId());
		access.setCandidateId(candidateId);
		access.setAuthorized(req.isAuthorized());
		access.setDeclined(req.isDeclined());
		access.setDtpStatus("Request Access");
		access.setDtpReportId(dtpData.getId());
		access.setApprovedAt(LocalDateTime.now());
		candidateDtpAccessRepository.save(access);
		return new ResponseEntity<>("saved", null, HttpStatus.OK);
	}
	
	public ResponseEntity<Object> requestDtpAccess(long clientId, long candidateId) throws MessagingException{
		System.out.println(clientId);
		System.out.println(candidateId);
		CandidateDtpAccess dtp = candidateDtpAccessRepository.findByClientIdAndCandidateId(clientId, candidateId);
		Optional<User> userOpt = userRepository.findById(candidateId);
		User user = userOpt.get();
		Optional<Client> cliOpt = clientRepository.findById(clientId);
		Client cli = cliOpt.get();
		if(dtp == null) {
			dtp = new CandidateDtpAccess();
			dtp.setCandidateId(candidateId);
			dtp.setClientId(clientId);
			dtp.setClientName(cli.getCompanyName());
		}
		dtp.setDtpStatus("Request Sent");
		candidateDtpAccessRepository.save(dtp);
		String body = "Hi " + user.getUsername() + "\n"
				+ "\n"
				+ "client "+cli.getCompanyName() + " sents a request to see your Digital Talent Profile (DTP) results"
				+ "\n"
				+ "If you want to give Access " 
				+ "\n please login to your account and go authorize section and approve accesss"
				+ "\n \n"
				+ "Best regards,\n"
				+ "Ram Konduru";
		emailService.sendSimpleEmail(user.getEmail(), "Request Access for DTP", body);	
		return new ResponseEntity<>("request sent", null, HttpStatus.OK);
	}
	
	

}
