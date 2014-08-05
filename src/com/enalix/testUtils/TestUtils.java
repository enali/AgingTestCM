package com.enalix.testUtils;

import android.os.RemoteException;

import com.android.uiautomator.core.*;
import com.android.uiautomator.testrunner.*;

public class TestUtils extends BasicUtils {
	/**
	 * get wifi state
	 * @return "ON" or "OFF"
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	public String getWifiState() throws UiObjectNotFoundException, RemoteException {
		UiObject uiObj = new UiObject(new UiSelector().resourceId("android:id/action_bar_title"));
		if (!uiObj.exists() || !uiObj.getText().equalsIgnoreCase("Settings"))
			openApp("Settings");
		UiObject uiSwitch = getSwitch("Wi‑Fi");
		return uiSwitch.getText();
	}
	/**
	 * check whether wifi is on
	 * @return true or false
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	public boolean isWifiOn() throws UiObjectNotFoundException, RemoteException {
		UiObject uiObj = new UiObject(new UiSelector().resourceId("android:id/action_bar_title"));
		if (!uiObj.exists() || !uiObj.getText().equalsIgnoreCase("Settings"))
			openApp("Settings");
		UiObject uiSwitch = getSwitch("Wi‑Fi");
		if (uiSwitch.exists() && uiSwitch.isChecked()) {
			return true;
		}
		return false;
	}
	/**
	 * check whether wifi is off
	 * @return true or false
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	public boolean isWifiOff() throws UiObjectNotFoundException, RemoteException {
		UiObject uiObj = new UiObject(new UiSelector().resourceId("android:id/action_bar_title"));
		if (!uiObj.exists() || !uiObj.getText().equalsIgnoreCase("Settings"))
			openApp("Settings");
		UiObject uiSwitch = getSwitch("Wi‑Fi");
		if (uiSwitch.exists() && !uiSwitch.isChecked()) {
			return true;
		}
		return false;
	}
	/**
	 * turn on wifi
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	public void openWifi() throws UiObjectNotFoundException, RemoteException {
		UiObject uiObj = new UiObject(new UiSelector().resourceId("android:id/action_bar_title"));
		if (!uiObj.exists() || !uiObj.getText().equalsIgnoreCase("settings"))
			openApp("Settings");
		UiObject uiSwitch = getSwitch("Wi‑Fi");
		if (uiSwitch.exists() && !uiSwitch.isChecked()) {
			uiSwitch.click();
		}
	}
	/**
	 * turn off wifi
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	public void closeWifi() throws UiObjectNotFoundException, RemoteException {
		UiObject uiObj = new UiObject(new UiSelector().resourceId("android:id/action_bar_title"));
		if (!uiObj.exists() || !uiObj.getText().equalsIgnoreCase("Settings"))
			openApp("Settings");
		UiObject uiSwitch = getSwitch("Wi‑Fi");
		if (uiSwitch.exists() && uiSwitch.isChecked()) {
			uiSwitch.click();
		}
	}
	/**
	 * connect wifi
	 * @param wifiName
	 * @param wifiPasswd
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void connectWifi(String wifiName, String wifiPasswd) throws RemoteException, UiObjectNotFoundException {
		openWifi();
		UiObject uiObj = getObjByText("Wi‑Fi");
		if (uiObj.exists())
			uiObj.clickAndWaitForNewWindow();
		UiObject uiObjWifi = getObjByText(wifiName);
		if (uiObjWifi.exists())
			uiObjWifi.clickAndWaitForNewWindow();
		UiObject uiFor = getObjByText("Forget");
		if (uiFor.exists()) {
			uiFor.clickAndWaitForNewWindow();
			uiObjWifi.clickAndWaitForNewWindow();
		}
		getEdit().setText(wifiPasswd);
		getUiDevice().pressBack();
		UiObject uiOk = getObjByText("Connect");
		if (uiOk.exists())
			uiOk.clickAndWaitForNewWindow();
	}
	/**
	 * get contact telphone number
	 * @param name contact name
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void getTelnum(String name) throws RemoteException, UiObjectNotFoundException {
		openContact(name);
		//TODO:
	}
	/**
	 * make a call to contact
	 * @param name
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void callName(String name) throws RemoteException, UiObjectNotFoundException {
		openApp("People");
		//TODO:
	}
	/**
	 * call directly a Telphone number
	 * @param number tel number
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	public void callTelnum(String number) throws UiObjectNotFoundException, RemoteException {
		openApp("Phone");
		UiObject uiObj = getObjByDesc("dial pad");
		if (uiObj.exists())
			uiObj.clickAndWaitForNewWindow();
		clearEditText(getEdit());
		for (int i=0; i<number.length(); i++)
			getObjByText(String.valueOf(number.charAt(i))).click();
		getObjByDesc("dial").clickAndWaitForNewWindow();
	}
	public void callHistoryNum() {
		
	}
	public void callHistoryName() {
		
	}
	/**
	 * add a contact
	 * @param name
	 * @param phone
	 * @param email
	 * @param address
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	public void addContact(String name, String phone, String email, String address) throws UiObjectNotFoundException, RemoteException {
		openApp("People");
		getObjByDesc("Add Contact").clickAndWaitForNewWindow();
		//UiScrollable uiScr = getScrObj();
		//uiScr.getChildByText(new UiSelector().className("android.widget.EditText"), "Name").setText(name);
		//uiScr.getChildByText(new UiSelector().className("android.widget.EditText"), "Phone").setText(phone);
		//uiScr.getChildByText(new UiSelector().className("android.widget.EditText"), "Email").setText(email);
		//uiScr.getChildByText(new UiSelector().className("android.widget.EditText"), "Address").setText(address);
		//TODO:can't work well
		getEditByText("Name").setText(name);
		getUiDevice().pressBack();
		getEditByText("Phone").setText(phone);
		getUiDevice().pressBack();
		getEditByText("Email").setText(email);
		getUiDevice().pressBack();
		getEditByText("Address").setText(address);
		getUiDevice().pressBack();
		getObjByText("Done").clickAndWaitForNewWindow();		
	}
	/**
	 * add a contact
	 * @param contact, an instance of People
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void addContact(People contact) throws RemoteException, UiObjectNotFoundException {
		openApp("People");
		getObjByDesc("Add Contact").clickAndWaitForNewWindow();
		getEditByText("Name").setText(contact.getName());
		getUiDevice().pressBack();
		getEditByText("Phone").setText(contact.getPhone());
		getUiDevice().pressBack();
		getEditByText("Email").setText(contact.getEmail());
		getUiDevice().pressBack();
		getEditByText("Address").setText(contact.getAddress());
		getUiDevice().pressBack();
		getObjByText("Done").clickAndWaitForNewWindow();
	}
	/**
	 * delete a contact
	 * @param name
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	public void delContact(String name) throws UiObjectNotFoundException, RemoteException {
		openContact(name);
		getUiDevice().pressMenu();
		getObjByText("Delete").clickAndWaitForNewWindow();
		getObjByText("OK").clickAndWaitForNewWindow();
	}
	/**
	 * open a contact
	 * @param name
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	public void openContact(String name) throws UiObjectNotFoundException, RemoteException {
		openApp("People");
		getChildByText("android.widget.TextView", name).clickAndWaitForNewWindow();
	}
	public People getContactInfo(String name) throws RemoteException, UiObjectNotFoundException {
		openContact(name);
		return new People();
		//TODO:difficult
	}
	public void searchContactByTelnum(String num) {

	}
	public void searchContactByName(String name) {
		
	}
	/**
	 * send message with a telphone number
	 * @param num
	 * @param mes
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	public void sendSmsByTelnum(String num, String mes) throws UiObjectNotFoundException, RemoteException {
		openApp("Messaging");
		getObjByDesc("New message").clickAndWaitForNewWindow();
		getMultiEditByText("To").setText(num);
		getEditByText("Type message").setText(mes);
		pressBack();
		getObjByDesc("Send").clickAndWaitForNewWindow();
	}
	/**
	 * send message with a contact name
	 * @param name
	 * @param mes
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	public void sendSms(String name, String mes) throws UiObjectNotFoundException, RemoteException {
		openContact(name);
		getObjByDesc("Text mobile").clickAndWaitForNewWindow();
		getEditByText("Type message").setText(mes);
		pressBack();
		getObjByDesc("Send").clickAndWaitForNewWindow();
	}
	/**
	 * delete a contact's all messages
	 * @param name
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	public void delSms(String name) throws UiObjectNotFoundException, RemoteException {
		openApp("Messaging");
		getObjByTextContains(name).clickAndWaitForNewWindow();
		pressMenu();
		getObjByTextContains("Delete").clickAndWaitForNewWindow();
		getObjByText("Delete").clickAndWaitForNewWindow();
	}
	/**
	 * clear all notifications
	 * @throws UiObjectNotFoundException
	 */
	public void clearNotifications() throws UiObjectNotFoundException {
		getUiDevice().openNotification();
		UiObject uiObj = getObjByDescContains("Clear");
		if (uiObj.exists())
			uiObj.clickAndWaitForNewWindow();
		swipe("up");
	}
	/**
	 * open web url with browser
	 * @param url, such as "www.baidu.com"
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void openUrl(String url) throws RemoteException, UiObjectNotFoundException {
		openApp("Browser");
		setEditText(getEdit(), url);
		pressEnter();
	}
	/**
	 * do some simply calculate
	 * @param cal, such as "1+2", "1*9/4+2"
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void calculate(String cal) throws RemoteException, UiObjectNotFoundException {
		openApp("Calculator");
		clearEditText(getEdit());
		cal = cal.replace("*", "×").replace("/", "÷").trim();
		for (int i=0; i<cal.length(); i++)
			getObjByText(String.valueOf(cal.charAt(i))).click();
		getObjByText("=").click();
	}
	/**
	 * open a dirpath
	 * @param dirPath, such as "baidu/ime", "/storage/emulated/0/baidu/ime"
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void openDir(String dirPath) throws RemoteException, UiObjectNotFoundException {
		openApp("File Manager");
		String prefix = "/storage/emulated/o/";
		if (dirPath.startsWith(prefix))
			dirPath = dirPath.trim().replace(prefix, "");
		String[] dir = dirPath.split("/");
		for (String dirname:dir)
			getChildByText("android.widget.TextView", dirname).clickAndWaitForNewWindow();
	}
	/**
	 * turn on airplane mode
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	public void openApMode() throws UiObjectNotFoundException, RemoteException {
		UiObject uiObj = new UiObject(new UiSelector().resourceId("android:id/action_bar_title"));	
		String title = "";
		if (uiObj.exists())
			title = uiObj.getText();
		if (title.isEmpty() || (!title.equalsIgnoreCase("Settings") 
				&& !title.equalsIgnoreCase("Wireless & networks"))) {
			openApp("Settings");
			getObjByTextContains("More").clickAndWaitForNewWindow();
		}else if (!title.isEmpty() && title.equalsIgnoreCase("Settings"))
			getObjByTextContains("More").clickAndWaitForNewWindow();
		UiObject uiCbox = new UiObject(new UiSelector().className("android.widget.CheckBox").index(0));
		if (!uiCbox.isChecked())
			uiCbox.click();
	}
	/**
	 * turn off airplane mode
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void closeApMode() throws RemoteException, UiObjectNotFoundException {
		UiObject uiObj = new UiObject(new UiSelector().resourceId("android:id/action_bar_title"));	
		String title = "";
		if (uiObj.exists())
			title = uiObj.getText();
		if (title.isEmpty() || (!title.equalsIgnoreCase("Settings") 
				&& !title.equalsIgnoreCase("Wireless & networks"))) {
			openApp("Settings");
			getObjByTextContains("More").clickAndWaitForNewWindow();
		}else if (!title.isEmpty() && title.equalsIgnoreCase("Settings"))
			getObjByTextContains("More").clickAndWaitForNewWindow();
		UiObject uiCbox = new UiObject(new UiSelector().className("android.widget.CheckBox").index(0));
		if (uiCbox.isChecked())
			uiCbox.click();
	}
	/**
	 * turn on bluetooth
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void openBluetooth() throws RemoteException, UiObjectNotFoundException {
		UiObject uiObj = new UiObject(new UiSelector().resourceId("android:id/action_bar_title"));
		if (!uiObj.exists() || !uiObj.getText().equalsIgnoreCase("Settings"))
			openApp("Settings");
		UiObject uiSwitch = getSwitch("Bluetooth");
		if (uiSwitch.exists() && !uiSwitch.isChecked())
			uiSwitch.click();
	}
	/**
	 * turn off bluetooth
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void closeBluetooth() throws RemoteException, UiObjectNotFoundException {
		UiObject uiObj = new UiObject(new UiSelector().resourceId("android:id/action_bar_title"));
		if (!uiObj.exists() || !uiObj.getText().equalsIgnoreCase("Settings"))
			openApp("Settings");
		UiObject uiSwitch = getSwitch("Bluetooth");
		if (uiSwitch.exists() && uiSwitch.isChecked())
			uiSwitch.click();
	}
	/**
	 * turn on mobile networks
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void openDataNet() throws RemoteException, UiObjectNotFoundException {
		UiObject uiObj = new UiObject(new UiSelector().resourceId("android:id/action_bar_title"));
		if (!uiObj.exists() || !uiObj.getText().equalsIgnoreCase("Settings"))
			openApp("Settings");
		UiObject uiSwitch = getSwitch("Mobile networks");
		if (uiSwitch.exists() && !uiSwitch.isChecked())
			uiSwitch.click();
	}
	/**
	 * turn off mobile networks
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void closeDataNet() throws RemoteException, UiObjectNotFoundException {
		UiObject uiObj = new UiObject(new UiSelector().resourceId("android:id/action_bar_title"));
		if (!uiObj.exists() || !uiObj.getText().equalsIgnoreCase("Settings"))
			openApp("Settings");
		UiObject uiSwitch = getSwitch("Mobile networks");
		if (uiSwitch.exists() && uiSwitch.isChecked())
			uiSwitch.click();
	}
}
