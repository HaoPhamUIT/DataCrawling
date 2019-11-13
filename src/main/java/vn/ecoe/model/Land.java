package vn.ecoe.model;

import java.io.Serializable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.util.Pair;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Document
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Land implements Serializable 
{
	private static final long serialVersionUID = -2187059859733393330L;
	
	@Id
	private String id;
    private String title;
    
    // Address information
    private String fullAddress;
    private String district;
    private String ward;
    private String street;    
    
    // Basic Information
    private Integer numBedrooms;
    private Integer numBathrooms;
    private Integer numFloors;

    private String floorArea; 
    private String lengthValue;
    private String widthValue;
    private String acreage; 
    private String areaOfUse;
    private String lengthString;
    private String widthString;
    
    private String saleStartDate;
    private String type; 
    private String uses;    
    private String price;
    private String description;
    
    private BigDecimal priceValue;
    
    private List<Pair<String, String>> appliances = new ArrayList<>();    
    private List<Pair<String, String>> furniture = new ArrayList<>();
    private List<Pair<String, String>> utilities = new ArrayList<>();

    private List<String> nearbySchools = new ArrayList<>();    
    
    private List<String> images = new ArrayList<>();

}
