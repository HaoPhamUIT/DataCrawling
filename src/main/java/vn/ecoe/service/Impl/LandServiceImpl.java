package vn.ecoe.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vn.ecoe.converter.LandConverter;
import vn.ecoe.dto.LandDTO;
import vn.ecoe.model.Land;
import vn.ecoe.repository.LandRepository;
import vn.ecoe.service.LandService;

import java.util.List;

@Service
public class LandServiceImpl implements LandService {

    @Autowired
    LandRepository landRepository;

    @Autowired
    CrawlerServiceImpl crawlerService;

    @Autowired
    LandConverter landConverter;

    public Pair<String, String> nextUrlToCrawl() 
    {
        // TODO: crawl project list or obtain reliable list of projects other way
        index = (index + 1) % EXAMPLE_KEYS.length;
        return Pair.of(EXAMPLE_KEYS[index], URL + EXAMPLE_KEYS[index]);
    }
    private int index = -1;
        
    private final static String URL = "https://rever.vn/mua/";
    private final static String[] EXAMPLE_KEYS = new String[] { "can-ho-dream-home-residence-2-phong-ngu-tang-trung-thap-b-nha-trong",
    															"can-ho-tang-trung-chung-cu-phu-gia-hung-2-view-song-mat-me"};

    @Override
    public List<LandDTO> getAll() 
    {
        return landConverter.convertEntitiesToDTOs(landRepository.findAll());
    }

    @Override
    @Scheduled(fixedRate = 1000)
    public void saveListLand() 
    {
        var key_url = nextUrlToCrawl();
        var land = crawlerService.fetchLand(key_url.getFirst(), key_url.getSecond());
        System.out.println("fetched: " + land);
        if (validateForSave(land))
            landRepository.save(land);
        else
            System.err.println("Skipping save of incomplete crawled data for key: " + key_url.getFirst());
    }

    private boolean validateForSave(Land project) 
    {
        return project != null && 
        	   project.getDistrict() != null && 
        	   project.getStreet() != null && 
        	   !project.getDescription().isEmpty();
    }
}
