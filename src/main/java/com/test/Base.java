package com.test;

import static org.testng.Assert.assertTrue;
import io.appium.java_client.android.AndroidDriver;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.logic.readExcel;

public class Base {

	public static Boolean flag = true; // 判断控件是否存在
	@SuppressWarnings("rawtypes")
	public static AndroidDriver driver;


	/**
	 * 通过id判断控件存不存在，存在找控件的等待
	 */
	public static boolean isPlay(String id) {
		for (int i = 0; i <= 5; i++) {
			if (isPlayById(id)) {
				break;
			}
			sleep(500);
			if (i == 3) {
				break;
			}
		}
		if (isPlayById(id)) {
			return true;
		} else {
			return false;
		}
	}
	public static boolean isPlayXpath(String xpath) {
		for (int i = 0; i <= 5; i++) {
			if (isPlayBy(By.xpath(xpath))) {
				break;
			}
			sleep(500);
			if (i == 2) {
				break;
			}
		}
		if (isPlayBy(By.xpath(xpath))) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isPlayById(String id) {
		try {
			driver.findElementById(id).isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 通过by判断控件存不存在，判断完就立即返回
	 */
	public static boolean isPlayBy(By by) {
		try {
			driver.findElement(by).isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void getActivity() {
		String s = driver.currentActivity();
		System.out.println(s);
	}

	/**
	 * 往控件输入信息
	 */
	public static void Input(WebElement el, String value) {
		el.sendKeys(value);
	}

	/**
	 * 点击某个控件
	 */
	public static void click(WebElement el) {
		el.click();
	}
    /**
     * 通过不同的方式点击
     */
	public static WebElement el(String id){
		if(isPlay(id)){
			return findById(id);
		}else if(isPlayXpath(id)){
			return findByXpath(id);
		}else{
			print(id+"控件不存在");
			return null;
		}
	}
	/**
	 * 不可以点击的强制点击
	 */
	// public static void strongclick(WebElement el){
	// int x=el.getRect().x;
	// int y=el.getRect().y;
	//
	//
	// JavascriptExecutor js = (JavascriptExecutor) driver;
	// HashMap tapObject = new HashMap();
	// tapObject.put("x", x);
	// tapObject.put("y", y);
	// tapObject.put("duration", 1);
	// js.executeScript("mobile: tap", tapObject);
	// //或者使用adb命令：adb shell input tap x y
	// }

	/**
	 * 输出信息
	 */
	public static void print(String flag2) {
		if (flag2 == "") {
			Reporter.log("在：" + Time() + "输出了空字符串");
			System.out.println("在：" + Time() + "输出了空字符串");
		} else {
			Reporter.log("在：" + Time() + flag2);
			System.out.println("在：" + Time() + flag2);
		}
	}

	/**
	 * 按返回键
	 */
	public static void back() {
		driver.pressKeyCode(0);
	}

	/**
	 * 隐式等待
	 */
	public void waitAuto(int time) {
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	/**
	 * 显式等待
	 */
	public static WebElement waitAuto(WebDriver driver, final By by,
			int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		for (int attempt = 1; attempt <= waitTime; attempt++) {
			try {
				driver.findElement(by);
				break;
			} catch (Exception e) {
				driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
				print("等了" + attempt + "秒");
				if (attempt == waitTime) {
					break;
				}
			}
		}
		if (isPlayBy(by)) {
			flag = true;
			return wait
					.until(ExpectedConditions.visibilityOfElementLocated(by));
		} else {
			flag = false;
			return null;
		}
	}
	public static WebElement waitAuto(String text, int waitTime) {
		WebDriverWait wait = new WebDriverWait(driver, waitTime);
		for (int attempt = 1; attempt <= waitTime; attempt++) {
			try {
				driver.findElementByAndroidUIAutomator(text);
				break;
			} catch (Exception e) {
				driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
				print("等了" + attempt + "秒");
				if (attempt == waitTime) {
					break;
				}
			}
		}
		if (isPlayByText(text)) {
			flag = true;
			return wait.until(ExpectedConditions.visibilityOfElementLocated(By
					.tagName(text)));
		} else {
			flag = false;
			return null;
		}
	}
	public static boolean isPlayByText(String text) {
		try {
			driver.findElementByAndroidUIAutomator(text).isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public static WebElement findById(String id) {
		WebElement el = waitAuto(driver, By.id(id), 3);
		if (flag) {
			return el;
		} else {
			print("控件：" + id + "不存在");
			assertTrue(false);
			return null;
		}
	}
	public static WebElement findByClass(String id) {
		WebElement el = waitAuto(driver, By.className(id), 3);
		if (flag) {
			return el;
		} else {
			print("控件：" + id + "不存在");
			assertTrue(false, "等了3秒还找不到控件");
			return null;
		}
	}
	public static WebElement findByXpath(String id) {
		WebElement el = waitAuto(driver, By.xpath(id), 3);
		if (flag) {
			return el;
		} else {
			print("控件：" + id + "不存在");
			assertTrue(false, "等了3秒还找不到控件");
			return null;
		}
	}
	public static WebElement findByText(String id) {
		WebElement el = waitAuto(id, 3);
		if (flag) {
			return el;
		
		} else {
			print("控件：" + id + "不存在");
			assertTrue(false, "等了3秒还找不到控件");
			return null;
		}
	}
	/**
	 * 获取时间
	 * 
	 * @return
	 */
	public static String Time() {
		SimpleDateFormat formattime1 = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss:SSS");
		long ctime = System.currentTimeMillis();
		String currenttime = formattime1.format(new Date(ctime));
		return currenttime;
	}
	
	public static void sleep(int s) {
		try {
			Thread.sleep(s);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@BeforeSuite
	public void beforeSuite() {
		cmdCtrl.startServer("4723");
	}
	@AfterSuite
	public void afterSuite() {
		driver.quit();
		cmdCtrl.stopServer("4723");
		cmdCtrl.openHtml();
	}
	final static int i=2;
	@SuppressWarnings("rawtypes")
	@BeforeClass
	public void BeforeClass(){
		DesiredCapabilities cap = new DesiredCapabilities();
		 cap.setCapability("deviceName", getInfo(i).get("deviceName"));
		cap.setCapability("platformName", "Android");
		 cap.setCapability("platformVersion", getInfo(i).get("platformVersion"));
		cap.setCapability("unicodeKeyboard", "true");
		cap.setCapability("resetKeyBoard", "true");
		cap.setCapability("appPackage", "com.thetiger.ldd");
		cap.setCapability("appActivity",
				"com.thetiger.ldd.ui.activity.SplashActivity");
		// cap.setCapability("sessionOverride", true);
		try {
			driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"),
					cap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取安卓设备信息
	 */
	public static  Map<String,String> getInfo(int i){
		Map<String,String> maps=new HashMap<String, String>();
		switch (i) {
		case 1:
			maps.put("deviceName", "127.0.0.1:62001");
			maps.put("platformVersion","4.4.2");
			break;
        case 2:
        	maps.put("deviceName", "eddffa38");
			maps.put("platformVersion","5.1.1");
			break;	
		default:
			break;
		}
		return maps;
	}
	
	@AfterClass
	public void afterClass(){
		driver.quit();
	}
	
	/**
	 * 随机生成手机号码
	 */
	public static String phoneNum() {
		int i = 10000000 + (int) (Math.random() * 99999999);
		String pn = "137" + Integer.toString(i);
		return pn;
	}
	
	
	/**
	 * 点击或者输入
	 * @param map1控件id
	 * @param map2操作类型
	 * @param map3输入内容
	 */
	public static void ClickandInput(int i1,int i2,int i3){
		Map<String,String> m1=null;
		Map<String,String> m2=null;
		Map<String,String> m3=null;
		m1=readExcel.excelData(i1);
		m2=readExcel.excelData(i2);
		m3=readExcel.excelData(i3);
		sleep(2000);
		for(String str:m1.keySet()){
			dealData(m1.get(str), m2.get(str), m3.get(str));
		}	
	}
	public static void dealData(String s1,String s2,String s3){
		int i=0;
		if(s2.equalsIgnoreCase("click")){
			i=1;
		}else if(s2.equalsIgnoreCase("input")){
			i=2;
		}
		System.out.println(i);
		if(i==1){
			el(s1).click();
			System.out.println(s1);}
		else if(i==2){
			System.out.println(s1+":"+s3);
			el(s1).sendKeys(s3);
			}
			else{
			print("输入有误");	
		}	
	}	
	public static void main(String[] args){

		
	}
}
