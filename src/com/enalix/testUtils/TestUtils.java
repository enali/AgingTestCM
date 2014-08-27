package com.enalix.testUtils;

import java.util.ArrayList;

import android.os.RemoteException;

import com.android.uiautomator.core.*;

public class TestUtils extends BasicUtils {
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
		UiObject uiObjWifi = getObjByTxt(wifiName);
		if (uiObjWifi.exists())
			uiObjWifi.clickAndWaitForNewWindow();
		UiObject uiFor = getObjByTxt("Forget");
		if (uiFor.exists()) {
			uiFor.clickAndWaitForNewWindow();
			uiObjWifi.clickAndWaitForNewWindow();
		}
		getEdit().setText(wifiPasswd);
		pressBack();
		UiObject uiOk = getObjByTxt("Connect");
		if (uiOk.exists())
			uiOk.clickAndWaitForNewWindow();
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
		UiObject uiCont = getScrObj().getChildByText(new UiSelector().className("android.widget.TextView"), name);
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
}
