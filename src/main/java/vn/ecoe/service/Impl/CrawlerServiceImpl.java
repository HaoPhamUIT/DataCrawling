package vn.ecoe.service.Impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import vn.ecoe.exception.ResourceNotFoundException;
import vn.ecoe.model.Project;
import vn.ecoe.service.CrawlerService;
import vn.ecoe.utils.Constants;
import vn.ecoe.utils.JSoupUtils;

@Service
public class CrawlerServiceImpl implements CrawlerService {

    public Project fetchProject(String key, String url) {
        System.out.println("fetching key " + key + " from " + url);
        Document doc = fetchUrlOrThrow(url);

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

    public List<String> fetchProjectKeys(String url) {
        Document doc = fetchUrlOrThrow(url);
        List<String> keys = new ArrayList<>();
        for (var el : doc.select(".search-result .result-body article.projectCard-list div.cover-image a")) {
            var refUrl = el.attr("href");
            if (StringUtils.isEmpty(refUrl))
                continue;
            var parts = refUrl.split("/");
            if (parts.length > 1)
                keys.add(parts[parts.length - 1]);
        }
        System.out.println("fetched keys " + keys);
        return keys;
    }

    private String extractAddressProperty(Document doc, String key) {
        return JSoupUtils.extractAttribute(doc,
                ".project-header div.address a[title='" + key + "'] meta[itemprop='name']", "content");
    }

    private Document fetchUrlOrThrow(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new ResourceNotFoundException(Constants.CAN_NOT_BE_FOUND_ENTITY_MESSAGE);
        }
    }
}
