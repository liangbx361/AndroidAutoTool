package com.nd.hy.android.auto.config;

public class PathConfig {

	/**
	 * 工程配置文件路径
	 */
	public static final String PROJECT_CONFIG_PATH = "auto/";
	
	/**
	 * 工程配置文件名称
	 */
	public static final String PROJECT_CONFIG_NAME = "projectConfig.xml";
	
	
	public static final String TEMPLATE_PATH = "src/com/nd/hy/android/auto/template";
	
	/**
	 * Activity 模板目录
	 */
	public static final String ACTIVITY_PATH = "templete/activity/";
	
	/**
	 * Volley 样式 Activity模板目录
	 */
	public static final String VOLLEY_ACTIVITY_PATH = ACTIVITY_PATH + "volley/";
	
	/**
	 * AsyncTask 样式Activity模板目录
	 */
	public static final String TASK_ACTIVITY_PATHC = ACTIVITY_PATH + "task/";
	
	/**
	 * Volley 框架请求模板目录
	 */
	public static final String NET_VOLLEY = "template/net/volley/";
	
	/**
	 * Task 框架请求模板目录
	 */
	public static final String NET_TASK = "template/net/task/";

	public static final String MODEL_PATH = "gen/model";
		
	public static String getTaskPath(String type) {
		if(type.equals("Volley")) {
			return NET_VOLLEY;
		} else {
			return NET_TASK;
		}
	}
}
