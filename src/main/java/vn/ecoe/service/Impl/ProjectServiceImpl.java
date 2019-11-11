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

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    CrawlerServiceImpl crawlerService;

    @Autowired
    ProjectConverter projectConverter;

    public Pair<String, String> nextUrlToCrawl() {
        if (keysLeft == null || keysLeft.isEmpty()) {
            // TODO: add multiple starting points, e.g. HCMC, Ha Noi, Da Nang, Hai Phong, etc. and do
            // breadth-search from them, first going over all starting cities at areaIteration=0, then 1...
            var locationParams = latLonUrlParams(10.817303082969843, 106.738454294191, areaIteration++);
            var keys = crawlerService.fetchProjectKeys(DISCOVER_URL + locationParams);
            keysLeft = new LinkedList<>(keys);
        }
        if (keysLeft.isEmpty())
            return null;
        var key = keysLeft.remove();
        return Pair.of(key, URL + key);
    }
    private int areaIteration = 0;
    private Queue<String> keysLeft;

    private final static String BASE_URL = "https://rever.vn/";
    private final static String URL = BASE_URL + "du-an/";
    private final static String DISCOVER_URL = BASE_URL + "s/unnamed-road/du-an/mua?zoom=16";

    private static String latLonUrlParams(double lat, double lon, int areaIter) {
        double SEARCH_BOX_REACH = 0.01;
        var xyOffset = spiralCoordinates(areaIter);
        lat += xyOffset.getFirst() * SEARCH_BOX_REACH * 2;
        lon += xyOffset.getSecond() * SEARCH_BOX_REACH * 2;
        return "&lat=" + lat + "&lon=" + lon + "&bounds=" + (lat + SEARCH_BOX_REACH) + "," + (lon + SEARCH_BOX_REACH) + "," + (lat - SEARCH_BOX_REACH) + "," + (lon - SEARCH_BOX_REACH);
    }

    /**
     * Calculate coordinates of moving on 2d grid in a spiral pattern starting in center, then
     * going up-right, down, left, up, right, down, left, up, right, etc.
     * @param i - step of movement
     * @return x,y coordinates of position at i-th step
     */
    private static Pair<Integer, Integer> spiralCoordinates(int i) {
        int distance = (int) Math.round(Math.sqrt(i) / 2);
        int rectSize = 2 * distance - 1;
        int rectSteps = rectSize + 1;
        int step = i - rectSize * rectSize;
        int side = distance > 0 ? step / rectSteps : 0;
        int pos = distance > 0 ? step % rectSteps : 0;
        switch (side) {
            // top-right corner, moving down
            case 0: return Pair.of(distance, -distance + pos);
            // bottom-right corner, moving left
            case 1: return Pair.of(distance - pos, distance);
            // bottom-left corner, moving up
            case 2: return Pair.of(-distance, distance - pos);
            // top-left corner, moving right
            case 3: return Pair.of(-distance + pos, -distance);
        }
        return null;
    }

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
