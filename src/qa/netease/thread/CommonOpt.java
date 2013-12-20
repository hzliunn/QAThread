package qa.netease.thread;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;

public class CommonOpt {
	public static void login(RemoteWebDriver driver, String user, String password) {
		driver.get("http://qa-lab.163.org");
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(user);
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys(
				password);
		driver.findElement(By.xpath("//input[@id='user_login']")).click();
	}

	public static String getRandomProjectMembers() {
		int index = (int) (Math.random() * Users.projectMembers.length);
		return Users.projectMembers[index];
	}

	public static String getRandomIssues() {
		int index = (int) (Math.random() * Users.issueName.length);
		return Users.issueName[index];
	}
}
