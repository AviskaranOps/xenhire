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

import xenhire.model.ClientSettings;
import xenhire.model.ClientValueAssessmentData;
import xenhire.request.ClientAssessmentBatchRequest;
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
	
	
	@PostMapping("/saveClientSettings")
	public ResponseEntity<Object> saveClientSettings(@RequestParam("clientId") long clientId, @RequestBody ClientSettings req){
		
		try {
			return clientService.saveClientSettings(clientId, req);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	
	@GetMapping("/getClientSettings")
	public ResponseEntity<Object> getClientSettings(@RequestParam("clientId") long clientId){
		
		try {
			return clientService.getClientSettings(clientId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@GetMapping("/getClientDashboardData")
	public ResponseEntity<Object> getClientDashboardData(@RequestParam("clientId") long clientId){
		
		try {
			return clientService.getClientDashboardData(clientId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

}
