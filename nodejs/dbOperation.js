const connection = require('./dbConnect'),
      config = require('./config.js'),
      qs = require('querystring');

function validateUserLogin(req, res) {
    var body = '';
    req.on('data', function (data) {
        body += data;

        // Too much POST data, kill the connection!
        // 1e6 === 1 * Math.pow(10, 6) === 1 * 1000000 ~~~ 1MB
        if (body.length > 1e6)
            request.connection.destroy();
    });

    req.on('end', function () {
        let post = qs.parse(body);
        let userId = post.User_Id;
        let password = post.Password;

        connection.query(`SELECT 1 AS Result FROM User WHERE User_Id='${userId}' AND Password='${password}'`, function (err, result, fields) {
            if (err) {
                throw err;
            }
            var response = {};
            if (JSON.stringify(result) !== '[]' && result[0].Result == 1) {
                response[config.RESPONSE_CONFIG.ERROR] = 'false';
                response[config.RESPONSE_CONFIG.MESSAGE] = 'Valid User'
            } else {
                response[config.RESPONSE_CONFIG.ERROR] = 'true';
                response[config.RESPONSE_CONFIG.MESSAGE] = 'Invalid User Id or Password';
            }
            res.send(response)
        });
    });
}

function getSlotsInfoPerZone(req, res) {
    var body = '';
    req.on('data', function (data) {
        body += data;

        // Too much POST data, kill the connection!
        // 1e6 === 1 * Math.pow(10, 6) === 1 * 1000000 ~~~ 1MB
        if (body.length > 1e6)
            request.connection.destroy();
    });

    req.on('end', function () {
        let post = qs.parse(body);
        let zoneId = post.Zone_Id;
        connection.query(`SELECT Slot_Id, Is_Available FROM Parking_Slot WHERE Zone_Id='${zoneId}'`, function (err, result, fields) {
            if (err) {
                throw err;
            }
            var response = {};
            if (JSON.stringify(result) !== '[]') {
                response[config.RESPONSE_CONFIG.SLOTS_PER_ZONE] = result;
                response[config.RESPONSE_CONFIG.ERROR] = 'false';
                response[config.RESPONSE_CONFIG.MESSAGE] = 'Zone Id successfully retrieved'
            } else {
                response[config.RESPONSE_CONFIG.ERROR] = 'true';
                response[config.RESPONSE_CONFIG.MESSAGE] = 'Could not retrieve Zone Id information';
            }
            res.send(response)
        });
    });
}

function getAllZonesInfo(req, res) {
    connection.query(`SELECT * FROM Zone`, function (err, result, fields) {
        if (err) {
            throw err;
        }
        var response = {};
        if (JSON.stringify(result) !== '[]') {
            response[config.RESPONSE_CONFIG.ZONES] = result;
            response[config.RESPONSE_CONFIG.ERROR] = 'false';
            response[config.RESPONSE_CONFIG.MESSAGE] = 'Zones info successfully retrieved'
        } else {
            response[config.RESPONSE_CONFIG.ERROR] = 'true';
            response[config.RESPONSE_CONFIG.MESSAGE] = 'Could not retrieve zones information';
        }
        res.send(response)
    });
}

function updateSlotInfo(req, res) {
    var body = '';
    req.on('data', function (data) {
        body += data;

        // Too much POST data, kill the connection!
        // 1e6 === 1 * Math.pow(10, 6) === 1 * 1000000 ~~~ 1MB
        if (body.length > 1e6)
            request.connection.destroy();
    });

    req.on('end', function () {
        let post = qs.parse(body);
        let zoneId = post.Zone_Id;
        let slotId = post.Slot_Id;
        let isAvailable = post.Is_Available;

        connection.query(`SELECT Is_Available FROM Parking_Slot WHERE Zone_Id='${zoneId}' AND Slot_Id='${slotId}'`, function( err, result, fields) {
            if (err) {
              throw err;
            }
            prev_avaiable = result[0].Is_Available;
            if (prev_avaiable !== isAvailable) {
              connection.query(`UPDATE Parking_Slot SET Is_Available=${isAvailable} WHERE Zone_Id='${zoneId}' AND Slot_Id='${slotId}'`, function (err, result, fields) {
                  if (err) {
                      throw err;
                  }
                  var response = {};
                  if (JSON.stringify(result) !== '[]' && result.affectedRows == 1) {
                      connection.query(`UPDATE Zone SET Slots_Occupied = Slots_Occupied + ${isAvailable} - ${prev_avaiable} WHERE Zone_Id='${zoneId}'`, function (err, result, fields) {
                        if (err) {
                          throw err;
                        }
                        connection.query(`UPDATE Zone SET Slots_Available = Slots_Available + ${prev_avaiable} - ${isAvailable} WHERE Zone_Id='${zoneId}'`, function (err, result, fields) {
                          if (err) {
                            throw err;
                          }
                        });
                      });
                      response[config.RESPONSE_CONFIG.ERROR] = 'false';
                      response[config.RESPONSE_CONFIG.MESSAGE] = 'Slot info updated successfully'
                  } else {
                      response[config.RESPONSE_CONFIG.ERROR] = 'true';
                      response[config.RESPONSE_CONFIG.MESSAGE] = 'Error while updating slot info';
                  }
                  res.send(response)
              });
            }
        });
    });
}

module.exports = { validateUserLogin, getSlotsInfoPerZone, getAllZonesInfo, updateSlotInfo }
