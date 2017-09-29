package com.alibaba.dingtalk.openapi.springbootdemo.config.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
public class DatasourceConfig {
    @Primary
    @ConfigurationProperties("spring.datasource.druid.readwrite")
    @Bean
    public DataSource dataSource() {
        DruidDataSource druidDataSource = DruidDataSourceBuilder.create().build();
        druidDataSource.setProxyFilters(Arrays.asList(wallFilter()));
        return druidDataSource;
    }

    @ConfigurationProperties("spring.datasource.druid.readonly")
    @Bean
    public DataSource dataSourceReadOnly() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    public WallFilter wallFilter(){
        WallFilter wallFilter = new WallFilter();
        wallFilter.setConfig(wallConfig());
        return wallFilter;
    }

    @Bean
    public WallConfig wallConfig(){
        WallConfig wallConfig = new WallConfig();
        wallConfig.setMultiStatementAllow(true);
        return wallConfig;
    }
}
