package com.locationWeb.controllers;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.locationWeb.entities.Location;
import com.locationWeb.repositories.LocationRepository;
import com.locationWeb.services.LocationService;
import com.locationWeb.util.EmailUtil;
import com.locationWeb.util.ReportUtil;

@Controller
public class LocationController {
	
	@Autowired
	EmailUtil email;
	@Autowired
	LocationService service;
	@Autowired
	LocationRepository repository;
	@Autowired
	ReportUtil reportUtil;
	@Autowired
	ServletContext sc;
	
	
	
	@RequestMapping("/showCreate")
	public String showCreate() {
		return "createLocation";
	}
	@RequestMapping("/saveLoc")
	public String saveLocation(@ModelAttribute("location") Location location, ModelMap modelMap)
	{
		Location saveLocation = service.saveLocation(location);
		String msg = "Location saved with id : " + saveLocation.getId();
		modelMap.addAttribute("msg" , msg);
		email.sendEmail("springboot240@gmail.com", "Location Saved", "Saved data");
		return "createLocation";
	}
	
	@RequestMapping("/displayLocations")
	public String displayLocations(ModelMap modelMap)
	{
		List<Location> locations = service.getAllLocations();
		modelMap.addAttribute("locations" , locations);
		return "displayLocations";
	}

	@RequestMapping("deleteLocations")
	public String deleteLocation(@RequestParam("id")int id, ModelMap modelMap)
	{
	Location location = service.getLocationByID(id);
	service.deleteLocation(location);
	List<Location> locations = service.getAllLocations();
	modelMap.addAttribute("locations" , locations);
	return "displayLocations";
	}
	
	@RequestMapping("/showUpdate")
	public String showUpdate(@RequestParam("id")int id, ModelMap modelMap)
	{
	Location location = service.getLocationByID(id);
	modelMap.addAttribute("location" , location);
	return "updateLocation";
	}
	
	@RequestMapping("/updateLoc")
	public String updateLocation(@ModelAttribute("location") Location location, ModelMap modelMap)
	{
		service.updateLocation(location);
		List<Location> locations = service.getAllLocations();
		modelMap.addAttribute("locations" , locations);
		return "displayLocations";
	}
	
	@RequestMapping("/generateReport")
	public String generateReport()
	{
		
		List<Object[]> data = repository.findTypeAndCount();
		reportUtil.generatePieChart(new File("pieChart.jpeg"), data);	
		return "report";
	}
}
