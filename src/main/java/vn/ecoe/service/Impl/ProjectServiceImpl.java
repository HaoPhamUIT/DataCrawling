package vn.ecoe.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vn.ecoe.converter.ProjectConverter;
import vn.ecoe.dto.ProjectDTO;
import vn.ecoe.model.Project;
import vn.ecoe.repository.ProjectRepository;
import vn.ecoe.service.ProjectService;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    CrawlerServiceImpl crawlerService;

    @Autowired
    ProjectConverter projectConverter;

    public Pair<String, String> nextUrlToCrawl() {
        // TODO: crawl project list or obtain reliable list of projects other way
        index = (index + 1) % EXAMPLE_KEYS.length;
        return Pair.of(EXAMPLE_KEYS[index], URL + EXAMPLE_KEYS[index]);
    }
    private int index = -1;
    
    private final static String URL = "https://rever.vn/du-an/";
    private final static String[] EXAMPLE_KEYS = new String[] { "picity-high-park", "ricca", "palm-garden-palm-city" };

    @Override
    public List<ProjectDTO> getAll() {
        return projectConverter.convertEntitiesToDTOs(projectRepository.findAll());
    }

    @Override
    @Scheduled(fixedRate = 1000)
    public void saveListProject() {
        var key_url = nextUrlToCrawl();
        var project = crawlerService.fetchProject(key_url.getFirst(), key_url.getSecond());
        System.out.println("fetched: " + project);
        if (validateForSave(project))
            projectRepository.save(project);
        else
            System.err.println("Skipping save of incomplete crawled data for key: " + key_url.getFirst());
    }

    private boolean validateForSave(Project project) {
        return project != null && project.getDistrict() != null && project.getStreet() != null
                && project.getDescriptions().size() > 0;
    }
}
