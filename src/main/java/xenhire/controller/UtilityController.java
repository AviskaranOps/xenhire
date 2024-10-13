package xenhire.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
			e.printStackTrace();
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
	
	
	@PostMapping("/postAssessments")
	public ResponseEntity<Object> postAssessments(@RequestParam("file") MultipartFile file){
		try {
			return utilityService.postAssessments(file);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getOptionsList")
	public ResponseEntity<Object> getOptionsList(@RequestParam("paramList") String paramList){
		List<Map> resp = new ArrayList();
		System.out.println(paramList);
		for(String str : paramList.split(",")) {
			System.out.println(str);
			Map m = new HashMap();
			m.put("label", str);
			m.put("value", str);
			resp.add(m);
		}
		return new ResponseEntity<>(resp, null, HttpStatus.OK);
	}
	
	
	

}
