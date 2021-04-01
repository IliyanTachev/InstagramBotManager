package common;

public final class Paths {
    public static String CREDENTIALS_FILE; // exception
    //TODO: HAVE THE SAME 'FOLLOWED_PROFILES' VARIABLE FOR BOTH 'MAC OS' AND 'WINDOWS' AND CHANGE ITS PATH IN THE 'IF' STATEMENT BELLOW
    public static String ACCOUNTS_FOLDER = "D:\\COMPUTER\\Hristian projects\\JAVA\\IG_Bot\\InstagramBotManager\\src\\main\\resources\\accounts\\";
    public static String UPLOAD_PHOTOS_FOLDER = ACCOUNTS_FOLDER + "account-name-here\\uploadPhotos\\";

    public static String FOLLOWED_PROFILES = ACCOUNTS_FOLDER + "account-name-here\\peopleToUnfollow.txt";
    public static String PHOTOS_TO_BE_CHECKED = ACCOUNTS_FOLDER + "account-name-here\\uploadPhotos\\";

    public static String BANNED_PHOTOS = UPLOAD_PHOTOS_FOLDER + "bannedPhotos.txt";
    public static String PHOTOS_CAPTIONS = UPLOAD_PHOTOS_FOLDER + "photosCaptions.txt";
    public static String PHOTOS_TO_UPLOAD = UPLOAD_PHOTOS_FOLDER + "images\\";
    public static String ACCOUNT_HASHTAGS = UPLOAD_PHOTOS_FOLDER + "accountHashtags.txt";
    public static String ACCOUNTS_TO_GET_PHOTOS_FROM = UPLOAD_PHOTOS_FOLDER + "accountsToGetPhotosFrom.txt";

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
