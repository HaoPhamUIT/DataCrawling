package vn.ecoe.utils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.springframework.data.util.Pair;
import org.springframework.util.StringUtils;

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

    public static Pair<String, String> splitToRange(String value, String separator) {
        if (StringUtils.isEmpty(value))
            return Pair.of(null, null);
        String[] range = value.split(separator);
        return Pair.of(range[0], range.length > 1 ? range[1] : range[0]);
    }

    public static BigDecimal tryParseBigDecimal(String text) {
        if (StringUtils.isEmpty(text = StringUtils.trimWhitespace(text)))
            return null;
        try {
            return new BigDecimal(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static Integer tryParseInt(String text) {
        if (StringUtils.isEmpty(text = StringUtils.trimWhitespace(text)))
            return null;
        text = text.replace(",", "");
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
