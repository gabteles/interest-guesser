package br.com.gabrielteles.interestguesser.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import br.com.gabrielteles.interestguesser.security.SecurityConfiguration;

@Component
public class CsrfHeaderFilter extends OncePerRequestFilter {
	@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		CsrfToken csrf = (CsrfToken)request.getAttribute(CsrfToken.class.getName());
		
		if (csrf != null) {
            Cookie cookie = WebUtils.getCookie(request, SecurityConfiguration.CSRF_COOKIE_NAME);
            String token = csrf.getToken();
            
            if ((cookie == null) || token != null && !token.equals(cookie.getValue())) {
                cookie = new Cookie(SecurityConfiguration.CSRF_COOKIE_NAME, token);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        
        filterChain.doFilter(request, response);
    }
}