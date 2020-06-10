var settings = {
    "async": true,
    "crossDomain": true,
    "url": "http://localhost:3000/flights/",
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
    document.getElementById("Header").innerHTML = "Flight Info";
        if (data.AirlineFlightSchedulesResult.data <= 0)
        {
            var res = document.createElement("P");
            res.innerHTML = "There no flights found for your destination right now.";

            document.getElementById("Info").appendChild(res);
        } else
        {

                for (var i = 0; i < 15; i++) {
                    var departureTimeDate = new Date(data.AirlineFlightSchedulesResult.data[i].departuretime * 1000);
                    departureDateString = departureTimeDate.toGMTString();
                    var dtimep = document.createElement("P");
                    dtimep.innerHTML = "Departure time is " + departureDateString;

                    departureTerminal = data.AirlineFlightSchedulesResult.data[i].origin
                    var dterminalp = document.createElement("P");
                    dterminalp.innerHTML = "Departure terminal is " + departureTerminal;

                    var arrivalTimeDate = new Date(data.AirlineFlightSchedulesResult.data[i].arrivaltime * 1000);
                    arrivalDateString = arrivalTimeDate.toGMTString();
                    dterminalp.innerHTML = "Departure airport is " + departureTerminal;
                    arrivalTime = data.AirlineFlightSchedulesResult.data[i].arrivaltime

                    var atimep = document.createElement("P");
                    atimep.innerHTML = "Arrival time is " + arrivalDateString;
                    arrivalTerminal = data.AirlineFlightSchedulesResult.data[i].destination
                    var aterminalp = document.createElement("P");
                    aterminalp.innerHTML = "Arrival airport is " + arrivalTerminal;


                    var separator = document.createElement("P");
                    separator.innerHTML = "__________________________________________";

                    document.getElementById("Info").appendChild(dtimep);
                    document.getElementById("Info").appendChild(dterminalp);
                    document.getElementById("Info").appendChild(atimep);
                    document.getElementById("Info").appendChild(aterminalp);
                    document.getElementById("Info").appendChild(separator)

                }

        }
console.log(data);

});
//}
