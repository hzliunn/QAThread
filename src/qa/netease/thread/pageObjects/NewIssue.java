package qa.netease.thread.pageObjects;

/**
 * Add a new issue.
 * <img src="/images/New\ Issues\ 01.png" />
 * <img src="/images/New\ Issues\ 02.png" />
 * @author Jing, Cheng
 *
 */
public class NewIssue {
	
	// 项目任务
	public static final String BTN_REQ = "//div[@id='div_ptrackers_25']//a";
	public static final String BTN_PLAN = "//div[@id='div_ptrackers_12']//a";
	public static final String BTN_UED = "//div[@id='div_ptrackers_13']//a";
	public static final String BTN_DEV = "//div[@id='div_ptrackers_14']//a";
	public static final String BTN_TEST = "//div[@id='div_ptrackers_15']//a";
	public static final String BTN_REQ_CHANGE = "//div[@id='div_ptrackers_16']//a";
	public static final String BTN_SECURITY = "//div[@id='div_ptrackers_26']//a";
	
	// 项目相关任务
	public static final String BTN_BUG = "//div[@id='div_otherissues_2']//a";
	public static final String BTN_INTER_BUG = "//div[@id='div_otherissues_4']//a";
	public static final String BTN_SUSTAIN = "//div[@id='div_otherissues_3']//a";
	public static final String BTN_DBA = "//div[@id='div_otherissues_6']//a";
	public static final String BTN_STATISTICS = "//div[@id='div_otherissues_9']//a";
	public static final String BTN_DEFECT_TRACK = "//div[@id='div_otherissues_8']//a";
	public static final String BTN_SERVER_REQ = "//div[@id='div_otherissues_11']//a";
	public static final String BTN_CONFIG = "//div[@id='div_otherissues_18']//a";
	public static final String BTN_AUTO_TEST = "//div[@id='div_otherissues_19']//a";
	
	// 其他任务
	// TODO: 井诚 - 可测性不强，前端页面上的div都没有id，明显与其他两类任务不一致
	public static final String BTN_TEAM_TASK = "//a[text()='新建组内任务']";
	public static final String BTN_WORK_SUMMARY = "//a[text()='新建工作总结']";
	public static final String BTN_STANDARD = "//div[@id='']";
	
	public static final String DRPLIST_PROJECT = "//select[@id='project_id_test']";
	public static final String DRPLIST_VERSION = "//select[@id='fixed_version_id']";
	public static final String TXT_SUBJECT = "//input[@id='issue_subject']";
	
	public static final String TAB_WIKI_EDTR = "//a[@id='desc_wiki_title']";
	public static final String TAB_FCK_EDTR = "//a[@id='desc_fck_title']";
	
	// TODO: 井诚 - 当前自动化还没办法在FckEditor里输入字符串
	public static final String TXT_DESC = "//textarea[@id='issue_description']";
	
	public static final String TXT_ASSIGNEE = "//input[@id='edit_assignedUser']";
	public static final String CAL_START_DATE = "//input[@id='issue_start_date']";
	public static final String CAL_DUE_DATE = "//input[@id='issue_due_date']";
	public static final String DRPLIST_STATUS = "//select[@id='issue_status_id']";
	public static final String DRPLIST_SEVERITY = "//select[@id='issue_priority_id']";
	public static final String TXT_POINT = "//input[@id='issue_score']";
	public static final String DRPLIST_PRIORITY = "//select[@id='issue_issue_priority_id']";
	
	public static final String BTN_CREATE = "//input[@id='add_issue_button'] ";
}
