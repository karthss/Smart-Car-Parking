const mysql = require('mysql'),
      config = require('./config.js');

var connection = mysql.createConnection({
    host     : config.DB_CONFIG.DB_HOST,
    port     : config.DB_CONFIG.DB_PORT,
    database : config.DB_CONFIG.DB_NAME,
    user     : config.DB_CONFIG.DB_USER,
    password : config.DB_CONFIG.DB_PASS,
});

module.exports = connection;