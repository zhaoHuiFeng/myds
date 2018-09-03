package huifengzhao.myds.database.base;

import huifengzhao.myds.database.config.SystemColumn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Set;

/**
 * @author huifengzhao
 * @ClassName WriteBase
 * @date 2018/8/29
 */
public abstract class WriteBase<T> extends Base<T> {

    private static final transient Logger log = LoggerFactory.getLogger(Base.class);
    private WriteBase.Callback callback;
    protected Connection transactionConnection;
    private T bean;
    private Throwable throwable;
    private boolean isAsync;

    protected WriteBase() {
    }

    protected WriteBase(Connection transactionConnection) {
        this.transactionConnection = transactionConnection;
    }

    public WriteBase(T bean) {
        this.bean = bean;
    }

    public T getBean() {
        return this.bean;
    }

    public void setBean(T bean) {
        this.bean = bean;
    }

    private Throwable getThrowable() {
        return this.throwable;
    }

    protected void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    private boolean isAsync() {
        return this.isAsync;
    }

    protected void setAsync() {
        this.isAsync = true;
    }

    /**
     * 是否抛出异常
     *
     * @param t 异常
     */
    @Override
    public void isThrows(Throwable t) {
        if (this.isAsync()) {
            t.addSuppressed(this.getThrowable());
            if (this.isThrows()) {
                throw new RuntimeException(t);
            }

            log.error("执行数据库操作", t);
        } else {
            super.isThrows(t);
        }
    }

    /**
     * 回收对象,全部置为null
     */
    @Override
    protected void recycling() {
        super.recycling();
        this.bean = null;
        this.throwable = null;
        this.transactionConnection = null;
    }

    /**
     * 成功后的回调
     */
    public interface Callback {
        void success(Object obj);
    }

    /**
     * 执行操作的方法
     */
    public abstract void run();

    public interface Event {
        public static enum BeforeCode {
            /**
             * 事件
             */
            CONTINUE("继续", 0),
            END("结束", -100);

            private String desc;
            private int resultCode;

            private BeforeCode(String desc, int resultCode) {
                this.desc = desc;
                this.resultCode = resultCode;
            }

            public int getResultCode() {
                return this.resultCode;
            }

            public String getDesc() {
                return this.desc;
            }
        }
    }
}
