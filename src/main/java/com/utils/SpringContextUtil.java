package com.utils;

import com.jobs.elastic.ElasticClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/11.
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext; // Spring应用上下文环境
    /*
     * 实现了ApplicationContextAware 接口，必须实现该方法；
     *通过传递applicationContext参数初始化成员变量applicationContext
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class clazz) throws BeansException {
        return (T) applicationContext.getBean(clazz);
    }

    /*
     * 动态注册方法
     */
    public static void autoRegisterBean(HashMap map,String beanId,Class clazz){
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
        //创建bean信息.
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        Iterator entries = map.entrySet().iterator();

        while (entries.hasNext()) {

            Map.Entry entry = (Map.Entry) entries.next();

            String key = String.valueOf(entry.getKey());

            String value = String.valueOf(entry.getValue());

            System.out.println("key:"+key +"  value:"+value);

            beanDefinitionBuilder.addPropertyValue(key,value);

        }
        beanDefinitionBuilder.setSingleton(true);
        //动态注册bean.
        beanFactory.registerBeanDefinition(beanId,beanDefinitionBuilder.getBeanDefinition());

    }

}