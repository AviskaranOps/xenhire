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
import xenhire.constants.AppConstants;
import xenhire.model.Client;
import xenhire.request.AdminJobRequest;
import xenhire.response.CommonResponse;
import xenhire.service.AdminService;

@RestController
@Slf4j
public class AdminController {
	
	
	@Autowired
	AdminService adminService;

	
	@GetMapping("/getAdminDashboardProperties")
	public ResponseEntity<Object> getJobsAndActiveClientsAndApplicants(){
		try {
			return adminService.getJobsAndActiveClientsAndApplicants();
		}
		catch(Exception e) {
			e.printStackTrace();
			CommonResponse cr = CommonResponse.builder().data(null).message(e.getMessage()).result(AppConstants.FAILURE).build();
			return new ResponseEntity<>(cr, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getApplicationActivities")
	public ResponseEntity<Object> getApplicationRecentActivities(@RequestParam int pageNo, @RequestParam int pageSize){
		try {
			return adminService.getApplicationRecentActivities(pageNo, pageSize);
		}
		catch(Exception e) {
			e.printStackTrace();
			CommonResponse cr = CommonResponse.builder().data(null).message(e.getMessage()).result(AppConstants.FAILURE).build();
			return new ResponseEntity<>(cr, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	@GetMapping("/getAllClients")
	public ResponseEntity<Object> getAllClients(@RequestParam int pageNo, @RequestParam int pageSize){
		try {
			return adminService.getAllClients(pageNo, pageSize);
		}
		catch(Exception e) {
			e.printStackTrace();
			CommonResponse cr = CommonResponse.builder().data(null).message(e.getMessage()).result(AppConstants.FAILURE).build();
			return new ResponseEntity<>(cr, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getAllAdminJobs")
	public ResponseEntity<Object> getAllJobs(@RequestParam int pageNo, @RequestParam int pageSize){
		try {
			return adminService.getAllJobs(pageNo, pageSize);
		}
		catch(Exception e) {
			e.printStackTrace();
			CommonResponse cr = CommonResponse.builder().data(null).message(e.getMessage()).result(AppConstants.FAILURE).build();
			return new ResponseEntity<>(cr, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getAllJobsByFilter")
	public ResponseEntity<Object> getAllJobsByFilter(@RequestParam int pageNo, @RequestParam int pageSize, @RequestParam String filter){
		try {
			return adminService.getAllJobsByFilter(pageNo, pageSize, filter);
		}
		catch(Exception e) {
			e.printStackTrace();
			CommonResponse cr = CommonResponse.builder().data(null).message(e.getMessage()).result(AppConstants.FAILURE).build();
			return new ResponseEntity<>(cr, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@GetMapping("/getAllApplicants")
	public ResponseEntity<Object> getAllApplicants(@RequestParam int pageNo, @RequestParam int pageSize){
		try {
			return adminService.getAllApplicants(pageNo, pageSize);
		}
		catch(Exception e) {
			e.printStackTrace();
			CommonResponse cr = CommonResponse.builder().data(null).message(e.getMessage()).result(AppConstants.FAILURE).build();
			return new ResponseEntity<>(cr, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/saveClient")
	public ResponseEntity<Object> saveOrUpdateClient(@RequestBody Client req){
		try {
			return adminService.saveOrUpdateClient(req);
		}
		catch(Exception e) {
			e.printStackTrace();
			CommonResponse cr = CommonResponse.builder().data(null).message(e.getMessage()).result(AppConstants.FAILURE).build();
			return new ResponseEntity<>(cr, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping("/saveJob")
	public ResponseEntity<Object> saveJob(@RequestBody AdminJobRequest req){
		try {
			return adminService.saveJob(req);
		}
		catch(Exception e) {
			e.printStackTrace();
			CommonResponse cr = CommonResponse.builder().data(null).message(e.getMessage()).result(AppConstants.FAILURE).build();
			return new ResponseEntity<>(cr, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
}
