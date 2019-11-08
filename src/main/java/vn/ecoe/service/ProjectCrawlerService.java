package vn.ecoe.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import vn.ecoe.model.Project;
import vn.ecoe.repository.ProjectRepository;
import vn.ecoe.utils.JSoupUtils;

@Component
public class ProjectCrawlerService {
    @Scheduled(fixedRate = 1000)
    public void crawlProjects() {
        var key_url = urlsProvider.nextUrlToCrawl();
        try {
            var project = fetchProject(key_url.getFirst(), key_url.getSecond());
            System.out.println("fetched: " + project);
            if (validateForSave(project))
                projectRepository.save(project);
            else
                System.err.println("Skipping save of incomplete crawled data for key: " + key_url.getFirst());
        } catch (IOException e) {
            System.err.println("Can't fetch: " + key_url.getSecond() + " due to " + e);
        }
    }

    private Project fetchProject(String key, String url) throws IOException {
        var doc = Jsoup.connect(url).get();

        var project = new Project();
        project.setId(key);
        project.setTitle(JSoupUtils.extractText(doc, "div.address .title-project"));
        project.setDistrict(extractAddressProperty(doc, HEADER_KEY_DISTRICT));
        project.setArea(extractAddressProperty(doc, HEADER_KEY_AREA));
        project.setStreet(extractAddressProperty(doc, HEADER_KEY_STREET));

        var priceSpec = JSoupUtils.extractText(doc, ".project-sumary .listing-detail-price .price-redRV");
        var priceRange = JSoupUtils.splitToRange(priceSpec, "-");
        project.setPriceMin(JSoupUtils.tryParseBigDecimal(priceRange.getFirst()));
        project.setPriceMax(JSoupUtils.tryParseBigDecimal(priceRange.getSecond()));

        var props = JSoupUtils.extractPropertiesWithKeyValue(doc, ".project-sumary .listing-detail div.listing-item",
                ".item-left", ".item-right");
        project.setStatus(props.get(SUMMARY_KEY_STATUS));
        project.setType(props.get(SUMMARY_KEY_TYPE));
        project.setNumBlocks(JSoupUtils.tryParseInt(props.get(SUMMARY_KEY_NUM_BLOCKS)));
        project.setNumFloors(JSoupUtils.tryParseInt(props.get(SUMMARY_KEY_NUM_FLOORS)));
        project.setNumUnits(JSoupUtils.tryParseInt(props.get(SUMMARY_KEY_NUM_UNITS)));

        var descriptionElements = doc.select("div.view-more-desc *");
        for (var el : descriptionElements)
            project.getDescriptions().add(el.text());

        var imageElements = doc.select(".project-header .imageContainer li a img");
        for (var el : imageElements)
            project.getImages().add(el.attr("src"));

        return project;
    }

    private boolean validateForSave(Project project) {
        return project != null && project.getDistrict() != null && project.getStreet() != null
                && project.getDescriptions().size() > 0;
    }

    private String extractAddressProperty(Document doc, String key) {
        return JSoupUtils.extractAttribute(doc,
                ".project-header div.address a[title='" + key + "'] meta[itemprop='name']", "content");
    }

    private final static String HEADER_KEY_DISTRICT = "Quận";
    private final static String HEADER_KEY_AREA = "Khu dân cư";
    private final static String HEADER_KEY_STREET = "Đường";
    private final static String SUMMARY_KEY_STATUS = "Trạng thái:";
    private final static String SUMMARY_KEY_TYPE = "Loại hình:";
    private final static String SUMMARY_KEY_NUM_UNITS = "Số căn:";
    private final static String SUMMARY_KEY_NUM_BLOCKS = "Số block:";
    private final static String SUMMARY_KEY_NUM_FLOORS = "Số tầng:";

    @Autowired
    private ProjectListService urlsProvider;
    
    @Autowired
    private ProjectRepository projectRepository;
}
