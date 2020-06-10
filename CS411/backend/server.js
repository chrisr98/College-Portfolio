
const express = require('express');
const app = express();
const session = require('express-session');

const cors = require('cors');
const port = 3000;

app.use(cors({
    origin: true,
    credentials: true,
    preflightContinue: true
}));;

app.use(session({
    'secret' : 'p9o8i7u6hyg5t4r3e2w'
}))

app.listen(port);

//sessions??

const flightRoutes = require('./API/routes/flights');
const OAuth = require('./API/routes/google');
const callbacks = require('./API/routes/callback');
const where2 = require('./API/routes/where');
const accountt = require('./API/routes/account');


app.use('/flights', flightRoutes);
app.use('/signin', OAuth);
app.use('/callback', callbacks);
app.use('/where', where2);
app.use('/account', accountt)