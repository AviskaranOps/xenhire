package xenhire.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xenhire.model.ClientValueAssessmentData;
import xenhire.request.ClientAssessmentRequest;
import xenhire.request.ClientFormRequest;
import xenhire.service.ClientService;

@RestController
public class ClientController {
	
	
	@Autowired
	ClientService clientService;
	
	
	@GetMapping("/getClientQuestionnaire")
	public ResponseEntity<Object> getClientAssessment(){
		
		try {
			return clientService.getClientQuestionnaire();
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
	@PostMapping("/saveClientAssessment")
	public ResponseEntity<Object> saveClientAssessment(@RequestBody List<ClientAssessmentRequest> req, @RequestParam("clientId") long clientId){
		
		try {
			return clientService.saveClientQuestionnaire(req, clientId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/saveClientForm")
	public ResponseEntity<Object> saveClientForm(@RequestBody ClientFormRequest req, @RequestParam("clientId") long clientId){
		try {
			return clientService.saveClientForm(req, clientId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
	@PostMapping("/saveClientValueAssessment")
	public ResponseEntity<Object> saveClientValueAssessment(@RequestBody ClientValueAssessmentData req, @RequestParam("clientId") long clientId){
		
		try {
			return clientService.saveClientValueAssessment(req, clientId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/getJobMatchingScore")
	public ResponseEntity<Object> getMatchingScore(@RequestParam("clientId") long clientId, @RequestParam("candidateId") long candidateId){
		
		try {
			return clientService.getMatchingScore(clientId, candidateId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/postClientRanking")
	public ResponseEntity<Object> postCandidateRanking(@RequestParam("clientId") long clientId){
		
		try {
			clientService.extractClientAssessmentScoreAndSave(clientId);
			return new ResponseEntity<>("saved", null, HttpStatus.OK);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	
	@GetMapping("/getClientIcpReport")
	public ResponseEntity<Object> getClientIcpReport(@RequestParam("clientId") long clientId){
		
		try {
			return clientService.getClientIcpReport(clientId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	
	@GetMapping("/getClientJobs")
	public ResponseEntity<Object> getClientJobs(@RequestParam("clientId") long clientId){
		
		try {
			return clientService.getClientJobs(clientId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
