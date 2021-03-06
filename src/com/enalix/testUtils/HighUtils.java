package com.enalix.testUtils;

import java.io.IOException;
import java.util.Random;

import android.os.RemoteException;
import android.util.Log;

import com.android.uiautomator.core.UiObjectNotFoundException;

public class HighUtils extends MidUtils{
	
	/**
	 * send, recv, del message
	 * @param action	send, recv, del
	 * @param cont	using contact's phone number
	 * @throws IOException
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void messaging(String action, Contact cont) throws IOException, RemoteException, UiObjectNotFoundException {
		System.out.println("Messaging " + action +" " +  cont.getName() + " start");
		openApp("Messaging");
		switch (action) {
		case "send":
			String[] mess = TestHelper.getMessage(4);	//get 4 message
			sendSmsByTelnum(cont.getPhone(), mess);
			pressBack();
			break;
		case "recv":
			break;
		case "del":
			delSms(cont.getName());
			break;
		}
		clearRecentApp("Messaging");
		pressHome();
		System.out.println("Messaging " + action +" " +  cont.getName() + " end");
	}
	
	/**
	 * send, recv, del email(mail master)
	 * @param action	send, recv, del
	 * @param cont	to/from contact's email
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 * @throws IOException
	 */
	public void emailMM(String action, Contact cont) throws RemoteException, UiObjectNotFoundException, IOException {
		System.out.println("Email mail master " + action + " " + cont.getName() + " start");
		openApp("Mail Master");
		switch (action) {
		case "send":
			String[] mess = TestHelper.getMessage(2);
			String content = mess[0] + "\n" + mess[1] + "\n";
			sendEmailMM(cont.getEmail(), TestHelper.EMAIL_SUBJECT, content);
		case "recv":
			break;
		case "del":
			break;
		}
		clearRecentApp("Mail Master");
		pressHome();
		System.out.println("Email mail master " + action + " " + cont.getName() + " end");
	}
	
	/**
	 * open/down url, getted via TestHelper.getUrl
	 * @param action	open, download
	 * @param num	url's number
	 * @throws IOException
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void browser(String action, int num) throws IOException, RemoteException, UiObjectNotFoundException {
		System.out.println("Browser " + action + " " + num + " start");
		openApp("Browser");
		switch (action) {
		case "open":
			String[] urlopen = TestHelper.getUrlOpen(num);
			for (int i=0; i<num; i++) {
				openUrl(urlopen[i]);
				randSwipe(2);
				sleep(TestHelper.OPERATION_DELAY);
				randClick(2);
				sleep(TestHelper.OPERATION_DELAY);
			}
			break;
		case "down":
			String[] urldown = TestHelper.getUrlDown(num);
			for (int i=0; i<num; i++) {
				openUrl(urldown[i]);
				//TODO:
			}
			break;
		}
		clearRecentApp("Browser");
		pressHome();
		System.out.println("Browser " + action + " " + num + " end");
	}
	
	/**
	 * creat/edit/del/mv file
	 * @param action	creat, edit, del, mv
	 * @param dirPath	directory path
	 * @param fileList	filename array
	 * @throws UiObjectNotFoundException
	 * @throws RemoteException
	 * @throws IOException
	 */
	public void fileManager(String action, String dirPath, String[] fileList) throws UiObjectNotFoundException, RemoteException, IOException {
		System.out.println("file manager " + action + " start");
		openApp("File Manager");
		switch(action) {
		case "creat":
			System.out.println("creat dir start");
			creatDir(dirPath);
			System.out.println("creat dir end");
			for (int i=0; i<fileList.length; i++) {
				System.out.println("creat file " + fileList[i] + " start");
				String[] content = TestHelper.getMessage(3);
				String str = "";
				for (int j=0; j<content.length; j++)
					str += content[j] + "\n";
				creatFile(fileList[i], str);
				System.out.println("creat file " + fileList[i] + " start");
			}
			break;
		case "edit":
			for (int i=0; i<fileList.length; i++) {
				System.out.println("edit file " + fileList[i] + " start");
				String[] content = TestHelper.getMessage(3);
				String str="";
				for (int j=0; j<content.length; j++)
					str += content[j] + "\n";
				editFile(fileList[i], str);
				System.out.println("edit file " + fileList[i] + " end");
			}
			break;
		case "del":
			delFile(fileList);
			delDir(dirPath);
			break;
		case "mv":
			//TODO:
			break;
		}
		clearRecentApp("File Manager");
		pressHome();
		System.out.println("file manager " + action + " end");
	}
	
	/**
	 * install/uninstall app
	 * @param action	install/uninstall
	 * @param app	appName array
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void appManager(String action, String[] app) throws RemoteException, UiObjectNotFoundException {
		System.out.println("App manager " + action + " " + app.length + " start");
		switch (action) {
		case "install":
			openApp("File Manager");
			openDir(TestHelper.APP_PATH);
			installApp(app);
			clearRecentApp("File Manager");
			break;
		case "uninstall":
			openSet("Settings->Apps");
			uninstallApp(app);	
			clearRecentApp("Settings");
			break;
		}
		pressHome();
		System.out.println("App manager " + action + " " + app.length + " end");
	}

	public void phone(String action, Contact cont) {
		switch (action) {
		case "call":
			break;
		}
		//TODO:
	}

	public void settings() {
		//TODO:
	}
	
	/**
	 * add, del, edit contact
	 * @param action	add, del, edit
	 * @param cont	contact array
	 * @throws RemoteException
	 * @throws UiObjectNotFoundException
	 */
	public void people(String action, Contact[] cont) throws RemoteException, UiObjectNotFoundException{
		System.out.println("People " + action + " " + cont.length + " start");
		openApp("People");
		switch(action) {
		case "add":
			addContact(cont);
			break;
		case "del":
			delContact(cont);
			break;
		case "edit":
			break;
		}
		clearRecentApp("People");
		pressHome();
		System.out.println("People " + action + " " + cont.length + " end");
	}
	
	
	
	/* ---------------------------------------------------------------------For Calculator*/
	public void calculator(int num) throws RemoteException, UiObjectNotFoundException {
		System.out.println("Calculator start");
		String[] expr = TestHelper.generateCalExpr(num);
		openApp("Calculator");
		for (int i=0; i<num; i++)
			calculate(expr[i]);
		clearRecentApp("Calculator");
		pressHome();
		System.out.println("Calculator end");
	}
	/* ---------------------------------------------------------------------End For Calculator*/
	
	
	/* ---------------------------------------------------------------------For Tapet*/
	public void tapet() throws UiObjectNotFoundException, RemoteException {
		System.out.println("tapet start");
		openApp("Tapet");
		Random rand = new Random();
		int num = rand.nextInt(10);
		randSwipe(num);
		getObjByClsId("android.widget.ImageView", "com.sharpregion.tapet:id/applyEffect").clickAndWaitForNewWindow();
		clearRecentApp("Tapet");
		pressHome();
		System.out.println("tapet end");
	}
	/* ---------------------------------------------------------------------End For Tapet*/
	
	
	/* ---------------------------------------------------------------------For Game Play*/
	public void gamePlay(int num) throws UiObjectNotFoundException, RemoteException {
		Random rand = new Random();
		String game = TestHelper.GAMES_LIST[rand.nextInt(TestHelper.GAMES_LIST.length)];
		System.out.println("Game play " + game + " start");
		if (game.equalsIgnoreCase("2048")) {
			openApp("2048");
			for (int i=0; i<num; i++) {
				getObjByDesc("Reset").clickAndWaitForNewWindow();
				int step = play2048();
				//System.out.println(i + " times");
				Log.v(TAG, "play game 2048, using " + step + " steps");
				getObjByTxt("Try Again").clickAndWaitForNewWindow();
				if (!getObjByDesc("Reset").exists()) {
					pressBack();
					getObjByDesc("Reset").waitForExists(30000);
				}
				//System.out.println("reset exists");
			}
			pressBack();
			clearRecentApp("2048");
		}
		pressHome();
		System.out.println("Game play " + game + " end");
	}
	public int play2048() {
		String[] direction = {"right", "left", "up", "down"	};
		Random rand = new Random();
		int step = 0;
		while (getObjByDesc("Reset").exists()) {
			swipe(direction[rand.nextInt(4)]);
			step++;
		}
		return step;
	}
	/* --------------------------------------------------------------------End For Game Play*/

	
	
	/* --------------------------------------------------------------------For Mail Master*/
	public String[] initEmailMM(int num) throws UiObjectNotFoundException, RemoteException, IOException {
		System.out.println("init email start");
		String[] emailList = TestHelper.getEmail(num);
		openApp("Mail Master");
		String[] emailAcc = new String[emailList.length];
		//System.out.println(emailList.length);
		for (int i=0; i<emailList.length; i++) {
			String[] email = emailList[i].split("\\s+");
			emailAcc[i] = email[0];
			addAccountMM(email[0], email[1]);
			//System.out.printf("%s\t%s\n", email[0], email[1]);
		}
		clearRecentApp("Mail Master");
		Log.v(TAG, "init Email Mail Master");
		pressHome();
		System.out.println("init email end");
		return emailAcc;
	}
	public void clearEmailMM(String[] emailAcc) throws RemoteException, UiObjectNotFoundException {
		System.out.println("clear Email start");
		openApp("Mail Master");
		delAccountMM(emailAcc);
		clearRecentApp("Mail Master");
		Log.v(TAG, "clear Email Mail Master");
		pressHome();
		System.out.println("clear Email end");
	}
	public int clearAllEmailMM() throws RemoteException, UiObjectNotFoundException {
		openApp("Mail Master");
		int num = delAllAccountMM();
		clearRecentApp("Mail Master");
		Log.v(TAG, "clear all Email Mail Master");
		pressHome();
		return num;
	}
	/* --------------------------------------------------------------------End For Mail Master*/
	
	
	
	/* ----------------------------------------------------------------------For Contact*/
	public Contact[] initContact(int num) throws RemoteException, UiObjectNotFoundException {
		System.out.println("init contact start");
		openApp("People");
		while (!getUiDevice().getCurrentPackageName().equalsIgnoreCase("com.android.contacts"));
		Contact[] cont = TestHelper.generateContact(num);
		addContact(cont);
		clearRecentApp("People");
		Log.v(TAG, "init People with " + cont.length + "contacts");
		pressHome();
		System.out.println("init contact end");
		return cont;
	}
	public void clearContact(Contact[] cont) throws RemoteException, UiObjectNotFoundException {
		System.out.println("clear contact start");
		openApp("People");
		for (int i=0; i<cont.length; i++)
			delContact(cont[i].getName());
		clearRecentApp("People");
		Log.v(TAG, "clear People with " + cont.length + "contacts");
		pressHome();
		System.out.println("clear contact end");
	}
	public int clearAllContact() throws UiObjectNotFoundException, RemoteException {
		openApp("People");
		int contNumber = delAllContact();
		Log.v(TAG, "clear all People with " + contNumber + "contacts");
		clearRecentApp("People");
		pressHome();
		return contNumber;
	}
	/* -----------------------------------------------------------------------End For Contact*/
	
	
	
	/* ---------------------------------------------------------------------For App Manager*/
	public String[] initApp(int num) throws RemoteException, UiObjectNotFoundException {
		System.out.println("init app start");
		openApp("File Manager");
		openDir(TestHelper.APP_PATH);
		String[] appArray = TestHelper.getAppList(num);
		installApp(appArray);
		clearRecentApp("File Manager");
		Log.v(TAG, "init App with " + appArray.length + "app");
		pressHome();
		System.out.println("init app end");
		return appArray;
	}
	public void clearApp(String[] appArray) throws RemoteException, UiObjectNotFoundException {
		System.out.println("clear app start");
		openSet("Settings->Apps");
		uninstallApp(appArray);	
		clearRecentApp("Settings");
		Log.v(TAG, "clear App with " + appArray.length + "app");
		pressHome();
		System.out.println("clear app end");
	}
	/* ---------------------------------------------------------------------End For App Manager*/
	

	/* ---------------------------------------------------------------------For Run Behavior*/
	//一个behavior实例是用户一天的行为, 行为与行为间延时TestHelper.HOUR_DELAY
	//Behavior可以自己配置，也可以通过TestHelper的getBehavior系列函数获取。
	public void runBehavior(Behavior bh, Contact[] cont, String[] app, String[] email) throws RemoteException, UiObjectNotFoundException, IOException {
		Random rand = new Random();
		
		int SendSmsNum = bh.getSendSmsNum();
		int SendEmailNum = bh.getSendEmailNum();
		int RecvSmsNum = bh.getRecvSmsNum();
		int RecvEmailNum = bh.getRecvEmailNum();
		int UrlOpenNum = bh.getUrlOpenNum();
		int UrlDownNum = bh.getUrlDownNum();
		
		int GameNum = bh.getGameNum();
		int ContactNum = bh.getContactNum();
		int AppNum = bh.getAppNum();
		int FileNum = bh.getFileNum();
		int CalculateNum = bh.getCalculateNum();
		
		for (int i=0; i<GameNum; i++)
			gamePlay(5);
		sleepHourDelay();
		
		//###########send message
		for (int i=0; i<SendSmsNum; i++)
			messaging("send", cont[rand.nextInt(cont.length)]);
		sleepHourDelay();
		
		//###########open url
		browser("open", UrlOpenNum);
		sleepHourDelay();
		
		//###########uninstall app
		String[] uninstallApps = TestHelper.getSubStrArray(app, AppNum);
		appManager("uninstall", uninstallApps);
		sleepHourDelay();
		
		//###########install app
		appManager("install", uninstallApps);
		sleepHourDelay();
		
		//###########delete contact
		int[] select = TestHelper.generateNoReptNumber(ContactNum, cont.length);
		Contact[] delCont = new Contact[ContactNum];
		for (int i=0; i<ContactNum; i++)
			delCont[i] = cont[select[i]];
		people("del", delCont);
		sleepHourDelay();
		
		//###########add contact what you delete forward
		people("add", delCont);
		sleepHourDelay();
		
		//###########send email
		for (int i=0; i<SendEmailNum; i++)
			emailMM("send", cont[rand.nextInt(cont.length)]);
		sleepHourDelay();
		
		//###########create file
		String dirPath = TestHelper.generatePath(4);
		String[] fileList = TestHelper.generateFile(FileNum, ".txt");
		fileManager("creat", dirPath, fileList);
		sleepHourDelay();
		
		//###########edit file what you create forward
		fileManager("edit", dirPath, fileList);
		sleepHourDelay();
		
		//###########delete file waht you create forward
		fileManager("del", dirPath, fileList);
		sleepHourDelay();
		
		//###########calculate expression
		calculator(CalculateNum);
		sleepHourDelay();
	}
	/* ---------------------------------------------------------------------End For Run Behavior*/
	
	
	/* ---------------------------------------------------------------------For run customer*/
	//customer即是behavior数组。若以周为单位，Behavior[7] customer。以月为单位， Behavior[30] customer。
	//customer可以通过配置behavior数组得到， 也可以通过TestHelper的getCustomer系列函数得到
	public void runCustomer(Behavior[] customer, Contact[] cont, String[] app, String[] email) throws RemoteException, UiObjectNotFoundException, IOException {
		for (int i=0; i<customer.length; i++) {
			runBehavior(customer[i], cont, app, email);
			sleepDayDelay();
		}
	}
	/* ---------------------------------------------------------------------End for run customer*/
}
