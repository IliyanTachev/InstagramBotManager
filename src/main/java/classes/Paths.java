package classes;

public final class Paths {
    public static String CREDENTIALS_FILE; // exception
    public static final String SITE_URL = "https://instagram.com";
    public static String DRIVER_PATH;
    public static String FOLLOWED_PROFILES_LINKS = "/Users/iliyan/Documents/programming/instagram_bot_manager/git/followed_accounts.txt";
    public static String CUSTOM_CURSOR = "D:\\COMPUTER\\Hristian projects\\JAVA\\InstagramBotManeger project\\git\\resources\\cantClickCursor.png";
    String driverPath;

    static {
        String os = System.getProperty("os.name");
        if (os.equals("Mac OS X")) {
            DRIVER_PATH = "./chromedriver";
            CREDENTIALS_FILE = "/Users/iliyan/Documents/programming/instagram_bot_manager/git/config/credentials";
        } else {
            DRIVER_PATH = "chromedriver.exe"; // windows
            CREDENTIALS_FILE = "D:\\COMPUTER\\Hristian projects\\JAVA\\InstagramBotManeger project\\git\\resources\\credentials";
        }
    }
}
