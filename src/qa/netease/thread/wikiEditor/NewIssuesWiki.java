package qa.netease.thread.wikiEditor;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import qa.netease.thread.Testing;

public class NewIssuesWiki implements Runnable {
	private String localhost;
	private int delay;

	NewIssuesWiki(String localhostip, int delayTime) {
		localhost = localhostip;
		delay = delayTime;
	}

	public void run() {
		try {
			String[] issueName = { "需求任务", "策划任务", "开发任务", "测试任务", "需求变更",
					"BUG任务", "开发内测BUG", "DBA任务", "统计任务", "事故跟踪", "维护任务",
					"服务器申请", "配置任务", "组内任务", "工作总结", "UED任务" };
			int index=(int) (Math.random()*issueName.length);
	        String issue = issueName[index];
	        
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			RemoteWebDriver driver = new RemoteWebDriver(new URL("http://"
					+ localhost + ":9515"), capabilities);
			driver.get("http://qa-lab.163.org");
			// login
			driver.findElement(By.xpath("//input[@id='username']")).sendKeys(
					"zhengpeifeng");
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys(
					"sdfafeasdf");
			driver.findElement(By.xpath("//input[@id='user_login']")).click();
			// new issues
			driver.get("http://qa-lab.163.org/issues/new_issues");
			Thread.sleep(delay);
			driver.findElement(By.xpath("//div[text()='" + issue + "']")).click();

			driver.quit();
		} catch (MalformedURLException e) {
			return;
		} catch (InterruptedException e) {
			return;
		}
	}

	public static void main(String args[]) {
//		Runnable test1 = new NewIssuesWiki("192.168.145.98", 100);
		Runnable test2 = new NewIssuesWiki("192.168.145.101", 100);
//		for (int i = 0; i < 9; i++) {
//			new Thread(test1).start();
//		}
		for (int i = 0; i < 15; i++) {
			new Thread(test2).start();
		}
	}
}
