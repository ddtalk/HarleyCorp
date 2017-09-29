package com.alibaba.dingtalk.openapi.springbootdemo.config;

import com.dingtalk.open.client.DefaultConfig;
import com.dingtalk.open.client.ServiceFactory;
import com.dingtalk.open.client.api.service.corp.*;
import com.dingtalk.open.client.api.service.isv.IsvService;
import com.dingtalk.open.client.common.SdkInitException;
import com.dingtalk.open.client.spring.DingOpenClientApiBean;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * <bean id="defaultConfig" class="com.dingtalk.open.client.DefaultConfig">
 * <property name="apiBasePackage" value="com.dingtalk.open.client.api"><!--存放API代码的包路径-->
 * <property name="globalHttpConfig"><!--全局配置-->
 * <bean class="com.dingtalk.open.client.DefaultConfig.HttpConfig">
 * <property name="readTimeout" value="30000" /><!--发出http请求后,在收到响应前等待的最长时间,超过等待时间则抛出服务器没有返回数据的异常,单位毫秒-->
 * <property name="connectTimeout" value="3000" /><!--建立http连接最长等待时间,超过等待时间则抛出连接建立失败的异常,单位毫秒-->
 * </bean>
 * </property>
 * <property name="folwControl"><!--流控选项-->
 * <bean class="com.dingtalk.open.client.DefaultConfig.FolwControl">
 * <property name="isUse" value="true" /><!--是否启用流控,默认启用-->
 * <property name="maxQpm" value="1500" /><!--流控开启时,每分钟发送请求的最大次数-->
 * <property name="threadPoolSize" value="4" /><!--从流控队列读取任务的线程池中线程的数量,默认4-->
 * </bean>
 * </property>
 * <property name="customHttpConfig"><!--自定义配置项,优先级高于全局配置-->
 * <map>
 * <entry key="/service/get_suite_token"><!--服务端接口-->
 * <bean class="com.dingtalk.open.client.DefaultConfig.HttpConfig">
 * <property name="requestTimeout" value="1000" /><!--在服务器返回全部结果前等待的最长时间,单位毫秒-->
 * </bean>
 * </entry>
 * </map>
 * </property>
 * </bean>
 * <bean id="serviceFactory"
 * class="com.dingtalk.open.client.spring.DingOpenClientServiceFactoryBean">
 * <property name="defaultConfig" ref="defaultConfig" />
 * </bean>
 * <p>
 * <bean id="corpConnectionService" class="com.dingtalk.open.client.spring.DingOpenClientApiBean">
 * <property name="serviceFactory" ref="serviceFactory" />
 * <property name="interfaceName"
 * value="com.dingtalk.open.client.api.service.corp.CorpConnectionService" />
 * </bean>
 * <bean id="corpUserService" class="com.dingtalk.open.client.spring.DingOpenClientApiBean">
 * <property name="serviceFactory" ref="serviceFactory" />
 * <property name="interfaceName"
 * value="com.dingtalk.open.client.api.service.corp.CorpUserService" />
 * </bean>
 */

@Configuration
public class DTalkOpenApiConfig {

    @Bean
    public Map<String, DefaultConfig.HttpConfig> oaCustomHttpConfig() {
        String key = "/service/get_suite_token";
        DefaultConfig.HttpConfig httpConfig = new DefaultConfig.HttpConfig();
        httpConfig.setConnectTimeout(3000);
        httpConfig.setReadTimeout(3000);
        httpConfig.setMaxRequestRetry(0);
        httpConfig.setRequestTimeout(3000);
        //httpConfig.
        HashMap<String, DefaultConfig.HttpConfig> map = Maps.newHashMap();
        map.put(key, httpConfig);
        return map;
    }

    @Bean
    public DefaultConfig.HttpConfig oaGlobalHttpConfig() {
        DefaultConfig.HttpConfig httpConfig = new DefaultConfig.HttpConfig();
        httpConfig.setConnectTimeout(3000);
        httpConfig.setReadTimeout(3000);
        httpConfig.setMaxRequestRetry(0);
        httpConfig.setRequestTimeout(3000);
        return httpConfig;
    }

    @Bean
    public DefaultConfig.FolwControl folwControl() {
        DefaultConfig.FolwControl folwControl = new DefaultConfig.FolwControl();
        folwControl.setIsUse(true);
        folwControl.setMaxQpm(1500);
        folwControl.setThreadPoolSize(4);
        return folwControl;
    }

    @Bean
    public DefaultConfig defaultConfig(@Qualifier("oaGlobalHttpConfig") DefaultConfig.HttpConfig oaGlobalHttpConfig,
                                       DefaultConfig.FolwControl folwControl,
                                       @Qualifier("oaCustomHttpConfig") Map<String, DefaultConfig.HttpConfig> oaCustomHttpConfig) {
        DefaultConfig defaultConfig = new DefaultConfig();
        defaultConfig.setApiBasePackage("com.dingtalk.open.client.api");
        defaultConfig.setGlobalHttpConfig(oaGlobalHttpConfig);
        defaultConfig.setFolwControl(folwControl);
        defaultConfig.setCustomHttpConfig(oaCustomHttpConfig);
        return defaultConfig;
    }

    @Bean
    public ServiceFactory serviceFactory(DefaultConfig defaultConfig) throws SdkInitException {
        return ServiceFactory.getInstance(defaultConfig);
    }


    @Bean
    public DingOpenClientApiBean corpUserService(ServiceFactory serviceFactory) throws Exception {
        DingOpenClientApiBean apiBean = new DingOpenClientApiBean();
        apiBean.setServiceFactory(serviceFactory);
        apiBean.setInterfaceName(CorpUserService.class.getName());
        return apiBean;
    }

    @Bean
    public DingOpenClientApiBean corpConnectionService(ServiceFactory serviceFactory) throws Exception {
        DingOpenClientApiBean apiBean = new DingOpenClientApiBean();
        apiBean.setServiceFactory(serviceFactory);
        apiBean.setInterfaceName(CorpConnectionService.class.getName());
        return apiBean;
    }

    @Bean
    public DingOpenClientApiBean jsapiService(ServiceFactory serviceFactory) throws Exception {
        DingOpenClientApiBean apiBean = new DingOpenClientApiBean();
        apiBean.setServiceFactory(serviceFactory);
        apiBean.setInterfaceName(CorpConnectionService.class.getName());
        return apiBean;
    }

    @Bean
    public DingOpenClientApiBean ssoService(ServiceFactory serviceFactory) throws Exception {
        DingOpenClientApiBean apiBean = new DingOpenClientApiBean();
        apiBean.setServiceFactory(serviceFactory);
        apiBean.setInterfaceName(SsoService.class.getName());
        return apiBean;
    }

    @Bean
    public DingOpenClientApiBean isvService(ServiceFactory serviceFactory) throws Exception {
        DingOpenClientApiBean apiBean = new DingOpenClientApiBean();
        apiBean.setServiceFactory(serviceFactory);
        apiBean.setInterfaceName(IsvService.class.getName());
        return apiBean;
    }

    @Bean
    public DingOpenClientApiBean corpDepartmentService(ServiceFactory serviceFactory) throws Exception {
        DingOpenClientApiBean apiBean = new DingOpenClientApiBean();
        apiBean.setServiceFactory(serviceFactory);
        apiBean.setInterfaceName(CorpDepartmentService.class.getName());
        return apiBean;
    }

    @Bean
    public DingOpenClientApiBean callBackService(ServiceFactory serviceFactory) throws Exception {
        DingOpenClientApiBean apiBean = new DingOpenClientApiBean();
        apiBean.setServiceFactory(serviceFactory);
        apiBean.setInterfaceName(CallBackService.class.getName());
        return apiBean;
    }

    @Bean
    public DingOpenClientApiBean mediaService(ServiceFactory serviceFactory) throws Exception {
        DingOpenClientApiBean apiBean = new DingOpenClientApiBean();
        apiBean.setServiceFactory(serviceFactory);
        apiBean.setInterfaceName(MediaService.class.getName());
        return apiBean;
    }

    @Bean
    public DingOpenClientApiBean messageService(ServiceFactory serviceFactory) throws Exception {
        DingOpenClientApiBean apiBean = new DingOpenClientApiBean();
        apiBean.setServiceFactory(serviceFactory);
        apiBean.setInterfaceName(MessageService.class.getName());
        return apiBean;
    }
}
