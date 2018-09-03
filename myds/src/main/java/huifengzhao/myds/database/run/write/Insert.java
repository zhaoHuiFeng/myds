package huifengzhao.myds.database.run.write;

import huifengzhao.myds.database.base.WriteBase;
import huifengzhao.myds.database.run.read.Select;
import huifengzhao.myds.database.utils.ConvertUtil;
import huifengzhao.myds.database.utils.DruidUtil;
import huifengzhao.myds.database.utils.SqlUtil;
import huifengzhao.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author huifengzhao
 * @ClassName Insert
 * @date 2018/8/29
 */
public class Insert<T> extends WriteBase<T> {

    private static final transient Logger log = LoggerFactory.getLogger(Insert.class);
    private List<T> beanList;

    public Insert() {
    }

    public Insert(Connection connection) {
        super(connection);
        this.setThrows(true);
    }

    public Insert(T bean) {
        super(bean);
    }

    public Insert(List<T> beanList) {
        super((T) null);
        this.beanList = beanList;
    }

    public Insert(T data, boolean isThrows) {
        super(data);
        this.setThrows(isThrows);
    }

    public Insert(List<T> beanList, boolean isThrows) {
        super((T) null);
        this.beanList = beanList;
        this.setThrows(isThrows);
    }

    public List<T> getBeanList() {
        return beanList;
    }

    public void setBeanList(List<T> beanList) {
        this.beanList = beanList;
    }

    @Override
    public void run() {
        String runSql = this.getSql();
        if (StringUtil.isEmpty(runSql)) {
            runSql = SqlUtil.getInsertSql(this);
        }
        Connection connection = DruidUtil.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.execute(runSql);
        } catch (SQLException e) {
            log.error("获取insert操作对象失败", e);
        } finally {
            DruidUtil.close();
        }
    }

}
