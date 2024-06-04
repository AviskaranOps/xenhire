package xenhire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import xenhire.service.UtilityService;

@RestController
public class UtilityController {
	
	@Autowired
	UtilityService utilityService;
	
	@PostMapping("/postClientQuestionnaire")
	public ResponseEntity<Object> postClientQuestionnaire(@RequestParam("file") MultipartFile file){
		try {
			return utilityService.postClientQuestionnaire(file);
		}
		catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/postCandidateQuestionnaire")
	public ResponseEntity<Object> postCandidateQuestionnaire(@RequestParam("file") MultipartFile file){
		try {
			return utilityService.postCandidateQuestionnaire(file);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

}
