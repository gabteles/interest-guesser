package br.com.gabrielteles.interestguesser.security;

import java.util.Collections;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import br.com.gabrielteles.interestguesser.domain.UserRepository;
import br.com.gabrielteles.interestguesser.domain.model.User;

@Component
public class AuthenticationProviderImpl implements AuthenticationProvider {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String email = authentication.getName();
		String password = (String)authentication.getCredentials();
		
		User user = userRepository.findOneByEmail(email);
		
		if (user == null) {
			throw new UsernameNotFoundException("Bad Credentials");
		} else if (user.getPassword().equals(password)) {
			Set<GrantedAuthority> autorities = Collections.emptySet();
			return new UsernamePasswordAuthenticationToken(
				authentication.getName(), 
				authentication.getCredentials(), 
				autorities
			);
		} else {
			throw new BadCredentialsException("Bad Credentials");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
