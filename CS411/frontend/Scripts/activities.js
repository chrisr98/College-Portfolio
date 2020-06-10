document.getElementById("nature").onclick = function() {
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "http://localhost:3000/where/nature",
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
        document.getElementById("Header").innerHTML = "Nature";
        name = data.name
        var placeToVisit = document.createElement("P");
        placeToVisit.innerHTML = "You should visit \"" + name + "\"";
        document.getElementById("Info").innerHTML = ""
        document.getElementById("Info").appendChild(placeToVisit);
        console.log(data.name);


        var x = document.getElementById("goButton");
        x.style.display = "block";

        var y = document.getElementById("resultCards");
        y.style.display = "block";


    });
}



document.getElementById("museums").onclick = function() {
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "http://localhost:3000/where/museums",
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
        document.getElementById("Header").innerHTML = "Museums";
        name = data.name
        var placeToVisit = document.createElement("P");
        placeToVisit.innerHTML = "You should check out \"" + name + "\"";
        document.getElementById("Info").innerHTML = ""
        document.getElementById("Info").appendChild(placeToVisit);
        console.log(data.name);

        var x = document.getElementById("goButton");
        x.style.display = "block";

        var y = document.getElementById("resultCards");
        y.style.display = "block";

    });
}

document.getElementById("shows").onclick = function() {
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "http://localhost:3000/where/shows",
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
        document.getElementById("Header").innerHTML = "Entertainment";
        name = data.name
        var placeToVisit = document.createElement("P");
        placeToVisit.innerHTML = "You should look up \"" + name + "\"";
        document.getElementById("Info").innerHTML = ""
        document.getElementById("Info").appendChild(placeToVisit);
        console.log(data.name);

        var x = document.getElementById("goButton");
        x.style.display = "block";

        var y = document.getElementById("resultCards");
        y.style.display = "block";

    });
}

document.getElementById("history").onclick = function() {
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "http://localhost:3000/where/history",
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
        document.getElementById("Header").innerHTML = "History";
        name = data.name
        var placeToVisit = document.createElement("P");
        placeToVisit.innerHTML = "You should go to \"" + name + "\"";
        document.getElementById("Info").innerHTML = ""
        document.getElementById("Info").appendChild(placeToVisit);
        console.log(data.name);

        var x = document.getElementById("goButton");
        x.style.display = "block";

        var y = document.getElementById("resultCards");
        y.style.display = "block";

    });
}

document.getElementById("active").onclick = function() {
    var settings = {
        "async": true,
        "crossDomain": true,
        "url": "http://localhost:3000/where/active",
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
        document.getElementById("Header").innerHTML = "Active";
        name = data.name
        var placeToVisit = document.createElement("P");
        placeToVisit.innerHTML = "You should look for \"" + name + "\"";
        document.getElementById("Info").innerHTML = ""
        document.getElementById("Info").appendChild(placeToVisit);
        console.log(data.name);

        var x = document.getElementById("goButton");
        x.style.display = "block";

        var y = document.getElementById("resultCards");
        y.style.display = "block";

    });
}
