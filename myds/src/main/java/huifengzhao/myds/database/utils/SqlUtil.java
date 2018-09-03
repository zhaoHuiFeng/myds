package huifengzhao.myds.database.utils;

import huifengzhao.myds.database.achieve.WriterAchieve;
import huifengzhao.myds.database.base.Base;
import huifengzhao.myds.database.run.read.Select;
import huifengzhao.myds.database.run.write.Insert;
import huifengzhao.myds.database.run.write.Delete;
import huifengzhao.myds.database.run.write.Update;
import huifengzhao.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author huifengzhao
 * @ClassName SqlUtil
 * @date 2018/8/30
 */
public class SqlUtil implements WriterAchieve {

    private static final transient Logger log = LoggerFactory.getLogger(Base.class);

    public static String getSelectSql(Select<?> select) {
        StringBuilder sql = new StringBuilder("select ");

        return null;
    }

    public static String getInsertSql(Insert<?> insert) {
        StringBuilder sql = new StringBuilder("insert into ");
        return null;
    }


    public static String getUpdateSql(Update<?> update) {

        return null;
    }

    public static String getRemoveSql(Delete<?> delete) {

        return null;
    }

    /**
     * 暴露出来的方法
     *
     * @param base 操作对象
     * @return 表名
     */
    @Override
    public String getTableName(Base base) {
        Class beanClass = base.getBeanClass();
        if (beanClass == null) {
            return null;
        }
        return getTableName(base, beanClass.getName());
    }

    /**
     * 获取数据库表的名称
     *
     * @param base     操作对象
     * @param beanName 实体类的名称
     * @return 表的名称
     */
    private String getTableName(Base base, String beanName) {

        String tableName = base.getTableName();
        if (!StringUtil.isEmpty(tableName)) {
            return tableName;
        }


        return null;
    }

}
