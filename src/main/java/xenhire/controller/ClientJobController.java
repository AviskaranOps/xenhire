package xenhire.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import xenhire.model.ClientTeamTemplate;
import xenhire.model.JobTemplate;
import xenhire.request.AssessmentBatchAddCandidateRequest;
import xenhire.request.ClientAssessmentRequest;
import xenhire.request.ClientIcpRequest;
import xenhire.request.ClientScreeningQuestionsRequest;
import xenhire.request.ClientValuesRequest;
import xenhire.request.ClinetPreferenceTemplateRequest;
import xenhire.request.JobDetailsRequest;
import xenhire.request.JobStatusRequest;
import xenhire.request.JobTitleRequest;
import xenhire.response.BatchCandidateResponse;
import xenhire.service.ClientJobService;
import xenhire.service.ClientTemplateService;

@RestController
public class ClientJobController {
	
	@Autowired
	ClientJobService clientJobService;
	
	@Autowired
	ClientTemplateService clientTemplateService;
	
	@GetMapping("/getJobDetails")
	public ResponseEntity<Object> getJobDetails(@RequestParam("clientId") long clientId, @RequestParam("jobId") long jobId){
		try {
			return clientJobService.getClientJobDetails(clientId, jobId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

	@PostMapping("/saveJobTemplateForJob")
	public ResponseEntity<Object> saveJobTemplate(@RequestParam("clientId") long clientId, @RequestBody JobDetailsRequest req, @RequestParam("jobId") long jobId, @RequestParam("template") int template){
		try {
			return clientJobService.saveJobTemplate(clientId, req, jobId, template);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/saveValueTemplateForJob")
	public ResponseEntity<Object> saveValuesTemplate(@RequestParam("clientId") long clientId, @RequestBody ClientValuesRequest req, @RequestParam("jobId") long jobId,@RequestParam("template") int template ){
		try {
			return clientJobService.saveClientValues(clientId, jobId, req, template);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/assignValueTemplateForJob")
	public ResponseEntity<Object> assignValuesTemplate(@RequestParam("clientId") long clientId, @RequestParam("valuesId") long valuesId, @RequestParam("jobId") long jobId ){
		try {
			return clientJobService.assignClientValues(clientId, jobId, valuesId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/saveTeamTemplateForJob")
	public ResponseEntity<Object> saveTeamTemplate(@RequestParam("clientId") long clientId, @RequestBody ClientTeamTemplate req, @RequestParam("jobId") long jobId, @RequestParam("template") int template){
		try {
			return clientJobService.saveTeamTemplate(clientId, jobId, req, template);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
//	@PostMapping("/savePreferenceTemplateForJob")
//	public ResponseEntity<Object> savePreferenceTemplate(@RequestParam("clientId") long clientId, @RequestBody ClinetPreferenceTemplateRequest req, @RequestParam("jobId") long jobId ){
//		try {
//			return clientJobService.savePreferenceTemplate(clientId, jobId, req);
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	@PostMapping("/saveIcpTemplateForJob")
	public ResponseEntity<Object> saveIcpTemplate(@RequestParam("clientId") long clientId, @RequestBody ClientIcpRequest req, @RequestParam("jobId") long jobId, @RequestParam("template") int template){
		try {
			return clientJobService.saveClientICPTempalte(req, clientId, jobId, template);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
	@GetMapping("/getAllJobs")
	public ResponseEntity<Object> getAllJobs(@RequestParam("clientId") long clientId, @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize){
		try {
			return clientJobService.getAllJobsofClient(clientId, pageNo, pageSize);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getAllJobCandidates")
	public ResponseEntity<Object> getAllJobCandidates(@RequestParam("clientId") long clientId, @RequestParam("jobId") long jobId, @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize){
		try {
			return clientJobService.showCandidates(clientId, jobId, pageNo, pageSize);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getAllClientAssignedCandidates")
	public ResponseEntity<Object> getAllClientAssignedCandidates(@RequestParam("clientId") long clientId, @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize){
		try {
			return clientJobService.getAllClientAssignedCandidates(clientId, pageNo, pageSize);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getIcpResult")
	public ResponseEntity<Object> getIcpSpectrumResult(@RequestParam("clientId") long clientId, @RequestParam("jobId") long jobId){
		try {
			return new ResponseEntity<>(clientTemplateService.getICPResult(clientId, jobId), null, HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/assignCandidatesToJob")
	public ResponseEntity<Object> assignCandidatesToJob(@RequestParam("clientId") long clientId, @RequestParam("jobId") long jobId, @RequestBody List<BatchCandidateResponse> req){
		try {
			return clientJobService.assignCandidatesToJob(clientId, jobId, req);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/addCandidateToJob")
	public ResponseEntity<Object> addCandidatesToJob(@RequestParam("clientId") long clientId, @RequestParam(defaultValue="0") long jobId, @RequestBody AssessmentBatchAddCandidateRequest req){
		try {
			return clientJobService.addCandidateToJob(req,clientId, jobId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getJobDescription") 
	public ResponseEntity<Object> getJobDescription(@RequestParam("clientId") long clientId, @RequestParam("jobId") long jobId){
		try {
			return clientJobService.getJobDescription(clientId, jobId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/cloneJob")
	public ResponseEntity<Object> cloneJob(@RequestParam("jobId") long jobId, @RequestParam("clientId") long clientId){
		try {
			return clientJobService.cloneJob(clientId, jobId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/deleteJob")
	public ResponseEntity<Object> deleteJob(@RequestParam("jobId") long jobId, @RequestParam("clientId") long clientId){
		try {
			return clientJobService.deleteJob(clientId, jobId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getJobDetailVersions")
	public ResponseEntity<Object> getJobDetailVersions(@RequestParam("jobId") long jobId, @RequestParam("clientId") long clientId){
		try {
			return clientJobService.getJobDetailVersions(clientId, jobId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getFilterJobs")
	public ResponseEntity<Object> getFilterJobs(@RequestParam("filterValue") String filterValue, @RequestParam("clientId") long clientId){
		try {
			return clientJobService.getFilterJobs(clientId, filterValue);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
//	@GetMapping("/updateScreeningQuestion")
//	public ResponseEntity<Object> updateScreeningQuestion(@RequestParam("jobId") long jobId, @RequestParam("clientId") long clientId, @RequestParam("screening") boolean screening){
//		try {
//			return clientJobService.updateScreening(clientId, jobId, screening);
//		}
//		catch(Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	
	@GetMapping("/updateSwitch")
	public ResponseEntity<Object> updateAssessment(@RequestParam("jobId") long jobId, @RequestParam("clientId") long clientId, @RequestParam("switchType") String switchType, @RequestParam("value") boolean value){
		try {
			return clientJobService.updateAssessment(clientId, jobId, switchType, value);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	
	@PostMapping("saveJobTitle")
	public ResponseEntity<Object> SaveJobTitle(@RequestBody JobTitleRequest req){
		try {
			return clientJobService.createJob(req);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getJD")
	public ResponseEntity<Object> getJD(@RequestParam("jobId") long jobId, @RequestParam("clientId") long clientId){
		try {
			return clientJobService.getJD(clientId, jobId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getExternalStaffingJobs")
	public ResponseEntity<Object> getExternalStaffingJobs(@RequestParam("clientId") long clientId){
		try {
			return clientJobService.getExternalStaffingJobs(clientId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/updateJobStatus")
	public ResponseEntity<Object> updateJobStatus(@RequestParam("clientId") long clientId, @RequestBody JobStatusRequest req){
		try {
			return clientJobService.updateJobStatus(clientId, req);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/updateCandidateStatus")
	public ResponseEntity<Object> updateCandidateStatus(@RequestParam("clientId") long clientId, @RequestBody JobStatusRequest req){
		try {
			return clientJobService.updateCandidateStatus(clientId, req);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

}
