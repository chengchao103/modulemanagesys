package com.chinamobile.iot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * 读取主配置文件参数
 *
 * @author dds
 */
public class MainConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainConfig.class);
    private static Properties props;

    /**
     * 初始化配置文件到内存
     */
    private static void init(){
        InputStream is = MainConfig.class.getResourceAsStream("/config/main.properties");
        props = new Properties();
        try {
            props.load(is);
        }
        catch (Exception e) {
            LOGGER.error("不能读取属性文件. 请确保main.properties在CLASSPATH指定的路径中");
        }
    }

    /**
     * 获取字符串类型的参数值
     *
     * @param name 参数名字
     * @return 参数值
     */
    public static String getStringProperty(String name) {
        if (props == null) {
            init();
        }
        return props.getProperty(name);

    }

    /**
     * 获取长整型类型的参数值
     *
     * @param name 参数名字
     * @return 参数值
     */
    public static long getLongProperty(String name) {

        String value = getStringProperty(name);
        long longvalue = 0;
        if (value != null) {
            longvalue = Long.parseLong(value);
        } else {
            LOGGER.error("不能读取属性" + name + ". 请确保该属性已配置");
        }
        return longvalue;

    }


}
