package qa.netease.thread;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Testing implements Runnable {
	private String localhost;
	private int delay;

	Testing(String localhostip, int delayTime) {
		localhost = localhostip;
		delay = delayTime;
	}

	public void run() {
		try {
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			RemoteWebDriver driver = new RemoteWebDriver(new URL("http://" + localhost + ":9515"),  capabilities);
//			RemoteWebDriver driver = new RemoteWebDriver(new URL("http://" + localhost + ":4444/wd/hub"), capabilities);		
			
			driver.get("http://qa-lab.163.org");
			// login
			driver.findElement(By.xpath("//input[@id='username']")).sendKeys(
					"zhengpeifeng");
			driver.findElement(By.xpath("//input[@id='password']")).sendKeys(
					"sdfafeasdf");
			driver.findElement(By.xpath("//input[@id='user_login']")).click();
			// new issues
			driver.get("http://qa-lab.163.org/issues/new_issues");
			

			driver.quit();

			Thread.sleep(delay);
		} catch (MalformedURLException e) {
			return;
		} catch (InterruptedException e) {
			return;
		}
	}

	public static void main(String args[]) {
//		Runnable test = new Testing("localhost", 0);
//		new Thread(test).start();
		
		Runnable test1 = new Testing("192.168.145.98", 100);
//		Runnable test2 = new Testing("192.168.145.101", 100);
		// for (int i = 0; i < 9; i++) {
		new Thread(test1).start();
		// }
		// for (int i = 0; i < 15; i++) {
//		new Thread(test2).start();
//		 }
	}
}

