package xenhire.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(UserDetailsService userDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable().authorizeHttpRequests()
				.requestMatchers(
						"/getEncryptedPass","/" , "/login", "/saveClientAssessment", "/xen/saveClientAssessment", "/xen/signup","/signup",
						"/saveCandidateAssessment", "/xen/saveCandidateAssessment", "/getClientQuestionnaire", "/xen/getClientQuestionnaire",
						"/getCandidateQuestionnaire", "/xen/getCandidateQuestionnaire", "/saveClientForm", "/xen/saveClientForm", "/getSection", "/xen/getSection",
						"/saveCandidateForm", "/xen/saveCandidateForm", "/getCandidateValuesQuestionnaire", "/xen/getCandidateValuesQuestionnaire",
						"/postClientQuestionnaire", "/xen/postClientQuestionnaire", "/postCandidateQuestionnaire", "/xen/postCandidateQuestionnaire",
						"/postCandidateRanking", "/xen/postCandidateRanking", "/getCandidateValuesQuestionnaire", "/xen/getCandidateValuesQuestionnaire",
						"/saveCandidateValueAssessment", "/xen/saveCandidateValueAssessment", "/saveClientValueAssessment", "/xen/saveClientValueAssessment",
						"/getJobMatchingScore", "/xen/getJobMatchingScore", "/getClientIcpReport", "/xen/getClientIcpReport", "/getCandidateDtpReport", "/xen/getCandidateDtpReport"
						
						
				)
				
				.permitAll().and().authorizeHttpRequests().anyRequest().authenticated().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class).build();
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
