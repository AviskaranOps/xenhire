package xenhire.request;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xenhire.model.Role;
import xenhire.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OtpRequest {
	
	String email;
	String otp;

}
