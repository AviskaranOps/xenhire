package xenhire.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
	@GetMapping("/")
	public ResponseEntity<Object> home(){
		Map m = new HashMap();
		m.put("AppName", "Xenhire");
		m.put("version", "0.0.1");
		m.put("timestamp", LocalDateTime.now());
		
		return new ResponseEntity<>(m, null, HttpStatus.OK);
	}

}
