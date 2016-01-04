
(function() {
  'use strict';

  angular
    .module('interestGuesser')
    .config(routerConfig);

  /** @ngInject */
  function routerConfig($stateProvider, $urlRouterProvider, $locationProvider) {
    $locationProvider.html5Mode(true);
    
    $stateProvider
      .state('app', {
        abstract: true,
        views: {
        	'@': {
        		templateUrl: 'app/template/template.html'
        	},
        	'toolbar@app': {
        		templateUrl: 'app/toolbar/toolbar.html'
        	}
        },
        resolve: {
          BackendApi: function() {
            return new SwaggerClient({
              url: "http://localhost:8080/api-docs/v2",
              usePromise: true
            });
          }
        }
      });

    $urlRouterProvider.otherwise('/login');
  }

})();