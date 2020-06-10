const express = require('express');
const router = express.Router();
var request = require("request")

// gets user info from DB to pass to front end, via get call

router.get('/', (req, res, next) => {
    var options = {
        method: 'GET',
        url: 'http://localhost:3002/person/' + req.session.email,
        headers:
        {
            'cache-control': 'no-cache',
            Connection: 'keep-alive',
            Cookie: 'connect.sid=s%3AA7SbFSCni63RiWhvdiuhPJ58yFcE8k4_.UrKKwJl68lH2A5P1pIacUQFV6VEM%2BSsSXbC%2BKkgsO8o',
            'Content-Length': '65',
            'Accept-Encoding': 'gzip, deflate',
            Host: 'localhost:3002',
            'Postman-Token': 'cab16a67-5921-416d-9cbc-8d2e918fcb2c,fe7addb0-18fc-41a8-9785-534d9801254c',
            'Cache-Control': 'no-cache',
            Accept: '*/*',
            'User-Agent': 'PostmanRuntime/7.20.1',
            'Content-Type': 'application/json'
        },
        json: true
    };

    request(options, function (error, response, body) {
        if (error) throw new Error(error);

        console.log(body);
        test = {user: body, obj: req.session.obj};
        console.log(test)
        res.status(200).json(test);
    });
    
});

//functionality for a delete account button ! 

router.get('/delete', (req, res, next) => {var options = { method: 'DELETE',
url: 'http://localhost:3002/person/' + req.session.email,
headers: 
 { 'cache-control': 'no-cache',
   Connection: 'keep-alive',
   'Content-Length': '0',
   Cookie: 'connect.sid=s%3AlheecWbTzeP7B0YTJneawkcFHSH8V3RK.RwVfY0vLOH3yFd0QeJ51w7fPWNu3J6wsaUYbxA6jU9E',
   'Accept-Encoding': 'gzip, deflate',
   Host: 'localhost:3002',
   'Postman-Token': 'fc9b10f0-5b58-4f11-a183-dfb04b077de9,8eeb34bb-e80d-4cf6-865e-01e9d7467cf7',
   'Cache-Control': 'no-cache',
   Accept: '*/*',
   'User-Agent': 'PostmanRuntime/7.20.1' } };

request(options, function (error, response, body) {
if (error) throw new Error(error);

console.log(body);
res.redirect('http://localhost:3001/index')
});

    
});
module.exports = router;