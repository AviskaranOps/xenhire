package xenhire.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import xenhire.model.CandidateReferences;
import xenhire.request.ClientReferenceRequest;
import xenhire.response.CommonResponse;
import xenhire.service.CandidateReferencesService;
import xenhire.constants.AppConstants;;

@RestController
@Slf4j
public class CandidateReferencesController {

	@Autowired
	private CandidateReferencesService candidateReferencesService;
	
	
	@GetMapping("/getReferencesCandidates")
	public ResponseEntity<Object> getReferencesCandidates(@RequestParam long clientId){
		try {
			return candidateReferencesService.getAllCandidatsWithReferences(clientId);
		}
		catch(Exception e) {
			e.printStackTrace();
			CommonResponse cr = CommonResponse.builder().data(null).message(e.getMessage()).result(AppConstants.FAILURE).build();
			return new ResponseEntity<>(cr, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/saveCandidateReference")
	public ResponseEntity<Object> saveCandidateReference(@RequestBody CandidateReferences req){
		try {
			return candidateReferencesService.saveCandidateReferences(req);
		}
		catch(Exception e) {
			e.printStackTrace();
			CommonResponse cr = CommonResponse.builder().data(null).message(e.getMessage()).result(AppConstants.FAILURE).build();
			return new ResponseEntity<>(cr, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getAllCandidateReferences")
	public ResponseEntity<Object> getAllCandidateReferences(@RequestParam long clientId, @RequestParam long candidateId){
		try {
			return candidateReferencesService.getAllCandidateReferences(clientId, candidateId);
		}
		catch(Exception e) {
			e.printStackTrace();
			CommonResponse cr = CommonResponse.builder().data(null).message(e.getMessage()).result(AppConstants.FAILURE).build();
			return new ResponseEntity<>(cr, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/sendReferenceNotification")
	public ResponseEntity<Object> sendReferenceNotification(@RequestParam long referenceId){
		try {
			return candidateReferencesService.sendReferenceNotification(referenceId);
		}
		catch(Exception e) {
			e.printStackTrace();
			CommonResponse cr = CommonResponse.builder().data(null).message(e.getMessage()).result(AppConstants.FAILURE).build();
			return new ResponseEntity<>(cr, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/sendAllReferenceNotification")
	public ResponseEntity<Object> sendAllReferenceNotification(@RequestParam long clientId, @RequestParam long candidateId){
		try {
			return candidateReferencesService.sendAllReferenceNotification(clientId, candidateId);
		}
		catch(Exception e) {
			e.printStackTrace();
			CommonResponse cr = CommonResponse.builder().data(null).message(e.getMessage()).result(AppConstants.FAILURE).build();
			return new ResponseEntity<>(cr, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/sendAskReferenceNotification")
	public ResponseEntity<Object> sendAskReferenceNotification(@RequestParam long clientId, @RequestParam long candidateId){
		try {
			return candidateReferencesService.sendAskReferenceNotification(clientId, candidateId);
		}
		catch(Exception e) {
			e.printStackTrace();
			CommonResponse cr = CommonResponse.builder().data(null).message(e.getMessage()).result(AppConstants.FAILURE).build();
			return new ResponseEntity<>(cr, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getClientReferenceQuestionnaire")
	public ResponseEntity<Object> getClientAssessment(){
		
		try {
			return candidateReferencesService.getClientQuestionnaire();
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@PostMapping("/saveClientReferenceQuestionnaire")
	public ResponseEntity<Object> saveClientReferenceQuestionnaire(@RequestBody ClientReferenceRequest req){
		try {
			return candidateReferencesService.saveClientReferencesQuestionnaire(req);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
