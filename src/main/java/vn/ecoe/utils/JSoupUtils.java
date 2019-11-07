package vn.ecoe.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;

public class JSoupUtils {
    public static Map<String, String> extractPropertiesWithKeyValue(Document doc, String itemsSelector, String keySubSelector, String valSubSelector) {
        var items = doc.select(itemsSelector);
        var props = new HashMap<String, String>();
        for (var item : items) {
            var key = item.selectFirst(keySubSelector);
            var value = item.selectFirst(valSubSelector);
            if (key == null || !key.hasText() || value == null || !value.hasText())
                continue;
            props.put(key.text(), value.text());
        }
        return props;
    }

    public static String extractText(Document doc, String selector) {
        var element = doc.selectFirst(selector);
        if (element == null)
            return null;
        var textNodes = element.textNodes();
        if (textNodes.size() == 0)
            return null;
        return textNodes.get(0).text();
    }

    public static String extractAttribute(Document doc, String selector, String attribute) {
        var element = doc.selectFirst(selector);
        if (element == null)
            return null;
        return element.attr(attribute);
    }

    public static BigDecimal parseBigDecimal(String text) {
        if (text == null)
            return null;
        text = text.trim();
        if (text.isEmpty())
            return null;
        return new BigDecimal(text);
    }
}
