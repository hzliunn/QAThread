package qa.netease.thread.pageObjects;

/**
 * Generic Web frame after login.
 * <img src="/images/main_frame.png" />
 * @author Jing, Cheng
 *
 */
public class MainFrame {
	public static final String NAV_ISSUES = "//div[@id='header']/div[2]/a";
	public static final String NAV_PROJECTS = "//div[@id='header']/div[3]/a";
	public static final String NAV_GROUPS = "//div[@id='header']/div[4]/a";
	public static final String NAV_STANDARD = "//div[@id='header']//a[text()='规范库']";
	public static final String NAV_BUGS = "//div[@id='header']//a[text()='BUGS']";
	public static final String NAV_MISC = "//div[@id='header']//a[text()='其 他']";
	
	public static final String BTN_LOGOUT = "//a[@class='logout']";
	public static final String BTN_MY_ACCOUNT = "//a[@class='my-account' or @class='myaccount']";
	public static final String BTN_NEW_ISSUE = "//a[@class='issue-tmp']";
	
}