<?php

class DbOperation
{
    //Database connection link
    private $con;

    //Class constructor
    function __construct() {

        //Getting the Constants.php file
        require_once dirname(__FILE__) . '/Constants.php';

        //Getting the DbConnect.php file
        require_once dirname(__FILE__) . '/DbConnect.php';

        //Creating a DbConnect object to connect to the database
        $db = new DbConnect();

        //Initializing our connection link of this class
        //by calling the method connect of DbConnect class
        $this->con = $db->connect();
    }

   function isValidUser($user_id, $password) {

      $sql_statement = $this -> con -> prepare("SELECT " . COL_USER_ID . " FROM " . TABLE_USER . " WHERE " . COL_USER_ID . " ='$user_id' AND " . COL_PASSWORD . " ='$password'");
      $sql_statement -> execute();
      $sql_statement -> bind_result($id);

      $sql_statement -> fetch();
      if ($id) {
          return true;
      }

      return false;
   }

   function getSlotsPerZoneInfo($zone_id) {

      $sql_statement = $this -> con -> prepare("SELECT " . COL_SLOT_ID . ", " . COL_IS_AVAILABLE . " FROM " . TABLE_PARKING_SLOT . " WHERE " . COL_ZONE_ID . "='$zone_id'");
      $sql_statement -> execute();
      $sql_statement -> bind_result($slot_id, $is_available);
      $slots = array();

      while ($sql_statement -> fetch()) {
          $slot = array();
          $slot[API_SLOT_ID] = $slot_id;
          $slot[API_IS_AVAILABLE] = $is_available;

          array_push($slots, $slot);
      }

      $result[API_SLOTS_PER_ZONE] = $slots;

      return $result;
   }

   function getAllZonesInfo() {

      $sql_statement = $this -> con -> prepare("SELECT * FROM " . TABLE_ZONE);
      $sql_statement -> execute();
      $sql_statement -> bind_result($zone_id, $zone_name, $slots_occupied, $slots_available, $supervisor_id);
      $zones = array();

      while($sql_statement -> fetch()) {
        $zone  = array();
        $zone[API_ZONE_ID] = $zone_id;
        $zone[API_ZONE_NAME] = $zone_name;
        $zone[API_SLOTS_OCCUPIED] = $slots_occupied;
        $zone[API_SLOTS_AVAILABLE] = $slots_available;

        array_push($zones, $zone);
      }

      $result[API_ZONES] = $zones;

      return $result;
   }



   function updateSlotInfo($zone_id, $slot_id, $is_available) {
      $current_slot_info_sql = $this -> con -> prepare("SELECT " . COL_IS_AVAILABLE . " FROM " . TABLE_PARKING_SLOT . " WHERE " . COL_ZONE_ID . "='$zone_id' AND " . COL_SLOT_ID . "='$slot_id'");
      $current_slot_info_sql -> execute();
      $current_slot_info_sql -> bind_result($current_slot_info);
      $current_slot_info_sql -> fetch();
      if ($current_slot_info != $is_available) {
        $current_slot_info_sql -> close();
        $sql_statement_parking_slot = $this -> con -> prepare("UPDATE " . TABLE_PARKING_SLOT . " SET " . COL_IS_AVAILABLE . "=$is_available WHERE " . COL_ZONE_ID . "='$zone_id' AND " . COL_SLOT_ID . "='$slot_id'");
        if ($sql_statement_parking_slot -> execute()) {
            $this -> con -> query("UPDATE " . TABLE_ZONE . " SET " . COL_SLOTS_OCCUPIED . "=" . COL_SLOTS_OCCUPIED . " + $is_available - $current_slot_info WHERE " . COL_ZONE_ID . "='$zone_id'");
            $this -> con -> query("UPDATE " . TABLE_ZONE . " SET " . COL_SLOTS_AVAILABLE . "=" . COL_SLOTS_AVAILABLE . " + $current_slot_info - $is_available WHERE " . COL_ZONE_ID . "='$zone_id'");
            return true;
        }
      } else {
        return true;
      }
      return false;
   }
}
?>
