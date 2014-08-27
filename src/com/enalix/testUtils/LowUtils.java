package com.enalix.testUtils;

import java.util.ArrayList;

import android.graphics.Rect;
import android.os.RemoteException;

import com.android.uiautomator.core.*;
import com.android.uiautomator.testrunner.*;

public class LowUtils extends UiAutomatorTestCase {
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
	/* work in home screen */
	public void openAppList() throws UiObjectNotFoundException {
		UiObject uiObj = getObjByTxt("Apps");
		if (uiObj.exists()) 
			uiObj.clickAndWaitForNewWindow();
	}
	/**
	 * launch a app with it's name
	 * @param appName
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	/* work in apps list screen 0 */
	public void openApp(String appName) throws UiObjectNotFoundException, RemoteException {	
		while (!getObjByTxt(appName).exists())
			swipe("left");
		getObjByTxt(appName).clickAndWaitForNewWindow();
	}
	/**
	 * open the set component
	 * @param setRoute, such like "Settings->display & light->sleep"
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void openSet(String setRoute) throws RemoteException, UiObjectNotFoundException {
		if (!getUiDevice().getCurrentPackageName().equalsIgnoreCase("com.android.settings"))
			openApp("Settings");
		String[] route = setRoute.split("->");
		String nowTitle = getObjById("android:id/action_bar_title").getText();
		int i;
		for (i=0; i<route.length; i++)
			if (route[i].equalsIgnoreCase(nowTitle))
				break;
		if (i == route.length)
			for (i=1; i<route.length; i++)
				getScrObj().getChildByText(new UiSelector()
					.className("android.widget.TextView"), route[i]).clickAndWaitForNewWindow();
		else
			for (i+=1; i<route.length; i++)
				getScrObj().getChildByText(new UiSelector()
					.className("android.widget.TextView"), route[i]).clickAndWaitForNewWindow();
	}
	/**
	 * print your apps list. format: name--page--col--row--point
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	/* work in apps list screen 0 , will back to 0*/
	public ArrayList<App> getAppList() throws UiObjectNotFoundException, RemoteException {
		ArrayList<App> appList = new ArrayList<App>(100);
		int page = getObjById("com.android.launcher3:id/apps_customize_page_indicator").getChildCount();
		for (int i=0; i<page; i++) {
			int num = getScrObj().getChildCount(new UiSelector().className("android.widget.TextView"));
			for (int j=0; j<num; j++) {
				UiObject uiObj = getObjByClsIdx("android.widget.TextView", j);
				App app = new App(uiObj.getText(), i, 
						j/4, j%4, uiObj.getBounds().centerX(), uiObj.getBounds().centerY());
				appList.add(app);
			}
			swipe("left");
		}
		for (int i=0; i<page; i++)
			swipe("right"); //keep the screen not change
		return appList;
	}
	/**
	 * get the switch widget that at right of switchname's textview.
	 * @param SwitchName
	 * @return android.widget.Switch
	 * @throws UiObjectNotFoundException
	 */
	public UiObject getSwitch(String SwitchName) throws UiObjectNotFoundException {
		return getChildByClsTxt("android.widget.LinearLayout", SwitchName)
				.getChild(new UiSelector().className("android.widget.Switch"));
	}
	/**
	 * get UiObject
	 * @param text
	 * @return
	 */
	public UiObject getObjByTxt(String text) {
		return new UiObject(new UiSelector().text(text));
	}
	public UiObject getObjByTxtContains(String text) {
		return new UiObject(new UiSelector().textContains(text));
	}
	public UiObject getObjByClsTxt(String cls, String text) {
		return new UiObject(new UiSelector().className(cls).text(text));
	}
	public UiObject getObjByClsTxtContains(String cls, String text) {
		return new UiObject(new UiSelector().className(cls).textContains(text));
	}
	public UiObject getObjById(String id) {
		return new UiObject(new UiSelector().resourceId(id));
	}
	public UiObject getObjByClsIdx(String className, int index) {
		return new UiObject(new UiSelector().className(className).index(index));
	}
	public UiObject getObjByDesc(String desc) {
		return new UiObject(new UiSelector().description(desc));
	}
	public UiObject getObjByDescContains(String desc) {
		return new UiObject(new UiSelector().descriptionContains(desc));
	}
	/**
	 * get scrollable
	 * @return
	 */
	public UiScrollable getScrObj() {
		return new UiScrollable(new UiSelector().scrollable(true));
	}
	public UiScrollable getScrObjByCls(String cls) {
		return new UiScrollable(new UiSelector().className(cls).scrollable(true));
	}
	/**
	 * get edit
	 * @return
	 */
	public UiObject getEdit() {
		return new UiObject(new UiSelector().className("android.widget.EditText"));
	}
	public UiObject getMultiEdit() {
		return new UiObject(new UiSelector().className("android.widget.MultiAutoCompleteTextView"));
	}
	public UiObject getMultiEditByTxt(String text) {
		return getObjByClsTxt("android.widget.MultiAutoCompleteTextView", text);
	}
	public UiObject getEditByTxt(String text) {
		return getObjByClsTxt("android.widget.EditText", text);
	}
	/**
	 * get/open/clear/clear all UiObject of the recent app
	 * @param appName
	 * @return
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	public UiObject getRecentApp(String appName) throws UiObjectNotFoundException, RemoteException {
		getUiDevice().pressRecentApps();
		while (!getUiDevice().getCurrentPackageName().equalsIgnoreCase("com.android.systemui")); //wait for window update
		if (getScrObj().exists())
			getScrObj().scrollIntoView(getObjByTxt(appName));
		return getObjByTxt(appName).getFromParent(new UiSelector().className("android.widget.FrameLayout"));
	}
	public void openRecentApp(String appName) throws RemoteException, UiObjectNotFoundException {
		getRecentApp(appName).clickAndWaitForNewWindow();
	}
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
		pressBack();
	}
	public void clearAllRecentApp() throws UiObjectNotFoundException, RemoteException {
		getUiDevice().pressRecentApps();
		getObjById("com.android.systemui:id/recents_clear").clickAndWaitForNewWindow();
	}


	/**
	 * get child by Text, no matter whether UiScrollable exist
	 * @param cls
	 * @param text
	 * @return
	 * @throws UiObjectNotFoundException
	 */
	public UiObject getChildByClsTxt(String cls, String text) throws UiObjectNotFoundException {
		UiScrollable uiScr = getScrObj();
		if (uiScr.exists())
			return uiScr.getChildByText(new UiSelector().className(cls), text);
		else
			return new UiObject(new UiSelector().className(cls).text(text));
	}
	/**
	 * press
	 */
	public void pressBack() {
		getUiDevice().pressBack();
	}
	public void pressHome() {
		getUiDevice().pressHome();
	}
	public void pressMenu() {
		getUiDevice().pressMenu();
	}
	public void pressDelete() {
		getUiDevice().pressDelete();
	}
	public void pressEnter() {
		getUiDevice().pressEnter();
	}
	/**
	 * clear/set the text in EditText
	 * @param uiEdit
	 * @throws UiObjectNotFoundException
	 */
	public void clearEditTxt(UiObject uiEdit) throws UiObjectNotFoundException {
		String content = uiEdit.getText();
		for (int i=0; i<content.length(); i++)
			pressDelete();
	}
	public void setEditTxt(UiObject uiEdit, String text) throws UiObjectNotFoundException {
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
	 * get X/Y with the percent of width
	 * @param percent
	 * @return
	 */
	public int toScreenX(float percent) {
		return Math.round(percent*getUiDevice().getDisplayWidth());
	}
	public int toScreenY(float percent) {
		return Math.round(percent*getUiDevice().getDisplayHeight());
	}
	/**
	 * make a long press on a UiObject
	 * @param uiObj
	 * @throws UiObjectNotFoundException 
	 */
	public void longPress(UiObject uiObj) throws UiObjectNotFoundException {
		longPress(uiObj.getBounds().centerX(), uiObj.getBounds().centerY());
	}
	public void longPress(int _x, int _y) {
		//TODO:
	}
	public void longPress (float _xpercent, float _ypercent) {
		longPress(toScreenX(_xpercent), toScreenY(_ypercent));
	}
	/**
	 * swipe 
	 * @param direction
	 */
	public boolean swipe(String direction) {
		UiDevice d = getUiDevice();
		switch(direction.toLowerCase()) {
		case "left":
			return d.swipe(toScreenX(0.8f), toScreenY(0.5f), toScreenX(0.2f), toScreenY(0.5f), 30);
		case "right":
			return d.swipe(toScreenX(0.2f), toScreenY(0.5f), toScreenX(0.8f), toScreenY(0.5f), 30);	
		case "up":
			return d.swipe(toScreenX(0.5f), toScreenY(0.8f), toScreenX(0.5f), toScreenY(0.2f), 30);		
		case "down":
			return d.swipe(toScreenX(0.5f), toScreenY(0.2f), toScreenX(0.5f), toScreenY(0.8f), 30);	
		default:
			return false;
		}
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
}
