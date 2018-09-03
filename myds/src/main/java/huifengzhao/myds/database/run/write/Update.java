package huifengzhao.myds.database.run.write;

import huifengzhao.myds.database.base.WriteBase;
import huifengzhao.myds.database.run.read.Select;
import huifengzhao.myds.database.utils.DruidUtil;
import huifengzhao.myds.database.utils.SqlUtil;
import huifengzhao.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author huifengzhao
 * @ClassName Update
 * @date 2018/8/29
 */
public class Update<T> extends WriteBase<T> {

    private static final transient Logger log = LoggerFactory.getLogger(Update.class);

    private String keyColumn;
    private Object keyValue;
    private String where;

    public Update() {
    }

    public String getKeyColumn() {
        return keyColumn;
    }

    public void setKeyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
    }

    public Object getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(Object keyValue) {
        this.keyValue = keyValue;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    @Override
    public void run() {
        String runSql = this.getSql();
        if (StringUtil.isEmpty(runSql)) {
            runSql = SqlUtil.getUpdateSql(this);
        }
        Connection connection = DruidUtil.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(runSql);
        } catch (SQLException e) {
            log.error("获取update操作对象失败", e);
        } finally {
            DruidUtil.close();
        }
    }
}
