(function() {
  'use strict';

  angular
    .module('interestGuesser.login', ['classy'])
    .config(configure);

  /** @ngInject **/
  function configure($stateProvider) {
		$stateProvider
      .state('app.login', {
        url: '/login',
        views: {
        	'content@app': {
        		templateUrl: 'app/login/login.html',
            controller: 'LoginController',
            controllerAs: 'vm'
        	}
        }
      });
  }
})();
