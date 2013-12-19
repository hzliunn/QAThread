package qa.netease.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.netease.dagger.BrowserEmulator;
import com.netease.dagger.GlobalSettings;
import qa.netease.thread.pageObjects.Login;
import qa.netease.thread.pageObjects.MainFrame;
import qa.netease.thread.pageObjects.NewIssue;
import qa.netease.thread.pageObjects.UpdateIssue;
import qa.netease.thread.pageObjects.ViewIssue;

/**
 * qa平台页面上一些常规操作，具体见下面各方法的注释
 * 
 * @author hzjingcheng
 * 
 */
public class CommonOpt {
    // private static Logger logger = Logger.getLogger(CommonOpt.class);

    /**
     * Login to QA Platform with specified user name/password.
     * 
     * @param be
     * @param user
     * @param pwd
     * @throws Exception
     */
    public static void login(BrowserEmulator be, String user, String pwd) {
        be.open(GlobalVariables.HOSTNAME);
        be.expectElementExistOrNot(true, "//form[@id='login']/table[@class='icon_total bg_icon']/tbody/tr[2]/td[2]/strong", 5000);
        if (be.isElementPresent(MainFrame.BTN_LOGOUT, 5000)) {
            be.click(MainFrame.BTN_LOGOUT);
        }
        be.type(Login.TXT_NAME, user);
        be.type(Login.TXT_PASSWORD, GlobalVariables.PWD);
        be.click(Login.BTN_LOGIN);
        be.expectElementExistOrNot(true, MainFrame.BTN_LOGOUT, 5000);
    }

    /**
     * Re-login to QA Platform with specified user name/password.
     * 
     * @param be
     * @param user
     * @param pwd
     * @throws Exception
     */
    public static void relogin(BrowserEmulator be, String user, String pwd) {
        if (be.isElementPresent(MainFrame.BTN_LOGOUT, 5000)) {
            be.click(MainFrame.BTN_LOGOUT);
        }
        login(be, user, pwd);
    }

    /**
     * 快速创建一个Issue,填充主题与描述 选择一个项目,同时选择一个指派人
     * 
     * @param be
     * @param issueType
     * @param projectName
     * @param issueName
     * @param issueDesc
     * @param assignUser
     * @return
     */
    public static String quickNewIssue(BrowserEmulator be, String issueType,
            String projectName, String issueName, String issueDesc,
            String assignUser) {
        HashMap<String, String> issueTypes = new HashMap<String, String>();
        issueTypes.put("需求任务", NewIssue.BTN_REQ);
        issueTypes.put("策划任务", NewIssue.BTN_PLAN);
        issueTypes.put("UED任务", NewIssue.BTN_UED);
        issueTypes.put("开发任务", NewIssue.BTN_DEV);
        issueTypes.put("测试任务", NewIssue.BTN_TEST);
        issueTypes.put("需求变更", NewIssue.BTN_REQ_CHANGE);
        issueTypes.put("安全任务", NewIssue.BTN_SECURITY);

        issueTypes.put("BUG任务", NewIssue.BTN_BUG);
        issueTypes.put("开发内测BUG", NewIssue.BTN_INTER_BUG);
        issueTypes.put("维护任务", NewIssue.BTN_SUSTAIN);
        issueTypes.put("DBA任务", NewIssue.BTN_DBA);
        issueTypes.put("统计任务", NewIssue.BTN_STATISTICS);
        issueTypes.put("事故跟踪", NewIssue.BTN_DEFECT_TRACK);
        issueTypes.put("服务器申请", NewIssue.BTN_SERVER_REQ);
        issueTypes.put("配置任务", NewIssue.BTN_CONFIG);

        issueTypes.put("组内任务", NewIssue.BTN_TEAM_TASK);
        issueTypes.put("工作总结", NewIssue.BTN_WORK_SUMMARY);

        Assert.assertNotNull(issueTypes.get(issueType),
                "Unsupported type of task");

        be.open(GlobalVariables.HOSTNAME + "/issues/new_issues");
        be.click(issueTypes.get(issueType));
        be.expectTextExistOrNot(true, "新建" + issueType, 10000);
        if (projectName != null) {
            be.select(NewIssue.DRPLIST_PROJECT, " » " + projectName);
            try {
                Thread.currentThread();
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        be.type(NewIssue.TXT_SUBJECT, issueName);
        if (be.isElementPresent(NewIssue.TAB_WIKI_EDTR, 5000)) {
            be.click(NewIssue.TAB_WIKI_EDTR);
        }
        be.type(NewIssue.TXT_DESC, issueDesc);
        if (issueType.equals("维护任务")) {
            be.click("//input[@id='plan_check']");
            be.type("//input[@id='dev_id']", GlobalVariables.PROJECT_MEMBERS[0]);
        } else if (issueType.equals("统计任务")) {
            be.type("//input[@id='issue_leader']", GlobalVariables.ACCOUNT1);
        } else if (issueType.equals("UED任务")) {
            be.click("//input[@id='interactive_check']");
            be.type("//input[@id='interactive_id']",
                    GlobalVariables.PROJECT_MEMBERS[0]);
        } else if (issueType.equals("BUG任务")) {
            // 选择测试版本
            RemoteWebDriver driver = be.getBrowserCore();
            WebElement element = driver.findElement(By
                    .xpath("//select[@id='issue_test_version_id']"));
            Select select = new Select(element);
            Assert.assertTrue(select.getOptions().size() > 1, "当前没有可用的测试版本");
            select.selectByIndex(2);

            // 选择测试模块
            element = driver.findElement(By
                    .xpath("//select[@id='bug_issue_category_id']"));
            select = new Select(element);
            Assert.assertTrue(select.getOptions().size() > 1, "当前没有可用的测试模块");
            select.selectByIndex(2);
            be.type(NewIssue.TXT_SUBJECT, issueName);
            if (be.isElementPresent(NewIssue.TAB_WIKI_EDTR, 5000)) {
                be.click(NewIssue.TAB_WIKI_EDTR);
            }
            be.type(NewIssue.TXT_DESC, issueDesc);
        } else if (assignUser != null && issueType.equals("策划任务")) {
            // 选择指派人员
            be.type("//input[@id='edit_assignedUser']", assignUser);
            be.pressKeyboard(9);
            try {
                Thread.currentThread();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        be.click(NewIssue.BTN_CREATE);
        try {
            Thread.currentThread();
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        be.expectTextExistOrNot(true, "创建成功", 10000);
        be.expectTextExistOrNot(true, issueType + " #", 5000);
        be.expectTextExistOrNot(true, issueName, 5000);
        be.expectTextExistOrNot(true, issueDesc, 5000);

        String[] urls = be.getBrowserCore().getCurrentUrl().split("/");
        return urls[urls.length - 1].split("\\?")[0];
    }

    /**
     * 快速创建一个issue，选择默认项目，使用默认标题和表述
     * 
     * @param be
     * @param issueType
     * @return Issue ID
     */
    public static String quickNewIssue(BrowserEmulator be, String issueType) {
        long time = System.currentTimeMillis();
        return quickNewIssue(be, issueType, null, "task_name_" + issueType
                + "_" + time, "task_desc_" + issueType + "_" + time, null);
    }

    /**
     * 快速创建一个Issue<br>
     * 选择默认项目 只填充主题与描述
     * 
     * @param be
     * @param issueType
     * @param issueName
     * @param issueDesc
     * @return
     */
    public static String quickNewIssue(BrowserEmulator be, String issueType,
            String issueName, String issueDesc) {
        return quickNewIssue(be, issueType, null, issueName, issueDesc, null);
    }

    /**
     * 快速创建一个Issue<br>
     * 选择默认被指派人 填充主题与描述,选择一个项目
     * 
     * @param be
     * @param issueType
     * @param projectName
     * @param issueName
     * @param issueDesc
     * @return
     */
    public static String quickNewIssue(BrowserEmulator be, String issueType,
            String projectName, String issueName, String issueDesc) {
        return quickNewIssue(be, issueType, projectName, issueName, issueDesc,
                null);
    }

    /**
     * 快速更新一个Issue<br>
     * 只更新主题与描述
     * 
     * @param be
     * @param oldName
     * @param oldDesc
     * @param newName
     * @param newDesc
     */
    public static void quickUpdateIssue(BrowserEmulator be, String oldName,
            String oldDesc, String newName, String newDesc) {
        be.click(ViewIssue.BTN_UPDATE);
        be.type(NewIssue.TXT_SUBJECT, newName);
        be.type(NewIssue.TXT_DESC, newDesc);
        be.click(UpdateIssue.BTN_COMMIT);
//        be.expectTextExistOrNot(true, "更新成功", 5000);
        RemoteWebDriver driver = be.getBrowserCore();
        WebElement element = driver.findElementByXPath(ViewIssue.TXT_SUBJECT);
        Assert.assertEquals(element.getText(), newName);
        element = driver.findElementByXPath(ViewIssue.TXT_DESC);
        Assert.assertEquals(element.getText(), newDesc);
        // 检查历史记录
        be.expectElementExistOrNot(
                true,
                "//li[contains(strong/text(),'描述') and a/@title='查看差别' and contains(a/text(),'diff')]",
                5000);
        be.expectElementExistOrNot(true, "//i[text()='" + oldName + "']", 5000);
        be.expectElementExistOrNot(true, "//i[text()='" + newName + "']", 5000);
    }

    /**
     * 快速发表一个任务评论<br>
     * 只填充评论内容并发表，无其它操作
     * 
     * @param be
     * @param comment
     */
    public static void quickNewComment(BrowserEmulator be, String comment) {
        be.click(ViewIssue.BTN_ADD_COMMIT);
        be.type(UpdateIssue.TXT_NOTES, comment);
        be.click(UpdateIssue.BTN_COMMIT);
//        be.expectTextExistOrNot(true, "更新成功", 5000);
        be.expectElementExistOrNot(true, "//p[text()='" + comment + "']", 5000);
    }

    /**
     * 编辑第一条任务评论
     * 
     * @param be
     * @param oldComment
     * @param newComment
     */
    public static void editComment(BrowserEmulator be, String oldComment,
            String newComment) {
        be.click("//a[@title='编辑']");
        be.type(UpdateIssue.TXT_NOTES, newComment);
        be.click(UpdateIssue.BTN_COMMIT);
//        be.expectTextExistOrNot(true, "更新成功", 5000);
        be.expectElementExistOrNot(true, "//p[text()='" + newComment + "']",
                5000);
        be.expectElementExistOrNot(false, "//p[text()='" + oldComment + "']",
                5000);
    }

    /**
     * 新建一个UED任务
     * 
     * @param be
     * @param issueName
     * @param issueDesc
     * @param ixD
     *            交互设计人员
     * @param vslD
     *            视觉设计人员
     * @param developer
     *            前端开发人员
     */
    public static void newUEDIssue(BrowserEmulator be, String issueName,
            String issueDesc, String ixD, String vslD, String developer) {
        be.open(GlobalVariables.HOSTNAME
                + "/issues/new_issues?showall=true&nav=111");
        be.click("UED任务");
        be.expectTextExistOrNot(true, "新建UED任务", 5000);
        be.type(NewIssue.TXT_SUBJECT, issueName);
        if (be.isElementPresent(NewIssue.TAB_WIKI_EDTR, 5000)) {
            be.click(NewIssue.TAB_WIKI_EDTR);
        }

        be.type(NewIssue.TXT_DESC, issueDesc);

        if (ixD != null) {
            be.click("//input[@id='interactive_check']");
            be.type("//input[@id='interactive_id']", ixD);
        }
        if (vslD != null) {
            be.click("//input[@id='visual_check']");
            be.type("//input[@id=' visual_id']", vslD);
        }
        if (developer != null) {
            be.click("//input[@id='dev_check']");
            be.type("//input[@id='dev_id']", developer);
        }
        be.click(NewIssue.BTN_CREATE);
//        be.expectTextExistOrNot(true, "创建成功", 5000);
        be.expectTextExistOrNot(true, "UED任务 #", 5000);
        be.expectTextExistOrNot(true, issueName, 5000);
        be.expectTextExistOrNot(true, issueDesc, 5000);
    }

    public static String newSustainingIssue(BrowserEmulator be, String product,
            String project, String issueName, String issueDesc, String auditor,
            boolean needIxD, boolean needVslD, String developer) {
        be.open(GlobalVariables.HOSTNAME
                + "/issues/new_issues?showall=true&nav=111");
        be.click(NewIssue.BTN_SUSTAIN);
        be.expectTextExistOrNot(true, "新建维护任务", 5000);

        // Select product/project
        if (project == null) {
            be.select(NewIssue.DRPLIST_PROJECT, product);
        } else {
            be.select(NewIssue.DRPLIST_PROJECT, " » " + project);
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        be.type(NewIssue.TXT_SUBJECT, issueName);
        if (be.isElementPresent(NewIssue.TAB_WIKI_EDTR, 5000)) {
            be.click(NewIssue.TAB_WIKI_EDTR);
        }
        be.type(NewIssue.TXT_DESC, issueDesc);
        if (auditor != null) {
            be.click("//input[@id='plan_check']");
            be.type("//input[@id='plan_id']", auditor);
        }
        if (needIxD) {
            be.click("//input[@id='interactive_check']");
        }
        if (needVslD) {
            be.click("//input[@id='visual_check']");
        }
        if (developer != null) {
            be.click("//input[@id='dev_check']");
            be.type("//input[@id='dev_id']", developer);
        }
        be.click("//input[@id='test_check']");
        be.click(NewIssue.BTN_CREATE);
//        be.expectTextExistOrNot(true, "创建成功", 5000);
        be.expectTextExistOrNot(true, "维护任务 #", 5000);
        be.expectTextExistOrNot(true, issueName, 5000);
        be.expectTextExistOrNot(true, issueDesc, 5000);
        String[] urls = be.getBrowserCore().getCurrentUrl().split("/");
        return urls[urls.length - 1].split("\\?")[0];
    }

    /**
     * 上传一个附件
     * 
     * @param be
     */
    public static void uploadAttachment(BrowserEmulator be,
            String uploadedFile, String fileName, String svnXpath) {
        be.click("//a[text()='上传']");
        be.expectElementExistOrNot(true, "//form[@id='attachment_upload']/div[2]/div[1]", 5000);
        if (GlobalSettings.BrowserCoreType == 1) {
            be.getJavaScriptExecutor().executeScript(
                    "$('//object[@id='SWFUpload_1']').click()");
        }
        if (GlobalSettings.BrowserCoreType == 2) {
            be.click("//object[@id='SWFUpload_1']");
        }
        typeInWindowsPop(System.getProperty("user.dir") + "\\res\\" + fileName
                + ".txt");
        typeInWindowsPop("{enter}");
        if (svnXpath != null) {
            // 上传到SVN选项
            RemoteWebDriver driver = be.getBrowserCore();
            WebElement element = driver.findElementByXPath(svnXpath);
            if ((element.isSelected()) == false) {
                // 勾选上传到SVN
                be.click("//input[@id='library_doc']");
            }
        }
        be.click("//input[@id='submit_file']");
        be.expectElementExistOrNot(true, uploadedFile, 10000);
    }

    /**
     * 删除一个附件
     * 
     * @param be
     */
    public static void deleteAttachment(BrowserEmulator be, String uploadedFile) {
        be.click("//div[@class='attachments']//a[text()='删除']");
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        be.getBrowserCore().switchTo().alert().accept();
        be.expectElementExistOrNot(false, uploadedFile, 5000);
    }

    /**
     * 在Windows弹框内输入文本
     * 
     * @param text
     */
    public static void typeInWindowsPop(String text) {
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String cmd = System.getProperty("user.dir")
                + "\\res\\SeleniumCommand.exe" + " sendKeys " + text;

        Process p = null;
        try {
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            p.destroy();
        }
    }

    /**
     * 点击流程按钮buttonText，如果有弹出框则直接确定，并检查“更新成功”文字是否出现
     * 
     * @param be
     * @param buttonText
     * @author JING, Cheng
     */
    public static String clickProcessButton(BrowserEmulator be,
            String buttonText, String leader, String requirement) {
        be.expectElementExistOrNot(true, "//div[@id='button_image']", 5000);
        // TODO: hzliunn 需求变更任务拒绝变更不能直接点击 MouseOver考虑浏览器差异不能使用 未解决
        be.mouseOver("//div[@id='button_image']");
        if ("拒绝变更".equals(buttonText)) {
            be.expectElementExistOrNot(true,
                    "//div[@id='more_opers']//a[text()='" + buttonText + "']",
                    5000);
            be.click("//div[@id='more_opers']//a[text()='" + buttonText + "']");
        } else {
            be.expectElementExistOrNot(
                    true,
                    "//div[@id='button_image']//a[text()='" + buttonText + "']",
                    5000);
            be.click("//div[@id='button_image']//a[text()='" + buttonText
                    + "']");
        }                       
        if (be.isElementPresent("//input[@id='submit_request']", 3000)) {
            be.type("//textarea[@id='submit_notes']", buttonText);
            be.click("//input[@id='submit_request']");
        }
        // 针对需求变更
        if (be.isElementPresent("//input[@id='saverequire']", 3000)) {
            be.type("//input[@id='requirement_confirmor_1']", leader);
            // 获取当前时间
            Date now = new Date();
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            String nowTime = dt.format(now);
            be.type("//input[@id='due_time_1']", nowTime);
            be.type("//textarea[@id='notes_requirement']", requirement);
            be.click("//input[@id='saverequire']");
            // FIXME: 需求变更任务通过变更 更新状态时没有“更新成功”的flash notice - jc
            // FIXME: 需求变更任务的进度条没有更新 - jc
        } else if (be.isElementPresent("//form[@id='test-form']/input", 5000)) { // 针对测试任务
            be.click("//form[@id='test-form']/input[contains(@value, '提交')]");
        }
        //无法出现“更新成功”
//        be.expectElementExistOrNot(true,
//                "//div[@class='flash notice' and text()='更新成功']", 20000);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String[] urls = be.getBrowserCore().getCurrentUrl().split("/");
        return urls[urls.length - 1].split("\\?")[0];
    }

    public static String clickProcessButton(BrowserEmulator be,
            String buttonText) {
        return clickProcessButton(be, buttonText, null, null);
    }

    /**
     * 检查当前任务流程状态为text 流程图id=issue_flow上流程显示#DD6C00的背景色
     * 
     * @param be
     * @param text
     * @author JING, Cheng
     */
    public static void expectProcessStatus(BrowserEmulator be, String text) {
        for (WebElement i : be.getBrowserCore().findElements(
                By.xpath("//div[@id='issue_flow']//span"))) {
            if (i.getText().equals(text)) {
                Assert.assertEquals(i.getCssValue("color"),
                        "rgba(221, 108, 0, 1)", "流程状态显示不正确");
            }
        }
    }

    /**
     * 检查当前任务流程状态为text 流程图id=issue_flow上流程显示#333的背景色
     * 
     * @param be
     * @param text
     * @author hzliunn
     */
    public static void unexpectProcessStatus(BrowserEmulator be, String text) {
        for (WebElement i : be.getBrowserCore().findElements(
                By.xpath("//div[@id='issue_flow']//span"))) {
            if (i.getText().equals(text)) {
                Assert.assertEquals(i.getCssValue("color"),
                        "rgba(51, 51, 51, 1)", "流程状态显示不正确");
            }
        }
    }
}
