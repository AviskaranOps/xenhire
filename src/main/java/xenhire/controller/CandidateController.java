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

import xenhire.model.CandidatePreferences;
import xenhire.request.CandidateAssessmentRequest;
import xenhire.request.CandidatePreferencesRequest;
import xenhire.request.CandidateValueAssessmentRequest;
import xenhire.request.ClientAssessmentRequest;
import xenhire.service.CandidateService;

@RestController
public class CandidateController {
	
	@Autowired
	CandidateService candidateService;
	
	@GetMapping("/getCandidateQuestionnaire")
	public ResponseEntity<Object> getCandidateAssessment(@RequestParam("candidateId") long candidateId, @RequestParam("versionNo") int versionNo){
		try {
			return candidateService.getCandidateQuestionnaire(candidateId, versionNo);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	

	@GetMapping("/getSection")
	public ResponseEntity<Object> getCandidateSection(@RequestParam("candidateId") long candidateId, @RequestParam("versionNo") int versionNo){
		
		try {
			return candidateService.getCandidateSection(candidateId, versionNo);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@GetMapping("/getCandidateValuesQuestionnaire")
	public ResponseEntity<Object> getCandidateValueQuestionnaire(@RequestParam("candidateId") long candidateId){
		
		try {
			return candidateService.getCandidateValues(candidateId);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PostMapping("/saveCandidateAssessment")
	public ResponseEntity<Object> saveCandidateAssessment(@RequestBody List<CandidateAssessmentRequest> req, @RequestParam("candidateId") long candidateId, @RequestParam("section") int section, @RequestParam("versionNo") int versionNo){
		
		try {
			return candidateService.savecandidateQuestionnaire(req, candidateId, section, versionNo);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/saveCandidateValueAssessment")
	public ResponseEntity<Object> saveCandidateValueAssessment(@RequestBody List<CandidateValueAssessmentRequest> req, @RequestParam("candidateId") long candidateId){
		
		try {
			return candidateService.savecandidateValueAssessment(req, candidateId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/saveCandidateForm")
	public ResponseEntity<Object> saveCandidateForm(@RequestBody CandidatePreferencesRequest req, @RequestParam("candidateId") long candidateId){
		
		try {
			return candidateService.savecandidateForm(req, candidateId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/postCandidateRanking")
	public ResponseEntity<Object> postCandidateRanking(@RequestParam("candidateId") long candidateId, @RequestParam("versionNo") int versionNo){
		
		try {
			return candidateService.postCandidateRanking(candidateId, versionNo);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/getCandidateDtpReport")
	public ResponseEntity<Object> getClientIcpReport(@RequestParam("candidateId") long candidateId){
		
		try {
			return candidateService.getCandidateDtpReport(candidateId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	

}
