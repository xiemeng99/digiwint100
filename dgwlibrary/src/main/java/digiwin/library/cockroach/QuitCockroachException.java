package digiwin.library.cockroach;

/**
 * Created by ChangquanSun
 * 2017/2/26
 * 自定义异常类型
 */

public final class QuitCockroachException extends RuntimeException {

    public QuitCockroachException(String message) {
        super(message);
    }
}
