package com.enalix.testUtils;

public class App {
	private String appName;
	private String pkgName;
	private int	appPage;
	private int appCol;
	private int appRow;
	private int appX;
	private int appY;
	
	public App(String _app, String _pkg, int _page, int _col, int _row, int _x, int _y) {
		this.appName = _app;
		this.pkgName = _pkg;
		this.appPage = _page;
		this.appCol = _col;
		this.appRow = _row;
		this.appX = _x;
		this.appY = _y;
	}
	public String getName() {
		return this.appName;
	}
	public String getPkg() {
		return this.pkgName;
	}
	public int getPage() {
		return this.appPage;
	}
	public int getCol() {
		return this.appCol;
	}
	public int getRow() {
		return this.appRow;
	}
	public int getX() {
		return this.appX;
	}
	public int getY() {
		return this.appY;
	}
	@Override
	public String toString() {
		return this.appName + "---K:" + this.pkgName + "---P:" + this.appPage + "---C:" +
				this.appCol + "---R:" + this.appRow + "---(" + this.appX + ", " + this.appY + ");";
	}
	public static void main(String[] args) {
		App app = new App("Settings", "com.android.Settings",  1, 0, 1, 234, 458);
		System.out.println(app);
	}
}
