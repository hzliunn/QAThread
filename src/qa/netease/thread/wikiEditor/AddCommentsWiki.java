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
	        String issue = CommonOpt.getRandomIssues();
	        
	        long time = System.currentTimeMillis();
			String comment = "task_desc_" + issue + "_" + time;
			
			// open chrome
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			RemoteWebDriver driver = new RemoteWebDriver(new URL("http://"
					+ localhost + ":9515"), capabilities);
			// login
			CommonOpt.login(driver, user, password);
			driver.findElement(By.id("q")).sendKeys(issue);
			driver.findElement(By.xpath("//div[@id='quick-search']/form/a")).click();
			
			for(String winHandle : driver.getWindowHandles()){
			    driver.switchTo().window(winHandle);
			}
			Thread.sleep(delay);
			driver.findElement(By.xpath("//*[@id='search-results']/dt[1]/a")).click();
			for(String winHandle : driver.getWindowHandles()){
			    driver.switchTo().window(winHandle);
			}
			driver.findElement(By.xpath("//*[@id='history']/div[1]/a")).click();
			driver.findElement(By.id("issues_notes_wiki_title")).click();
			// driver.findElement(By.id("issues_notes_fck_title")).click();
			driver.findElement(By.id("notes")).sendKeys(comment);
			driver.findElement(By.xpath("//*[@id='issue-form']/input[4]")).click();
			Thread.sleep(delay);
			element = driver.findElement(By.xpath("//*[@id='flash_msg']/div"));
			Assert.assertEquals(element.getText(), "更新成功");

			driver.quit();
		} catch (MalformedURLException e) {
			return;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	public static void main(String args[]) {
		Runnable test1 = new AddCommentsWiki("192.168.146.83", 10000);
			new Thread(test1).start();
	}

}
