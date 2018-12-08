<?php

//getting the dboperation class
require_once dirname(__FILE__) . '/DbOperation.php';

require_once dirname(__FILE__) . '/Constants.php';


//function validating all the paramters are available
//we will pass the required parameters to this function
function areParametersAvailable($params) {

    //assuming all parameters are available
    $available = true;
    $missingparams = "";

    foreach($params as $param) {
        if(!isset($_POST[$param]) || strlen($_POST[$param])<=0) {
            $available = false;
            $missingparams = $missingparams . ", " . $param;
        }
    }

    //if parameters are missing
    if(!$available) {
        $response = array();
        $response[ERROR_RESPONSE] = true;
        $response[MESSAGE_RESPONSE] = 'Parameters ' . substr($missingparams, 1, strlen($missingparams)) . ' missing';

        //displaying error
        echo json_encode($response);

        //stopping further execution
        die();
    }
}

//an array to display response
$response = array();


    switch($_GET['apicall']) {

        case 'validate_user_login':

            areParametersAvailable(array(API_USER_ID, API_PASSWORD));

            $db = new DbOperation();

            $result = $db -> isValidUser(
                $_POST[API_USER_ID],
                $_POST[API_PASSWORD]
            );

            if ($result) {
                $response[ERROR_RESPONSE] = false;
                $response[MESSAGE_RESPONSE] = 'Valid User';
            } else {
                $response[ERROR_RESPONSE] = true;
                $response[MESSAGE_RESPONSE] = 'Invalid User Id or Password';
           }
           break;

      case 'get_slots_info_per_zone':

           areParametersAvailable(array(API_ZONE_ID));

           $db = new DbOperation();

           $result = $db -> getSlotsPerZoneInfo(
                $_POST[API_ZONE_ID]
           );

           if($result) {
                $response[ERROR_RESPONSE] = false;
                $response[MESSAGE_RESPONSE] = 'Zone Id successfully retrieved';
                $response[API_SLOTS_PER_ZONE] = $result[API_SLOTS_PER_ZONE];
           } else {
                $response[ERROR_RESPONSE] = true;
                $response[MESSAGE_RESPONSE] = 'Could not retrieve Zone Id information';
           }
           break;

      case 'get_all_zones_info':

           $db = new DbOperation();

           $result = $db -> getAllZonesInfo();

           if($result) {
                $response[ERROR_RESPONSE] = false;
                $response[MESSAGE_RESPONSE] = 'Zones info successfully retrieved';
                $response[API_ZONES] = $result[API_ZONES];
           } else {
                $response[ERROR_RESPONSE] = true;
                $response[MESSAGE_RESPONSE] = 'Could not retrieve Zones information';
           }

           break;

           default:
               $response[ERROR_RESPONSE] = true;
               $response[MESSAGE_RESPONSE] = 'Invalid API call';
               break;
    }

//displaying the response in json structure
echo json_encode($response);
?>
