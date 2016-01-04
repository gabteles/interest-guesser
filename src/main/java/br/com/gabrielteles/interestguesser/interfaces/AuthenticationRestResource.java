package br.com.gabrielteles.interestguesser.interfaces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.gabrielteles.interestguesser.application.AuthenticationApplicationService;
import br.com.gabrielteles.interestguesser.util.SuccessIndicator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "/authentication", tags = "auth")
public class AuthenticationRestResource {
	
	@Autowired
	AuthenticationApplicationService authenticationApplicationService;
	
	@RequestMapping(path = "/login", method = { RequestMethod.GET })
	@ApiOperation(value = "Authenticates an user", nickname = "login")
	@ApiResponses({
		@ApiResponse(code = 200, message = "Logged")
	})
	@CrossOrigin(exposedHeaders = "x-auth-token")
	public SuccessIndicator login() {
		System.out.println("CATCHA!");
		return SuccessIndicator.indicate(true);
	}
}
