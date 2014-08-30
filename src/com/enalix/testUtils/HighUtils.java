package com.enalix.testUtils;

import java.util.Random;

import android.os.RemoteException;
import android.util.Log;

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
	public void gamePlay() throws UiObjectNotFoundException, RemoteException {
		Random rand = new Random();
		String game = games[rand.nextInt(games.length)];
		if (game.equalsIgnoreCase("2048")) {
			int step = play2048();
			Log.v(TAG, "play game 2048, using " + step + " steps");
			getObjByTxt("Try Again").clickAndWaitForNewWindow();
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
	public int initPeople(int num) throws RemoteException, UiObjectNotFoundException {
		openApp("People");
		Contact[] cont = TestHelper.generateContact(num);
		addContact(cont);
		clearRecentApp("People");
		return 12;
	}
	public int initApp(String appPath, String[] appArray) {
		return 12;
	}
	/* give a customer argument, it will behave what you have defined in customer */
	public void behaveCustomer(Customer cust) {
		
	}
}
