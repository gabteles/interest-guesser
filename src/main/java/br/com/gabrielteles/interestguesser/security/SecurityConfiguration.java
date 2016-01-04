package br.com.gabrielteles.interestguesser.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import br.com.gabrielteles.interestguesser.web.CsrfHeaderFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	public static final String CSRF_COOKIE_NAME = "XSRF-TOKEN";
	public static final String CSRF_HEADER_NAME = "X-XSRF-TOKEN";
	public static final String CSRF_PARAM_NAME = "_csrf";
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf()
				.csrfTokenRepository(this.csrfTokenRepository())
				.and()
			.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
			.authorizeRequests()
				// SWAGGER
				.antMatchers(
					"/api-docs/**", 
					"/webjars/springfox-swagger-ui/**", 
					"/swagger-ui.html", 
					"/swagger-resources",
					"/configuration/ui",
					"/configuration/security"
				).permitAll()
				// API
				.antMatchers("/api/auth/login").permitAll()
				.antMatchers("/api/**").authenticated()
				// APPLICATION
				.antMatchers("/login").anonymous()
				.antMatchers(
					"/assets/**",
					"/fonts/**",
					"/maps/**",
					"/scripts/**",
					"/styles/**",
					"/favicon.ico"
				).permitAll()
				.antMatchers("/**").authenticated()
				.and()
			.sessionManagement()
				.maximumSessions(1).and()
				.and()
			.formLogin()
				.loginPage("/login")
				.defaultSuccessUrl("/", true)
				.permitAll()
				.and()
			.httpBasic();
	}
		
	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName(CSRF_HEADER_NAME);
		repository.setParameterName(CSRF_PARAM_NAME);
		return repository;
	}
}
