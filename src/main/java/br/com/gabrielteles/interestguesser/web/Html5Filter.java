package br.com.gabrielteles.interestguesser.web;
import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(0)
public class Html5Filter extends OncePerRequestFilter {

	private List<RequestMatcher> staticResources = asList(
		// SWAGGER
		new AntPathRequestMatcher("/swagger-ui.html"),
		new AntPathRequestMatcher("/webjars/springfox-swagger-ui/**"),
		// APPLICATION
		new AntPathRequestMatcher("/assets/**"),
		new AntPathRequestMatcher("/fonts/**"),
		new AntPathRequestMatcher("/maps/**"),
		new AntPathRequestMatcher("/scripts/**"),
		new AntPathRequestMatcher("/styles/**"),
		new AntPathRequestMatcher("/favicon.ico"),
		new AntPathRequestMatcher("/"),
		new AntPathRequestMatcher("/login")
	);
	
	private List<RequestMatcher> dynamicResources = asList(
		// SWAGGER
		new AntPathRequestMatcher("/api-docs/**"),
		new AntPathRequestMatcher("/swagger-resources"),
		new AntPathRequestMatcher("/configuration/ui"),
		new AntPathRequestMatcher("/configuration/security"),
		// APPLICATION
		new AntPathRequestMatcher("/api/**")
	);
		
    @Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    	if (matches(request, staticResources) || matches(request, dynamicResources)) {
            filterChain.doFilter(request, response);
        } else {
            request.getRequestDispatcher("/").forward(request, response);
        }
    }

	private boolean matches(HttpServletRequest request, List<RequestMatcher> resources) {
		return resources.stream().anyMatch(m -> m.matches(request));
	}
	
}