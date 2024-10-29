package xenhire.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import xenhire.dto.CandidatePersonalInfoDTO;
import xenhire.model.CandidateAssessmentRanking;
import xenhire.model.CandidateDetails;
import xenhire.model.CandidatePreferences;
import xenhire.model.CandidateScreeningQuestions;
import xenhire.model.User;
import xenhire.request.ApproveClientRequest;
import xenhire.request.CandidateAssessmentRequest;
import xenhire.request.CandidateDTPQuestionnaireRequest;
import xenhire.request.CandidatePreferencesRequest;
import xenhire.request.CandidateValueAssessmentRequest;
import xenhire.request.ClientAssessmentRequest;
import xenhire.request.PersonalInfoOptionRequest;
import xenhire.response.CandidateSpectrumResults;
import xenhire.response.CandidateValueResultResponse;
import xenhire.service.CandidateDetailsService;
import xenhire.service.CandidateService;
import xenhire.service.ClientJobService;

@RestController
public class CandidateController {
	
	@Autowired
	CandidateService candidateService;
	@Autowired
	CandidateDetailsService candidateDetailsService;
	@Autowired
	ClientJobService clientJobService;
	
	@GetMapping("/getCandidateQuestionnaire")
	public ResponseEntity<Object> getCandidateAssessment(@RequestParam("candidateId") long candidateId){
		try {
			return candidateService.getCandidateQuestionnaire(candidateId);
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
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PostMapping("/saveCandidateDTPAnalysis")
	public ResponseEntity<Object> saveCandidateAssessment(@RequestBody List<CandidateDTPQuestionnaireRequest> req, @RequestParam("candidateId") long candidateId){
		
		try {
			return candidateService.savecandidateQuestionnaire(req, candidateId);
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
	public ResponseEntity<Object> saveCandidateForm(@Valid@RequestBody CandidatePreferencesRequest req, @RequestParam("candidateId") long candidateId){
		
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
	public ResponseEntity<Object> getClientIcpReport(@RequestParam("candidateId") long candidateId, @RequestParam("dtpReportId") long reportId){
		
		try {
			return candidateService.getCandidateDtpReport(candidateId, reportId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@GetMapping("/getCandidateProfileInfo")
	public ResponseEntity<Object> getCandidateProfileInfo(@RequestParam("candidateId") long candidateId){
		
		try {
			return candidateService.getCandidateProfileInfo(candidateId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/postCandidatePersonalInfo")
	public ResponseEntity<Object> postCandidatePersonalInfo(@RequestParam("candidateId") long candidateId, @RequestBody CandidatePersonalInfoDTO req){
		
		try {
			return candidateService.postCandidatePersonalInfo(candidateId, req);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getCandidatePersonalInfo")
	public ResponseEntity<Object> getCandidatePersonalInfo(@RequestParam("candidateId") long candidateId, @RequestParam("personalInfoId") long personalInfoId){
		
		try {
			return new ResponseEntity<>(candidateService.getCandidatePersonalInfo(candidateId, personalInfoId), null, HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	
	@GetMapping("/getCandidatePreferences")
	public ResponseEntity<Object> getCandidatePreferences(@RequestParam("candidateId") long candidateId, @RequestParam("preferenceId") long preferenceId){
		
		try {
			return new ResponseEntity<>(candidateService.getCandidatePreferences(candidateId, preferenceId), null, HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/getCandidateDTPInfo")
	public ResponseEntity<Object> getCandidateDTPInfo(@RequestParam("candidateId") long candidateId){
		
		try {
			return candidateService.getCandidateDTPInfo(candidateId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@GetMapping("/getCandidateAssessmentCount")
	public ResponseEntity<Object> getCandidateAssessmentCount(@RequestParam("candidateId") long candidateId){
		
		try {
			return candidateService.getCandidateAsssessmentsCount(candidateId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@GetMapping("/getCandidateAssessments")
	public ResponseEntity<Object> getCandidateSelfAssessments(@RequestParam("candidateId") long candidateId){
		
		try {
			return candidateService.getCandidateSelfAssessments(candidateId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@GetMapping("/getCandidateValueResult")
	public ResponseEntity<Object> getCandidateValueResult(@RequestParam("candidateId") long candidateId, @RequestParam("versionNo") int versionNo){
		
		try {
			List<CandidateValueResultResponse> resp = candidateService.getCandidateValueResult(candidateId, versionNo);
			
			return new ResponseEntity<>(resp, null, HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@GetMapping("/getCandidateSpectrumResults")
	public ResponseEntity<Object> getCandidateSpectrumResults(@RequestParam("candidateId") long candidateId, @RequestParam("versionNo") long versionNo){
		
		try {
			CandidateSpectrumResults resp = candidateService.getCandidateSpectrumResults(candidateId, versionNo);
			return new ResponseEntity<>(resp, null, HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@GetMapping("/getAllJobsForCandidates")
	public ResponseEntity<Object> getAllJobsForCandidates(@RequestParam("candidateId") long candidateId, @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize){
		try {
			return clientJobService.getAllJobsForCandidates(candidateId, pageNo, pageSize);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/saveCandidateDtpAccess")
	public ResponseEntity<Object> approveClientForDTP(@RequestParam("candidateId") long candidateId, @RequestBody ApproveClientRequest req ){
		
		try {
			CandidateSpectrumResults resp = candidateService.approveCandidateDtpAccess(candidateId, req);
			return new ResponseEntity<>(resp, null, HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@GetMapping("/getCandidateDTPAccess")
	public ResponseEntity<Object> getCandidateDTPAccess(@RequestParam("candidateId") long candidateId){
		
		try {
			 return candidateService.getCandidateDTPAccess(candidateId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@GetMapping("/downloadResume")
    public ResponseEntity<Object> downloadResume(@RequestParam("candidateId") long candidateId) throws IOException {
        User u = candidateService.getCandidate(candidateId);
		byte[] content = u.getResume();
		ByteArrayResource resource = new ByteArrayResource(content);
		// Set the content disposition and content type headers
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+u.getResumeName());
		//headers.add(HttpHeaders.CONTENT_TYPE, "application/pdf");
		headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");

		return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(content.length)
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
    
    
    @GetMapping("/getCandidateDTPDescription")
	public ResponseEntity<Object> getCandidateDTPDescription(@RequestParam("candidateId") long candidateId){
		
		try {
			 return candidateService.getCandidateDTPDescription(candidateId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
    
    
    @GetMapping("/getCandidateScreeningQuestions")
	public ResponseEntity<Object> getCandidateScreeningQuestions(@RequestParam("candidateId") long candidateId, @RequestParam("jobId") long jobId ){
		
		try {
			 return candidateService.getCandidateScreeningQuestions(candidateId, jobId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
    
    
    @PostMapping("/getCandidateScreeningQuestions")
	public ResponseEntity<Object> saveCandidateScreeningQuestions(@RequestParam("candidateId") long candidateId, @RequestParam("jobId") long jobId, @RequestBody List<CandidateScreeningQuestions> reqList ){
		
		try {
			 return candidateService.saveCandidateScreeningQuestions(candidateId, jobId, reqList);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
    
	
	
    @GetMapping("/getPersonalInfoOptions")
	public ResponseEntity<Object> getPersonalInfoOptions(@RequestParam("candidateId") long candidateId){
		
		try {
			 return candidateService.getPersonalInfoOptions(candidateId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
    
    
    @PostMapping("/addPersonalInfoOption")
	public ResponseEntity<Object> addPersonalInfoOption(@RequestParam("candidateId") long candidateId, @RequestBody PersonalInfoOptionRequest req){
		
		try {
			 return candidateService.addPersonalOptionRequest(candidateId, req);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PostMapping("/saveCandidateDetailsForm")
	public ResponseEntity<?> saveCandidateDetailsForm(@RequestBody CandidateDetails req, @RequestParam("candidateId") long candidateId){
		
		try {
			CandidateDetails candidateDetails = candidateDetailsService.saveCandidateDetailsForm(req, candidateId);
			return new ResponseEntity<CandidateDetails>(candidateDetails, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	@GetMapping("/getCandidateDetailsForm")
	public ResponseEntity<?> getCandidateDetails(@RequestParam("candidateId") long candidateId){
		
		try {
			CandidateDetails candidateDetails = candidateDetailsService.getCandidateDetailsForm(candidateId);
			return new ResponseEntity<CandidateDetails>(candidateDetails, HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	@PostMapping("/convertImageToByte")
	public ResponseEntity<?> getImageByteData(@RequestParam("image") MultipartFile imageFile){
		
		try {
			
			return new ResponseEntity<byte[]>(imageFile.getBytes(), HttpStatus.OK);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	
}

