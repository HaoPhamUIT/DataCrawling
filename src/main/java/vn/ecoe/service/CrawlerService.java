package vn.ecoe.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import vn.ecoe.model.Project;
import vn.ecoe.utils.JSoupUtils;

@Component
public class CrawlerService {
  private static String URL = "https://rever.vn/du-an/";
  private static String EXAMPLE_KEY = "picity-high-park";

  Project fetchProject(String baseUrl, String key) throws IOException {
      var doc = Jsoup.connect(baseUrl + key).get();

      var project = new Project();
      project.setId(key);
      project.setTitle(JSoupUtils.extractText(doc, "div.address .title-project"));
      project.setDistrict(JSoupUtils.extractAttribute(doc, ".project-header div.address a[title='Quận'] meta[itemprop='name']", "content"));
      project.setStreet(JSoupUtils.extractAttribute(doc, ".project-header div.address a[title='Đường'] meta[itemprop='name']", "content"));
      
      var priceValue = JSoupUtils.extractText(doc, ".project-sumary .listing-detail-price .price-redRV");
      project.setPrice(JSoupUtils.parseBigDecimal(priceValue));

      var props = JSoupUtils.extractPropertiesWithKeyValue(doc, ".project-sumary .listing-detail div.listing-item", ".item-left", ".item-right");
      project.setType(props.get("Loại hình:"));

      return project;
  }

  @Scheduled(fixedRate = 10000)
  public void crawlProjects() {      
      for (var key : new String[] { EXAMPLE_KEY }) {
          try {
              System.out.println("fetched: " + fetchProject(URL, key));
          } catch (IOException e) {
              System.err.println("Can't fetch: " + key);
          }
      }
  }
}
