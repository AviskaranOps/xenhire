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

import xenhire.model.ClientTeamTemplate;
import xenhire.model.JobTemplate;
import xenhire.request.ClientAssessmentRequest;
import xenhire.request.ClientIcpRequest;
import xenhire.request.ClientValuesRequest;
import xenhire.request.ClinetPreferenceTemplateRequest;
import xenhire.request.JobDetailsRequest;
import xenhire.service.ClientTemplateService;

@RestController
public class ClientTemplatesController {
	
	@Autowired
	ClientTemplateService clientTemplateService;
	
	@GetMapping("/getJobTemplate")
	public ResponseEntity<Object> getJobTemplate(@RequestParam("clientId") long clientId, @RequestParam("templateId") long templateId){
		try {
			return new ResponseEntity<>(clientTemplateService.getJobTemplate(clientId, templateId), null, HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@GetMapping("/getAllJobTemplate")
	public ResponseEntity<Object> getJobTemplateList(@RequestParam("clientId") long clientId, @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize){
		try {
			return clientTemplateService.getAllJobTemplates(clientId, pageNo, pageSize);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@PostMapping("/saveJobTemplate")
	public ResponseEntity<Object> saveJobTemplate(@RequestParam("clientId") long clientId, @RequestBody JobDetailsRequest req ){
		try {
			return clientTemplateService.saveJobTemplate(clientId, req);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getValueTemplate")
	public ResponseEntity<Object> getValueTemplate(@RequestParam("clientId") long clientId, @RequestParam("templateId") long templateId){
		try {
			return new ResponseEntity<>(clientTemplateService.getValueTemplate(clientId, templateId), null, HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/saveValueTemplate")
	public ResponseEntity<Object> saveValueTemplate(@RequestParam("clientId") long clientId, @RequestBody ClientValuesRequest req ){
		try {
			return clientTemplateService.saveClientValues(clientId, req);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getAllValueTemplate")
	public ResponseEntity<Object> getValueTemplateList(@RequestParam("clientId") long clientId, @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize){
		try {
			return clientTemplateService.getAllValueTemplates(clientId, pageNo, pageSize);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getTeamTemplate")
	public ResponseEntity<Object> getTeamTemplate(@RequestParam("clientId") long clientId, @RequestParam("templateId") long templateId){
		try {
			return new ResponseEntity<>(clientTemplateService.getTeamTemplate(clientId, templateId), null, HttpStatus.OK);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/saveTeamTemplate")
	public ResponseEntity<Object> saveTeamTemplate(@RequestParam("clientId") long clientId, @RequestBody ClientTeamTemplate req ){
		try {
			return clientTemplateService.saveTeamTemplate(clientId, req);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getAllTeamTemplate")
	public ResponseEntity<Object> getTeamTemplateList(@RequestParam("clientId") long clientId, @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize){
		try {
			return clientTemplateService.getAllTeamTemplates(clientId, pageNo, pageSize);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getPreferenceTemplate")
	public ResponseEntity<Object> getPreferenceTemplate(@RequestParam("clientId") long clientId, @RequestParam("templateId") long templateId){
		try {
			return clientTemplateService.getJobPreferenceTemplate(clientId, templateId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/savePreferenceTemplate")
	public ResponseEntity<Object> savePreferenceTemplate(@RequestParam("clientId") long clientId, @RequestBody ClinetPreferenceTemplateRequest req ){
		try {
			return clientTemplateService.savePreferenceTemplate(clientId, req);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getAllPreferenceTemplate")
	public ResponseEntity<Object> getPreferenceTemplateList(@RequestParam("clientId") long clientId, @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize){
		try {
			return clientTemplateService.getAllPreferenceTemplate(clientId, pageNo, pageSize);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/saveIcpTemplate")
	public ResponseEntity<Object> saveIcpTemplate(@RequestParam("clientId") long clientId, @RequestBody ClientIcpRequest req ){
		try {
			return clientTemplateService.saveClientICPTempalte(req, clientId);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@GetMapping("/getIcpTemplateResult")
	public ResponseEntity<Object> getIcpTempalteResult(@RequestParam("clientId") long clientId, @RequestParam("templateNo") int templateNo){
		try {
			return new ResponseEntity<>(clientTemplateService.getICPResult(clientId, templateNo), null, HttpStatus.OK);
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getAllIcpTemplates")
	public ResponseEntity<Object> getAllIcpTemplateList(@RequestParam("clientId") long clientId, @RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize){
		try {
			return clientTemplateService.getAllIcpTemplate(clientId, pageNo, pageSize);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
