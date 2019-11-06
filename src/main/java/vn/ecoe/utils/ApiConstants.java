package vn.ecoe.utils;

/**
 * @author locld
 */
public class ApiConstants {


    private ApiConstants() {
    }

    public static final String API_VERSION_1 = "api/v1";
    public static final String ID = "/{id}";
    public static final String CRAWLER = "/crawler";

    /**
     * category crawler
     */
    public static final String CATEGORY_END_POINT = "/category";
    public static final String CATEGORY_CRAWLER_END_POINT = CATEGORY_END_POINT + CRAWLER;


    /**
     * Ticker Crawler
     */
    public static final String TICKER_END_POINT = "/ticker";


    /**
     * department end point
     */
    public static final String DEPARTMENT_END_POINT = "/department";
    public static final String DEPARTMENT_BY_ID_END_POINT = DEPARTMENT_END_POINT + ID;
    public static final String DEPARTMENT_CRAWLER_END_POINT = DEPARTMENT_END_POINT + CRAWLER;

    /**
     * Financial Highlight end point
     */
    public static final String FINANCIAL_HIGHLIGHT_END_POINT = "/financial-highlight";
    public static final String FINANCIAL_HIGHLIGHT_BY_ID_END_POINT = FINANCIAL_HIGHLIGHT_END_POINT + ID;
    public static final String FINANCIAL_HIGHLIGHT_CRAWLER_END_POINT = FINANCIAL_HIGHLIGHT_END_POINT + CRAWLER;
    public static final String FINANCIAL_HIGHLIGHT_TOP_END_POINT = FINANCIAL_HIGHLIGHT_END_POINT + "/top";


    /**
     * Fundamental Financial end point
     */
    public static final String FUNDAMENTAL_FINANCIAL_END_POINT = "/fundamental-financial";
    public static final String FUNDAMENTAL_FINANCIAL_BY_ID_END_POINT = FUNDAMENTAL_FINANCIAL_END_POINT + ID;
    public static final String FUNDAMENTAL_FINANCIAL_CRAWLER_END_POINT = FUNDAMENTAL_FINANCIAL_END_POINT + CRAWLER;


    /**
     * Key Financial Ratios Key end point
     */
    public static final String KEY_FINANCIAL_RATIOS_END_POINT = "/key-financial";
    public static final String KEY_FINANCIAL_RATIOS_CRAWLER_END_POINT = KEY_FINANCIAL_RATIOS_END_POINT + CRAWLER;
    public static final String KEY_FINANCIAL_RATIOS_BY_ID_END_POINT = KEY_FINANCIAL_RATIOS_END_POINT + ID;

    /**
     * Price Performance
     */
    public static final String PRICE_PERFORMANCE_END_POINT = "/price-performance";
    public static final String PRICE_PERFORMANCE_CRAWLER_END_POINT = PRICE_PERFORMANCE_END_POINT + CRAWLER;
    public static final String PRICE_PERFORMANCE_BY_ID_END_POINT = PRICE_PERFORMANCE_END_POINT + ID;
}
