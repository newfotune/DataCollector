<?php
   require_once '../includes/DbOperations.php';
   $response  = array();

   if($_SERVER['REQUEST_METHOD']=='POST') { //make sure we have a post request
        if(isset($_POST["type"])) { //make sure the image_name field is present
            $type = $_POST["type"];
            $db = new DbOperations();

            $current_entity_id = -1;
            if (checkEntity()) { //check if i have all reuired entity fields
                $images = array();
                $user_id = $_POST["user"];
                $geolat = $_POST["geolat"];
                $geolong = $_POST["geolong"];
                $type_id = $_POST["entity_type"];
                $note = (isset($_POST["note"])) ? $_POST["note"] : NULL;

               if (isset($_POST["image_0"])) $images["image_0"] = $_POST["image_0"];
               if (isset($_POST["image_1"])) $images["image_1"] = $_POST["image_1"];
               if (isset($_POST["image_2"])) $images["image_2"] = $_POST["image_2"];

                $current_entity_id = uploadEntity($db, $type_id, $user_id, $geolat, $geolong, $note);
                $db->addMedia($user_id, $current_entity_id, $images["image_0"]);

                if (isset($_POST["image_1"])) $db->addMedia($user_id, $current_entity_id, $images["image_1"]);
                if (isset($_POST["image_2"])) $db->addMedia($user_id, $current_entity_id, $images["image_2"]);
            }
            //use Type id instead of String type.
            if(strcmp($type,"gas station") == 0) {
                if (isset($_POST["brand"])) {
                  $brand = $_POST["brand"];
                  $result = $db->addGasStation($current_entity_id, $brand);
                  if ($result == 1) {
                    $response['error'] = false;
                    $response['message'] = "successfully added Gas Station" ;
                    echo json_encode($response);
                  } else {
                    $response['error'] = true;
                    $response['message'] = "Operation Unexpectedly failed" ;
                    echo json_encode($response);
                  }
                } else {
                  requiredFieldsError($type);
                }
            } else if(strcmp($type,"restaurant") == 0) {
              if (isset($_POST["brand"])) {
                $brand = $_POST["brand"];
                $result = $db->addResturant($current_entity_id, $brand);
                if ($result == 1) {
                  $response['error'] = false;
                  $response['message'] = "successfully added Restaurant" ;
                  echo json_encode($response);
                } else {
                  $response['error'] = true;
                  $response['message'] = "Operation Unexpectedly failed" ;
                  echo json_encode($response);
                }
              } else {
                requiredFieldsError($type);
              }
            } else if(strcmp($type,"stop sign") == 0) {
                $result = $db->addStopSign($current_entity_id);
                if ($result == 1) {
                  $response['error'] = false;
                  $response['message'] = "Successfully added Stop Sign" ;
                  echo json_encode($response);
                } else {
                  $response['error'] = true;
                  $response['message'] = "Operation Unexpectedly failed" ;
                  echo json_encode($response);
                }
            } else if(strcmp($type,"traffic light") == 0) {
              $result = $db->addTrafficLight($current_entity_id);
              if ($result == 1) {
                $response['error'] = false;
                $response['message'] = "Successfully added Traffic Light" ;
                echo json_encode($response);
              } else {
                $response['error'] = true;
                $response['message'] = "Operation Unexpectedly failed" ;
                echo json_encode($response);
              }
            } else if(strcmp($type,"traffic camera") == 0) {
              $result = $db->addTrafficCamera($current_entity_id);
              if ($result == 1) {
                $response['error'] = false;
                $response['message'] = "successfully added Traffic Camera" ;
                echo json_encode($response);
              } else {
                $response['error'] = true;
                $response['message'] = "Operation Unexpectedly failed" ;
                echo json_encode($response);
              }
            } else if(strcmp($type,"road construction") == 0) {
              $result = $db->addStopSign($current_entity_id);
              if ($result == 1) {
                $response['error'] = false;
                $response['message'] = "successfully added Road Construction" ;
                echo json_encode($response);
              } else {
                $response['error'] = true;
                $response['message'] = "Operation Unexpectedly failed" ;
                echo json_encode($response);
              }
            }
      } else {
        $response['error'] = true;
        $response['message'] = "No Type specified.";
        echo json_encode($response);
      }
   } else {
     //not a post request
     $response['error'] = true;
     $response['message'] = "Invalid request";
     echo json_encode($response);
   }

   function uploadEntity($db, $type_id, $user_id, $geolat, $geolong, $note) {
    $result = $db->createEntity($type_id, $user_id, $geolat, $geolong, $note);
     if ($result > 0) {
       return $result;
     } else {
       $response['error'] = true;
       $response['message'] = "Operation Unexpectedly failed" ;
       echo json_encode($response);
     }
   }

   function requiredFieldsError() {
     $response['error'] = true;
     $response['message'] = "Required fields are missing for " + $type;
      echo json_encode($response);
   }

   function checkEntity() {
     if(isset($_POST["geolat"]) and isset ($_POST["geolong"]) and isset($_POST["image_0"])) {
       return true;
     }
     return false;
   }
 ?>
