const Express = require("express");
const BodyParser = require("body-parser");
const Mongoose = require("mongoose");

var app = Express();

Mongoose.connect("mongodb+srv://ejaysing:cs411@cs411-mw1ai.mongodb.net/test?retryWrites=true&w=majority");


app.listen(3002, () => {
    console.log("Listening at :3002...");
});

const User = Mongoose.model("person", {
  name: String,
  email: String,
  loc: Object

});


app.post("/person", async (request, response, next) => {

  

  try { 
    console.log(request.query.name);
    var person = new User({
      name: request.query.name,
      email: request.query.email,
      loc: null
    });
    console.log(person);
    var result = await person.save();
    response.send(result);
  } catch (error) {
    response.status(500).send(error);
  }

});

app.get("/people", async (request, response, next) => {
  try {
    var result = await User.find().exec();
    response.send(result);
  } catch (error) {
    response.status(500).send(error);
  }
});

app.get("/person/:email", async (request, response, next) => {
  try {
    var person = await User.findOne({email: request.params.email}).exec();
    response.send(person);
  } catch (error) {
    response.status(500).send(error);
  }
});

app.put("/person/:email", async (request, response, next) => {
  try {
    var person = await User.findOne({email: request.params.email}).exec();
    person.set(request.body);
    var result = await person.save();
    console.log("arrive")
    response.send(result);
  } catch (error) {
    response.status(500).send(error);
  }
});

app.delete("/person/:email", async (request, response, next) => {
  try {
    var result = await User.deleteOne({email: request.params.email}).exec();
    response.send(result);
  } catch (error) {
    response.status(500).send(error);
  }
});
