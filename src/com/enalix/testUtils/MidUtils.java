package com.enalix.testUtils;

import java.util.ArrayList;
import java.util.Random;

import android.os.RemoteException;
import android.util.Log;

import com.android.uiautomator.core.*;

public class MidUtils extends LowUtils {
	/**
	 * get wifi state, open/close wifi
	 * @return "ON" or "OFF"
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	/* work in settings list screen */
	public String getWifiStat() throws UiObjectNotFoundException, RemoteException {
		return getSwitch("Wi‑Fi").getText();
	}
	public void openWifi() throws UiObjectNotFoundException, RemoteException {
		UiObject uiSwitch = getSwitch("Wi‑Fi");
		if (uiSwitch.exists() && !uiSwitch.isChecked()) {
			uiSwitch.click();
		}
	}
	public void closeWifi() throws UiObjectNotFoundException, RemoteException {
		UiObject uiSwitch = getSwitch("Wi‑Fi");
		if (uiSwitch.exists() && uiSwitch.isChecked()) {
			uiSwitch.click();
		}
	}
	/* work in wifi list screen */
	public void connectWifi(String wifiName, String wifiPasswd) throws RemoteException, UiObjectNotFoundException {
		Log.d(TAG, "Connect to Wifi with password...");
		UiObject uiObjWifi = getObjByTxt(wifiName);
		if (uiObjWifi.exists()) {
			Log.d(TAG, "Ap finded");
			uiObjWifi.clickAndWaitForNewWindow();
		}
		UiObject uiFor = getObjByTxt("Forget");
		if (uiFor.exists()) {
			uiFor.clickAndWaitForNewWindow();
			uiObjWifi.clickAndWaitForNewWindow();
		}
		getEdit().setText(wifiPasswd);
		pressBack();
		UiObject uiOk = getObjByTxt("Connect");
		uiOk.waitForExists(10);
		if (uiOk.exists())
			uiOk.clickAndWaitForNewWindow();
		String state = getObjByTxt(wifiName).getFromParent(new UiSelector().className("android.widget.TextView")).getText();
		if (state.equalsIgnoreCase("Connected"))
			Log.d(TAG, "wifi connected");
	}
	/**
	 * call directly a Telphone number
	 * @param number tel number
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	/* work in phone screen */
	public void callTelnum(String number) throws UiObjectNotFoundException, RemoteException {
		UiObject uiObj = getObjByDesc("dial pad");
		if (uiObj.exists())
			uiObj.clickAndWaitForNewWindow();
		clearEditTxt(getEdit());
		for (int i=0; i<number.length(); i++)
			getObjByTxt(String.valueOf(number.charAt(i))).click();
		getObjByDesc("dial").clickAndWaitForNewWindow();
	}
	/* work in contact list screen */
	public void callName(String name) throws RemoteException, UiObjectNotFoundException {
		openContact(name);
		getObjByClsIdx("android.widget.FrameLayout", 2).clickAndWaitForNewWindow();
	}
	
	public void endCall() {
		//TODO:
	}
	/**
	 * open/add/delete a contact
	 * @param name
	 * @param phone
	 * @param email
	 * @param address
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	/* now is the contacts list, will open contact window */
	public void openContact(String name) throws UiObjectNotFoundException, RemoteException {
		UiObject uiCont = getScr().getChildByText(new UiSelector().className("android.widget.TextView"), name);
		if (uiCont.exists())
			uiCont.clickAndWaitForNewWindow();
	}
	/* now is contacts list, will back to it after add a contact */
	public void addContact(Contact contact) throws RemoteException, UiObjectNotFoundException {
		getObjByDesc("Add Contact").clickAndWaitForNewWindow();
		getEditByTxt("Name").setText(contact.getName());
		getUiDevice().pressBack();
		getEditByTxt("Phone").setText(contact.getPhone());
		getUiDevice().pressBack();
		getEditByTxt("Email").setText(contact.getEmail());
		getUiDevice().pressBack();
		getEditByTxt("Address").setText(contact.getAddress());
		getUiDevice().pressBack();
		getObjByTxt("Done").clickAndWaitForNewWindow();	
		pressBack();
	}
	public void addContact(ArrayList<Contact> contactList) throws RemoteException, UiObjectNotFoundException {
		for (Contact contact:contactList)
			addContact(contact);
	}
	/* now is the contact screen */
	public void delContact(String name) throws UiObjectNotFoundException, RemoteException {
		getUiDevice().pressMenu();
		getObjByTxt("Delete").clickAndWaitForNewWindow();
		getObjByTxt("OK").clickAndWaitForNewWindow();
	}
	public void delContact(String[] nameList) throws RemoteException, UiObjectNotFoundException {
		for (String name:nameList)
			delContact(name);
	}
	public void delAllContact() {
		//TODO:
	}
	public void exportContact() {
		
	}
	public void importContact() {
		
	}
	/* now is the contact screen */
	public Contact getContact(String name) throws RemoteException, UiObjectNotFoundException {
		UiObject uiObj = getObjByClsIdx("android.widget.FrameLayout", 2);
		String phone = uiObj.getChild(new UiSelector().resourceId("com.android.contacts:id/data")).getText();
		phone = phone.replaceAll("-", "").replace(" ", "");
		uiObj = getObjByClsIdx("android.widget.FrameLayout", 4);
		String email = uiObj.getChild(new UiSelector().resourceId("com.android.contacts:id/data")).getText();
		uiObj = getObjByClsIdx("android.widget.FrameLayout", 6);
		String address = uiObj.getChild(new UiSelector().resourceId("com.android.contacts:id/data")).getText();
		return new Contact(name, phone, email, address);
	}
	public void editContact(Contact cont) {
		
	}
	/**
	 * send message
	 * @param num
	 * @param mes
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	/* work in messaging list screen */
	public void sendSmsByTelnum(String num, String mes) throws UiObjectNotFoundException, RemoteException {
		getObjByDesc("New message").clickAndWaitForNewWindow();
		getMultiEditByTxt("To").setText(num);
		getEditByTxt("Type message").setText(mes);
		pressBack();
		getObjByDesc("Send").clickAndWaitForNewWindow();
	}
	/* work in contact list screen , will keep in contact sms screen */
	public void sendSms(String name, String mes) throws UiObjectNotFoundException, RemoteException {
		openContact(name);
		getObjByDesc("Text mobile").clickAndWaitForNewWindow();
		getEditByTxt("Type message").setText(mes);
		pressBack();
		getObjByDesc("Send").clickAndWaitForNewWindow();
	}
	/* work in messaging list screen */
	public void delSms(String name) throws UiObjectNotFoundException, RemoteException {
		getObjByTxtContains(name).clickAndWaitForNewWindow();
		pressMenu();
		getObjByTxtContains("Delete").clickAndWaitForNewWindow();
		getObjByTxt("Delete").clickAndWaitForNewWindow();
	}
	public void delAllSms() {
		//TODO:
	}

	/**
	 * open web url with browser
	 * @param url, such as "www.baidu.com"
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	/* work in browser main screen */
	public void openUrl(String url) throws RemoteException, UiObjectNotFoundException {
		setEditTxt(getEdit(), url);
		pressEnter();
	}
	/**
	 * do some simply calculate
	 * @param cal, such as "1+2", "1*9/4+2"
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	/* work in calculator main screen */
	public void calculate(String cal) throws RemoteException, UiObjectNotFoundException {
		clearEditTxt(getEdit());
		cal = cal.replace("*", "×").replace("/", "÷").trim();
		if (!cal.endsWith("="))
			cal = cal + "=";
		for (int i=0; i<cal.length(); i++)
			getObjByTxt(String.valueOf(cal.charAt(i))).click();
	}
	/**
	 * open a dirpath
	 * @param dirPath, such as "baidu/ime", "/storage/emulated/0/baidu/ime"
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	/* work in FileManager main screen , but will go to the path that you have gave*/
	public void openDir(String dirPath) throws RemoteException, UiObjectNotFoundException {
		String prefix = "/storage/emulated/o/";
		if (dirPath.startsWith(prefix))
			dirPath = dirPath.trim().replace(prefix, "");
		String[] dir = dirPath.split("/");
		for (String dirname:dir)
			getChildByClsTxt("android.widget.TextView", dirname).clickAndWaitForNewWindow();
	}
	public void delFile(String fileName) {
		//TODO:longPress
		Log.d(TAG, "file" + fileName + "will delete ...");
		if (!getObjByTxt(fileName).exists())
			Log.d(TAG, "file" + fileName + "has deleted");
	}
	public void delFile(String[] fileArray) {
		for (String file:fileArray) {
			delFile(file);
		}
	}
	public void renameFile(String oldName, String newName) {
		
	}
	/**
	 * turn on airplane mode
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	/* work in Settings -> Wireless & networks screen */
	public void openApMode() throws UiObjectNotFoundException, RemoteException {
		UiObject uiCbox = getObjByClsIdx("android.widget.CheckBox", 0);
		if (!uiCbox.isChecked())
			uiCbox.click();
	}
	public void closeApMode() throws RemoteException, UiObjectNotFoundException {
		UiObject uiCbox = getObjByClsIdx("android.widget.CheckBox", 0);
		if (uiCbox.isChecked())
			uiCbox.click();
	}
	/**
	 * turn on bluetooth
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	/* work in Settings */
	public void openBluetooth() throws RemoteException, UiObjectNotFoundException {
		UiObject uiSwitch = getSwitch("Bluetooth");
		if (uiSwitch.exists() && !uiSwitch.isChecked())
			uiSwitch.click();
	}
	public void closeBluetooth() throws RemoteException, UiObjectNotFoundException {
		UiObject uiSwitch = getSwitch("Bluetooth");
		if (uiSwitch.exists() && uiSwitch.isChecked())
			uiSwitch.click();
	}
	/**
	 * turn on mobile networks
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	/* work in Settings */
	public void openDataNet() throws RemoteException, UiObjectNotFoundException {
		UiObject uiSwitch = getSwitch("Mobile networks");
		if (uiSwitch.exists() && !uiSwitch.isChecked())
			uiSwitch.click();
	}
	public void closeDataNet() throws RemoteException, UiObjectNotFoundException {
		UiObject uiSwitch = getSwitch("Mobile networks");
		if (uiSwitch.exists() && uiSwitch.isChecked())
			uiSwitch.click();
	}
	/**
	 * add email with address, passwd, and email type, such as POP, IMAP, EXCHANGE
	 * @param address
	 * @param passwd
	 * @param type
	 * @throws UiObjectNotFoundException
	 */
	/* work in Account setup screen */
	public void addAccount(String account, String passwd, String type) throws UiObjectNotFoundException {
		getObjById("com.android.email:id/account_email").setText(account);
		getObjById("com.android.email:id/account_password").setText(passwd);
		getObjById("com.android.email:id/manual_setup").clickAndWaitForNewWindow();
		getObjByTxt(type).clickAndWaitForNewWindow();
		pressBack();
		getObjByClsTxt("android.widget.Button", "Next").clickAndWaitForNewWindow();
		pressBack();
		getObjByClsTxt("android.widget.Button", "Next").clickAndWaitForNewWindow();
	}
	public void delAccount(String account) {
		
	}
	public void sendEmail(String toAddress, String content) {
		
	}
	public void delEmail(String address) {
		
	}
	/* work in clock main screen */
	public void setAlarm(String time) throws UiObjectNotFoundException {
		UiObject uiTab = getObjByClsIdx("android.app.ActionBar$Tab", 0);
		if (!uiTab.isSelected())
			uiTab.clickAndWaitForNewWindow();
		getObjByDesc("Add alarm").clickAndWaitForNewWindow();
		//TODO:
	}
	public void delAlarm(String time) {
		
	}
	/* time format:	00:00:00 */
	public void addCountdown(String time) throws UiObjectNotFoundException {
		UiObject uiTab = getObjByClsIdx("android.app.ActionBar$Tab", 2);
		if (!uiTab.isSelected())
			uiTab.clickAndWaitForNewWindow();
		UiObject uiTim = getObjByDesc("Add Timer");
		if (uiTim.exists())
			uiTim.clickAndWaitForNewWindow();
		time = time.replaceAll(":", "");
		getObjByDesc("Delete").longClick();
		for (int i=0; i<time.length(); i++)
			getObjByTxt(String.valueOf(time.charAt(i))).click();
		getObjByTxt("Start").click();
	}
	public void addCityClock(String cityName) throws UiObjectNotFoundException {
		UiObject uiTab = getObjByClsIdx("android.app.ActionBar$Tab", 1);
		if (!uiTab.isSelected())
			uiTab.clickAndWaitForNewWindow();
		getObjByDesc("Cities").clickAndWaitForNewWindow();
		getScr().getChildByText(new UiSelector().className("android.widget.TextView"), "cityName").click();
		pressBack();
	}
	public void getCityTime(String cityName) {
		
	}
	/* work in every screen, but will keep in Date & time */
	public void set24HourFormat() throws RemoteException, UiObjectNotFoundException {
		openSet("Settings->Date & time");
		UiObject uiCbox = getObjByClsIns("android.widget.CheckBox", 2);
		if (uiCbox.exists() && !uiCbox.isChecked())
			uiCbox.click();
	}
	public void set12HourFormat() throws RemoteException, UiObjectNotFoundException {
		openSet("Settings->Date & time");
		UiObject uiCbox = getObjByClsIns("android.widget.CheckBox", 2);
		if (uiCbox.exists() && uiCbox.isChecked())
			uiCbox.click();
	}
	/* work in Camera screen */
	public void takePhoto() throws UiObjectNotFoundException {
		getObjByDesc("Shutter").click();
	}
	public void takeVideo(int tms) throws UiObjectNotFoundException {
		getObjByDesc("Camera, video, or panorama selector").clickAndWaitForNewWindow();
		getObjByDesc("Switch to video").clickAndWaitForNewWindow();
		getObjByDesc("Shutter").click();
		sleep(tms);
		getObjByDesc("Shutter").click();
	}
	/* work in Apollo music play screen */
	public void playMusicBySearch(String songName) throws UiObjectNotFoundException {
		getObjByDesc("Search").click();
		getEdit().setText(songName);
		pressEnter();
		pressEnter();
		pressBack();
		pressBack();
	}
	public void playMusic(String songName) {
		//TODO:not support well for uiautomator. It's difficult to play some song.
	}
	public void controlMusic(String control) throws UiObjectNotFoundException {
		if (control.equalsIgnoreCase("pause")) {
			UiObject uiPause = getObjByClsDesc("android.widget.ImageButton", "Pause");
			if (uiPause.exists())	uiPause.click();
		}
		else if (control.equalsIgnoreCase("next"))
			getObjByClsDesc("android.widget.ImageButton", "Next").click();
		else if (control.equalsIgnoreCase("previous"))
			getObjByClsDesc("android.widget.ImageButton", "Previous").click();
		else if (control.equalsIgnoreCase("play")) {
			UiObject uiPlay = getObjByClsDesc("android.widget.ImageButton", "Play");
			if (uiPlay.exists())	uiPlay.click();
		}	
	}
	/* work in wechat main screen */
	public void sendWMessage(String contactName, String message) throws UiObjectNotFoundException {
		getObjByClsTxt("android.widget.TextView", "CHATS").clickAndWaitForNewWindow();
		UiObject uiCont = getScrByCls("android.widget.ListView")
				.getChildByText(new UiSelector().className("android.view.View").resourceId("com.tencent.mm:id/nickname_tv"), contactName);
		if (uiCont.exists())
			uiCont.clickAndWaitForNewWindow();
		else {
			getObjByClsTxt("android.widget.TextView", "CONTACTS").clickAndWaitForNewWindow();
			uiCont = getScrByCls("android.widget.ListView")
					.getChildByText(new UiSelector().className("android.view.View").resourceId("com.tencent.mm:id/myview"), contactName);
			uiCont.clickAndWaitForNewWindow();
			getObjByTxt("Message").clickAndWaitForNewWindow();
		}
		getEdit().setText(message);
		pressEnter();
	}
	public void addWContact(String nickName) throws UiObjectNotFoundException {
		getObjByDesc("My account and settings").click();
		getObjByTxt("Add Contacts").clickAndWaitForNewWindow();
		getEdit().setText(nickName);
		pressEnter();
		UiObject uiObj = getObjByTxt("Follow");
		if (uiObj.exists())
			uiObj.clickAndWaitForNewWindow();
		else
			getObjByTxt("Add").clickAndWaitForNewWindow();
	}
	/* play game 2048 */
	public int play2048() {
		String[] direction = {"right", "left", "up", "down"	};
		Random rand = new Random();
		int step = 0;
		while (!getObjByTxt("Game Over!").exists()) {
			swipe(direction[rand.nextInt(4)]);
			step++;
		}
		return step;
	}
	public void setSleep(String time) throws RemoteException, UiObjectNotFoundException {
		openSet("Settings->Display & lights->Sleep");
		getObjByTxt(time).clickAndWaitForNewWindow();
	}
	public void installApp(String appName) throws UiObjectNotFoundException {
		getObjByTxt(appName).clickAndWaitForNewWindow();
		while (getObjByTxt("Next").exists())
			getObjByTxt("Next").clickAndWaitForNewWindow();
		getObjByTxt("Install").clickAndWaitForNewWindow();
		getObjByTxt("Done").clickAndWaitForNewWindow();
	}
	public void uninstallApp(String appName) throws UiObjectNotFoundException {
		openAppList();
		while (!getObjByTxt(appName).exists())
			swipe("left");
		getObjByTxt(appName).longClick();
	}
	/* one app to set wallpaper */
	public void tapet() throws UiObjectNotFoundException, RemoteException {
		openAppList();
		openApp("Tapet");
		Random rand = new Random();
		int num = rand.nextInt(10);
		randSwipe(rand, num);
		getObjByClsId("android.widget.ImageView", "com.sharpregion.tapet:id/applyEffect").clickAndWaitForNewWindow();
		quitPkg("com.sharpregion.tapet");
	}
	
	public void switchNetMode(String netMode) {
		//TODO:
	}
	
	public void getPhoneStatus() {
		
	}
}
