package qa.netease.thread.pageObjects;
/**
 * 新建任务页面新建项目按钮、输入项目名称、选择项目类型、输入项目内容、创建项目按钮模块定义
 * @author hzjingcheng
 *
 */
public enum Projects {

	btnCreateNewProject("//input[@type='button' and @value='新建项目']"),
	txtProjectName("//input[@id='project_name']"),
	drplstProduct("//select[@id='project_parent_id']"),
	txtProjectMgr("//input[@id='addpm']"),
	btnSubmitCreate("//input[@id='addproject' or @class='project_submit_button']");
	
	private String xpath;
	
	private Projects(String xpath) {
		this.xpath = xpath;
	}
	
	public String getXpath() { return xpath; }
}
