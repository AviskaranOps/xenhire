package xenhire.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		 http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authorizeRequest->{
					authorizeRequest.requestMatchers(AntPathRequestMatcher.antMatcher("/xen/swagger-ui.html")).permitAll();
					authorizeRequest.requestMatchers(AntPathRequestMatcher.antMatcher("/xen/swagger-ui/**")).permitAll();
					authorizeRequest.requestMatchers(AntPathRequestMatcher.antMatcher("/xen/v*/api-docs/**")).permitAll();
					authorizeRequest.requestMatchers(AntPathRequestMatcher.antMatcher("/xen/swagger-resources/**")).permitAll();
					authorizeRequest.requestMatchers(AntPathRequestMatcher.antMatcher("/xen/login")).permitAll();
					authorizeRequest.anyRequest().authenticated();
		}).sessionManagement(sessionManageMent->sessionManageMent.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
				 return http.build();
    }
   
	@Bean
	public AuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
			authenticationProvider.setUserDetailsService(userDetailsService);
			authenticationProvider.setPasswordEncoder(passwordEncoder());
			return authenticationProvider;
	}
		
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

  
}
