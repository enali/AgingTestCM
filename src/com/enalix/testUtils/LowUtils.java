package com.enalix.testUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;

import com.android.uiautomator.core.*;
import com.android.uiautomator.testrunner.*;

public class LowUtils extends UiAutomatorTestCase {
    public static long TIMEOUT = Constant.TIMEOUT;

    public  String TAG = Constant.LOG_TAG;
    /**
     * Run in end of each test case, this case is for capturing screenshot when
     * running parameter is "1" Example:
     * {@code adb shell uiautomator xxxx.jar	-e screenshot 1}
     */
    @Override
    protected void tearDown() throws Exception {
        if (getParams().getString("screenshot") != null)
            if (getParams().getString("screenshot").contentEquals("1")) {
                takeScreenshot(Constant.SCREENSHOTS);
            }
        super.tearDown();
    }
    public LowUtils() {
    	
    }
    public LowUtils(long timeout, String tag) {
    	LowUtils.TIMEOUT = timeout;
    	this.TAG = tag;
    }
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
		openAppList();
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
				getScr().getChildByText(new UiSelector()
					.className("android.widget.TextView"), route[i]).clickAndWaitForNewWindow();
		else
			for (i+=1; i<route.length; i++)
				getScr().getChildByText(new UiSelector()
					.className("android.widget.TextView"), route[i]).clickAndWaitForNewWindow();
	}
	/**
	 * print your apps list. format: name--page--col--row--point
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 */
	/* work in apps list screen 0 , will back to 0*/
	public HashMap<String, App> getAppList() throws UiObjectNotFoundException, RemoteException {
		HashMap<String, App> appHash = new HashMap<String, App>(100);
		int page = getObjById("com.android.launcher3:id/apps_customize_page_indicator").getChildCount();
		for (int i=0; i<page; i++) {
			int num = getScr().getChildCount(new UiSelector().className("android.widget.TextView"));
			for (int j=0; j<num; j++) {
				UiObject uiObj = getObjByClsIdx("android.widget.TextView", j);
				String appName = uiObj.getText();
				Point pt = getObjPoint(uiObj);
				uiObj.clickAndWaitForNewWindow();
				String pkgName = getUiDevice().getCurrentPackageName();
				quitPkg(pkgName);
				App app = new App(appName, pkgName, i, 
						j/4, j%4, pt.x, pt.y);
				appHash.put(appName, app);
			}
			swipe("left");
		}
		for (int i=0; i<page; i++)
			swipe("right"); //keep the screen not change
		clearAllRecentApp();
		return appHash;
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
	public UiObject getObjByClsTxtId(String cls, String text, String id) {
		return new UiObject(new UiSelector().className(cls).text(text).resourceId(id));
	}
	public UiObject getObjById(String id) {
		return new UiObject(new UiSelector().resourceId(id));
	}
	public UiObject getObjByClsId(String cls, String id) {
		return new UiObject(new UiSelector().className(cls).resourceId(id));
	}
	public UiObject getObjByClsIns(String cls, int ins) {
		return new UiObject(new UiSelector().className(cls).instance(ins));
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
	public UiObject getObjByClsDesc(String cls, String desc) {
		return new UiObject(new UiSelector().className(cls).description(desc));
	}
	public UiObject getObjByClsDescContains(String cls, String desc) {
		return new UiObject(new UiSelector().className(cls).descriptionContains(desc));
	}
	/**
	 * get scrollable
	 * @return
	 */
	public UiScrollable getScr() {
		return new UiScrollable(new UiSelector().scrollable(true));
	}
	public UiScrollable getScrByCls(String cls) {
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
    public UiObject getCboxIns(int ins) {
    	return getObjByClsIns("android.widget.CheckBox", ins);
    }
    public UiObject getSwitchIns(int ins) {
    	return getObjByClsIns("android.widget.Switch", ins);
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
		if (getScr().exists())
			getScr().scrollIntoView(getObjByTxt(appName));
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
		UiScrollable uiScr = getScr();
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
		longPress(getObjPoint(uiObj));
	}
	public void longPress(int _x, int _y) {
		//TODO:
	}
	public void longPress (float _xRel, float _yRel) {
		longPress(toScreenX(_xRel), toScreenY(_yRel));
	}
	public void longPress(Point pt) {
		longPress(pt.x, pt.y);
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
	public boolean swipeRel(float xs, float ys, float xe, float ye, int step) {
		return getUiDevice().swipe(toScreenX(xs), toScreenY(ys), toScreenX(xe), toScreenY(ye), step);
	}
	public boolean swipeObj(UiObject uiObj1, UiObject uiObj2, int step) throws UiObjectNotFoundException {
		Point[] seg = {getObjPoint(uiObj1), getObjPoint(uiObj2)};
		return getUiDevice().swipe(seg, step);
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
	public Point getObjPoint(UiObject uiObj) throws UiObjectNotFoundException {
		Rect rect = uiObj.getBounds();
		return new Point(rect.centerX(), rect.centerY());
	}
	public void clickObj(UiObject uiObj, boolean newWindow) throws UiObjectNotFoundException {
		if (newWindow)
			uiObj.clickAndWaitForNewWindow(TIMEOUT);
		else
			uiObj.click();
	}
	public void clickPoint(int _x, int _y) {
		getUiDevice().click(_x, _y);
	}
	public void clickPoint(Point pt) {
        getUiDevice().click(pt.x, pt.y);
	}
	/* sometimes the uiObj is not clickable */
	public void clickPoint(UiObject uiObj) throws UiObjectNotFoundException {
		clickPoint(getObjPoint(uiObj));
	}
	public void clickPoint(float _xRel, float _yRel) {
		getUiDevice().click(toScreenX(_xRel), toScreenY(_yRel));
	}
    /**
     * Take screenshot of the device, the picture will be generated in /sdcard/
     *
     * @param storePath The storage path in /sdcard/uiAutoTest/, if it's empty,
     *            it will be in /sdcard/uiAutoTest/uitestName_2013xxxxxxx.png
     * @return {@code true} if capture successfully.
     */
    public boolean takeScreenshot(String storePath) {
        Log.v(TAG, "takeScreenshot.... ");
        String baseDir = "/sdcard";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateandTime = sdf.format(new Date());

        String fileName = getName() + "_" + currentDateandTime.toString() + ".png";

        String path = "";
        if (storePath.isEmpty()) {
            path = baseDir + File.separator + Constant.SCREENSHOTS + File.separator + fileName;
        } else {
            path = baseDir + File.separator + Constant.SCREENSHOTS + File.separator + storePath
                    + File.separator + fileName;
        }

        File f = new File(path);
        try {
            if (!f.getParentFile().exists()) {
                Runtime.getRuntime().exec("mkdir -p " + f.getParentFile().getAbsolutePath())
                        .waitFor();
            }
            return getUiDevice().takeScreenshot(f, 0.5f, 30);
        } catch (IOException e) {
            Log.v(TAG, "filename1: " + fileName); //2???
            Log.v(TAG, "filename1: " + fileName);
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.v(TAG, "filename: " + fileName);
            Log.v(TAG, "filename: " + fileName);
            e.printStackTrace();
        }
        return false;
    }
    public void assertObj(UiObject uiObj) {
    	assertTrue("The UiObject is not exists.", uiObj.exists());
    }
    public void assertPkg(String pkgName) {
        assertEquals("Package name: \"" + pkgName + "\" is not current package", pkgName,
                getUiDevice().getCurrentPackageName());
    }
    /*
    public void dismissUnWantedWindow(final String targetPkg) {
        UiWatcher dismissWatcher = new UiWatcher() {
            @Override
            public boolean checkForCondition() {
                for (int i = 0; i < 2; i++) {
                    if (!getUiDevice().getCurrentPackageName().equalsIgnoreCase(targetPkg)) {
                    	pressBack();
                    }
                }
                return getUiDevice().getCurrentPackageName().equalsIgnoreCase(targetPkg);
            }
        };
        UiDevice.getInstance().registerWatcher("dismissWatcher", dismissWatcher);
        UiDevice.getInstance().runWatchers();
    }*/
    public boolean quitPkg(String pkg) {
    	while (getUiDevice().getCurrentPackageName().equalsIgnoreCase(pkg))
    		pressBack();
    	return getUiDevice().getCurrentPackageName().equalsIgnoreCase(pkg);
    }
    private StringBuilder getLog(StringBuilder stringBuilder) {
        Process p = null;
        BufferedReader reader = null;
        String line = null;

        try {
            p = Runtime.getRuntime().exec("logcat -d");
            reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            stringBuilder.setLength(0);
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.destroy();
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder;
    }
    public boolean waitForLogMessage(String logMessage, int timeout) {
        StringBuilder stringBuilder = new StringBuilder();
        long endTime = SystemClock.uptimeMillis() + timeout;
        while (SystemClock.uptimeMillis() < endTime) {
            if (getLog(stringBuilder).lastIndexOf(logMessage) != -1) {
                return true;
            }
            sleep(500);
        }
        return false;
    }
    /* img compare */
    public boolean isImageSame(String sourceBitmapLocation, int x1, int y1, int x2, int y2) {
        Bitmap sourceBitmap = BitmapFactory.decodeFile(sourceBitmapLocation + ".png");
        Bitmap runtimeBitmap;

        String runtimeBitmapPath = sourceBitmapLocation + "_runtime.png";
        File runtimeFile = new File(runtimeBitmapPath);
        getUiDevice().takeScreenshot(runtimeFile, 1f, 100);
        runtimeBitmap = BitmapFactory.decodeFile(runtimeBitmapPath);

        return Bitmap.createBitmap(sourceBitmap, x1, y1, x2 - x1, y2 - y1).sameAs(
                Bitmap.createBitmap(runtimeBitmap, x1, y1, x2 - x1, y2 - y1));
    }
    public void launchApp(String pkg, String cls) {
        String prog = "am start -n " + pkg + "/" + cls;
        try {
            Process process = Runtime.getRuntime().exec(prog);
            process.waitFor();
            getUiDevice().waitForWindowUpdate(null, 6000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void backHome() throws RemoteException, UiObjectNotFoundException {
    	pressHome();
    	clearAllRecentApp();
    }
    /**
     * num times' random action
     * @param rand
     * @param num
     */
    public void randSwipe(Random rand, int num) {
    	String[] dir = {"left", "right", "up", "down"};
    	for (int i=0; i<num; i++) {
    		swipe(dir[rand.nextInt(4)]);
    	}
    }
}
