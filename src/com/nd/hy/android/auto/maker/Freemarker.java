package com.nd.hy.android.auto.maker;

import com.nd.hy.android.auto.config.PathConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * Author liangbx
 * Date 2015/8/28
 */
public class Freemarker {

    private static Freemarker mfreemarker;
    private Configuration cfg = null;

    public static synchronized Freemarker getInstance() {
        if(null == mfreemarker) {
            mfreemarker = new Freemarker();
        }

        return mfreemarker;
    }

    private Freemarker() {
        init();
    }

    //初始化工作
    public void init(){
        cfg = new Configuration();
        cfg.setDefaultEncoding("utf-8");
        //设置模板文件位置
        try {
            cfg.setDirectoryForTemplateLoading(new File(PathConfig.TEMPLATE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置模板的加载目录
     * @param file
     * @throws IOException
     */
    public void setDirectoryForTemplateLoading(File file) throws IOException {
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
