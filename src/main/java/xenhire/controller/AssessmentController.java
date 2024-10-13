package xenhire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xenhire.request.CandidateAssessmentRequest;
import xenhire.service.AssessmentService;

@RestController
public class AssessmentController {
	
	
	@Autowired
	AssessmentService assessmentService;	
	
	
	@GetMapping("/getAssessments")
	public ResponseEntity<Object> getAssessments(@RequestParam("clientId") long clientId, @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize){
		
		try {
			return assessmentService.getAssessments(clientId, pageNo, pageSize);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/saveCandidateAssessment")
	public ResponseEntity<Object> saveCandidateAssessments(@RequestParam("batchId") long batchId, @RequestParam("candidateId") long candidateId, @RequestBody CandidateAssessmentRequest req ){
		
		try {
			return assessmentService.saveAssessment(batchId, candidateId, req);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@GetMapping("/getAssessmentQuestionnaire")
	public ResponseEntity<Object> getAssessmentQuestionnaire(@RequestParam("assessmentType") String assessmentType){
		try {
			return assessmentService.getAssessmentQuestionnaire(assessmentType);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getAdaptability")
	public ResponseEntity<Object> getAdaptability(){
		try {
			return assessmentService.getAdaptability();
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getTimeManagement")
	public ResponseEntity<Object> getTimeManagement(){
		try {
			return assessmentService.getTimeManagement();
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getDecisionMaking")
	public ResponseEntity<Object> getDecisionMaking(){
		try {
			return assessmentService.getDecisionMaking();
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getConflictManagement")
	public ResponseEntity<Object> getConflictManagement(){
		try {
			return assessmentService.getConflictManagement();
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getCommunicationSkills")
	public ResponseEntity<Object> getCommunictionSkills(){
		try {
			return assessmentService.getCommunicationSkills();
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getRelationshipBuilding")
	public ResponseEntity<Object> getRelationshipBuilding(){
		try {
			return assessmentService.getRelationshipBuilding();
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getStressManagement")
	public ResponseEntity<Object> getStressManagement(){
		try {
			return assessmentService.getStressManagement();
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getResilience")
	public ResponseEntity<Object> getResilience(){
		try {
			return assessmentService.getResilience();
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@GetMapping("/getEmpathy")
	public ResponseEntity<Object> getEmpathy(){
		try {
			return assessmentService.getEmpathy();
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getSelfAwareness")
	public ResponseEntity<Object> getSelfAwareness(){
		try {
			return assessmentService.getSelfAwareness();
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

}
