<?php

 // DB setup
 define('DB_HOST', 'localhost');
 define('DB_USER', 'root');
 define('DB_PASS', 'root');
 define('DB_NAME', 'SmartCarSpace');

//User Table
define('TABLE_USER', 'User');
define('COL_USER_ID', 'User_Id');
define('COL_USER_NAME', 'User_Name');
define('COL_PASSWORD', 'Password');

//Zone Table
define('TABLE_ZONE', 'Zone');
define('COL_ZONE_ID', 'Zone_Id');
define('COL_ZONE_NAME', 'Zone_Name');
define('COL_SLOTS_OCCUPIED', 'Slots_Occupied');
define('COL_SLOTS_AVAILABLE', 'Slots_Available');
define('COL_SUPERVISOR_ID', 'Supervisor_Id');

 //Parking Slot Table
 define('TABLE_PARKING_SLOT', 'Parking_Slot');
 define('COL_SLOT_ID', 'Slot_Id');
 define('COL_IS_AVAILABLE', 'Is_Available');
 define('COL_TIME_OF_ARRIVAL', 'Time_Of_Arrival');

 //API constants
 define('API_USER_ID', 'user_id');
 define('API_PASSWORD', 'password');
 define('API_ZONES', 'zones');
 define('API_ZONE_NAME', 'zone_name');
 define('API_SLOTS_OCCUPIED', 'slots_occupied');
 define('API_SLOTS_PER_ZONE', 'slots_per_zone');
 define('API_SLOTS_AVAILABLE', 'slots_available');
 define('API_ZONE_ID', 'zone_id');
 define('API_SLOT_ID', 'slot_id');
 define('API_IS_AVAILABLE', 'is_available');
 define('API_SLOTS_PER_ZONE', 'slots_per_zone');
 define('API_TIME_OF_ARRIVAL', 'Time_Of_Arrival');

 //REST Response
 define('ERROR_RESPONSE', 'error');
 define('MESSAGE_RESPONSE', 'msg');
?>
