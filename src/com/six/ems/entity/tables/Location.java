package com.six.ems.entity.tables;

import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLTableName;

@SQLTableName(table="t_location")
public class Location {
@SQLColnum(colName = "location_id")
private Integer locationId;
@SQLColnum(colName = "location_name")
private String locationName;
@SQLColnum(colName = "available")
private String available;

public Integer getLocationId() {
	return locationId;
}
public void setLocationId(Integer locationId) {
	this.locationId = locationId;
}
public String getLocationName() {
	return locationName;
}
public void setLocationName(String locationName) {
	this.locationName = locationName;
}
public String getAvailable() {
	return available;
}
public void setAvailable(String available) {
	this.available = available;
}
}
