package com.Case;


import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.test.Base;



public class Listener extends TestListenerAdapter{
	 @Override
	    public void onTestFailure(ITestResult tr) {
		 Base.isFail=true;
	 }
}
