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

    //��ʼ������
    public void init(){
        cfg = new Configuration();
        cfg.setDefaultEncoding("utf-8");
        //����ģ���ļ�λ��
        try {
            cfg.setDirectoryForTemplateLoading(new File(PathConfig.TEMPLATE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ����ģ��ļ���Ŀ¼
     * @param file
     * @throws IOException
     */
    public void setDirectoryForTemplateLoading(File file) throws IOException {
        cfg.setDirectoryForTemplateLoading(file);
    }

    //ģ�� + ����ģ�� = ���
    public void process(Map<String, Object> rootMap, String templete, File file) throws Exception {
        //ʹ��Configurationʵ������ָ��ģ��
        Template template = cfg.getTemplate(templete);
        //�ϲ�����ģ�� + ����ģ�ͣ�
        template.process(rootMap, new FileWriter(file));
    }
}
