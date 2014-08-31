package com.enalix.testUtils;

import java.util.Random;

import android.os.RemoteException;
import android.util.Log;

import com.android.uiautomator.core.UiObject;
import com.android.uiautomator.core.UiObjectNotFoundException;

public class HighUtils extends MidUtils{
	public final String[] games = {"2048"};
	
	public void messaging() {
		
	}
	public void email() {
		
	}
	public void calculator(int num) throws RemoteException, UiObjectNotFoundException {
		String[] expr = TestHelper.generateCalExpr(num);
		openApp("Calculator");
		for (int i=0; i<num; i++)
			calculate(expr[i]);
		clearRecentApp("Calculator");
		pressHome();
	}
	public void browser() {
		
	}
	public void fileManager() {
		
	}
	public void appManager() {
		
	}
	public void gamePlay(int num) throws UiObjectNotFoundException, RemoteException {
		Random rand = new Random();
		String game = games[rand.nextInt(games.length)];
		if (game.equalsIgnoreCase("2048")) {
			unlock();
			openAppList();
			openApp("2048");
			for (int i=0; i<num; i++) {
				int step = play2048();
				Log.v(TAG, "play game 2048, using " + step + " steps");
				getObjByTxt("Try Again").clickAndWaitForNewWindow();
			}
			clearRecentApp("2048");
		}
	}
	public void phone() {
		
	}
	/* one app to set wallpaper */
	public void tapet() throws UiObjectNotFoundException, RemoteException {
		pressHome();
		openAppList();
		openApp("Tapet");
		Random rand = new Random();
		int num = rand.nextInt(10);
		randSwipe(num);
		getObjByClsId("android.widget.ImageView", "com.sharpregion.tapet:id/applyEffect").clickAndWaitForNewWindow();
		clearRecentApp("Tapet");
		pressHome();
	}
	public void settings() {
		
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
	/**
	 * initial contact, generate num contact to fill 
	 * @param num, the real number that you have added to People
	 * @return
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public Contact[] initContact(int num) throws RemoteException, UiObjectNotFoundException {
		unlock();
		openAppList();
		openApp("People");
		while (!getUiDevice().getCurrentPackageName().equalsIgnoreCase("com.android.contacts"));
		Contact[] cont = TestHelper.generateContact(num);
		addContact(cont);
		clearRecentApp("People");
		return cont;
	}
	public int delAllContact() throws UiObjectNotFoundException, RemoteException {
		int contNumber = 0;
		UiObject uiCont = getObjByClsId("android.widget.TextView", "com.android.contacts:id/cliv_name_textview");
		while (uiCont.exists()) {
			String contName = uiCont.getText();
			delContact(contName);
			Log.v(TAG, "delete contact: " + contName);
			contNumber++;
		}
		return contNumber;
	}
	public String[] initApp(int num) throws RemoteException, UiObjectNotFoundException {
		unlock();
		openAppList();
		openApp("File Manager");
		openDir(TestHelper.APP_PATH);
		String[] appArray = new String[num];
		if (num>=TestHelper.APP_LIST.length)
			appArray = TestHelper.APP_LIST.clone();
		else {
			int[] select = TestHelper.generateNoReptNumber(num, TestHelper.APP_LIST.length);
			for (int i=0; i<select.length; i++)
				appArray[i] = TestHelper.APP_LIST[select[i]];
		}
		installApp(appArray);
		clearRecentApp("File Manager");
		return appArray;
	}
	public void People(int num) throws RemoteException, UiObjectNotFoundException {

	}

	public void runBehavior(Behavior bh, Contact[] cont, String[] app, String[] mess) throws RemoteException, UiObjectNotFoundException {
		int GameNum = bh.getGameNum();
		int SendSmsNum = bh.getSendSmsNum();
		int SendEmailNum = bh.getSendEmailNum();
		int RecvSmsNum = bh.getRecvSmsNum();
		int RecvEmailNum = bh.getRecvEmailNum();
		int InstAppNum = bh.getInstAppNum();
		int UnInstAppNum = bh.getUnInstAppNum();
		
		for (int i=0; i<GameNum; i++)
			gamePlay(5);
		
	}
	public String[] initMessage(int num) {
		return new String[0];
		//TODO:
	}
}
