package qa.netease.thread.pageObjects;
/**
 * 评论界面更新、发表评论按钮和文本输入模块定义
 * @author hzjingcheng
 *
 */
public class ViewIssue {

	public static final String BTN_UPDATE = "//a[(text()='更新')]";
	public static final String TXT_SUBJECT = "//div[@class='subject']//h3";
	public static final String TXT_DESC = "//div[@class='wiki']";
	
	public static final String BTN_ADD_COMMIT = "//a[text()='发表评论']";
}
