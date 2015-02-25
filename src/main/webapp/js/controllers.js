'use strict';

/**
 * The root simulationApp module.
 *
 * @type {simulationApp|*|{}}
 * 
 */

/*
 * 
 * Load the visualization API and the linechart package.
 */
google.load('visualization', '1', {packages:['corechart']});



var simulationApp  = simulationApp  || {};

/**
 * @ngdoc module
 * @name simulationControllers
 *
 * @description
 * Angular module for controllers.
 *
 */
simulationApp.controllers = angular.module('simulationControllers', ['ui.bootstrap']);

/**
 * @ngdoc controller
 * @name MyProfileCtrl
 *
 * @description
 * A controller used for the My Profile page.
 */
simulationApp.controllers.controller('MyProfileCtrl',
    function ($scope, $log, oauth2Provider, HTTP_ERRORS) {
        $scope.submitted = false;
        $scope.loading = false;

        /**
         * The initial profile retrieved from the server to know the dirty state.
         * @type {{}}
         */
        $scope.initialProfile = {};

        /**
         * Candidates for the teeShirtSize select box.
         * @type {string[]}
         */
        $scope.teeShirtSizes = [
            'XS',
            'S',
            'M',
            'L',
            'XL',
            'XXL',
            'XXXL'
        ];

        /**
         * Initializes the My profile page.
         * Update the profile if the user's profile has been stored.
         */
        $scope.init = function () {
            var retrieveProfileCallback = function () {
                $scope.profile = {};
                $scope.loading = true;
                gapi.client.simulation.getProfile().
                    execute(function (resp) {
                        $scope.$apply(function () {
                            $scope.loading = false;
                            if (resp.error) {
                                // Failed to get a user profile.
                            } else {
                                // Succeeded to get the user profile.
                                $scope.profile.displayName = resp.result.displayName;
                                $scope.profile.firstName = resp.result.firstName;
                                $scope.profile.lastName = resp.result.lastName;
                                $scope.profile.teeShirtSize = resp.result.teeShirtSize;
                                $scope.initialProfile = resp.result;
                            }
                        });
                    }
                );
            };
            if (!oauth2Provider.signedIn) {
                var modalInstance = oauth2Provider.showLoginModal();
                modalInstance.result.then(retrieveProfileCallback);
            } else {
                retrieveProfileCallback();
            }
        };

        /**
         * Invokes the simulation.saveProfile API.
         *
         */
        $scope.saveProfile = function () {
            $scope.submitted = true;
            $scope.loading = true;
            gapi.client.simulation.saveProfile($scope.profile).
                execute(function (resp) {
                    $scope.$apply(function () {
                        $scope.loading = false;
                        if (resp.error) {
                            // The request has failed.
                            var errorMessage = resp.error.message || '';
                            $scope.messages = 'Failed to update a profile : ' + errorMessage;
                            $scope.alertStatus = 'warning';
                            $log.error($scope.messages + 'Profile : ' + JSON.stringify($scope.profile));

                            if (resp.code && resp.code == HTTP_ERRORS.UNAUTHORIZED) {
                                oauth2Provider.showLoginModal();
                                return;
                            }
                        } else {
                            // The request has succeeded.
                            $scope.messages = 'The profile has been updated';
                            $scope.alertStatus = 'success';
                            $scope.submitted = false;
                            $scope.initialProfile = {
                                displayName: $scope.profile.displayName,
                                teeShirtSize: $scope.profile.teeShirtSize
                            };

                            $log.info($scope.messages + JSON.stringify(resp.result));
                        }
                    });
                });
        };
    })
;


/**
 * @ngdoc controller
 * @name SimulationCtrl
 *
 * @description
 * A controller used for the run simulation page.
 */
simulationApp.controllers.controller('SimulationCtrl',
    function ($scope, $log, oauth2Provider, HTTP_ERRORS) {
	 $scope.simulationRun = $scope.simulationRun || {};
	 $scope.simulationRun.batches=100;
	 $scope.simulationRun.randomNumbers=1000;   
	 $scope.simultationResult ={};
	 
	 $scope.showChart = false;
        /**
         * Invokes the simulation.runSimulation API.
         *
         * @param simulationForm the form object.
         */
        $scope.runSimulation = function () {
           
            $scope.loading = true;
            gapi.client.simulation.runSimulation($scope.simulationRun).
                execute(function (resp) {
                    $scope.$apply(function () {
                        $scope.loading = false;
                        if (resp.error) {
                            // The request has failed.
                            var errorMessage = resp.error.message || '';
                            $scope.messages = 'Failed to run simlulation : ' + errorMessage;
                            $scope.alertStatus = 'warning';
                            $log.error($scope.messages + ' Simulation : ' + JSON.stringify($scope.simulationRun));

                            if (resp.code && resp.code == HTTP_ERRORS.UNAUTHORIZED) {
                                oauth2Provider.showLoginModal();
                                return;
                            }
                        } else {
                            // The request has succeeded.
                            $scope.messages = 'Random number simulation complete ';
                            $scope.alertStatus = 'success';
                            $scope.submitted = false;
                            $scope.simulationRun = {};
                            $scope.simultationResult =resp.result;
                            $scope.showChart = true;
                            $log.info($scope.messages + ' : ' + JSON.stringify(resp.result));
                        }
                    });
                });
        };
    });

/**
 * @ngdoc controller
 * @name ShowsimulationCtrl
 *
 * @description
 * A controller used for the Show simulations page.
 */
simulationApp.controllers.controller('ResultsCtrl', function ($scope, $log, oauth2Provider, HTTP_ERRORS) {

    /**
     * Chart generation happens here
     * 
     */
	
	
   
});

simulationApp.controllers.controller('GoogleChartController',
		['$scope','$log','oauth2Provider','HTTP_ERRORS',
		    function($scope,$log, oauth2Provider, HTTP_ERRORS) {
			
			 $scope.simulationRun = $scope.simulationRun || {};
			 $scope.simulationRun.batches=100;
			 $scope.simulationRun.randomNumbers=1000;   
			 $scope.simultationResult = $scope.simultationResult || {};
			 $scope.simultationResult.commonNumber=0;
			 $scope.simultationResult.duration=0;
			 
			 var json_data = {};
			 var numberResults = [];
			

			 $scope.runSimulation = function () {
			
				

		            $scope.loading = true;
		            gapi.client.simulation.runSimulation($scope.simulationRun).
		                execute(function (resp) {
		                    $scope.$apply(function () {
		                        $scope.loading = false;
		                        if (resp.error) {
		                            // The request has failed.
		                            var errorMessage = resp.error.message || '';
		                            $scope.messages = 'Failed to run simlulation : ' + errorMessage;
		                            $scope.alertStatus = 'warning';
		                            $log.error($scope.messages + ' Simulation : ' + JSON.stringify($scope.simulationRun));

		                            if (resp.code && resp.code == HTTP_ERRORS.UNAUTHORIZED) {
		                                oauth2Provider.showLoginModal();
		                                return;
		                            }
		                        } else {
		                            // The request has succeeded.
		                            $scope.messages = 'Random number simulation complete. Please click on the visualisation tab to view the results. ';
		                            $scope.alertStatus = 'success';
		                            $scope.submitted = false;
		                            $scope.simulationRun = {};
		                            $scope.simultationResult =resp.result;
		                            json_data =  $scope.simultationResult.summary
		                            $scope.showChart = true;
		                            $log.info($scope.messages + ' : ' + JSON.stringify(resp.result.summary));
		                        }
		                    });
		                });
		        };
		       // var json_data = $scope.simultationResult.summary
		        $log.info($scope.messages + ' Message 2: ' + JSON.stringify($scope.simultationResult.summary));
		        $scope.$watch('simultationResult', function() {
					 
		        	 for(var i in json_data)
		                    numberResults.push([i, json_data [i]]);
				        var data = new google.visualization.DataTable();
				        
				        data.addColumn('string', 'Number');
				        data.addColumn('number', 'Frequency');
				        data.addRows(numberResults);
				        var options = {
						        title: 'Simulation Results'
						      };
						      var chart = {};
						      
						      chart.data = data;
						      chart.options = options;
						      $scope.chartTypes = [
						           		        {typeName: 'LineChart', typeValue: '1'},
						           		    
						           		        {typeName: 'ColumnChart', typeValue: '3'},
						           		        {typeName: 'PieChart', typeValue: '4'}
						           		      ];
						      
						      
						      $scope.chartType = $scope.chartTypes[0];
						      chart.type = $scope.chartTypes[0].typeValue;
						      $scope.chart = chart;
						      
						      $scope.selectType = function(type) {
								  $scope.chart.type = type.typeValue;
								}
			        
				   });
               
		     /* var data = google.visualization.arrayToDataTable([
		        ['Number', 'Frequency'],
		        ['2004', 1000],
		        ['2005', 1170],
		        ['2006', 660],
		        ['2007', 1030]
		      ]);*/
		      

				

		     
		     
		    }
		
		  ]

);




/**
 * @ngdoc controller
 * @name RootCtrl
 *
 * @description
 * The root controller having a scope of the body element and methods used in the application wide
 * such as user authentications.
 *
 */
simulationApp.controllers.controller('RootCtrl', function ($scope, $location, oauth2Provider) {

    /**
     * Returns if the viewLocation is the currently viewed page.
     *
     * @param viewLocation
     * @returns {boolean} true if viewLocation is the currently viewed page. Returns false otherwise.
     */
    $scope.isActive = function (viewLocation) {
        return viewLocation === $location.path();
    };

    /**
     * Returns the OAuth2 signedIn state.
     *
     * @returns {oauth2Provider.signedIn|*} true if siendIn, false otherwise.
     */
    $scope.getSignedInState = function () {
        return oauth2Provider.signedIn;
    };

    /**
     * Calls the OAuth2 authentication method.
     */
    $scope.signIn = function () {
        oauth2Provider.signIn(function () {
            gapi.client.oauth2.userinfo.get().execute(function (resp) {
                $scope.$apply(function () {
                    if (resp.email) {
                        oauth2Provider.signedIn = true;
                        $scope.alertStatus = 'success';
                        $scope.rootMessages = 'Logged in with ' + resp.email;
                    }
                });
            });
        });
    };

    /**
     * Render the signInButton and restore the credential if it's stored in the cookie.
     * (Just calling this to restore the credential from the stored cookie. So hiding the signInButton immediately
     *  after the rendering)
     */
    $scope.initSignInButton = function () {
        gapi.signin.render('signInButton', {
            'callback': function () {
                jQuery('#signInButton button').attr('disabled', 'true').css('cursor', 'default');
                if (gapi.auth.getToken() && gapi.auth.getToken().access_token) {
                    $scope.$apply(function () {
                        oauth2Provider.signedIn = true;
                    });
                }
            },
            'clientid': oauth2Provider.CLIENT_ID,
            'cookiepolicy': 'single_host_origin',
            'scope': oauth2Provider.SCOPES
        });
    };

    /**
     * Logs out the user.
     */
    $scope.signOut = function () {
        oauth2Provider.signOut();
        $scope.alertStatus = 'success';
        $scope.rootMessages = 'Logged out';
    };

    /**
     * Collapses the navbar on mobile devices.
     */
    $scope.collapseNavbar = function () {
        angular.element(document.querySelector('.navbar-collapse')).removeClass('in');
    };

});


/**
 * @ngdoc controller
 * @name OAuth2LoginModalCtrl
 *
 * @description
 * The controller for the modal dialog that is shown when an user needs to login to achive some functions.
 *
 */
simulationApp.controllers.controller('OAuth2LoginModalCtrl',
    function ($scope, $modalInstance, $rootScope, oauth2Provider) {
        $scope.singInViaModal = function () {
            oauth2Provider.signIn(function () {
                gapi.client.oauth2.userinfo.get().execute(function (resp) {
                    $scope.$root.$apply(function () {
                        oauth2Provider.signedIn = true;
                        $scope.$root.alertStatus = 'success';
                        $scope.$root.rootMessages = 'Logged in with ' + resp.email;
                    });

                    $modalInstance.close();
                });
            });
        };
    });

/**
 * @ngdoc controller
 * @name DatepickerCtrl
 *
 * @description
 * A controller that holds properties for a datepicker.
 */
simulationApp.controllers.controller('DatepickerCtrl', function ($scope) {
    $scope.today = function () {
        $scope.dt = new Date();
    };
    $scope.today();

    $scope.clear = function () {
        $scope.dt = null;
    };

    // Disable weekend selection
    $scope.disabled = function (date, mode) {
        return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
    };

    $scope.toggleMin = function () {
        $scope.minDate = ( $scope.minDate ) ? null : new Date();
    };
    $scope.toggleMin();

    $scope.open = function ($event) {
        $event.preventDefault();
        $event.stopPropagation();
        $scope.opened = true;
    };

    $scope.dateOptions = {
        'year-format': "'yy'",
        'starting-day': 1
    };

    $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'shortDate'];
    $scope.format = $scope.formats[0];
});





