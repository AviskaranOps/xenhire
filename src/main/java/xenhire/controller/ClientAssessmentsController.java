package xenhire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xenhire.request.AssessmentBatchAddCandidateRequest;
import xenhire.request.CandidatesBatchRequest;
import xenhire.request.ClientAssessmentBatchRequest;
import xenhire.service.ClientAssessmentService;

@RestController
public class ClientAssessmentsController {
	
	@Autowired
	ClientAssessmentService clientAssessmentService;
	
	
	@PostMapping("/saveClientAssessmentBatch")
	public ResponseEntity<Object> saveClientAssessmentBatch(@RequestParam("clientId") long clientId, @RequestBody ClientAssessmentBatchRequest req){
		
		try {
			return clientAssessmentService.saveAssessmentBatchWithAssignCandidates(clientId, req);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PostMapping("/saveClientAssessmentBatchAddCandidate")
	public ResponseEntity<Object> saveClientAssessmentBatchwithAddCandidate(@RequestParam("clientId") long clientId, @RequestBody AssessmentBatchAddCandidateRequest req){
		
		try {
			return clientAssessmentService.saveAssessmentBatchWithAddCandidate(clientId, req);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	
	@PostMapping("/assignCandidatesToBatch")
	public ResponseEntity<Object> assignCandidatesToBatch(@RequestParam("clientId") long clientId, @RequestParam("batchId") long batchId, @RequestBody CandidatesBatchRequest req){
		
		try {
			return clientAssessmentService.assignCandidatesToBatch(clientId, batchId, req);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@GetMapping("/getBatchCandidates")
	public ResponseEntity<Object> getBatchCandidates(@RequestParam("clientId") long clientId, @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize){
		
		try {
			return clientAssessmentService.getCandidates(clientId, pageNo, pageSize);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/getBatchList")
	public ResponseEntity<Object> getBatchList(@RequestParam("clientId") long clientId, @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize){
		
		try {
			return clientAssessmentService.getAllAsessmentBatch(clientId, pageNo, pageSize);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@GetMapping("/getBatchCandidatesStatus")
	public ResponseEntity<Object> getBatchCandidatesStatus(@RequestParam("clientId") long clientId,@RequestParam("batchId") long batchId, @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize){
		
		try {
			return clientAssessmentService.getBatchCandidatesData(clientId, batchId, pageNo, pageSize);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
