package common;

public final class Paths {
    public static String CREDENTIALS_FILE; // exception
    //TODO: HAVE THE SAME 'FOLLOWED_PROFILES' VARIABLE FOR BOTH 'MAC OS' AND 'WINDOWS' AND CHANGE ITS PATH IN THE 'IF' STATEMENT BELLOW
    public static String FOLLOWED_PROFILES = "D:\\COMPUTER\\Hristian projects\\JAVA\\IG_Bot\\InstagramBotManager\\src\\main\\resources\\accounts\\phone_headmaster\\peopleToUnfollow.txt";
    public static String BANNED_PHOTOS = "D:\\COMPUTER\\Hristian projects\\JAVA\\IG_Bot\\InstagramBotManager\\src\\main\\resources\\accounts\\phone_headmaster\\uploadPhotos\\bannedPhotos.txt";
    public static String PHOTOS_CAPTIONS = "D:\\COMPUTER\\Hristian projects\\JAVA\\IG_Bot\\InstagramBotManager\\src\\main\\resources\\accounts\\phone_headmaster\\uploadPhotos\\photosCaptions.txt";
    public static String PHOTOS_TO_UPLOAD = "D:\\COMPUTER\\Hristian projects\\JAVA\\IG_Bot\\InstagramBotManager\\src\\main\\resources\\accounts\\phone_headmaster\\uploadPhotos\\images\\";
    public static String PHOTOS_TO_BE_CHECKED = "D:\\COMPUTER\\Hristian projects\\JAVA\\IG_Bot\\InstagramBotManager\\src\\main\\resources\\accounts\\phone_headmaster\\uploadPhotos\\";
    public static String FOLLOWED_PROFILES_LINKS = "/Users/iliyan/Documents/programming/instagram_bot_manager/git/followed_accounts.txt";
    public static final String SITE_URL = "https://instagram.com";
    public static String DRIVER_PATH;
    //public static String CUSTOM_CURSOR = "D:\\COMPUTER\\Hristian projects\\JAVA\\InstagramBotManeger project\\git\\resources\\cantClickCursor.png";
    String driverPath;

    static {
        String os = System.getProperty("os.name");
        if (os.equals("Mac OS X")) {
            DRIVER_PATH = "./chromedriver";
            CREDENTIALS_FILE = "/Users/iliyan/Documents/programming/instagram_bot_manager/git/config/credentials";
        } else {
            DRIVER_PATH = "chromedriver.exe"; // windows
            CREDENTIALS_FILE = "D:\\COMPUTER\\Hristian projects\\JAVA\\IG_Bot\\credentials.txt";
        }
    }
}
