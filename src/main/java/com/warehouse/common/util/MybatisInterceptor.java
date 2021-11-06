package com.warehouse.common.util;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;

import static com.warehouse.common.util.DateUtil.DATE_TIME;

/**
 * @Description:
 * @Author: gaojian@doctorwork.com
 * @Date: 2021/03/04 18:55
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class,
                ResultHandler.class, CacheKey.class, BoundSql.class})})
@SuppressWarnings({"unchecked", "rawtypes"})
public class MybatisInterceptor implements Interceptor {
    protected Logger logger = LoggerFactory.getLogger("mybatis-whole");
    private static final Integer NUMBER_5 = 5;

    /**
     * @param invocation 代理对象，被监控方法对象，当前被监控方法运行时需要的实参
     * @Return:
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object proceed = invocation.proceed();
        try {
            // 获取xml中的一个select/update/insert/delete节点，是一条SQL语句
            MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
            BoundSql boundSql = null;
            // 获取参数，if语句成立，表示sql语句有参数，参数格式是map形式
            if (invocation.getArgs().length > 1) {
                if (invocation.getArgs().length >= NUMBER_5) {
                    boundSql = (BoundSql) invocation.getArgs()[NUMBER_5];
                } else {
                    Object parameter = invocation.getArgs()[1];
                    boundSql = mappedStatement.getBoundSql(parameter);
                }
            }
            // 获取到节点的id,即sql语句的id
            String sqlId = mappedStatement.getId();
            Configuration configuration = mappedStatement.getConfiguration();
            String sql = getSql(configuration, boundSql, sqlId);
            logger.debug(sql);
        } catch (Exception e) {
            // 不影响业务执行不改变原有的sql执行过程
            logger.error("MybatisInterceptor 补全SQL失败，原因 {}", e.getMessage());
        }
        return proceed;
    }

    /**
     * @Description: 封装SQL
     * @return: SQL语句
     **/
    public static String getSql(Configuration configuration, BoundSql boundSql, String sqlId) {
        String sql = showSql(configuration, boundSql);
        return sqlId + "|" + sql;
    }

    /**
     * @Description: 如果参数是String，则添加单引号， 如果是日期，则转换为时间格式器并加单引号； 对参数是null和不是null的情况作了处理
     * @return:
     **/
    private static String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = new SimpleDateFormat(DATE_TIME);
            value = "'" + formatter.format(obj) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }

    /**
     * @Description: 进行问号处理
     * @return: 替换后的SQL
     **/
    public static String showSql(Configuration configuration, BoundSql boundSql) {
        // 获取参数
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // sql语句中多个空格都用一个空格代替
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings != null && parameterMappings.size() > 0 && parameterObject != null) {
            // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            // 如果根据parameterObject.getClass(）可以找到对应的类型，则替换
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(parameterObject)));

            } else {
                // MetaObject主要是封装了originalObject对象，提供了get和set的方法用于获取和设置originalObject的属性值,
                // 主要支持对JavaBean、Collection、Map三种类型对象的操作
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        // 该分支是动态sql
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", Matcher.quoteReplacement(getParameterValue(obj)));
                    } else {
                        // 打印出缺失，提醒该参数缺失并防止错位
                        sql = sql.replaceFirst("\\?", "缺失");
                    }
                }
            }
        }
        return sql;
    }

    /**
     * @param target 表示被拦截的对象，此处为 Executor 的实例对象 作用：如果被拦截对象所在的类有实现接口，就为当前拦截对象生成一个代理对象
     *               如果被拦截对象所在的类没有指定接口，这个对象之后的行为就不会被代理操作
     * @Return:
     */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties0) {

    }
}
