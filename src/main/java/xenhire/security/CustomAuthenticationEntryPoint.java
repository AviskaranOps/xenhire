package xenhire.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("status", HttpStatus.UNAUTHORIZED.value());
        responseData.put("message", "Authentication failed");

        if (authException instanceof AuthenticationServiceException) {
            responseData.put("message", "Invalid token");
        } else if (authException instanceof CredentialsExpiredException) {
            responseData.put("message", "Token expired");
        }

        objectMapper.writeValue(response.getWriter(), responseData);
    }

//	@Override
//	public void commence(jakarta.servlet.http.HttpServletRequest request,
//			jakarta.servlet.http.HttpServletResponse response, AuthenticationException authException)
//			throws IOException, ServletException {
//		// TODO Auto-generated method stub
//		
//	}
    
}

