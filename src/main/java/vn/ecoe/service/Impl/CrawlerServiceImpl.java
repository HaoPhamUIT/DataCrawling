package vn.ecoe.service.Impl;

import java.io.IOException;
import java.util.HashMap;
import javafx.util.Pair;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.springframework.stereotype.Service;
import vn.ecoe.exception.ResourceNotFoundException;
import vn.ecoe.model.Land;
import vn.ecoe.model.Project;
import vn.ecoe.repository.LandRepository;
import vn.ecoe.repository.ProjectRepository;
import vn.ecoe.service.CrawlerService;
import vn.ecoe.utils.Constants;
import vn.ecoe.utils.JSoupUtils;

@Service
public class CrawlerServiceImpl implements CrawlerService {

    public Project fetchProject(String key, String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new ResourceNotFoundException(Constants.CAN_NOT_BE_FOUND_ENTITY_MESSAGE);
        }
                        
        var project = new Project();
        project.setId(key);
        project.setTitle(JSoupUtils.extractText(doc, "div.address .title-project"));
        project.setDistrict(extractAddressProperty(doc, Constants.HEADER_KEY_DISTRICT));
        project.setArea(extractAddressProperty(doc, Constants.HEADER_KEY_AREA));
        project.setStreet(extractAddressProperty(doc, Constants.HEADER_KEY_STREET));
        
        var priceSpec = JSoupUtils.extractText(doc, ".project-sumary .listing-detail-price .price-redRV");
        var priceRange = JSoupUtils.splitToRange(priceSpec, "-");
        project.setPriceMin(JSoupUtils.tryParseBigDecimal(priceRange.getFirst()));
        project.setPriceMax(JSoupUtils.tryParseBigDecimal(priceRange.getSecond()));
        
        var props = JSoupUtils.extractPropertiesWithKeyValue(doc, ".project-sumary .listing-detail div.listing-item",
                ".item-left", ".item-right");
        project.setStatus(props.get(Constants.SUMMARY_KEY_STATUS));
        project.setType(props.get(Constants.SUMMARY_KEY_TYPE));
        project.setNumBlocks(JSoupUtils.tryParseInt(props.get(Constants.SUMMARY_KEY_NUM_BLOCKS)));
        project.setNumFloors(JSoupUtils.tryParseInt(props.get(Constants.SUMMARY_KEY_NUM_FLOORS)));
        project.setNumUnits(JSoupUtils.tryParseInt(props.get(Constants.SUMMARY_KEY_NUM_UNITS)));        
        
        var descriptionElements = doc.select("div.view-more-desc *");
        for (var el : descriptionElements)
            project.getDescriptions().add(el.text());

        var imageElements = doc.select(".project-header .imageContainer li a img");       
        for (var el : imageElements)
            project.getImages().add(el.attr("src"));

        return project;
    }
    
    public Land fetchLand(String key, String url) 
    {
        // Get HTML document of webpage
    	Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new ResourceNotFoundException(Constants.CAN_NOT_BE_FOUND_ENTITY_MESSAGE);
        }

        var land = new Land();        
        land.setId(key);         
        land.setTitle(doc.select("div header.detail-house h1").text());        
        land.setStreet(doc.select("div header.detail-house div.address h2").get(0).text().replace(",", ""));;
        land.setWard(doc.select("div header.detail-house div.address h2").get(1).text().replace(",", ""));
        land.setDistrict(doc.select("div header.detail-house div.address h2").get(2).text());      
        land.setFullAddress(land.getStreet() + " " + land.getWard() + " " + land.getDistrict());
                                      
        // Grab images 
        var images = doc.select("div.collapsed.gallery-property div.cover");   
        for(var image : images)
        {        	        	
        	land.getImages().add(image.childNode(1).childNode(1).attr("src").toString());
        }
        
        // Grab list of schools near the property
        var rawTextSchools = doc.select("div div.advandate-content").last().text();
        var listOfSchools = rawTextSchools.split(",");
        for(var school : listOfSchools)
        {
        	land.getNearbySchools().add(school);
        }
        
        // Grab realtor's description of the property
        land.setDescription(doc.select("div p.summary").text());        
                        
        // Grab and parse data from Basic Information section of Land page
        var basicInformation = doc.select("div.left-content div.content ul.detail-more").get(0);
        var keys  = basicInformation.getElementsByClass("left");
        var values = basicInformation.getElementsByClass("right");

        // Pair-up values in "left" and "right" classes from the CSS
        var leftRightprops = new HashMap<String, String>();
       
        // TODO assert that keys and values are the same size       
        for(Integer i = 0; i < values.size(); ++i)
        {
            var myKey = keys.get(i);
            var myValue= values.get(i);            	               
            if (myKey == null || !myKey.hasText() || myValue == null || !myValue.hasText())
            	continue;
            leftRightprops.put(myKey.text(), myValue.text());                
        }
        
        land.setNumBathrooms(JSoupUtils.tryParseInt(leftRightprops.get(Constants.LAND_BASIC_KEY_NUM_BATHROOMS)));
        land.setNumBedrooms(JSoupUtils.tryParseInt(leftRightprops.get(Constants.LAND_BASIC_KEY_NUM_BEDROOMS)));
        land.setNumFloors(JSoupUtils.tryParseInt(leftRightprops.get(Constants.LAND_BASIC_KEY_NUM_FLOORS)));
        
        land.setAcreage(leftRightprops.get(Constants.LAND_BASIC_KEY_ACREAGE));
        land.setFloorArea(leftRightprops.get(Constants.LAND_BASIC_KEY_AREA_OF_FLOOR));
        land.setAreaOfUse(leftRightprops.get(Constants.LAND_BASIC_KEY_AREA_OF_USE));
       
        land.setLengthValue(leftRightprops.get(Constants.LAND_BASIC_KEY_LENGTH));
        land.setWidthValue(leftRightprops.get(Constants.LAND_BASIC_KEY_WIDTH));
        land.setLengthString(leftRightprops.get(Constants.LAND_BASIC_KEY_LENGTH));
        land.setWidthString(leftRightprops.get(Constants.LAND_BASIC_KEY_WIDTH));
        
        land.setPriceValue(JSoupUtils.tryParseBigDecimal(leftRightprops.get(Constants.LAND_BASIC_KEY_PRICE)));
        land.setPrice(leftRightprops.get(Constants.LAND_BASIC_KEY_PRICE));
        
        land.setSaleStartDate(leftRightprops.get(Constants.LAND_BASIC_KEY_SALE_START_DATE));
        land.setType(leftRightprops.get(Constants.LAND_BASIC_KEY_TYPE));
        land.setUses(leftRightprops.get(Constants.LAND_BASIC_KEY_USES));

        // Grab values for Utilities from the land pages. Tells whether or not property 
        // has certain amenities
        var utilities = doc.select("div.left-content div.content ul.detail-more").get(1);
        var utiltiesListItems = utilities.getElementsByClass("left");
        var utiltiesListYesNo = utilities.getElementsByClass("right");
        // TODO assert that keys and values are the same size
        for(Integer i = 0; i < values.size(); ++i)
        {
            var utilityItem = utiltiesListItems.get(i);
            var utilityYesNo = utiltiesListYesNo.get(i);            	               

            String YesOrNo = "";
            if(utilityYesNo.className().contains("check"))                	
            	YesOrNo = "Yes";
            else if (utilityYesNo.className().contains("none"))
            	YesOrNo = "No";

            if (utilityItem == null || !utilityItem.hasText() || YesOrNo == null || YesOrNo.isEmpty())
            	continue;
            var newPair = new Pair<String,String>(utilityItem.text(), YesOrNo);
            land.getUtilities().add(newPair);               
        }
        
        // Grab values for Furniture from the land pages. Tells whether or not property 
        // contains certain pieces of furniture
        var furniture = doc.select("div.left-content div.content ul.detail-more").get(2);
        var furnitureListItems = furniture.getElementsByClass("left");
        var furnitureListYesNo = furniture.getElementsByClass("right");
        
        // TODO assert that keys and values are the same size
        for(Integer i = 0; i < values.size(); ++i)
        {
            var furnitureItem = furnitureListItems.get(i);
            var furnitureYesNo = furnitureListYesNo.get(i);            	               

            String YesOrNo = "";
            if(furnitureYesNo.className().contains("check"))                	
            	YesOrNo = "Yes";
            else if (furnitureYesNo.className().contains("none"))
            	YesOrNo = "No";

            if (furnitureItem == null || !furnitureItem.hasText() || YesOrNo == null || YesOrNo.isEmpty())
            	continue;
            var newPair = new Pair<String,String>(furnitureItem.text(), YesOrNo);
            land.getFurniture().add(newPair);              
        }
        
        // Grab values for Furniture from the land pages. Tells whether or not property 
        // contains certain pieces of furniture
        var appliances = doc.select("div.left-content div.content ul.detail-more").get(3);
        var appliancesListItems = appliances.getElementsByClass("left");
        var appliancesListYesNo = appliances.getElementsByClass("right");
        
        // TODO assert that keys and values are the same size        
        for(Integer i = 0; i < values.size(); ++i)
        {
            var appliancesItem = appliancesListItems.get(i);
            var appliancesYesNo = appliancesListYesNo.get(i);            	               

            String YesOrNo = "";
            if(appliancesYesNo.className().contains("check"))                	
            	YesOrNo = "Yes";
            else if (appliancesYesNo.className().contains("none"))
            	YesOrNo = "No";

            if (appliancesItem == null || !appliancesItem.hasText() || YesOrNo == null || YesOrNo.isEmpty())
            	continue;
            var newPair = new Pair<String,String>(appliancesItem.text(), YesOrNo);
            land.getAppliances().add(newPair);                
        }
             
        return land;
    }
    
    private String extractAddressProperty(Document doc, String key) {
        return JSoupUtils.extractAttribute(doc,
                ".project-header div.address a[title='" + key + "'] meta[itemprop='name']", "content");
    }

}
