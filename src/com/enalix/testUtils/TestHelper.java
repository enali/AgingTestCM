package com.enalix.testUtils;

import java.util.Random;

public class TestHelper {
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
	
	private static final String[] URL_DOWN = {};

	public static void main(String[] args) {
		String expr = generateCalExpr();
		System.out.println(expr);
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
	public static String generateEmail() {
		Random rand = new Random();
		String account = getRandString("alnum", 7, 15);
		String company = getRandString("lower", 2, 7);
		return account+"@"+company+"."+EMAIL_END[rand.nextInt(EMAIL_END.length)];
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
	/* need to configure the customer's behave , you can also add your customer */
	public Customer getGameCustomer() {
		return new Customer();
	}
	public Customer getGeneralCustomer() {
		return new Customer();
	}
	public Customer getBusinessCustomer() {
		return new Customer();
	}
	public Customer getStudentCustomer() {
		return new Customer();
	}
	public Customer getRandCustomer() {
		return new Customer();
	}
}
