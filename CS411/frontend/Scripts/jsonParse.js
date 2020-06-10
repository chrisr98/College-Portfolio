
// might delete




// document.getElementById("buttontest").onclick = function() {
//     $.getJSON( 'response.json', function( data ) {
//
//
//         originName = document.getElementById("userInput").elements["origin"].value;
//         destinationName = document.getElementById("userInput").elements["destination"].value;
//
//         if (destinationName == "LHR") {
//             test = 0;
//             lengthTest = 34;
//         } else if (destinationName == "AUS") {
//             test = 34;
//             lengthTest = 8;
//         } else if (destinationName == "MCO") {
//             test = 42;
//             lengthTest = 39;
//         } else if (destinationName == "LAX") {
//             test = 81;
//             lengthTest = 63;
//         } else if (destinationName == "JFK") {
//             test = 144;
//             lengthTest = 111;
//         } else if (destinationName == "MIA") {
//             test = 255;
//             lengthTest = 17;
//         }
//         console.log(test +" "+lengthTest)
//
//         if(data.scheduledFlights.length == 0 && destinationName == data.scheduledFlights[test].arrivalAirportFsCode)
//         {
//             document.getElementById("apiTest2").innerHTML = "There aren't any scheduled flights at this time";
//         } else if (originName == data.scheduledFlights[test].departureAirportFsCode && destinationName == data.scheduledFlights[test].arrivalAirportFsCode)
//         {
//             //Set apiTest to list out flight info
//             document.getElementById("apiTest").innerHTML = "Flight Info";
//             document.getElementById("apiTest2").innerHTML = "";
//             for (var i = test; i < lengthTest+test; i++) {
//                 // Create a p and set the text content to the flight info
//                 flightnumber = data.scheduledFlights[i].flightNumber
//                 var fnp = document.createElement("P");
//                 fnp.innerHTML = "Flight number is " + flightnumber;
//                 departureTime = data.scheduledFlights[i].departureTime
//                 var dtimep = document.createElement("P");
//                 dtimep.innerHTML = "Departure time is " + departureTime;
//                 departureTerminal = data.scheduledFlights[i].departureTerminal
//                 var dterminalp = document.createElement("P");
//                 dterminalp.innerHTML = "Departure terminal is " + departureTerminal;
//                 arrivalTime = data.scheduledFlights[i].arrivalTime
//                 var atimep = document.createElement("P");
//                 atimep.innerHTML = "Arrival time is " + arrivalTime;
//                 arrivalTerminal = data.scheduledFlights[i].arrivalTerminal
//                 var aterminalp = document.createElement("P");
//                 aterminalp.innerHTML = "Arrival terminal is " + arrivalTerminal;
//
//                 var separator = document.createElement("P");
//                 separator.innerHTML = "__________________________________________";
//                 //Append to empty element to fill out the page
//                 document.getElementById("apiTest2").appendChild(fnp)
//                 document.getElementById("apiTest2").appendChild(dtimep)
//                 document.getElementById("apiTest2").appendChild(dterminalp)
//                 document.getElementById("apiTest2").appendChild(atimep)
//                 document.getElementById("apiTest2").appendChild(aterminalp)
//                 document.getElementById("apiTest2").appendChild(separator)
//
//                 //This is for debbuging
//                 console.log( i + " flight number is " + flightnumber);
//                 console.log( i + " departure time is " + departureTime);
//                 console.log( i + " departure terminal is " + departureTerminal);
//                 console.log( i + " arrival time is " + arrivalTime);
//                 console.log( i + " arrival terminal is " + arrivalTerminal);
//                 console.log(data.scheduledFlights[i].departureAirportFsCode);
//                 console.log(data.scheduledFlights[i].arrivalAirportFsCode);
//
//
//             }
//
//         } else {
//            document.getElementById("apiTest2").innerHTML = "This place is not in our data base";
//         }
//
//
//     });
//
//
//
// };

//
// /* Use the code below when we figure out the CORS issue*/


document.getElementById("buttontest").onclick = function() {
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
        originName = document.getElementById("userInput").elements["origin"].value;
        destinationName = document.getElementById("userInput").elements["destination"].value;
        if(data.scheduledFlights.length == 0 && destinationName == data.scheduledFlights[0].arrivalAirportFsCode)
        {
            document.getElementById("apiTest2").innerHTML = "There aren't any scheduled flights at this time";
        } else if (originName == data.scheduledFlights[0].departureAirportFsCode && destinationName == data.scheduledFlights[0].arrivalAirportFsCode)
        {
            //Set apiTest to list out flight info
            document.getElementById("apiTest").innerHTML = "Flight Info";
            for (var i = 0; i < data.scheduledFlights.length; i++) {
                // Create a p and set the text content to the flight info
                flightnumber = data.scheduledFlights[i].flightNumber
                var fnp = document.createElement("P");
                fnp.innerHTML = "Flight number is " + flightnumber;
                departureTime = data.scheduledFlights[i].departureTime
                var dtimep = document.createElement("P");
                dtimep.innerHTML = "Departure time is " + departureTime;
                departureTerminal = data.scheduledFlights[i].departureTerminal
                var dterminalp = document.createElement("P");
                dterminalp.innerHTML = "Departure terminal is " + departureTerminal;
                arrivalTime = data.scheduledFlights[i].arrivalTime
                var atimep = document.createElement("P");
                atimep.innerHTML = "Arrival time is " + arrivalTime;
                arrivalTerminal = data.scheduledFlights[i].arrivalTerminal
                var aterminalp = document.createElement("P");
                aterminalp.innerHTML = "Arrival terminal is " + arrivalTerminal;

                var separator = document.createElement("P");
                separator.innerHTML = "__________________________________________";
                //Append to empty element to fill out the page
                document.getElementById("apiTest2").appendChild(fnp)
                document.getElementById("apiTest2").appendChild(dtimep)
                document.getElementById("apiTest2").appendChild(dterminalp)
                document.getElementById("apiTest2").appendChild(atimep)
                document.getElementById("apiTest2").appendChild(aterminalp)
                document.getElementById("apiTest2").appendChild(separator)

                //This is for debbuging
                console.log( i + " flight number is " + flightnumber);
                console.log( i + " departure time is " + departureTime);
                console.log( i + " departure terminal is " + departureTerminal);
                console.log( i + " arrival time is " + arrivalTime);
                console.log( i + " arrival terminal is " + arrivalTerminal);
                console.log(data.scheduledFlights[i].departureAirportFsCode);
                console.log(data.scheduledFlights[i].arrivalAirportFsCode);


            }

        } else {
           document.getElementById("apiTest2").innerHTML = "This place is not in our data base";
        }


    });

};

//
//
// Create a request variable and assign a new XMLHttpRequest object to it.
// var request = new XMLHttpRequest()
//
// // Open a new connection, using the GET request on the URL endpoint
// // request.open('GET', 'https://ghibliapi.herokuapp.com/films', true)
// request.open('GET', 'http://localhost/3000/flights/LHR', true)
//
// request.onload = function() {
//   // Begin accessing JSON data here
//     var data = JSON.parse(this.response)
//
//     if (request.status >= 200 && request.status < 400) {
//       data.forEach(movie => {
//         console.log(movie.title)
//       })
//     } else {
//       console.log('error')
//     }
// }
//
// // Send request
// request.send()
