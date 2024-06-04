package xenhire.response;

import xenhire.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {

	  long userId;
	  String username;
	  String email;
	  String accessToken;
	  String role;

}
