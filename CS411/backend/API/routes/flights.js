
const express = require('express');
const router = express.Router();
var request = require("request");

var code = "";

router.get('/LHR', (req, res, next) => {

  console.log(req.session)
    var request = require("request");

var options = { method: 'GET',
  url: 'http://flightxml.flightaware.com/json/FlightXML2/AirlineFlightSchedules',
  qs: 
   { startDate: '1576007032',
     endDate: '1576093432',
     origin: 'BOS',
     destination: 'LHR',
     howMany: '15',
     offset: '0' },
  headers: 
   { 'cache-control': 'no-cache',
     Connection: 'keep-alive',
     Host: 'flightxml.flightaware.com',
     'Postman-Token': '25e52a65-89e0-4272-b478-0d1b659f31a8,3394a9da-d9ec-4de9-b034-2455a544fa12',
     'Cache-Control': 'no-cache',
     Accept: '*/*',
     'User-Agent': 'PostmanRuntime/7.20.1',
     Authorization: 'Basic ZW1tYWxsYTpiMTlkZjQ0Y2Q4Nzk0NjIyYTQ5ZDhhOWNjYTFiNzhmNmEwMjYzM2Jl',
     'Content-Type': 'application/json' } ,
    json:true};

request(options, function (error, response, body) {
  
  if (error) throw new Error(error);

  console.log(body);
  res.status(200).json(body);
});
});

 

router.get('/',(req, res, next) => {
  console.log(req.session)

var options = { method: 'GET',
  url: 'http://iatageo.com/getCode/' + req.session.lat + '/' + req.session.lon,
  headers: 
   { 'cache-control': 'no-cache',
     Connection: 'keep-alive',
     Host: 'iatageo.com',
     'Postman-Token': 'e6b4b044-73fc-4124-a04b-96a3461525b0,4114284b-a272-439a-8bad-0440d0453345',
     'Cache-Control': 'no-cache',
     Accept: '*/*',
     'User-Agent': 'PostmanRuntime/7.20.1' }, 
     json:true};
     console.log(options.url);

request(options, function (error, response, body) {
  if (error) throw new Error(error);

  console.log(body);
  code = body.code;
  res.redirect('/flights/go')
});

});

// make sure all is well push


router.get('/go', (req, res, next) => {
    var request = require("request");

var options = { method: 'GET',
  url: 'http://flightxml.flightaware.com/json/FlightXML2/AirlineFlightSchedules',
  qs: 
   { startDate: '1576007032',
     endDate: '1576093432',
     origin: 'BOS',
     destination: code,
     howMany: '15',
     offset: '0' },
  headers: 
   { 'cache-control': 'no-cache',
     Connection: 'keep-alive',
     Host: 'flightxml.flightaware.com',
     'Postman-Token': '25e52a65-89e0-4272-b478-0d1b659f31a8,3394a9da-d9ec-4de9-b034-2455a544fa12',
     'Cache-Control': 'no-cache',
     Accept: '*/*',
     'User-Agent': 'PostmanRuntime/7.20.1',
     Authorization: 'Basic ZW1tYWxsYTpiMTlkZjQ0Y2Q4Nzk0NjIyYTQ5ZDhhOWNjYTFiNzhmNmEwMjYzM2Jl',
     'Content-Type': 'application/json' }, json:true };

request(options, function (error, response, body) {
  if (error) throw new Error(error);

  console.log(body);
  res.status(200).json(body);
});

});

module.exports = router;
