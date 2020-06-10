var settings = {
    "async": true,
    "crossDomain": true,
    "url": "http://localhost:3000/account",
    "method": "GET",
    "headers": {
      "Content-Type": "application/json",
      "User-Agent": "PostmanRuntime/7.20.1",
      "Accept": "*/*",
      "Cache-Control": "no-cache",
      "Postman-Token": "98d71482-bdcd-4e03-aa1a-d712e32d3c26,0fb3f995-c8ee-4248-ae64-6c4b6dd96a8d",
      "Cookie": "connect.sid=s%3AlheecWbTzeP7B0YTJneawkcFHSH8V3RK.RwVfY0vLOH3yFd0QeJ51w7fPWNu3J6wsaUYbxA6jU9E",
      "Referer": "http://localhost:3000/flights/",
      "Connection": "keep-alive",
      "cache-control": "no-cache"
    },
    'xhrFields': {
        'withCredentials': true
    },
    "processData": false,
    "data": ""
  }
  $.ajax(settings).done(function( data ) {

    // fix data.obj.loc


    console.log(data.obj);


    document.getElementById("userFirstName").innerHTML += data.user.name;
    document.getElementById("userEmail").innerHTML += data.user.email;
    document.getElementById("userLocation").innerHTML += data.obj.name;


});
