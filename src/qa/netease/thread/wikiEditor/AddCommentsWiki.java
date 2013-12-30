package qa.netease.thread.wikiEditor;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import qa.netease.thread.CommonOpt;

public class AddCommentsWiki implements Runnable {
	private String localhost;
	private int delay;

	AddCommentsWiki(String localhostip, int delayTime) {
		localhost = localhostip;
		delay = delayTime;
	}

	public void run() {
		try {
			// initial the element to be verified
			WebElement element = null;
			String user = CommonOpt.getRandomProjectMembers();
			String password = "sdfafeasdf";
			String issue = CommonOpt.getRandomCommentIssues();

			long time = System.currentTimeMillis();
			String comment = "task_desc_" + issue + "_" + time;

			// open chrome
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			RemoteWebDriver driver = new RemoteWebDriver(new URL("http://" + localhost
					+ ":9515"), capabilities);
			// login
			CommonOpt.login(driver, user, password);
			driver.findElement(By.id("q")).sendKeys(issue);
			driver.findElement(By.xpath("//div[@id='quick-search']/form/a"))
					.click();

			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
			}
			Thread.sleep(delay);
			driver.findElement(By.xpath("//*[@id='search-results']/dt[2]/a"))
					.click();
			for (String winHandle : driver.getWindowHandles()) {
				driver.switchTo().window(winHandle);
			}

			driver.findElement(By.xpath("//*[@id='history']/div[1]/a")).click();
			driver.findElement(By.id("issues_notes_wiki_title")).click();
			// driver.findElement(By.id("issues_notes_fck_title")).click();
			driver.findElement(By.id("notes")).sendKeys(comment);
			driver.findElement(By.xpath("//*[@id='issue-form']/input[4]"))
					.click();

			if (CommonOpt.isElementPresent(driver, "//*[@id='issue-form']/div[2]")) {
				driver.findElement(By.id("conflict_resolution_add_notes"))
						.click();
				driver.findElement(By.xpath("//*[@id='issue-form']/p[2]/input"))
						.click();
			}

			// Thread.sleep(delay);
			// 服务器申请，组内任务
			element = driver.findElement(By.xpath("//*[@id='flash_msg']/div"));
			Assert.assertEquals(element.getText(), "更新成功");

			driver.quit();
		} catch (MalformedURLException e) {
			return;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			return;
		}
	}

	

	public static void main(String args[]) {
		Runnable test1 = new AddCommentsWiki("10.241.20.87", 1000);
		Runnable test2 = new AddCommentsWiki("192.168.145.101", 100);
		for (int i = 0; i < 10; i++) {
			new Thread(test1).start();
		}
		for (int i = 0; i < 20; i++) {
			new Thread(test2).start();
		}
	}
}
