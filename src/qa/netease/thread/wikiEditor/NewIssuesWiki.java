package qa.netease.thread.wikiEditor;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import qa.netease.thread.CommonOpt;

public class NewIssuesWiki implements Runnable {
	private String localhost;
	private int delay;

	NewIssuesWiki(String localhostip, int delayTime) {
		localhost = localhostip;
		delay = delayTime;
	}

	public void run() {
		try {
			// 安全任务不能新建
			// 自动测试需求、工作总结、公司规范没有wiki和fck
			// 统计任务--部门主管必填
			// 维护任务、UED任务--开发人员必填
			// BUG任务--测试版本必填
			
			// initial the element to be verified
			WebElement element = null;
	        String user = CommonOpt.getRandomProjectMembers();
			String password = "sdfafeasdf";
	        String issue = CommonOpt.getRandomIssues();
	        
			long time = System.currentTimeMillis();
	        String name = "task_name_" + issue + "_" + time;
			String desc = "task_desc_" + issue + "_" + time;
			
			// open chrome
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			RemoteWebDriver driver = new RemoteWebDriver(new URL("http://"
					+ localhost + ":9515"), capabilities);
			// login
			CommonOpt.login(driver, user, password);
			// new issues
			driver.get("http://qa-lab.163.org/issues/new_issues");
			driver.findElement(By.xpath("//div[text()='" + issue + "']")).click();
			Thread.sleep(delay);
			element = driver.findElement(By.xpath("//div[@id='content']/h2"));
			Assert.assertEquals(element.getText(), "新建" + issue);
			driver.findElement(By.xpath("//input[@id='issue_subject']")).sendKeys(name);
			// click wiki tab
			driver.findElement(By.xpath("//a[@id='desc_wiki_title']")).click();
			// click fck tab
			// driver.findElement(By.xpath("//a[@id='desc_fck_title']")).click();
			Thread.sleep(delay);
			driver.findElement(By.xpath("//textarea[@id='issue_description']")).sendKeys(desc);
			driver.findElement(By.xpath("//input[@id='add_issue_button']")).click();
			element = driver.findElement(By.xpath("//div[@id='flash_msg']/div"));
			Assert.assertEquals(element.getText(), "创建成功");
			
			driver.quit();
		} catch (MalformedURLException e) {
			return;
		} catch (InterruptedException e) {
			return;
		}
	}

	public static void main(String args[]) {
		Runnable test1 = new NewIssuesWiki("10.241.20.87", 1000);
//		Runnable test2 = new NewIssuesWiki("192.168.145.101", 100);
		for (int i = 0; i < 5; i++) {
			new Thread(test1).start();
		}
//		for (int i = 0; i < 15; i++) {
//			new Thread(test2).start();
//		}
	}
}
