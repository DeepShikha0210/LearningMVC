package com.locationWeb.services;

import java.util.List;

import com.locationWeb.entities.Location;

public interface LocationService {

	
	Location saveLocation(Location location);
	Location updateLocation(Location location);
	void deleteLocation(Location location);
	Location getLocationByID(int id);
	List<Location> getAllLocations();
	
	
	
}
