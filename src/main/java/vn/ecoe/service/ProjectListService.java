package vn.ecoe.service;

import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
public class ProjectListService {
    public Pair<String, String> nextUrlToCrawl() {
        // TODO: crawl project list or obtain reliable list of projects other way
        index = (index + 1) % EXAMPLE_KEYS.length;
        return Pair.of(EXAMPLE_KEYS[index], URL + EXAMPLE_KEYS[index]);
    }

    private int index = -1;
    
    private final static String URL = "https://rever.vn/du-an/";
    private final static String[] EXAMPLE_KEYS = new String[] { "picity-high-park", "ricca", "palm-garden-palm-city" };
}
