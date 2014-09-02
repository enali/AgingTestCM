package com.enalix.testCase;

import java.io.IOException;

import android.os.RemoteException;

import com.android.uiautomator.core.*;
import com.enalix.testUtils.*;

public class TestDemo extends HighUtils{
	public void testDemo() throws RemoteException, UiObjectNotFoundException, IOException {		

		//###########init
		String[] app = initApp(6);
		Contact[] cont = initContact(6);
		String[] email = initEmailMM(3);
		
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