package com.cmdc.nge.commonmq.core.utils;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author wangxing
 * @create 2020/4/8
 */
public class SpringContextUtils {

    public static ConfigurableApplicationContext getApplicationContext() {
        return XyatticSpringApplicationRunListener.getApplicationContext();
    }

    public static Environment getEnvironment() {
        return getApplicationContext().getEnvironment();
    }

}