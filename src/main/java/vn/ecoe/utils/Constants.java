package vn.ecoe.utils;

/**
 * @author locld
 */
public class Constants {


    // Path in environment
    //    public static final String ROOT_FOLDER = "/home/nhansilva/Working/Lab/data/stock-service/";
//    public static final String DEPARTMENT_FILE_PATH = "/home/nhansilva/Working/Lab/stock-service/data/all.html";
    public static final String ROOT_FOLDER = "D:\\Lab\\data\\stock-service";
    public static final String DEPARTMENT_FILE_PATH = "D:\\Lab\\stock-service\\data\\all.html";



    /**
     * URL
     */
    public static final String BASE_URL = "http://ra.vcsc.com.vn/Sector/";
    public static final String URL_EXPORT = "http://ra.vcsc.com.vn/Company/Export";
    public static final String SUFFIX_URL = "?icbLevel=1&lang=vi-VN";
    public static final String URL_GET_TICKER = "http://ra.vcsc.com.vn/Home/GetTicker?lang=vi-VN";


    public static final String BLANK = "";
    public static final String PREFIX_REPLACE = "/Sector/";
    public static final String SUFFIX_SUB = "?icbLevel=2&lang=vi-VN";
    public static final String HTML_EXTENSION = ".html";


    // Split
    public static final String HIGHLIGHT_SPLIT = "Highlights/";
    public static final String FUNDAMENTAL_SPLIT = "Fundamental/";
    public static final String RATIOS_SPLIT = "Ratios/";
    public static final String PERFORMANCE_SPLIT = "Performance/";


    // Nav for get file
    public static final String NAV_FOR_FUNDAMENTAL = "/Company/FundamentalFinancialData";
    public static final String NAV_FOR_RATIOS = "/Company/KeyFinancialRatios";
    public static final String NAV_FOR_HIGHLIGHT = "/Company/FinancialHighlights";
    public static final String NAV_FOR_PERFORMANCE = "/Company/PricePerformance";

    //Child Folder
    public static final String CONTENT_FILE_PATH = "/home/nhansilva/Working/Lab/stock-service/data/Content/";
    public static final String RATIOS = "data/Ratios/";
    public static final String HIGHLIGHT = "data/Highlights/";
    public static final String FUNDAMENTAL = "data/Fundamental/";
    public static final String PERFORMANCE = "data/Performance/";


    // Variable for compare
    public static final float BASIC_ROE = 15;
    public static final int DEFAULT_PAGE = 0;
    public static final int SIZE = 20;


    private Constants() {
    }


    public static final String URL_GET_IMAGE = "http://ra.vcsc.com.vn/Home/ExportExcel?title=Ng%C3%A0nh%20T%E1%BB%95ng%20quan&url=http%253a%252f%252fra.vcsc.com.vn%252fSector%252fP_SectorSummary%252f169%253ficbLevel%253d1%2526isExport%253dTrue&fileName=nganh_all";
    /**
     * Define message for 400 error code
     */
    public static final String CAN_NOT_BE_FOUND_FILE_MESSAGE = "Cannot be found file or file is illegal";
    public static final String CAN_NOT_BE_FOUND_ENTITY_MESSAGE = "Cannot be found entity";

    public final static String HEADER_KEY_DISTRICT = "Quận";
    public final static String HEADER_KEY_AREA = "Khu dân cư";
    public final static String HEADER_KEY_STREET = "Đường";
    public final static String SUMMARY_KEY_STATUS = "Trạng thái:";
    public final static String SUMMARY_KEY_TYPE = "Loại hình:";
    public final static String SUMMARY_KEY_NUM_UNITS = "Số căn:";
    public final static String SUMMARY_KEY_NUM_BLOCKS = "Số block:";
    public final static String SUMMARY_KEY_NUM_FLOORS = "Số tầng:";

}
