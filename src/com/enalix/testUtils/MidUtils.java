package com.enalix.testUtils;

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
		String state = getObjByTxt(wifiName)
				.getFromParent(new UiSelector().className("android.widget.TextView")).getText();
		while (!state.equalsIgnoreCase("Connected"));
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
		if (getObjByTxt("Create a new contact").exists())
			getObjByTxt("Create a new contact").clickAndWaitForNewWindow();
		if (getObjByDesc("Add Contact").exists())
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
	public void addContact(Contact[] cont) throws RemoteException, UiObjectNotFoundException {
		for (int i=0; i<cont.length; i++)
			addContact(cont[i]);
	}
	/* now is the contact screen */
	public void delContact(String name) throws UiObjectNotFoundException, RemoteException {
		if (getScr("android.widget.ListView").exists())
			getScr("android.widget.ListView")
				.getChildByText(new UiSelector().resourceId("com.android.contacts:id/cliv_name_textview"), name)
					.clickAndWaitForNewWindow();
		else
			getObjByTxtId(name, "com.android.contacts:id/cliv_name_textview")
				.clickAndWaitForNewWindow();
		pressMenu();
		getObjByTxt("Delete").clickAndWaitForNewWindow();
		getObjByTxt("OK").clickAndWaitForNewWindow();
	}
	public void delContact(String[] nameList) throws RemoteException, UiObjectNotFoundException {
		for (String name:nameList)
			delContact(name);
	}

	public void exportContact() {
		//TODO:
	}
	public void importContact() {
		//TODO:
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
	public void sendSms(String name, String[] mes) throws UiObjectNotFoundException, RemoteException {
		openContact(name);
		getObjByDesc("Text mobile").clickAndWaitForNewWindow();
		for (int i=0; i<mes.length; i++) {
			getEditByTxt("Type message").setText(mes[i]);
			pressBack();
			getObjByDesc("Send").clickAndWaitForNewWindow();
		}
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
	public void openUrl(String[] urlArray) throws RemoteException, UiObjectNotFoundException {
		for (int i=0; i<urlArray.length; i++)
			openUrl(urlArray[i]);
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
		String[] dir = getPathArray(dirPath);
		for (int i=1; i<dir.length; i++)
			getChildByClsTxt("android.widget.TextView", dir[i]).clickAndWaitForNewWindow();
	}
	/* work in dirpath, you have openDir(dirPath) */
	public void selectFile(String fileName) throws UiObjectNotFoundException {
		if (getScr("android.widget.ListView").exists())
			getScr("android.widget.ListView")
				.getChildByText(new UiSelector().className("android.widget.LinearLayout"), fileName)
					.getChild(new UiSelector().className("android.widget.ImageButton"))
						.click();
		else {
			getColByCls("android.widget.ListView")
				.getChildByText(new UiSelector().className("android.widget.LinearLayout"), fileName)
					.getChild(new UiSelector().className("android.widget.ImageButton"))
					.click();
		}
	}
	public void delFile(String fileName) throws UiObjectNotFoundException {
		selectFile(fileName);
		getObjByDesc("Actions").click();
		getObjByTxt("Delete selection").clickAndWaitForNewWindow();
		getObjByTxt("Yes").clickAndWaitForNewWindow();
		if (!getObjByTxt(fileName).exists())
			Log.d(TAG, "file" + fileName + "has deleted");
	}
	public void delFile(String[] fileArray) throws UiObjectNotFoundException {
		for (int i=0; i<fileArray.length; i++)
			selectFile(fileArray[i]);
		getObjByDesc("Actions").click();
		getObjByTxt("Delete selection").clickAndWaitForNewWindow();
		getObjByTxt("Yes").clickAndWaitForNewWindow();
	}
	public void delAllFile() throws UiObjectNotFoundException {
		getObjByDesc("Actions").click();
		getObjByTxt("Select all").clickAndWaitForNewWindow();
		getObjByDesc("Actions").click();
		getObjByTxt("Delete selection").clickAndWaitForNewWindow();
		getObjByTxt("Yes").clickAndWaitForNewWindow();
	}
	public void renameFile(String oldName, String newName) throws UiObjectNotFoundException {
		selectFile(oldName);
		getObjByDesc("Actions").click();
		//TODO:can't do it, without longpress
	}
	public String getCwd() throws UiObjectNotFoundException {
		String cwd = "/sdcard";
		int i=1; 
		UiObject uiObj = getObjByIdIns("com.cyanogenmod.filemanager:id/breadcrumb_item", i);
		while (uiObj.exists()) {
			cwd += "/" + uiObj.getText();
			i++;
			uiObj = getObjByIdIns("com.cyanogenmod.filemanager:id/breadcrumb_item", i);
		}
		return cwd;
	}
	/* convert /sdcard/wandoujia/app to {"wandoujia", "app"} */
	public String[] getPathArray(String path) {
		if (path.startsWith("/sdcard/"))
			path = path.replace("/sdcard/", "sdcard/");
		return path.split("/");
	}
	/**
	 * mv file from oldpath to newpath
	 * @param oldpath, must be absolute path
	 * @param newpath, can be absolute path, such as /sdcard/wandoujia/app, or relative path, such as ../../abb
	 * @param fileName, the file what you want mv
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException 
	 */
	public void mvFile(String oldpath, String newpath, String fileName) throws UiObjectNotFoundException, RemoteException {
		//TODO:
	}
	public String[] getFileList(String dirPath, String suffix) throws RemoteException, UiObjectNotFoundException {
		openDir(dirPath);
		String regex = "[-_a-zA-Z0-9\u4e00-\u9fa5]+\\."+suffix+"$";
		UiCollection uiCol = new UiCollection(new UiSelector().className("android.widget.ListView"));
		int num = uiCol.getChildCount(new UiSelector().className("android.widget.TextView").textMatches(regex));
		String[] fileList = new String[num];
		for (int i=0; i<num; i++) {
			fileList[i] = getObjByTxtMatchesIns(regex, i).getText();
		}
		return fileList;
		//TODO: not work when scrollable exists.
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
	 * add email with address, passwd, and email type, such as POP3, IMAP, Exchange
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
	/* just work for the app of Mail Master */
	public void addAccountMM(String account, String passwd) throws UiObjectNotFoundException {
		if (getObjByTxt("Add Account").exists()) {
			getObjByTxt("Add Account").clickAndWaitForNewWindow();
		}
		UiObject uiTitle = getObjByClsId("android.widget.TextView", "android:id/action_bar_title");
		switch(uiTitle.getText()) {
		case "Inbox":
			pressMenu();
			getObjByTxt("Settings").clickAndWaitForNewWindow();
		case "Settings":
			getScr("android.widget.ScrollView")
			.getChildByText(new UiSelector().className("android.widget.TextView"), "Add Account")
				.clickAndWaitForNewWindow();
		case "Add Account":
			getEditById("com.netease.mobimail:id/editor_email").setText(account);
			getEditById("com.netease.mobimail:id/editor_password").setText(passwd);
			pressBack();
			getObjByTxt("Sign In").clickAndWaitForNewWindow();
			Log.v(TAG, "add mail master account:" + account);
		default:
			break;
		}
	}
	/* work in Inbox screen */
	public void delAccountMM(String account) throws UiObjectNotFoundException {
		UiObject uiTitle = getObjByClsId("android.widget.TextView", "android:id/action_bar_title");
		switch(uiTitle.getText()) {
		case "Inbox":
			pressMenu();
			getObjByTxt("Settings").clickAndWaitForNewWindow();
		case "Settings":
			UiObject uiAcc = getScr("android.widget.ScrollView")
				.getChildByText(new UiSelector().className("android.widget.TextView"), account);
			if (uiAcc.exists()) {
				uiAcc.clickAndWaitForNewWindow();
				getObjByTxt("Delete Account").clickAndWaitForNewWindow();
				getObjByTxt("Delete Account").clickAndWaitForNewWindow();
				Log.v(TAG, "delete mail master account:" + account);
			}
			break;
		}
	}
	public void delAccountMM(String[] account) throws UiObjectNotFoundException {
		for (int i=0; i<account.length; i++)
			delAccountMM(account[i]);
	}
	public void delAllAccountMM() throws UiObjectNotFoundException {
		pressMenu();
		getObjByTxt("Settings").clickAndWaitForNewWindow();
		String regex = "\\w+@\\w+\\.\\w+";
		UiObject uiAcc = getObjByTxtMatches(regex);
		while (uiAcc.exists()) {
			String account = uiAcc.getText();
			uiAcc.clickAndWaitForNewWindow();
			getObjByTxt("Delete Account").clickAndWaitForNewWindow();
			getObjByTxt("Delete Account").clickAndWaitForNewWindow();
			Log.v(TAG, "delete mail master account:" + account);
			uiAcc = getObjByTxtMatches(regex);
		}
	}
	public void sendEmailMM(String account, String subject, String content, String[] cc, String[] bcc) throws UiObjectNotFoundException {
		UiObject uiTitle = getObjByClsId("android.widget.TextView", "android:id/action_bar_title");
		switch(uiTitle.getText()) {
		case "Settings":
			pressBack();
		case "Inbox":
			getObjByDesc("New Message").clickAndWaitForNewWindow();
			getSib("To: ", "android.widget.EditText").setText(account);//"To: "copy from uiautomatorviewer, not type it
			pressBack();
			if (cc.length + bcc.length > 0) {
				getObjByTxt("Cc/Bcc: ").clickBottomRight();
				if (cc.length>0) {
					UiObject uiCc = getSib("Cc: ", "android.widget.EditText");	//"Cc: "copy from uiautomatorviewer, not type it
					for (int i=0; i<cc.length; i++) {
						uiCc.setText(cc[i]);
						pressEnter();
					}
				}
				if (bcc.length>0) {
					UiObject uiBcc = getSib("Bcc: ", "android.widget.EditText");//"Bcc: "copy from uiautomatorviewer, not type it
					for (int i=0; i<bcc.length; i++) {
						uiBcc.setText(bcc[i]);
						pressEnter();
					}
				}		
				pressBack();
			}
			getEditByTxt("Subject").setText(subject);
			pressBack();
			getEditById("com.netease.mobimail:id/mailcompose_content").setText(content);
			pressBack();
			if (getObjByTxt("Send").isEnabled())
				getObjByTxt("Send").clickAndWaitForNewWindow();
			if (getObjByTxt("Enter your name").exists()) {
				String name = account.split("@")[0];
				getEdit().setText(name);
				pressBack();
				if (getObjByTxt("Save and Send").isEnabled())
					getObjByTxt("Save and Send").clickAndWaitForNewWindow();
			}
		}
		sleep(500);
		
	}
	// TODO:not work!
	public void switchTabMM(String tabName) throws UiObjectNotFoundException {
		String[] tabs = {"Inbox", "Outbox", "Drafts", "Sent", "Spam", "Trash"};
		boolean flag = false;
		for (int i=0; i<tabs.length; i++)
			if (tabName.equalsIgnoreCase(tabs[i])) {
				flag = true;
				break;
			}
		if (flag) {
			UiObject uiTitle = getObjByClsId("android.widget.TextView", "android:id/action_bar_title");
			if (uiTitle.getText().equalsIgnoreCase("Settings"))
				pressBack();
			swipe("lfmargin");
			getObjByClsTxtId("android.widget.TextView", tabName, "com.netease.mobimail:id/mail_folder_list_item_name").clickAndWaitForNewWindow();
		}
	}
	public void sendEmailMM(String account, String subject, String content) throws UiObjectNotFoundException {
		sendEmailMM(account, subject, content, new String[0], new String[0]);
	}
	public void delEmailMM(String account, String subject) throws UiObjectNotFoundException {
		//TODO:can't do.
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
		//TODO:
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
		//TODO:
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
		UiObject uiCont = getScr("android.widget.ListView")
				.getChildByText(new UiSelector().className("android.view.View").resourceId("com.tencent.mm:id/nickname_tv"), contactName);
		if (uiCont.exists())
			uiCont.clickAndWaitForNewWindow();
		else {
			getObjByClsTxt("android.widget.TextView", "CONTACTS").clickAndWaitForNewWindow();
			uiCont = getScr("android.widget.ListView")
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
		Log.v(TAG, "install app: " + appName);
	}
	public void installApp(String[] appList) throws UiObjectNotFoundException {
		for (int i=0; i<appList.length; i++)
			installApp(appList[i]);
	}
	public void uninstallApp(String appName) throws UiObjectNotFoundException {
		openAppList();
		while (!getObjByTxt(appName).exists())
			swipe("left");
		getObjByTxt(appName).longClick();
	}

	
	public void switchNetMode(String netMode) {
		//TODO:
	}
	/* Settings, About Phone , Notice: the type equal excatly what you see at phone */
	public String getPhoneStatus(String type) throws RemoteException, UiObjectNotFoundException {
		openSet("Settings->About Phone");
		UiObject uiType = getScr("android.widget.ListView")
				.getChildByText(new UiSelector().resourceId("android:id/title"), type);
		return uiType.getFromParent(new UiSelector().resourceId("android:id/summary")).getText();
	}
	/* Settings, About Phone, Status */
	public String getStatus(String type) throws RemoteException, UiObjectNotFoundException {
		openSet("Settings->About Phone->Status");
		UiObject uiType = getScr("android.widget.ListView")
				.getChildByText(new UiSelector().resourceId("android:id/title"), type);
		return uiType.getFromParent(new UiSelector().resourceId("android:id/summary")).getText();
	}
}
