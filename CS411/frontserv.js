const express = require('express');
const app = express();
const cors = require('cors');

const port = 3001;

app.use(express.static('frontend'))



app.listen(port);