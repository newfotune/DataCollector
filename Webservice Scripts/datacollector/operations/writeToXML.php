<?php
require_once '../includes/DbOperations.php';

if($_SERVER['REQUEST_METHOD'] == 'GET') {
  $db = new DbOperations();
  // Start XML file, create parent node
  $dom = new DOMDocument("1.0");
  $node = $dom->createElement("Entities");
  $parnode = $dom->appendChild($node);

  // Select all the rows in the markers table
  $result = $db->getAllEntity();
  $count = 0;
  if (!$result == NULL) {
    // Iterate through the rows, adding XML nodes for each
    while ($row = $result->fetch_assoc()) {
      $count = $count + 1;
      // Add to XML document node
      $node = $dom->createElement("entity");
      $newnode = $parnode->appendChild($node);

      $newnode->setAttribute("EntityType", $row['entity_type_id']);
      //echo $row['entity_type_id'] . "\n";
      $newnode->setAttribute("User", $row['user_id']);

      $newnode->setAttribute("lat", $row['geolat']);
        //echo $row['geolat'] . "\n";
      $newnode->setAttribute("long", $row['geolong']);
        //echo $row['geolat'] . "\n";
      $newnode->setAttribute("Desctiption", $row['description']);
    }
  }

  if ($count == 0) {
    echo "count = 0";
    $node = $dom->createElement("Data");
    $newnode = $parnode->appendChild($node);
    $newnode->setAttribute("NumberOfElements", 0);
  }
  //header("Content-type: text/xml");
  echo $dom->saveXML();
}
?>
