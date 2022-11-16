package edu.vanderbilt.vandycourseplanner.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class MyBatisConfig {

    @Resource(name = "dataSource")
    private DataSource dataSource;

    @Bean
    @ConfigurationProperties(prefix = "mybatis-plus")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        // Manually inject sqlSessionFactory due to deployment errors
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        // Manually inject MyBatis-Plus config due to auto-injection errors
        MybatisConfiguration mybatisConfiguration = new MybatisConfiguration();
        mybatisConfiguration.setMapUnderscoreToCamelCase(false);
        sqlSessionFactoryBean.setConfiguration(mybatisConfiguration);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:mapper/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("edu.vanderbilt.vandycourseplanner.pojo");

        return sqlSessionFactoryBean.getObject();
    }

}
