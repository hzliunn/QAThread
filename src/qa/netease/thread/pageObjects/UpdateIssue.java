package qa.netease.thread.pageObjects;
/**
 * 对新建任务的更改和提交
 * @author hzjingcheng
 *
 */
public class UpdateIssue extends NewIssue {

	public static final String BTN_COMMIT = "//form[@id='issue-form']/input[@value='提交'] | //div[@id='history']//input[@name='commit']";
	public static final String TXT_NOTES = "//textarea[@id='notes'] | //div[@id='editor_issue_notes_edit']//textarea";
}
