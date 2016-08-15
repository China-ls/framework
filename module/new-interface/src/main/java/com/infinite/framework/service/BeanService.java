package com.infinite.framework.service;

import org.springframework.beans.factory.config.BeanDefinition;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface BeanService {

    public <T> Object getBean(String beanName);

    public void registerBean(String beanName, BeanDefinition beanDefinition);

    public void removeBean(String beanName);

}
