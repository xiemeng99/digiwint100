package digiwin.smartdepott100.core.appcontants;

import digiwin.library.utils.TelephonyUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.base.BaseApplication;

/**
 * Created by Administrator on 2017/1/10
 */

public class AddressContants {
    /**
     * 拼接符号
     */
    public static String SPLITFLAG="|";
    /**
     * 语言常量
     */
    public static String EASY_CHINESE = "简体中文";
    public static String TRADITIONAL_CHINESE = "繁体中文";
    public static String ENGLISH = "English";

    /**
     * 下载好的安装包的名字
     */
    public static String APK_NAME = BaseApplication.getInstance().getString(R.string.app_name)+ TelephonyUtils.getMAppVersion(BaseApplication.getInstance())+".apk";
    /**
     *  默认测试区地址
     */
    public static String TEST_ADDRESS = "http://172.31.75.143/wstopprd/ws/r/awsp920";//深圳主机
//    public static String TEST_ADDRESS = "http://172.16.100.7/wstopprd/ws/r/awsp920";//南京主机
    /**
     * 测试区地址标记
     */
    public static String TEST_FLAG = "2";
    /**
     *  默认正式区地址http://180.167.0.43:8888/wtopprd/ws/r/awsp920
     */
    public static String FORMAL_ADDRESS = "http://218.17.27.132:8100/wstopprd/ws/r/awsp920";//深圳主机外网
//    public static String FORMAL_ADDRESS = "http://180.167.0.43:8888/wstopprd/ws/r/awsp920";//南京主机外网
    /**
     * 正式区地址标记
     */
    public static String FORMAL_FLAG = "1";
    /**
     * 上个页面有模块ID传入
     */
    public final  static String MODULEID_INTENT="moduleid_intent";
    /**
     * 分页标记默认值
     */
    public static String PAGE_NUM = "10";
    /**
     * 轮播默认时间
     */
    public static  String REPEATTIME="10";

    public static final String LoginModule="";
    /**
     *扫码延迟时间
     */
    public static final  int DELAYTIME=1;
    /**
     * 物料条码扫描传输xml的field值
     */
    public static final  String BARCODE_NO="barcode";
    /**
     * 装箱单号
     */
    public static final  String PACKAGE_NO="package_no";
    /**
     * 库位扫描传输xml的field值
     */
    public static final  String STORAGE_SPACES_BARCODE="storage_spaces_barcode";
    /**
     * 删除标记
     */
    public static  final String DELETETPYE="D";
    /**
     * 修改标记
     */
    public static  final String UPDATETPYE="U";
    /**
     * 退出界面传输xml的field值
     */
    public static  final  String FLAG="flag";
    /**
     * 删除
     */
    public static  final  String DELETE="d";
    /**
     * 新增
     */
    public static  final  String INSTER="i";
    /**
     * 流转卡号
     */
    public static  final  String PROCESSCARD="process_card";
    /**
     * 工序报工
     */
    public static  final  String PROCESSNO="process_no";
    /**
     * 时间
     */
    public static  final  String REPOSTDATETIME="report_datetime";
    /**
     * 线别
     */
    public static  final  String LINENO="line_no";

    /**
     * 退出界面传输xml的value值，后台判断是否有数据
     */
    public static final  String EXIT0="0";

    /**
     * 退出界面传输xml的value值，执行删除
     */
    public static final  String EXIT1="1";

    /**
     * 退出界面传输xml的value值，前台判断是否需要删除
     */
    public static final  String ISDELETE="1";
    /**
     * ERP的抛转状态
     */
    public static  final  String  N="N";
    /**
     * ERP的抛转状态
     */
    public static  final  String  F="F";

    /**
     * 物料编号
     */
    public static final String ITEM_NO = "item_no";
    /**
     * 报工单号
     */
    public static final String REPORT_NO = "report_no";
    /**
     * 日期
     */
    public static final String QC_DATE = "qc_date";
    /**
     * 时间
     */
    public static final String QC_TIME = "qc_time";
    /**
     * 出货单
     */
    public static final String RECEIPT_NO = "receipt_no";

    /**
     * 仓库代码
     */
    public static final String WAREHOUSE_NO = "warehouse_no";
    /**
     * 储位代码
     */
    public static final String WAREHOUSE_STORAGE = "warehouse_storage";
    /**
     * 批次
     */
    public static final String ITEMLOTNO = "item_lot_no";
    /**
     * 锁定原因
     */
    public static final String LOCKREASON = "lock_reason";
    /**
     * 人员编号
     */
    public static final String EMPLOYEENO = "employee_no";
    /**
     * 机器编号
     */
    public static final String MACHINENO = "machine_no";
    /**
     * 模具编号
     */
    public static final String MOULDNO = "mould_no";
    /**
     * 打印类型
     */
    public static final String PRINT_TYPE = "print_type";
    /**
     * 收货完成待检验看板中标记合格
     */
    public static  final String Y="Y";

    /**
     * 送货单号
     */
    public static final String DOC_NO = "doc_no";
    /**
     * 时间
     */
    public static final String DATE = "date";
    /**
     * 供应商
     */
    public static final String SUPPLIER = "supplier";
    /**
     * 客户
     */
    public static final String CUSTOM = "custom";
    /**
     * fifoY
     */
    public static final  String FIFOY="Y";
    /**
     * fifoY
     */
    public static final  String FIFON="N";
    /**
     * 物料条码标记1
     */
    public static  final String ONE="1";
    /**
     * 物料条码标记2
     */
    public static  final String TWO="2";
    /**
     *包装箱号
     */
    public static  final String PACKAGENO="package_no";
    /**
     *出货单号
     */
    public static  final String RECEIPTNO="receipt_no";
    /**
     *时间
     */
    public static  final String RECEIPTDATE="receipt_date";
    /**
     * 部分模块使用的单号key值
     */
    public static  final String ISSUING_NO="issuing_no";
    /**
     * 用户名
     */
    public static final String USERNAME = "username";
    /**
     * 密码
     */
    public static final String HASHKEY = "hashkey";
    /**
     * 营运中心
     */
    public static final String PLANT = "plant";
    /**
     * 部门编号
     */
    public static final String DEPARTMENTNO = "department_no";
    /**
     * orderData
     */
    public static final String ORDERDATA = "orderData";
    /**
     * 出仓库
     */
    public static final String WAREHOUSEOUTNO = "warehouse_out_no";
    /**
     * 工单号
     */
    public static final String WORKNO = "work_no";
    /**
     * qty
     */
    public static final String QTY = "qty";

    /**
     * 理由码
     */
    public static final String REASONCODENO = "reason_code_no";

    /**
     * 分页数
     */
    public static final String PAGESIZE = "pagesize";
    /**
     * 项次
     */
    public static final String SEQ = "seq";


    /**
     * 首次使用抓取界面的用户信息--输入的用户名
     */
    public static  String ACCTFIRSTLOGIN="tiptop";
    /**
     * 首次使用抓取界面的用户信息--集团
     */
    public static  String ENTERPRISEFIRSTLOGIN="99";
    /**
     * 首次使用抓取界面的用户信息--据点
     */
    public static  String SITEFIRSTLOGIN="1";


    /**
     *扫描工序号
     */
    public static  String PROCEDURE="subop_no";
    /**
     *作业序
     */
    public static  String OP_SEP="op_seq";
    /**
     *数量
     */
    public static  String QTY_UNDEFECT="undefect_qty";
    /**
     *检验结果
     */
    public static  String RESULT="result_type";
    /**
     * 扫描生产批次
     */
    public static  String PLOTNO ="plot_no";
    /**
     * 起始日期
     */
    public static  String DATEBEGIN ="date_begin";
    /**
     * 结束日期
     */
    public static  String DATEEND ="date_end";

    /**
     * 扫描工单号
     */
    public static  String ORDER="order_no";
    /**
     *MOVE_STATUS in
     */
    public static  String MOVEIN="movein";
    /**
     *MOVE_STATUS out
     */
    public static  String MOVEOUT="moveout";

    /**
     *CHECK_STATUS in
     */
    public static  String CHECKIN="checkin";
    /**
     * CHECK_STATUS out
     */
    public static  String CHECKOUT="checkout";
    /**
     * 资源编号
     */
    public static  String RESOURCE_NO="resource_no";
    /**
     * 资源类型
     */
    public static  String RESOURCE_TYPE="resource_type";
    /**
     * 工序号/作业编号
     */
    public static  String SUBOP_NO="subop_no";
    /**
     * 工单号
     */
    public static  String WO_NO="wo_no";
    /**
     * check状态
     */
    public static  String CHECK_STATUS="report_type";

    /**
     * move 状态
     */
    public static  String MOVE_STATUS="report_type";

    /**
     * 入库单号
     */
    public static String STOCKINNO="stock_in_no";

    /**
     * 打印类型
     */
    public static String PRINTTYPE="print_type";
    /**
     * 炉号
     */
    public static String FURNACE_NO="furnace_no";

    public static final String STORAGE_SPACES_NO="storage_spaces_no";

    /**
     * 单位
     */
    public static final String UNIT_NO = "unit_no";
    /**
     * 物料特征
     */
    public static String SIGNCODE = "signcode";

}
