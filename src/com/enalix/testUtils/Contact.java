package com.enalix.testUtils;

import java.util.ArrayList;
import java.util.Random;

public class Contact {
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
	
	private String name;
	private String phone;
	private String email;
	private String address;
	public Contact() {
	}
	public Contact(String _name, String _phone, String _email, String _address) {
		this.setName(_name);
		this.setPhone(_phone);
		this.setEmali(_email);
		this.setAddress(_address);
	}
	public void setName(String _name) {
		this.name = _name;
	}
	public String getName() {
		return this.name;
	}
	public void setPhone(String _phone) {
		this.phone = _phone;
	}
	public String getPhone() {
		return this.phone;
	}
	public void setEmali(String _email) {
		
		this.email = _email;
	}
	public String getEmail() {
		return this.email;
	}
	public void setAddress(String _address) {
		this.address = _address;
	}
	public String getAddress() {
		return this.address;
	}
	public String toString() {
		return this.name + "---" + this.phone + "---" + this.email + "---" + this.address;
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
		if (type.equalsIgnoreCase("digit"))		//0-9
			for (int i=0; i<num; i++)
				str += rand.nextInt(10);
		else if (type.equalsIgnoreCase("alpha"))	//a-zA-Z
			for (int i=0; i<num; i++) {
				int choice = rand.nextInt() % 2 == 0? 65:97;
				str += (char)(choice + rand.nextInt(26));						
			}
		else if (type.equalsIgnoreCase("lower"))	//a-z
			for (int i=0; i<num; i++)
				str += (char)(97+rand.nextInt(26));
		else if (type.equalsIgnoreCase("upper"))	//A-Z
			for (int i=0; i<num; i++)
				str += (char)(65+rand.nextInt(26));
		else if (type.equalsIgnoreCase("alnum"))	//0-9a-zA-Z
			for (int i=0; i<num; i++) {
				int choice = rand.nextInt(3);
				if (choice == 0)
					str += (char)(65+rand.nextInt(26));
				else if (choice == 1)
					str += (char)(97+rand.nextInt(26));
				else
					str += rand.nextInt(10);
			}
		else if (type.equalsIgnoreCase("graph"))	//32-127, all visiable char
			for (int i=0; i<num; i++)
				str += (char)(rand.nextInt(95)+32);
		return str;
	}
	public static String getRandString(String type, int len) {
		return getRandString(type, len, len);
	}
	public static Contact getContact() {
		return new Contact(generateName(), generatePhone(), generateEmail(), generateAddress()); 
	}
	public static Contact[] getContact(int num) {
		Contact[] cont = new Contact[num];
		for (int i=0; i<num; i++)
			cont[i] = getContact();
		return cont;
	}
	public static void main(String[] args) {
		Contact[] cont = getContact(5);
		for (int i=0; i<5; i++)
			System.out.println(cont[i]);
	}
}
