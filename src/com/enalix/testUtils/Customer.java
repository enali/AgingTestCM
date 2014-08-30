package com.enalix.testUtils;

public class Customer {
	private int numGame;
	private int numSendSms;
	private int numSendEmail;
	private int numRecvSms;
	private int numRecvEmail;
	private int numInstApp;	//install app
	private int numUninstApp;	//uninstall app
	
	public Customer() {
		
	}

	public Customer(int numGame, int numSendSms, int numSendEmail,
			int numRecvSms, int numRecvEmail, int numInstApp, int numUninstApp) {
		this.numGame = numGame;
		this.numSendSms = numSendSms;
		this.numSendEmail = numSendEmail;
		this.numRecvSms = numRecvSms;
		this.numRecvEmail = numRecvEmail;
		this.numInstApp = numInstApp;
		this.numUninstApp = numUninstApp;
	}

	public int getNumGame() {
		return numGame;
	}

	public void setNumGame(int numGame) {
		this.numGame = numGame;
	}

	public int getNumSendSms() {
		return numSendSms;
	}

	public void setNumSendSms(int numSendSms) {
		this.numSendSms = numSendSms;
	}

	public int getNumSendEmail() {
		return numSendEmail;
	}

	public void setNumSendEmail(int numSendEmail) {
		this.numSendEmail = numSendEmail;
	}

	public int getNumRecvSms() {
		return numRecvSms;
	}

	public void setNumRecvSms(int numRecvSms) {
		this.numRecvSms = numRecvSms;
	}

	public int getNumRecvEmail() {
		return numRecvEmail;
	}

	public void setNumRecvEmail(int numRecvEmail) {
		this.numRecvEmail = numRecvEmail;
	}

	public int getNumInsApp() {
		return numInstApp;
	}

	public void setNumInstApp(int numInstApp) {
		this.numInstApp = numInstApp;
	}

	public int getNumUninsApp() {
		return numUninstApp;
	}

	public void setNumUninsApp(int numUninstApp) {
		this.numUninstApp = numUninstApp;
	}
	
}
