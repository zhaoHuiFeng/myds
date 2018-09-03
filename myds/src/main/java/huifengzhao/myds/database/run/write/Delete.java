package huifengzhao.myds.database.run.write;

import huifengzhao.myds.database.base.WriteBase;
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
 * @ClassName Delete
 * @date 2018/8/29
 */
public class Delete<T> extends WriteBase<T> {

    private static final transient Logger log = LoggerFactory.getLogger(Delete.class);

    private String ids;
    private String where;
    private Delete.Type type;

    @Override
    public void run() {
        String runSql = this.getSql();
        if (StringUtil.isEmpty(runSql)) {
            runSql = SqlUtil.getRemoveSql(this);
        }
        Connection connection = DruidUtil.getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(runSql);
            System.out.println();
        } catch (SQLException e) {
            log.error("获取update操作对象失败", e);
        } finally {
            DruidUtil.close();
        }
    }

    public static enum Type {
        /**
         * 删除状态
         * delete彻底删除
         * recovery恢复
         * remove修改状态为删除状态
         */
        delete,
        recovery,
        remove;

        private Type() {
        }
    }
}
