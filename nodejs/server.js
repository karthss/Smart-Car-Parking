const express = require('express'),
      bodyParser = require('body-parser'),
      dbOperation = require('./dbOperation.js');

var app = express();
app.use(bodyParser.json());

app.listen(3000,()=>{
    console.log("listening ")
});

app.get('/',(req,res)=>{
    res.send('welcome to get method');
});

app.post('/validate_user_login', (req, res) => {
    console.log("Executing validate_user_login");
    dbOperation.validateUserLogin(req, res);
});

app.post('/get_slots_info_per_zone', (req, res) => {
    console.log("Executing get_slots_info_per_zone");
    dbOperation.getSlotsInfoPerZone(req, res);
});

app.get('/get_all_zones_info', (req, res) => {
    console.log("Executing get_all_zones_info");
    dbOperation.getAllZonesInfo(req, res);
});

app.post('/update_slot_info', (req, res) => {
    console.log("Executing update_slot_info");
    dbOperation.updateSlotInfo(req, res);
});
