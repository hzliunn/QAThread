package qa.netease.thread;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * qa平台项目／产品配置信息
 * 
 * @author hzjingcheng
 * 
 */
public class GlobalVariables {

	private static Logger logger = Logger.getLogger(GlobalVariables.class);
	public static final String HOSTNAME;
	public static final String OLD_HOSTNAME;
	public static final String ACCOUNT1;
	public static final String PWD;

	public static final String PRODUCT;
	public static final String[] PRODUCT_MEMBERS;
	public static final String[] PRODUCT_FOLLOWERS;
	public static final String[] PRODUCT_MANAGERS;
	public static final String[] PRODUCT_LEADERS;

	public static final String PROJECT;
	public static final String[] PROJECT_MEMBERS;
	public static final String[] PROJECT_FOLLOWERS;
	public static final String[] PROJECT_MANAGERS;
	public static final String[] PROJECT_LEADERS;
	public static final String[] PROJECT_QAS;

	public static final List<String> ADMINS;
	public static final List<String> NON_USER_ACCOUNTS;

	static {
		Properties prop = new Properties();

		// default configuration
		String hostname = "qa-lab.server.163.org";
		String oldHostname = "https://rb-10.lab.infra.mail";
		String account1 = "qa";
		String pwd = "";

		String product = "网易相册";
		String[] productMembers = new String[] { "sysadmin", "ding_jie",
				"w_li", "hzyuzr", "hznisanshan", "hzlingfei", "ye_wen",
				"situchenghao", "lvzeting" };
		String[] productFollowers = new String[] { "hzjingcheng" };
		String[] productManagers = new String[] { "zhujingbo" };
		String[] productLeaders = new String[] { "laoshukun", "hzdingyb",
				"hzliweiwei" };

		String project = "网易云相册iphone";
		String[] projectMembers = new String[] { "hzyangjian", "hzxiejunkai",
				"zhengmanman", "huangyaowu" };
		String[] projectFollowers = new String[] { "zhujingbo" };
		String[] projectManagers = new String[] { "taoran" };
		String[] projectLeaders = new String[] { "zhuliming", "shenqi",
				"wang_jie", "madiwu", "wei-wei" };
		String[] projectQas = new String[] { "lvzeting", "li_yan" };

		List<String> admins = new ArrayList<String>();
		admins.add("qianbeilei");
		admins.add("laoshukun");
		admins.add("kevinkong");
		List<String> nonUserAccounts = new ArrayList<String>();
		nonUserAccounts.add("sysadmin");

		try {
			prop.load(new FileInputStream("prop.properties"));

			if (prop.getProperty("hostname") != null) {
				hostname = prop.getProperty("hostname");
			}
			if (prop.getProperty("old_hostname") != null) {
				oldHostname = prop.getProperty("old_hostname");
			}
			if (prop.getProperty("account1") != null) {
				account1 = prop.getProperty("account1");
			}
			if (prop.getProperty("pwd") != null) {
				pwd = prop.getProperty("pwd");
			}

			if (prop.getProperty("prod1") != null) {
				product = prop.getProperty("prod1");
			}
			if (prop.getProperty("prod1_members") != null) {
				productMembers = prop.getProperty("prod1_members").trim()
						.split("\\W*[,;]\\W*");
			}
			if (prop.getProperty("prod1_followers") != null) {
				productFollowers = prop.getProperty("prod1_followers").trim()
						.split("\\W*[,;]\\W*");
			}
			if (prop.getProperty("prod1_leaders") != null) {
				productLeaders = prop.getProperty("prod1_leaders").trim()
						.split("\\W*[,;]\\W*");
			}
			if (prop.getProperty("prod1_prodmgrs") != null) {
				productManagers = prop.getProperty("prod1_prodmgrs").trim()
						.split("\\W*[,;]\\W*");
			}

			if (prop.getProperty("prod1_proj1") != null) {
				project = prop.getProperty("prod1_proj1");
			}
			if (prop.getProperty("prod1_proj1_members") != null) {
				projectMembers = prop.getProperty("prod1_proj1_members").trim()
						.split("\\W*[,;]\\W*");
			}
			if (prop.getProperty("prod1_proj1_qas") != null) {
				projectQas = prop.getProperty("prod1_proj1_qas").trim()
						.split("\\W*[,;]\\W*");
			}
			if (prop.getProperty("prod1_proj1_followers") != null) {
				projectFollowers = prop.getProperty("prod1_proj1_followers")
						.trim().split("\\W*[,;]\\W*");
			}
			if (prop.getProperty("prod1_proj1_projmgrs") != null) {
				projectManagers = prop.getProperty("prod1_proj1_projmgrs")
						.trim().split("\\W*[,;]\\W*");
			}
			if (prop.getProperty("prod1_proj1_leaders") != null) {
				projectLeaders = prop.getProperty("prod1_proj1_leaders").trim()
						.split("\\W*[,;]\\W*");
			}
			if (prop.getProperty("admins") != null) {
				admins.clear();
				for (String i : prop.getProperty("admins").trim()
						.split("\\W*[,;]\\W*")) {
					admins.add(i);
				}
			}
			if (prop.getProperty("non_user_accounts") != null) {
				nonUserAccounts.clear();
				for (String i : prop.getProperty("non_user_accounts").trim()
						.split("\\W*[,;]\\W*")) {
					nonUserAccounts.add(i);
				}
			}

		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		HOSTNAME = hostname;
		OLD_HOSTNAME = oldHostname;
		ACCOUNT1 = account1;
		PWD = pwd;

		PRODUCT = product;
		PRODUCT_FOLLOWERS = productFollowers;
		PRODUCT_MEMBERS = productMembers;
		PRODUCT_MANAGERS = productManagers;
		PRODUCT_LEADERS = productLeaders;

		PROJECT = project;
		PROJECT_MEMBERS = projectMembers;
		PROJECT_FOLLOWERS = projectFollowers;
		PROJECT_MANAGERS = projectManagers;
		PROJECT_LEADERS = projectLeaders;
		PROJECT_QAS = projectQas;

		ADMINS = admins;
		NON_USER_ACCOUNTS = nonUserAccounts;
	}

	/**
	 * Get a random, real project member.
	 * 
	 * @return alias
	 */
	public static String getRandProjectMember() {
		return getRandProjectMember(new ArrayList<String>());
	}

	/**
	 * Get a random, real project member, exclude system administrator and those
	 * in given list.
	 * 
	 * @param exclude
	 * @return alias
	 */
	public static String getRandProjectMember(List<String> exclude) {
		return getRandMember(PROJECT_MEMBERS, exclude);
	}

	/**
	 * Get a random, real project leader.
	 * 
	 * @return alias
	 */
	public static String getRandProjectLeader() {
		return getRandMember(PROJECT_LEADERS, new ArrayList<String>());
	}

	/**
	 * Get a random, real project leader, exclude system administrator and those
	 * in given list.
	 * 
	 * @param exclude
	 * @return alias
	 */
	public static String getRandProjectLeader(List<String> exclude) {
		return getRandMember(PROJECT_LEADERS, exclude);
	}

	/**
	 * Get a random, real project manager.
	 * 
	 * @return
	 */
	public static String getRandProjectManager() {
		return getRandMember(PROJECT_MANAGERS, new ArrayList<String>());
	}

	/**
	 * Get a random, real product manager.
	 * 
	 * @return
	 */
	public static String getRandProductManager() {
		return getRandMember(PRODUCT_MANAGERS, new ArrayList<String>());
	}

	/**
	 * Get a random, real admin.
	 * 
	 * @return
	 */
	public static String getRandAdmin() {
		return getRandAdmin(ADMINS, new ArrayList<String>());
	}

	private static String getRandMember(String[] source, List<String> exclude) {
		List<String> all = new ArrayList<String>();
		for (String i : source) {
			// Neither administrator nor non_user account
			if ((!NON_USER_ACCOUNTS.contains(i)) && (!ADMINS.contains(i))
					&& (!exclude.contains(i))) {
				all.add(i);
				logger.debug("Project member added: " + i);
			}
		}
		if (all.size() == 0) {
			return null;
		} else {
			Random rand = new Random();
			return all.get(rand.nextInt(all.size()));
		}
	}

	private static String getRandAdmin(List<String> source, List<String> exclude) {
		List<String> all = new ArrayList<String>();
		for (String i : source) {
			if (ADMINS.contains(i)) {
				all.add(i);
				logger.debug("Admin added: " + i);
			}
		}
		if (all.size() == 0) {
			return null;
		} else {
			Random rand = new Random();
			return all.get(rand.nextInt(all.size()));
		}
	}

	/**
	 * 
	 * @return
	 */
	public static String getRandProjectFollower() {
		return getRandMember(PROJECT_FOLLOWERS, new ArrayList<String>());
	}

	public static String getRandProjectQAS() {
		return getRandMember(PROJECT_QAS, new ArrayList<String>());
	}

	public static String getProjectName() {
		return PROJECT;
	}
}
