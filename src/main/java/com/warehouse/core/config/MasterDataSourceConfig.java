//package com.warehouse.core.config;
//
//import com.github.pagehelper.PageInterceptor;
//import com.warehouse.common.util.MybatisInterceptor;
//import com.zaxxer.hikari.HikariDataSource;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.DefaultResourceLoader;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import javax.sql.DataSource;
//
///**
// * @Description:
// * @Author: gaojian@doctorwork.com
// * @Date: 2021/08/10 15:24
// */
//@Configuration
//@MapperScan(basePackages = "com.warehouse.dal.mapper", sqlSessionFactoryRef = "masterSqlSessionFactory")
//public class MasterDataSourceConfig {
//
//    public static final String CLASSPATH_MAPPER_MASTER_XML = "classpath*:sqlmap/**/*Mapper.xml";
//
//    // 默认数据源
//    @Bean(name = "masterDataSource")
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource.master")
//    public HikariDataSource masterDataSource() {
//        return DataSourceBuilder.create().type(HikariDataSource.class).build();
//    }
//
//    @Bean(name = "masterSqlSessionFactory")
//    @Primary
//    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource datasource)
//        throws Exception {
//        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//        bean.setDataSource(datasource);
//        bean.setMapperLocations(
//            // 设置mybatis的xml所在位置
//            new PathMatchingResourcePatternResolver().getResources(CLASSPATH_MAPPER_MASTER_XML));
//        bean.setConfigLocation(new DefaultResourceLoader().getResource(""));
//        bean.setPlugins(new MybatisInterceptor(), new PageInterceptor());
//        return bean.getObject();
//    }
//
//    @Bean(name = "masterSqlSessionTemplate")
//    @Primary
//    public SqlSessionTemplate masterSqlSessionTemplate(
//        @Qualifier("masterSqlSessionFactory") SqlSessionFactory sessionfactory) {
//        return new SqlSessionTemplate(sessionfactory);
//    }
//
//    @Bean(name = "masterTransactionManager")
//    @Primary
//    public DataSourceTransactionManager transactionManagerPrimary(
//        @Qualifier("masterDataSource") DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//
//}
