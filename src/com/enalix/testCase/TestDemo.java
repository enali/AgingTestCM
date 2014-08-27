package com.enalix.testCase;

import java.util.HashMap;

import android.os.RemoteException;
import android.view.KeyEvent;

import com.android.uiautomator.core.UiCollection;
import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;
import com.android.uiautomator.core.UiScrollable;
import com.android.uiautomator.core.UiSelector;
import com.enalix.testUtils.*;

public class TestDemo extends MidUtils {
	public void testDemo() throws RemoteException, UiObjectNotFoundException {
		//turnWifiOn();
		//callTelnum("14592839");
		//clearRecentApp("Messaging");
		//addContact("lzp", "1452938293", "932584260@qq.com", "7department");
		//getContact("lzp");
		//delContact("Enalix");
		//getUiDevice().pressMenu();
		//sendSmsByNum("15652915898", "hello world");
		//getEdit().clearTextField();
		//getUiDevice().pressKeyCode(KeyEvent.DEL);
		//clearEditText();
		//delSms("Lzp");
		//clearNotifications();
		//openUrl("www.baidu.com");
		//calculate("1+2*4/5");
		// openDir("baidu/ime/theme");
		//openWifi();
		//closeWifi();
		//openApMode();
		//closeApMode();
		//openBluetooth();
		//closeBluetooth();
		//openDataNet();
		//closeDataNet();
		//openApp("Voice Search");
		//openSet("Settings->Display & lights->Sleep");
		//getScrObj().getChildByText(new UiSelector().className("android.widget.TextView"), "Display & lights").clickAndWaitForNewWindow();
		//printAppList();
		//clearAllRecentApp();
		//openContact("lzp");
		//callName("lzp");
		calculate("1+2=");
	}
}