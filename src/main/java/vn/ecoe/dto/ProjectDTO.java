package vn.ecoe.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDTO implements Serializable {
    private static final long serialVersionUID = 4435726574229605912L;

    private String id;

    private String title;

    // Address information
    private String district;
    private String area;
    private String street;

    private String status;

    private String type;

    private Integer numBlocks;
    private Integer numFloors;
    private Integer numUnits;

    private BigDecimal priceMin;
    private BigDecimal priceMax;

    private List<String> descriptions = new ArrayList<>();

    private List<String> images = new ArrayList<>();
}
