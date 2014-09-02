package com.enalix.testCase;

import java.io.IOException;

import android.os.RemoteException;

import com.android.uiautomator.core.*;
import com.enalix.testUtils.*;

public class TestDemo extends HighUtils{
	public void testDemo() throws RemoteException, UiObjectNotFoundException, IOException {		
		//###########init
		int initAppNum = 6;
		int initContNum = 6;
		int initEmailNum = 3;
		
		String[] app = initApp(initAppNum);
		Contact[] cont = initContact(initContNum);
		String[] email = initEmailMM(initEmailNum);
		
		//###########run
		Behavior bh = TestHelper.getGamerBehavior();
		runBehavior(bh, cont, app, email);
		//Behavior[] customer = TestHelper.getCustomer();
		//runCustomer(customer, cont, app, email);
		
		//###########clear
		clearEmailMM(email);
		clearContact(cont);
		clearApp(app);
	}
}