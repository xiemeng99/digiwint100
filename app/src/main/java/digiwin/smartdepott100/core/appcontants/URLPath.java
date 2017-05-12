package digiwin.smartdepott100.core.appcontants;

/**
 * @des    JSON请求时模块路径
 * @author  xiemeng
 * @date    2017/3/31
 */
public interface URLPath {
    /**
     * 主URL
     */
    static final String MAINURL="http://172.16.100.10:9018/ekb/api";
    /**
     * 待检验看板
     */
    static  final String RCCTBOARD ="/apm/v1/getListToCheckData";
    /**
     * 检验完成待入库看板
     */
    static  final String TCTSBOARD ="/apm/v1/getListToInData";
}
