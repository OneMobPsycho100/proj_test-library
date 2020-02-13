package com.dio.frame.mybatisplus.plugin;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Properties;

/**拦截插入和修改sql,给一些通用的字段赋值
 * @Author: chenmingzhe
 * @Date: 2020/2/12 20:15
 */
@Intercepts(@Signature(type = Executor.class,method = "update",args = {MappedStatement.class,Object.class}))
public class AutoMaticGenerateInterceptor implements Interceptor {

    private static final String CREATE_DATE = "createdate";
    private static final String UPDATE_DATE = "updatedate";
    private static final String UPDATE_USER = "upduser";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object parameter = invocation.getArgs()[1];
        if (parameter == null) {
            return invocation.proceed();
        }
        String now = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String userName = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        SqlCommandType commandType = mappedStatement.getSqlCommandType();
        MetaObject metaParam = mappedStatement.getConfiguration().newMetaObject(parameter);
        if (SqlCommandType.INSERT.equals(commandType)){
            // 存在这个字段并且传入的值为空时进行自动赋值
            if (this.isHasField(CREATE_DATE,metaParam)
                    && this.getFieldValByName(CREATE_DATE,metaParam) == null) {
                Class<?> clazz = this.getFieldClzByName(CREATE_DATE,metaParam);
                if (clazz != null) {
                    if (clazz.isAssignableFrom(String.class)) {
                        this.setFieldValByName(CREATE_DATE,now,metaParam);
                    }
                }
            }
        }
        if (SqlCommandType.UPDATE.equals(commandType)) {
            setFieldValByParam(now, metaParam, UPDATE_DATE);
            setFieldValByParam(userName, metaParam, UPDATE_USER);
        }
        return invocation.proceed();
    }

    private void setFieldValByParam(String val, MetaObject metaParam, String tableField) {
        if (this.isHasField(tableField,metaParam)
                && this.getFieldValByName(tableField,metaParam) != null) {
            Class<?> clazz = this.getFieldClzByName(tableField,metaParam);
        if (clazz != null) {
            if (clazz.isAssignableFrom(String.class)){
                this.setFieldValByName(tableField,val,metaParam);
            }
        }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    /**
     * 判断是否存在指定的字段
     * @param fieldName
     * @param metaObject
     * @return
     */
    private boolean isHasField(String fieldName, MetaObject metaObject) {
        return metaObject.hasGetter(fieldName) || metaObject.hasGetter(Constants.ENTITY_DOT + fieldName);
    }

    /**
     * get value from java bean by propertyName
     * <p>
     *  如果包含前缀 et 使用该方法，否则可以直接 metaObject.setValue(fieldName, fieldVal);
     * </p>
     * @param fieldName
     * @param metaObject
     * @return 字段值
     */
    private Object getFieldValByName(String fieldName, MetaObject metaObject) {
        if (metaObject.hasGetter(fieldName)) {
            return metaObject.getValue(fieldName);
        } else if (metaObject.hasGetter(Constants.ENTITY_DOT + fieldName)) {
            return metaObject.getValue(Constants.ENTITY_DOT + fieldName);
        }
        return null;
    }

    /**
     * 获取字段值类型 如果包含前缀 et 使用该方法，否则可以直接
     *  metaObject.getSetterType(fieldName);
     * @param fieldName
     * @param metaObject
     * @return
     */
    private Class<?> getFieldClzByName(String fieldName, MetaObject metaObject) {
        if (metaObject.hasSetter(fieldName) && metaObject.hasGetter(fieldName)) {
            return metaObject.getSetterType(fieldName);
        } else if (metaObject.hasGetter(Constants.ENTITY_DOT + fieldName)) {
            Object et = metaObject.getValue(Constants.ENTITY);
            if (et != null) {
                MetaObject etMeta = SystemMetaObject.forObject(et);
                if (etMeta.hasSetter(fieldName)) {
                    return etMeta.getSetterType(fieldName);
                }
            }
        }
        return null;
    }

    /**
     * Common method to set value for java bean.
     *<p>
     * 如果包含前缀 et 使用该方法，否则可以直接 metaObject.setValue(fieldName, fieldVal);
     *</p>
     * @param fieldName
     * @param fieldVal
     * @param metaObject
     */
    private void setFieldValByName(String fieldName, Object fieldVal, MetaObject metaObject) {
        if (Objects.nonNull(fieldVal)) {
            if (metaObject.hasSetter(fieldName) && metaObject.hasGetter(fieldName)) {
                metaObject.setValue(fieldName, fieldVal);
            } else if (metaObject.hasGetter(Constants.ENTITY)) {
                Object et = metaObject.getValue(Constants.ENTITY);
                if (et != null) {
                    MetaObject etMeta = SystemMetaObject.forObject(et);
                    if (etMeta.hasSetter(fieldName)) {
                        etMeta.setValue(fieldName, fieldVal);
                    }
                }
            }
        }
    }
}
