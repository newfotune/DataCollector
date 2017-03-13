//<![CDATA[
    var map;
    var markers = [];
    var infoWindow;
    var locationSelect;

    var customLabel = {
        2: { label: 'R' }, //Restaurant
        1: { label: 'G' },//gas station
        4: { label: 'L' }, //traffic light
        5: { label: 'T' }, //traffic camera
        3: { label: 'S' },  //stop sign
        6: { label: 'C' } //road construction
      };

      var fullNameMap = {
         1: { fullName: "Gas Station" },
         2: { fullName: "Restaurant" },
         3: { fullName: "Stop Sign" },
         4: { fullName: "Traffic light" },
         5: { fullName: "Traffic Camera" },
         6: { fullName: "RoaD construction" },
     };


    function load() {
      map = new google.maps.Map(document.getElementById("map"), {
        center: new google.maps.LatLng(40, -100),
        zoom: 4,
        mapTypeId: 'roadmap',
        mapTypeControlOptions: {style: google.maps.MapTypeControlStyle.DROPDOWN_MENU}
      });
      infoWindow = new google.maps.InfoWindow();

      locationSelect = document.getElementById("locationSelect");
      locationSelect.onchange = function() {
        var markerNum = locationSelect.options[locationSelect.selectedIndex].value;
        if (markerNum != "none"){
          google.maps.event.trigger(markers[markerNum], 'click');
        }
      };

      searchLocations();
   }

   function clearLocations() {
     infoWindow.close();
     for (var i = 0; i < markers.length; i++) {
       markers[i].setMap(null);
     }
     markers.length = 0;

     locationSelect.innerHTML = "";
     var option = document.createElement("option");
     option.value = "none";
     option.innerHTML = "See all results:";
     locationSelect.appendChild(option);
   }

   function searchLocations() {
     clearLocations();

    // var radius = document.getElementById('radiusSelect').value;
     var searchUrl = "http://tcss498.x10host.com/datacollector/operations/writeToXML.php";
     downloadUrl(searchUrl, function(data) {
       var xml = parseXml(data);
       var markerNodes = xml.documentElement.getElementsByTagName("entity");
       var bounds = new google.maps.LatLngBounds();
       for (var i = 0; i < markerNodes.length; i++) {
         var type = markerNodes[i].getAttribute("EntityType");
         var UserID = markerNodes[i].getAttribute("User");
         var desciption = markerNodes[i].getAttribute("Desctiption");
         var latlng = new google.maps.LatLng(
              parseFloat(markerNodes[i].getAttribute("lat")),
              parseFloat(markerNodes[i].getAttribute("long")));

         //createOption(name, distance, i);
         createMarker(latlng, type, UserID);
         bounds.extend(latlng);
       }
       map.fitBounds(bounds);
       locationSelect.style.visibility = "visible";
       locationSelect.onchange = function() {
         var markerNum = locationSelect.options[locationSelect.selectedIndex].value;
         google.maps.event.trigger(markers[markerNum], 'click');
       };
      });
    }

    function createMarker(latlng, type, UserID) {
      var b = fullNameMap[type];
      var name = b.fullName;

      var html = "<b> Type: " + name + "</b> <br/> User: " + UserID;
      var icon = customLabel[type] || {};//finish this
      var marker = new google.maps.Marker({
        map: map,
        position: latlng,
        label:icon.label
      });
      google.maps.event.addListener(marker, 'click', function() {
        infoWindow.setContent(html);
        infoWindow.open(map, marker);
      });
      markers.push(marker);
    }

  /*  function createOption(name, distance, num) {
      var option = document.createElement("option");
      option.value = num;
      option.innerHTML = name + "(" + distance.toFixed(1) + ")";
      locationSelect.appendChild(option);
    }*/

    function downloadUrl(url, callback) {
      var request = window.ActiveXObject ?
          new ActiveXObject('Microsoft.XMLHTTP') :
          new XMLHttpRequest;

      request.onreadystatechange = function() {
        if (request.readyState == 4) {
          request.onreadystatechange = doNothing;
          callback(request.responseText, request.status);
        }
      };

      request.open('GET', url, true);
      request.send(null);
    }

    function parseXml(str) {
      if (window.ActiveXObject) {
        var doc = new ActiveXObject('Microsoft.XMLDOM');
        doc.loadXML(str);
        return doc;
      } else if (window.DOMParser) {
        return (new DOMParser).parseFromString(str, 'text/xml');
      }
    }

    function doNothing() {}
