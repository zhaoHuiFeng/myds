package huifengzhao.myds.database.base;

import huifengzhao.myds.utils.DbReflectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;

/**
 * @author huifengzhao
 * @ClassName Base
 * @date 2018/8/30
 */
public class Base<T> {

    private static final transient Logger log = LoggerFactory.getLogger(Base.class);
    protected String sql;
    private boolean isThrows;
    private Class<?> beanClass;
    private String tableName;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * 获取当前抛出异常的状态
     *
     * @return 抛不抛异常
     */
    public boolean isThrows() {
        return this.isThrows;
    }

    /**
     * 设置抛出异常的状态
     *
     * @param isThrows 抛出异常的状态
     */
    public void setThrows(boolean isThrows) {
        this.isThrows = isThrows;
    }

    /**
     * 判断是否抛出异常,需要抛出异常就抛,不需要的就记录一下日志就行了
     *
     * @param t 异常
     */
    protected void isThrows(Throwable t) {
        if (this.isThrows) {
            throw new RuntimeException(t);
        } else {
            log.error("执行数据库操作异常", t);
        }
    }

    /**
     * 获取bean类型的class
     *
     * @return bean类的class
     */
    public Class<?> getBeanClass() {
        return getBeanClass(true);
    }

    /**
     * 获取bean类型的class
     *
     * @param getRef ...
     * @return class对象
     */
    public Class<?> getBeanClass(boolean getRef) {
        if (beanClass == null && getRef) {
            beanClass = DbReflectUtil.getBeanClass(getClass());
        }
        return beanClass;
    }

    /**
     * 设置bean类型的class
     *
     * @param beanClass 具体的class类型
     */
    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 回收的方法
     */
    protected void recycling() {

    }
}
