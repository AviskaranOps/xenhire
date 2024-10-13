package xenhire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xenhire.request.DtpAccessRequest;
import xenhire.service.DtpAccessService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class DtpAccessController {
	
	@Autowired
	DtpAccessService dtpAccessService;
	
	@PostMapping("/declineClient")
	public ResponseEntity<Object> declineDtpAccess(@RequestParam("candidateId") long candidateId, @RequestBody DtpAccessRequest req){
		try {
			 return dtpAccessService.declineDtpAccess(candidateId, req);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/authorizeClient")
	public ResponseEntity<Object> authorizeDtpAccess(@RequestParam("candidateId") long candidateId, @RequestBody DtpAccessRequest req){
		try {
			 return dtpAccessService.authorizeDtpAccess(candidateId, req);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/requestDtpAccess")
	public ResponseEntity<Object> requestDtpAccess(@RequestParam("clientId") long clientId, @RequestParam("candidateId") long candidateId){
		try {
			 return dtpAccessService.requestDtpAccess(clientId, candidateId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

}
