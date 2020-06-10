const express = require('express');
const router = express.Router();
var request = require("request");

// 5 options based on the 5 types of trips!

// note: lat and long boxes are severely limited due to the api and so this is unfortunately not worldwide


router.get('/nature',(req, res, next) => { 
  
    var options = { method: 'GET',
  url: 'http://api.opentripmap.com/0.1/en/places/bbox',
  qs: 
   { lon_min: '-2',
     lat_min: '48',
     lon_max: '0',
     lat_max: '52',
     kinds: 'natural',
     format: 'json',
     apikey: '5ae2e3f221c38a28845f05b6680bb28f85eb9d9eedd9330416422bf5' },
  headers: 
   { 'cache-control': 'no-cache',
     Connection: 'keep-alive',
     Host: 'api.opentripmap.com',
     'Postman-Token': '966a918e-512a-4ca7-b434-812dc57add2f,a4cee2eb-6998-4908-afc7-216cd7658c90',
     'Cache-Control': 'no-cache',
     Accept: '*/*',
     'User-Agent': 'PostmanRuntime/7.20.1' },
     json:true };

request(options, function (error, response, body) {
  if (error) throw new Error(error);
  var randomnumber=Math.floor(Math.random()*501); // api returns 500 destination ideas, choose one randomly
 
  
  req.session.lat = body[randomnumber].point.lat; // stpre lon/lat details of destination in the session for flight api use
  req.session.lon = body[randomnumber].point.lon;
  req.session.obj = body[randomnumber]; // store place obj in session for recent history
  req.session.save();


  /*var options = { method: 'PUT',
  url: 'http://localhost:3002/person/' + req.session.email,
  headers: 
   { 'cache-control': 'no-cache',
     Connection: 'keep-alive',
     'Content-Length': '255',
     Host: 'localhost:3002',
     'Postman-Token': 'ed8b65e3-6ae5-424e-86e7-8c9b599ad4cc,4317cf26-ae46-40e7-add6-5aee9f61cdc3',
     'Cache-Control': 'no-cache',
     Accept: '/',
     'User-Agent': 'PostmanRuntime/7.18.0',
     'Content-Type': 'application/json' },
  body: 
   {"loc": req.session.obj.name},
  json: true };

request(options, function (error, response, body) {
  if (error) throw new Error(error);
  console.log("hi")
  console.log(options.body);
});
console.log("made it to the end")*/

// the goal above was to store recent locations in db, isn't quite working yet but db still stores user data !

res.status(200).json(body[randomnumber]); // pass back the chosen location 
});

// note : process is the same for each type of trip and so not all are commented in detail like above


});


  router.get('/museums',(req, res, next) => {
    var options = { method: 'GET',
  url: 'http://api.opentripmap.com/0.1/en/places/bbox',
  qs: 
   { lon_min: '-1',
     lat_min: '50',
     lon_max: '1',
     lat_max: '52',
     kinds: 'museums',
     format: 'json',
     apikey: '5ae2e3f221c38a28845f05b6680bb28f85eb9d9eedd9330416422bf5' },
  headers: 
   { 'cache-control': 'no-cache',
     Connection: 'keep-alive',
     Host: 'api.opentripmap.com',
     'Postman-Token': '904c5b35-2344-4247-ab8f-bcc17e9ee4b6,f2edf387-e989-41c9-8157-10fa5f729fa1',
     'Cache-Control': 'no-cache',
     Accept: '*/*',
     'User-Agent': 'PostmanRuntime/7.20.1' },
     json:true };
     console.log('hello darkness');

request(options, function (error, response, body) {
  if (error) throw new Error(error);
  //var randomnumber=Math.floor(Math.random()*501);
  var randomnumber = 50 // this is for demo purposes, simply so we can  be sure that the flight page will be used and not the 'sorry, no flights' page - at least once !
  req.session.lat = body[randomnumber].point.lat;
  req.session.lon = body[randomnumber].point.lon;
  req.session.save();
  
  // again, the put request to save the last location to DB 

  var options = { method: 'PUT',
  url: 'http://localhost:3002/person/' + req.session.email,
  headers: 
   { 'cache-control': 'no-cache',
     Connection: 'keep-alive',
     'Content-Length': '255',
     'Accept-Encoding': 'gzip, deflate',
     Host: 'localhost:3002',
     'Postman-Token': 'ed8b65e3-6ae5-424e-86e7-8c9b599ad4cc,4317cf26-ae46-40e7-add6-5aee9f61cdc3',
     'Cache-Control': 'no-cache',
     Accept: '/',
     'User-Agent': 'PostmanRuntime/7.18.0',
     'Content-Type': 'application/json' },
  body: 
   { loc: body[randomnumber] },
  json: true };

request(options, function (error, response, body) {
  if (error) throw new Error(error);

  console.log(body);
});
  res.status(200).json(body[randomnumber]); // pass the chosen destination to front end
});

  });


  // process is exactly the same for the remaining 3 options ! 
  

  router.get('/shows',(req, res, next) => {
    var options = { method: 'GET',
  url: 'http://api.opentripmap.com/0.1/en/places/bbox',
  qs: 
   { lon_min: '30',
     lat_min: '-15',
     lon_max: '75',
     lat_max: '30',
     kinds: 'opera_houses,music_venues,other_theatres',
     format: 'json',
     apikey: '5ae2e3f221c38a28845f05b6680bb28f85eb9d9eedd9330416422bf5' },
  headers: 
   { 'cache-control': 'no-cache',
     Connection: 'keep-alive',
     Host: 'api.opentripmap.com',
     'Postman-Token': 'a52001ed-36df-4d27-b66a-234047493572,52e03b95-e895-452b-a4a2-4a3b4f847851',
     'Cache-Control': 'no-cache',
     Accept: '*/*',
     'User-Agent': 'PostmanRuntime/7.20.1' },
     json:true };

request(options, function (error, response, body) {
  if (error) throw new Error(error);
  var randomnumber=Math.floor(Math.random()*501);
  console.log(body[randomnumber]);
  req.session.lat = body[randomnumber].point.lat;
  req.session.lon = body[randomnumber].point.lon;
  req.session.save();
  console.log(req.session.lat)
  console.log(req.session.lon)
  console.log(req.session.id)
  var options = { method: 'PUT',
  url: 'http://localhost:3002/person/' + req.session.email,
  headers: 
   { 'cache-control': 'no-cache',
     Connection: 'keep-alive',
     'Content-Length': '255',
     'Accept-Encoding': 'gzip, deflate',
     Host: 'localhost:3002',
     'Postman-Token': 'ed8b65e3-6ae5-424e-86e7-8c9b599ad4cc,4317cf26-ae46-40e7-add6-5aee9f61cdc3',
     'Cache-Control': 'no-cache',
     Accept: '/',
     'User-Agent': 'PostmanRuntime/7.18.0',
     'Content-Type': 'application/json' },
  body: 
   { loc: body[randomnumber] },
  json: true };

request(options, function (error, response, body) {
  if (error) throw new Error(error);

  console.log(body);
});
  res.status(200).json(body[randomnumber]);
});

  })
  

  router.get('/history',(req, res, next) => {
    var options = { method: 'GET',
  url: 'http://api.opentripmap.com/0.1/en/places/bbox',
  qs: 
   {  lon_min: '30',
      lat_min: '-15',
      lon_max: '75',
      lat_max: '30',
     kinds: 'historical_places,castles,monuments,roman_villas,crypts',
     format: 'json',
     apikey: '5ae2e3f221c38a28845f05b6680bb28f85eb9d9eedd9330416422bf5' },
  headers: 
   { 'cache-control': 'no-cache',
     Connection: 'keep-alive',
     Host: 'api.opentripmap.com',
     'Postman-Token': '75d372ae-5d2d-4803-9b70-618529b0907e,14081fa9-e495-4ee4-9be3-2aadfd5b9295',
     'Cache-Control': 'no-cache',
     Accept: '*/*',
     'User-Agent': 'PostmanRuntime/7.20.1' },
     json:true};
     

request(options, function (error, response, body) {
  if (error) throw new Error(error);
  var randomnumber=Math.floor(Math.random()*501);
  console.log(body[randomnumber]);
  req.session.lat = body[randomnumber].point.lat;
  req.session.lon = body[randomnumber].point.lon;
  req.session.save();
  console.log(req.session.lat)
  console.log(req.session.lon)
  console.log(req.session.id)
  var options = { method: 'PUT',
  url: 'http://localhost:3002/person/' + req.session.email,
  headers: 
   { 'cache-control': 'no-cache',
     Connection: 'keep-alive',
     'Content-Length': '255',
     'Accept-Encoding': 'gzip, deflate',
     Host: 'localhost:3002',
     'Postman-Token': 'ed8b65e3-6ae5-424e-86e7-8c9b599ad4cc,4317cf26-ae46-40e7-add6-5aee9f61cdc3',
     'Cache-Control': 'no-cache',
     Accept: '/',
     'User-Agent': 'PostmanRuntime/7.18.0',
     'Content-Type': 'application/json' },
  body: 
   { loc: body[randomnumber] },
  json: true };

request(options, function (error, response, body) {
  if (error) throw new Error(error);

  console.log(body);
});
  res.status(200).json(body[randomnumber]);

  
});

  })
  

  router.get('/active',(req, res, next) => {
    var request = require("request");

var options = { method: 'GET',
  url: 'http://api.opentripmap.com/0.1/en/places/bbox',
  qs: 
   {  lon_min: '30',
      lat_min: '-15',
      lon_max: '75',
      lat_max: '30',
     kinds: 'winter_sports,climbing,diving',
     format: 'json',
     apikey: '5ae2e3f221c38a28845f05b6680bb28f85eb9d9eedd9330416422bf5' },
  headers: 
   { 'cache-control': 'no-cache',
     Connection: 'keep-alive',
     Host: 'api.opentripmap.com',
     'Postman-Token': '773a05cf-d724-4907-8367-df28b3c9dc48,60d83b5f-571e-4ef3-b7ab-c68a5e21460d',
     'Cache-Control': 'no-cache',
     Accept: '*/*',
     'User-Agent': 'PostmanRuntime/7.20.1' },
     json:true };

request(options, function (error, response, body) {
  if (error) throw new Error(error);
  var randomnumber=Math.floor(Math.random()*501);
  console.log(body[randomnumber]);
  req.session.lat = body[randomnumber].point.lat;
  req.session.lon = body[randomnumber].point.lon;
  req.session.save();
  console.log(req.session.lat)
  console.log(req.session.lon)
  console.log(req.session.id)
  var options = { method: 'PUT',
  url: 'http://localhost:3002/person/' + req.session.email,
  headers: 
   { 'cache-control': 'no-cache',
     Connection: 'keep-alive',
     'Content-Length': '255',
     'Accept-Encoding': 'gzip, deflate',
     Host: 'localhost:3002',
     'Postman-Token': 'ed8b65e3-6ae5-424e-86e7-8c9b599ad4cc,4317cf26-ae46-40e7-add6-5aee9f61cdc3',
     'Cache-Control': 'no-cache',
     Accept: '/',
     'User-Agent': 'PostmanRuntime/7.18.0',
     'Content-Type': 'application/json' },
  body: 
   { loc: body[randomnumber] },
  json: true };

request(options, function (error, response, body) {
  if (error) throw new Error(error);

  console.log(body);
});
  res.status(200).json(body[randomnumber]);
});
 });



module.exports = router;

