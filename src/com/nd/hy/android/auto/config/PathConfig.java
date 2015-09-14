package com.nd.hy.android.auto.config;

public class PathConfig {

	/**
	 * ���������ļ�·��
	 */
	public static final String PROJECT_CONFIG_PATH = "auto/";
	
	/**
	 * ���������ļ����
	 */
	public static final String PROJECT_CONFIG_NAME = "projectConfig.xml";
	
	
	public static final String TEMPLATE_PATH = "../template/";
	
	/**
	 * Activity ģ��Ŀ¼
	 */
	public static final String ACTIVITY_PATH = "templete/activity/";
	
	/**
	 * Volley ��ʽ Activityģ��Ŀ¼
	 */
	public static final String VOLLEY_ACTIVITY_PATH = ACTIVITY_PATH + "volley/";
	
	/**
	 * AsyncTask ��ʽActivityģ��Ŀ¼
	 */
	public static final String TASK_ACTIVITY_PATHC = ACTIVITY_PATH + "task/";
	
	/**
	 * Volley �������ģ��Ŀ¼
	 */
	public static final String NET_VOLLEY = "template/net/volley/";
	
	/**
	 * Task �������ģ��Ŀ¼
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
