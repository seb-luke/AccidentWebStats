function getLocationConstant()
{
    if(navigator.geolocation)
    {
        navigator.geolocation.getCurrentPosition(onGeoSuccess,onGeoError);  
    } else {
        alert("Your browser or device doesn't support Geolocation");
    }
}

// If we have a successful location update
function onGeoSuccess(event)
{
	var lat = event.coords.latitude;
	var long = event.coords.longitude;
    //document.getElementById("Latitude").value =  event.coords.latitude; 
    //document.getElementById("Longitude").value = event.coords.longitude;
	
	//alert("Lat: " + lat + " Long: " + long);
	
	saveCoordToBean([{name:'longitude', value:long}, {name:"latitude", value:lat}]);
	
}

// If something has gone wrong with the geolocation request
function onGeoError(event)
{
    alert("Error code " + event.code + ". " + event.message);
}
