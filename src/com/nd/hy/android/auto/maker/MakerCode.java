package com.nd.hy.android.auto.maker;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import com.nd.hy.android.auto.config.PathConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;


public class MakerCode {

	private Configuration cfg = null;

	public MakerCode() throws Exception {
		init();
	}

	//初始化工作
	public void init() throws Exception {
		cfg = new Configuration();
		cfg.setDefaultEncoding("utf-8");
		//设置模板文件位置
		cfg.setDirectoryForTemplateLoading(new File(PathConfig.TEMPLATE_PATH));
	}

	//设置目录
	public void setDir(File file) throws Exception {
		cfg.setDirectoryForTemplateLoading(file);
	}

	//模板 + 数据模型 = 输出
	public void process(Map<String, Object> rootMap, String templete, File file) throws Exception {
		//使用Configuration实例加载指定模板
		Template template = cfg.getTemplate(templete);
		//合并处理（模板 + 数据模型）
		template.process(rootMap, new FileWriter(file));
	}
}
