package huifengzhao.myds.database.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author huifengzhao
 * @ClassName DruidUtil
 * @date 2018/8/29
 */
public class DruidUtil {

    private static final transient Logger log = LoggerFactory.getLogger(DruidUtil.class);

    private static DruidDataSource dataSource;

    static {
        Properties properties = new Properties();
        try {
            properties.load(DruidUtil.class.getClassLoader().getResourceAsStream("db.properties"));
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
            log.info("数据库连接信息获取成功");
        } catch (Exception e) {
            log.error("读取连接数据库配置文件失败", e);
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    /**
     * 从数据源中获取连接
     *
     * @return 跟数据库的连接对象
     */
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            log.error("获取数据库连接失败", e);
        }
        return null;
    }

    /**
     * 关闭数据库连接
     */
    public static void close() {
        dataSource.close();
    }
}
