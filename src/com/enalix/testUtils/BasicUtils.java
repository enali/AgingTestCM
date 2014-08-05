package com.enalix.testUtils;

import android.graphics.Rect;
import android.os.RemoteException;

import com.android.uiautomator.core.*;
import com.android.uiautomator.testrunner.*;

public class BasicUtils extends UiAutomatorTestCase {
	/**
	 * unlock phone
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	public void unlock() throws UiObjectNotFoundException, RemoteException {
		getUiDevice().wakeUp();
    	UiObject uiObj = new UiObject(new UiSelector().description("Slide area."));
	    if (uiObj.exists()) {
	    	Rect rect = uiObj.getBounds();
	    	getUiDevice().drag(rect.centerX(), rect.centerY(), rect.right, rect.centerY(), 20);
	    }
	}
	/**
	 * enter the apps list
	 * @throws UiObjectNotFoundException
	 */
	public void openAppList() throws UiObjectNotFoundException {
		getUiDevice().pressHome();
		UiObject uiObj = new UiObject(new UiSelector().text("Apps"));
		if (uiObj.exists()) 
			uiObj.clickAndWaitForNewWindow();
		//TODO:
	}
	/**
	 * launch a app with it's name
	 * @param appName
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	public void openApp(String appName) throws UiObjectNotFoundException, RemoteException {
		unlock();
		openAppList();
		UiScrollable uiScr = new UiScrollable(new UiSelector().scrollable(true));
		UiObject uiObjApp = uiScr.getChildByText(new UiSelector().className("android.widget.TextView"), appName);
		if (uiObjApp.exists())
			uiObjApp.clickAndWaitForNewWindow();
	}
	/**
	 * get app's list what you have installed
	 * @return a list contains app'name
	 */
	public String getAppList() {
		return "abc";
	}
	/**
	 * get the switch widget that at right of switchname's textview.
	 * @param SwitchName
	 * @return android.widget.Switch
	 * @throws UiObjectNotFoundException
	 */
	public UiObject getSwitch(String SwitchName) throws UiObjectNotFoundException {
		return getChildByText("android.widget.LinearLayout", SwitchName)
				.getChild(new UiSelector().className("android.widget.Switch"));
	}
	/**
	 * get UiObject by text
	 * @param text
	 * @return
	 */
	public UiObject getObjByText(String text) {
		return new UiObject(new UiSelector().text(text));
	}
	/**
	 * get UiObject by text contains
	 * @param text
	 * @return
	 */
	public UiObject getObjByTextContains(String text) {
		return new UiObject(new UiSelector().textContains(text));
	}
	/**
	 * get android.widget.EditText 
	 * @return
	 */
	public UiObject getEdit() {
		return new UiObject(new UiSelector().className("android.widget.EditText"));
	}
	public UiObject getMultiEdit() {
		return new UiObject(new UiSelector().className("android.widget.MultiAutoCompleteTextView"));
	}
	public UiObject getMultiEditByText(String text) {
		return new UiObject(new UiSelector().className("android.widget.MultiAutoCompleteTextView").text(text));
	}
	public UiObject getEditByText(String text) {
		return new UiObject(new UiSelector().className("android.widget.EditText").text(text));
	}
	/**
	 * get UiObject of the recent app
	 * @param appName
	 * @return
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	public UiObject getRecentApp(String appName) throws UiObjectNotFoundException, RemoteException {
		getUiDevice().pressRecentApps();
		UiObject uiObj;
		UiScrollable uiScr = new UiScrollable(new UiSelector().className("android.widget.ScrollView")
				.scrollable(true));
		if (uiScr.exists())
			uiObj = uiScr.getChildByText(new UiSelector().className("android.widget.TextView"), appName);
		else
			uiObj = getObjByText(appName);
		return uiObj.getFromParent(new UiSelector().className("android.widget.FrameLayout"))
				.getChild(new UiSelector().className("android.widget.ImageView"));
	} //TODO:it'll not work well about "People", I don't know reason
	/**
	 * open the recent app
	 * @param appName
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void openRecentApp(String appName) throws RemoteException, UiObjectNotFoundException {
		UiObject uiObj = getRecentApp(appName);
		if (uiObj.exists()) {
			uiObj.clickAndWaitForNewWindow();
		}	
	}
	/**
	 * clear the recent app
	 * @param appName
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void clearRecentApp(String appName) throws RemoteException, UiObjectNotFoundException	{
		UiObject uiObj = getRecentApp(appName);
		if (uiObj.exists()) {
			if (getUiDevice().getDisplayRotation() == 0) {
				Rect rect = uiObj.getBounds();
				getUiDevice().swipe(rect.centerX(), rect.centerY(), 0, rect.centerY(), 40);
			} else {
				Rect rect = uiObj.getBounds();
				getUiDevice().swipe(rect.centerX(), rect.centerY(), rect.centerX(), 0, 40);
			}
		}
			
	}
	/**
	 * clear all recent app
	 */
	public void clearAllRecentApp() {
		//TODO:
	}
	/**
	 * get UiObject by description
	 * @param desc
	 * @return
	 */
	public UiObject getObjByDesc(String desc) {
		return new UiObject(new UiSelector().description(desc));
	}
	/**
	 * get UiObject by description contains
	 * @param desc
	 * @return
	 */
	public UiObject getObjByDescContains(String desc) {
		return new UiObject(new UiSelector().descriptionContains(desc));
	}
	/**
	 * get UiScrollable
	 * @return
	 */
	public UiScrollable getScrObj() {
		return new UiScrollable(new UiSelector().scrollable(true));
	}
	/**
	 * get UiScrollable with it's className
	 * @param cls
	 * @return UiScrollable
	 */
	public UiScrollable getScrObjByCls(String cls) {
		return new UiScrollable(new UiSelector().className(cls).scrollable(true));
	}
	/**
	 * get child by Text, no matter whether UiScrollable exist
	 * @param cls
	 * @param text
	 * @return
	 * @throws UiObjectNotFoundException
	 */
	public UiObject getChildByText(String cls, String text) throws UiObjectNotFoundException {
		UiScrollable uiScr = new UiScrollable(new UiSelector().scrollable(true));
		if (uiScr.exists())
			return uiScr.getChildByText(new UiSelector().className(cls), text);
		else
			return new UiObject(new UiSelector().className(cls).text(text));
	}
	/**
	 * pressBack
	 */
	public void pressBack() {
		getUiDevice().pressBack();
	}
	/**
	 * pressHome
	 */
	public void pressHome() {
		getUiDevice().pressHome();
	}
	/**
	 * pressMenu
	 */
	public void pressMenu() {
		getUiDevice().pressMenu();
	}
	/**
	 * pressDelete
	 */
	public void pressDelete() {
		getUiDevice().pressDelete();
	}
	/**
	 * pressEnter
	 */
	public void pressEnter() {
		getUiDevice().pressEnter();
	}
	/**
	 * clear the text in EditText
	 * @param uiEdit
	 * @throws UiObjectNotFoundException
	 */
	public void clearEditText(UiObject uiEdit) throws UiObjectNotFoundException {
		String content = uiEdit.getText();
		for (int i=0; i<content.length(); i++)
			pressDelete();
	}
	/**
	 * fill Editor with the text
	 * @param uiEdit
	 * @param text
	 * @throws UiObjectNotFoundException
	 */
	public void setEditText(UiObject uiEdit, String text) throws UiObjectNotFoundException {
		if (!uiEdit.isFocused())
			uiEdit.clickBottomRight();//focus it 
		String content = uiEdit.getText();
		if (!content.isEmpty())
			for (int i=0; i<text.length(); i++)
				pressDelete();
		uiEdit.setText(text);
		pressBack();
	}
	/**
	 * get X with the percent of width
	 * @param percent
	 * @return
	 */
	public int toScreenX(float percent) {
		return Math.round(percent*getUiDevice().getDisplayWidth());
	}
	/** 
	 * get Y with the percent of height
	 * @param percent
	 * @return
	 */
	public int toScreenY(float percent) {
		return Math.round(percent*getUiDevice().getDisplayHeight());
	}
	/**
	 * make a long press on a UiObject
	 * @param uiObj
	 */
	public void longPress(UiObject uiObj) {
		//TODO:
	}
	/**
	 * make a long press at (_x, _y)
	 * @param _x
	 * @param _y
	 */
	public void longPress(int _x, int _y) {
		
	}
	/**
	 * make a long press at relative (_xpercent, _ypercent)
	 * @param _xpercent
	 * @param _ypercent
	 */
	public void longPress (float _xpercent, float _ypercent) {
		
	}
	/**
	 * swipe 
	 * @param direction
	 */
	public void swipe(String direction) {
		UiDevice d = getUiDevice();
		switch(direction.toLowerCase()) {
		case "left":
			d.swipe(toScreenX(0.8f), toScreenY(0.5f), toScreenX(0.2f), toScreenY(0.5f), 30);
			break;
		case "right":
			d.swipe(toScreenX(0.2f), toScreenY(0.5f), toScreenX(0.8f), toScreenY(0.5f), 30);
			break;
		case "up":
			d.swipe(toScreenX(0.5f), toScreenY(0.8f), toScreenX(0.5f), toScreenY(0.2f), 30);
			break;
		case "down":
			d.swipe(toScreenX(0.5f), toScreenY(0.2f), toScreenX(0.5f), toScreenY(0.8f), 30);
			break;
		default:
			break;
		}
	}
	/**
	 * get UiObject with recource-id
	 * @param id
	 * @return
	 */
	public UiObject getObjById(String id) {
		return new UiObject(new UiSelector().resourceId(id));
	}
}