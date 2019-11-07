package vn.ecoe.model;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Document
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project implements Serializable {
    private static final long serialVersionUID = 4435726574229605912L;

    @Id
    private String id;
    
    private String title;
    
    private String district;
    
    private String street;
    
    private String type;
    
    private BigDecimal price;
}
