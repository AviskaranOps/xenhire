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

import xenhire.model.CandidateInterviewFeedback;
import xenhire.model.ScreeningQuestions;
import xenhire.request.CandidateInterviewFeedbackRequest;
import xenhire.request.ClientScreeningQuestionsRequest;
import xenhire.service.JobCandidateService;

@RestController
public class JobCandidateController {
	
	@Autowired
	JobCandidateService jobCandidateService;
	
	@GetMapping("/getJobCandidateInfo")
	public ResponseEntity<Object> updateAssessment(@RequestParam("jobId") long jobId, @RequestParam("clientId") long clientId, @RequestParam("candidateId") long candidateId){
		try {
			return jobCandidateService.getJobCandidateInfo(clientId, candidateId, jobId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/saveInterviewFeedback")
	public ResponseEntity<Object> saveInterviewFeedback(@RequestParam("clientId") long clientId, @RequestParam("jobId") long jobId, @RequestParam("candidateId") long candidateId, @RequestBody CandidateInterviewFeedbackRequest req){
		try {
			return jobCandidateService.saveInterviewFeedback(clientId,jobId, candidateId, req);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
	
	@GetMapping("/getJobScreeningQuestions")
	public ResponseEntity<Object> getJobScreeningQuestions(@RequestParam("jobId") long jobId, @RequestParam("clientId") long clientId){
		try {
			return jobCandidateService.getJobScreeningQuestions(clientId, jobId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/saveJobScreeningQuestions")
	public ResponseEntity<Object> saveJobScreeningQuestions(@RequestParam("jobId") long jobId, @RequestParam("clientId") long clientId, @RequestBody ClientScreeningQuestionsRequest reqList){
		try {
			return jobCandidateService.saveJobScreeningQuestions(clientId, jobId, reqList);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	

}
