'use strict';
 
/* Services */
 
// In this case it is a simple value service.


var simulationApp = simulationApp || {};

simulationApp.services = angular.module('simulationAppServices',[]);

simulationApp.services.value('version', '0.1');