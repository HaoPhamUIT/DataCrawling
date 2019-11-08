package vn.ecoe.service;

import vn.ecoe.model.Project;

public interface CrawlerService {
    Project fetchProject(String baseUrl, String key);
}
