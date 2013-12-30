package qa.netease.thread.wikiEditor;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import qa.netease.thread.CommonOpt;
import qa.netease.thread.Users;

public class NewIssuesWiki implements Runnable {
	private String localhost;

	NewIssuesWiki(String localhostip) {
		localhost = localhostip;
	}

	public void run() {
		try {
			// initial the element to be verified
			WebElement element = null;
			String user = CommonOpt.getRandomProjectMembers();
			String manager = Users.projectManagers[0];
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
			driver.findElement(By.xpath("//div[text()='" + issue + "']"))
					.click();
			Thread.sleep(5000);
			element = driver.findElement(By.xpath("//div[@id='content']/h2"));
			Assert.assertEquals(element.getText(), "新建" + issue);
			// 安全任务不能新建
			// 自动测试需求、工作总结、公司规范没有wiki和fck
			// 统计任务--部门主管必填
			if (issue == "统计任务") {
				driver.findElement(By.id("issue_leader")).sendKeys(manager);
			}
			// 维护任务、UED任务--开发人员必填
			if (issue == "维护任务") {
				driver.findElement(By.id("dev_id")).sendKeys(user);
			}
			if (issue == "UED任务") {
				driver.findElement(By.id("interactive_check")).click();
				driver.findElement(By.id("interactive_id")).sendKeys(user);
			}
			// BUG任务--测试版本必填
			if (issue == "BUG任务") {
				Select select = new Select(driver.findElement(By
						.id("project_id_test")));
				select.selectByVisibleText(" » " + Users.project);
				Thread.sleep(5000);
			}
			if (issue == "需求变更") {
				Select select = new Select(driver.findElement(By
						.id("project_id_test")));
				select.selectByVisibleText(" » " + Users.project);
				Thread.sleep(1000);
			}

			// click wiki tab
			driver.findElement(By.xpath("//a[@id='desc_wiki_title']")).click();
			// click fck tab
			// driver.findElement(By.xpath("//a[@id='desc_fck_title']")).click();
			driver.findElement(By.xpath("//input[@id='issue_subject']"))
					.sendKeys(name);
			driver.findElement(By.xpath("//textarea[@id='issue_description']"))
					.sendKeys(desc);
			if (issue == "BUG任务") {
				Select select = new Select(driver.findElement(By
						.id("issue_test_version_id")));
				try {
					select.selectByIndex(2);
				} catch (StaleElementReferenceException e) {
					select.selectByIndex(1);
				}
			}
			driver.findElement(By.xpath("//input[@id='add_issue_button']"))
					.click();
			element = driver.findElement(By.xpath("//*[@id='content']/h2"));
			Assert.assertEquals(element.getText().contains(issue + " #"), true);

			driver.quit();
		} catch (MalformedURLException e) {
			return;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			return;
		}
	}

	public static void main(String args[]) {
		Runnable test1 = new NewIssuesWiki("10.241.20.87");
		Runnable test2 = new NewIssuesWiki("192.168.145.101");
		for (int i = 0; i < 23; i++) {
			new Thread(test1).start();
		}
		for (int i = 0; i < 14; i++) {
			new Thread(test2).start();
		}
	}
}
