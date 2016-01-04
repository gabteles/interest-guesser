(function() {
	'use strict';

	angular.module('interestGuesser.login').classy.controller({
		name: 'LoginController',
		
		inject: ['BackendApi'],
		
		init: function() {
			this.user = {
				email: "",
				password: ""
			}
		},

		methods: {
			login: function() {
				this.BackendApi.clientAuthorizations.add(
					"userCredentials", 
					new SwaggerClient.PasswordAuthorization(this.user.email, this.user.password)
				);

				this.BackendApi.auth.login().then(function(response) {
					this.BackendApi.clientAuthorizations.remove("userCredentials");

					var authToken = response.headers['x-auth-token'];
					
					this.BackendApi.clientAuthorizations.add(
						"authToken", 
						new SwaggerClient.ApiKeyAuthorization("X-AUTH-TOKEN", authToken, "header")
					);
				}.bind(this), function(error) {
					console.log(error);
				}.bind(this));
			}
		}
	});
})();