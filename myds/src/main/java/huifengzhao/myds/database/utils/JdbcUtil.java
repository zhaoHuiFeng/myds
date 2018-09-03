package huifengzhao.myds.database.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * @author huifengzhao
 * @ClassName JdbcUtil
 * @date 2018/8/30
 */
public class JdbcUtil {

    private static final transient Logger log = LoggerFactory.getLogger(JdbcUtil.class);

    /**
     * 查询的方法
     *
     * @param conn       数据库连接
     * @param sql        sql
     * @param parameters 返回的结果集
     * @return 所有的结果集
     */
    public static List<Map<String, Object>> executeQuery(Connection conn, String sql, List<Object> parameters) {
        List<Map<String, Object>> rows = new ArrayList<>(0);
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            setParameters(ps, parameters);
            rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                for (int i = 1; i < metaData.getColumnCount(); i++) {
                    String columName = metaData.getColumnLabel(i);
                    Object value = rs.getObject(i);
                    row.put(columName, value);
                }
                rows.add(row);
            }
        } catch (SQLException e) {
            log.error("PreparedStatement对象操作失败,操作sql:" + sql, e);
        } finally {
            close(rs);
            close(ps);
        }
        return rows;
    }

    /**
     * 查询的方法
     *
     * @param dataSource 数据源
     * @param sql        sql
     * @param parameters 结果集
     * @return 所有的结果集
     */
    public static List<Map<String, Object>> executeQuery(DataSource dataSource, String sql, List<Object> parameters) throws SQLException {
        Connection connection = null;
        List<Map<String, Object>> results;
        try {
            connection = dataSource.getConnection();
            results = executeQuery(connection, sql, parameters);
        } finally {
            close(connection);
        }
        return results;
    }

    /**
     * 查询的方法
     *
     * @param dataSource 数据源
     * @param sql        sql
     * @param parameters 结果集
     * @return 所有的结果集
     * @throws SQLException sql异常,抛出的原因是为了能够准确的定位到是谁在使用的时候产生的异常
     */
    public static List<Map<String, Object>> executeQuery(DataSource dataSource, String sql, Object... parameters) throws SQLException {
        return executeQuery(dataSource, sql, Arrays.asList(parameters));
    }

    /**
     * PreparedStatement是Statement的子类,PreparedStatement对象预编译过,执行速度要快于Statement
     *
     * @param preparedStatement 操作增删改查的对象
     * @param parameters        结果集
     * @throws SQLException sql异常
     */
    private static void setParameters(PreparedStatement preparedStatement, List<Object> parameters) throws SQLException {
        for (int i = 0; i < parameters.size(); i++) {
            Object param = parameters.get(i);
            preparedStatement.setObject(i + 1, param);
        }
    }

    /**
     * 关闭Statement的连接
     *
     * @param statement 操作增删改查的对象
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (Exception e) {
                log.debug("close statement error", e
                );
            }

        }
    }

    /**
     * 关闭ResultSet的连接
     *
     * @param resultSet 操作返回结果集的对象
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (Exception e) {
                log.debug("close result set error", e);
            }

        }
    }

    /**
     * 关闭连接
     *
     * @param connection 连接对象
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                log.debug("close connection error", e);
            }

        }
    }
}
