package com.enalix.testUtils;

import java.util.Random;

public class TestHelper {
	public static String APP_PATH = "/sdcard/wandoujia/app";
	public static String[] APP_LIST = {"UC浏览器_1409479863771.apk", "WiFi伴侣_1409487164898.apk",
		"唯品会_1409479854592.apk", "捕鱼达人3_1409487270000.apk", "搜狗手机输入法_1409479860181.apk",
		"携程旅行_1409479871680.apk", "支付宝钱包_1409487199600.apk", "最美壁纸_1409487167417.apk", 
		"美团团购_1409479849508.apk", "课程格子_1409487186956.apk", "陌陌_1409487151713.apk", 
		"鳄鱼小顽皮爱洗澡官方正版_1409487341080.apk"};
	
	public static long TIMEOUT = 20000;
	public static String LOG_TAG = "CyanogenMod-nexus5";
	public static String SCREENSHOTS = "uiAutoTest";
	
	public static String ETE_CALL_NUMBER = "218";
	public static String EMERGENCY_CALL_NUMBER = "911";
	public static String CHINAMOBILE_CALL_NUMBER = "10086";
	public static final int  LONG_CALL_TIME=4*60;
	
	private static final String[] SUB_NUMBER = {
        "134", "156", "189", "135", "139", "131" };

	private static final String[] EMAIL_END = {
        "com", "net", "cn", "org" };
	
   private static final String[] CITY_LIST = {
   	"Shanghai", "Beijing", "Taibei", "Guangzhou", "Shenzhen", "Tianjin", "Nanjing", "Hangzhou", "Chengdu",
   	"Wuhan", "Chongqing", "Shenyang", "Xi'an", "Aomen", "Gaoxiong", "Dalian", "QingDao", "Suzhou", "Ningbo",
   	"Wuxi", "Changsha", "Zhengzhou", "Xiamen", "Haerbin", "Jinan", "Changchun", "Fuzhou", "Hefei", "Foshan", 
   	"Shijiazhuang", "Nanning", "Taiyuan", "Wulumuqi", "Kunming", "Nanchang", "Lanzhou", "Gunyang", "Taizhong",
   	"Dongguan", "Shaoxing", "Changzhou", "Yantai", "Quanzhou", "HongKong", "Wenzhou", "Tangshan", "Nantong"};
   
	private static final int NUMBER_LENGTH = 11;
	
	private static final String[] OPERATOR = {"+", "−", "×", "÷" };
	
	private static final String[] URL_DOWN = {};	//TODO:
	
	//do something , then sleep
	private static final int HOUR_DELAY = 0;
	private static final int DAY_DELAY = 0;
	private static final int WEEK_DELAY = 0;

	public static void main(String[] args) {
	}
	public static String generateName() {
        Random ran = new Random();
        String name = "" + (char)(65+ran.nextInt(26));
        return name+getRandString("lower", 5, 9);
    }
	public  static String generatePhone() {
		Random rand = new Random();
		String telnum = SUB_NUMBER[rand.nextInt(SUB_NUMBER.length)];
		return telnum + getRandString("digit", NUMBER_LENGTH-3);
	}
	public static String[] generatePhone(int num) {
		String[] phone = new String[num];
		for (int i=0; i<num; i++)
			phone[i] = generatePhone();
		return phone;
	}
	public static String generateEmail() {
		Random rand = new Random();
		String account = getRandString("alnum", 7, 15);
		String company = getRandString("lower", 2, 7);
		return account+"@"+company+"."+EMAIL_END[rand.nextInt(EMAIL_END.length)];
	}
	public static String[] generateEmail(int num) {
		String[] email = new String[num];
		for (int i=0; i<num; i++)
			email[i] = generateEmail();
		return email;
	}
	public static String generateAddress() {
		Random rand = new Random();
		return CITY_LIST[rand.nextInt(CITY_LIST.length)];
	}
	public static String getRandString(String type, int lenMin, int lenMax) {
		Random rand = new Random();
		String str="";
		int num = lenMin;
		if (lenMax != lenMin)
			num += rand.nextInt(lenMax-lenMin);
		if (type.equalsIgnoreCase("expr")) {	//a cal expr's first nubmer can't be 0
			str += rand.nextInt(9) + 1;
			if (num>1)
				if (rand.nextInt(2) == 1) {
					int num1 = rand.nextInt(num-1);
					for (int i=0; i<num1; i++)
						str += rand.nextInt(10);
					str += ".";
					for (int i=0; i<num-1-num1-1; i++)
						str += rand.nextInt(10);
					str += rand.nextInt(9) + 1;		//kill 23.450, the last number will not be 0, when the number has "."
				} else {
					for (int i=1; i<num; i++)
						str += rand.nextInt(10);
				}

		}
		if (type.equalsIgnoreCase("digit"))		//0-9
			for (int i=0; i<num; i++)
				str += rand.nextInt(10);
		if (type.equalsIgnoreCase("alpha"))	//a-zA-Z
			for (int i=0; i<num; i++) {
				int choice = rand.nextInt() % 2 == 0? 65:97;
				str += (char)(choice + rand.nextInt(26));						
			}
		if (type.equalsIgnoreCase("lower"))	//a-z
			for (int i=0; i<num; i++)
				str += (char)(97+rand.nextInt(26));
		if (type.equalsIgnoreCase("upper"))	//A-Z
			for (int i=0; i<num; i++)
				str += (char)(65+rand.nextInt(26));
		if (type.equalsIgnoreCase("alnum"))	//0-9a-zA-Z
			for (int i=0; i<num; i++) {
				int choice = rand.nextInt(3);
				if (choice == 0)
					str += (char)(65+rand.nextInt(26));
				else if (choice == 1)
					str += (char)(97+rand.nextInt(26));
				else
					str += rand.nextInt(10);
			}
		if (type.equalsIgnoreCase("graph"))	//32-127, all visiable char
			for (int i=0; i<num; i++)
				str += (char)(rand.nextInt(95)+32);
		return str;
	}
	public static String getRandString(String type, int len) {
		return getRandString(type, len, len);
	}
	/* just for People app usage */
	public static Contact generateContact() {
		return new Contact(generateName(), generatePhone(), generateEmail(), generateAddress()); 
	}
	public static Contact[] generateContact(int num) {
		Contact[] cont = new Contact[num];
		for (int i=0; i<num; i++)
			cont[i] = generateContact();
		return cont;
	}
	/* just for calculator usage */
	public static String generateCalExpr() {
		Random rand = new Random();
		int num = rand.nextInt(3)+2;	//a cal expr has 2 number at least, 5 number at most.
		String expr = getRandString("expr", 1, 7);	//a cal number's length between 1~7
		for (int i=0; i<num; i++) {
			expr += OPERATOR[rand.nextInt(OPERATOR.length)] + getRandString("expr", 1, 7);
		}
		return expr;		
	}
	public static String[] generateCalExpr(int num) {
		String[] str = new String[num];
		for (int i=0; i<num; i++)
			str[i] = generateCalExpr();
		return str;
	}
	/* use for generate url to download file */
	public static String[] generateRandUrlDown(int num) {
		if (num < URL_DOWN.length) {
			String[] str = new String[num];
			Random rand = new Random();
			for (int i=0; i<num; i++)
				str[i] = URL_DOWN[rand.nextInt(URL_DOWN.length)];
			return str;
		} else {
			return URL_DOWN;
		}
	}
	public static String[] generateUrlDown(int[] num) {
		String[] str = new String[num.length];
		for (int i=0; i<num.length; i++)
			str[i] = URL_DOWN[num[i]];
		return str;
	}
	
	
	
	//just for gamer habit
	public static Behavior getGamerBehavior() {
		Random rand = new Random();
		Behavior bh = new Behavior();
		bh.setGameNum(rand.nextInt(10)+20);	//20-30
		bh.setInstAppNum(rand.nextInt(10)+5); //5-15
		bh.setUnInstAppNum(rand.nextInt(5)+5); //5-10
		bh.setRecvEmailNum(rand.nextInt(1)+2); //2-3
		bh.setRecvSmsNum(rand.nextInt(4)+4); //4-8
		bh.setSendEmailNum(rand.nextInt(2)+1); //1-3
		bh.setSendSmsNum(rand.nextInt(3)+2); //2-5
		
		return bh;
	}
	//just for business' man habit
	public static Behavior getBusinessDayBehavior() {
		//TODO:
		return new Behavior();
	}
	public static Behavior[] getBusinessDayBehavior(int num) {
		Behavior[] behavior = new Behavior[num];
		for (int i=0; i<num; i++)
			behavior[i] = getStudentDayBehavior();
		return behavior;
	}
	public static Behavior getBusinessWeekDayBehavior() {
		return new Behavior();
	}
	//just for student's habit
	public static Behavior getStudentDayBehavior() {
		//TODO:
		return new Behavior();
	}
	public static Behavior[] getStudentDayBehavior(int num) {
		Behavior[] behavior = new Behavior[num];
		for (int i=0; i<num; i++)
			behavior[i] = getStudentDayBehavior();
		return behavior;
	}
	public static Behavior getStudentWeekdayBehavior() {
		return new Behavior();
	}
	//just for general customer's habit
	public static Behavior getRandWeekdayBehavior() {
		return new Behavior();
	}
	public static Behavior getRandDayBehavior() {
		//TODO:
		return new Behavior();
	}
	public static Behavior[] getRandDayBehavior(int num) {
		Behavior[] cust = new Behavior[num];
		for (int i=0; i<num; i++)
			cust[i] = getRandDayBehavior();
		return cust;
	}
	//generate a serial int array, no repeated num
	public static int[] generateNoReptNumber(int num, int within) {
		int[] numArray = new int[num];
		if (num>within)
			return new int[0];
		boolean[] bool = new boolean[within];
		Random rand = new Random();
		int randnum;
		for (int i=0; i<num; i++) {
			do {
				randnum = rand.nextInt(within);
			} while(bool[randnum]);
			bool[randnum] = true;
			numArray[i] = randnum;
		}
		return numArray;
	}
	public static String generateMessage() {
		return "abc";
	}
	public static String[] generateMessage(int num) {
		String[] str = new String[num];
		for (int i=0; i<num; i++)
			str[i] = getRandString("alpha", 20, 140);
		//TODO:better method to generate message
		return str;
	}
}
