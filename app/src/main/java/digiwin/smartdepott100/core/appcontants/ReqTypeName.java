package digiwin.smartdepott100.core.appcontants;

/**
 * @author xiemeng
 * @des reqType名称，返回码数字常量
 * @date 2017/1/18
 */
public interface ReqTypeName {
    /**
     * 成功时的返回码
     */
    static final String SUCCCESSCODE = "0";
    /**
     * 登录验证
     */
    static final String GETAC = "als.user.login.get";
    /**
     * 解绑设备
     */
    static  final  String GETAP="GetAp";
    /**
     * 获取营用中心
     */
    static final String GETOC = "als.ent.site.get";
    /**
     * 版本更新
     */
    static final String GETVERUP = "GetVerup";

    /**
     * 获取仓库
     */
    static final String GETWARE = "GetWare";
    /**
     * 扫描物料条码
     */
    static final String BARCODE = "app.barcode.no.get";
    /**
     * 扫描库位
     */
    static final String STORAGE = "app.storage.spaces.get";
    /**
     * 保存
     */
    static final String SCANINFOSAVE = "app.scaninfo.keep";
    /**
     * 装箱保存
     */
    static final String BINNINGSAVE = "app.package.keep";
    /**
     * 汇总数据展示
     */
    static final String SUMUPDATE = "app.aggregate.page.get";
    /**
     * 提交
     */
    static final String COMMIT = "app.receipt.submit";
    /**
     * 获取明细
     */
    static final String GETDETAIL = "app.scandetail.get";
    /**
     * 修改删除
     */
    static final String UPDATEDELETE = "app.scandetail.upd";
    /**
     * 修改删除(产品装箱)
     */
    static final String INSERTDELETE = "app.upd.package.detail";

    /**
     * 退出调用
     */
    static final String EXIT = "app.scan.del";
    /**
     * 获取未完事项
     */
    static final String UNCOMGET="app.uncompletedjob.get";
    /**
     * 删除未完事项
     */
    static final String UNCOMDELETE="app.uncompletedjob.del";

    /**
     * 调拨待复核待办事项
     */
    static final String TRANSFERSTOREVIEW="app.transfer.to.check";
    /**
     * 调拨待复核明细页面
     */
    static final String TRANSFERSTOREVIEWDETAIL="app.transferdetail.to.check";

    /**
     * 生产配货汇总数据展示
     */
    static final String DISTRIBUTESUMUPDATE = "app.workshop.transfer.get";


    static final String ITEM_NO = "app.low.order.item.req.get";

    /**
     * 获取FIFO数据
     */
    static final String  GETFIFO = "app.item.fifo.get";
    /**
     *  获取FIFO数据
     */
    static final String  POSTMATERIALFIFO = "app.doc.no.fifo.get";

    /**
     * 领料过账 获取待办事项
     */
    static final String POSTMATERIALGETORDERLIST = "app.get.data.list";
    /**
     * 领料过账 获取汇总数据
     */
    static final String POSTMATERIALSUMDATA = "app.get.data.list.detail";

    /**
     * 调拨复核提交
     */
    static final String  TRANSDERSUBMIT="app.transfer.check.submit";
    /**
     * 收货完成待检验看板
     */
    static  final  String RCCTBOARD="app.rctt.get.board";

    /**
     * 获取到货单信息
     */
    static final String  MATERIALRECEIPT="app.get.delivery.note";

    /**
     * 提交到货单信息
     */
    static final String  MATERIALRECEIPTCOMMIT="app.delivery.submit";

    /**
     * 检验完成待入库看板
     */
    static  final  String TCTSBOARD="app.tcts.get.board";

    /**
     * 依工单解析下阶料需求量
     */
    static final String WORKORDER = "app.low.order.wo.req.get";

    /**
     * 完工入库请求
     */
    static final String COMPLETINGSTORE = "app.date.wo.no.get";

    /**
     * 完工入库提交
     */
    static final String COMPLETINGSTORECOMMIT = "app.stock.in.no.submit";
    /**
     * 扫描理由码
     */
    static final String SCANREASONCODE = "app.reason.code.no.get";
    /**
     * 扫描申请部门
     */
    static final String SCANDEPARTMENT = "app.department.no.get";

    /**
     * 工单报工请求
     */
    static final String PROCESSREPORT = "app.process.name.get";

    /**
     * 报工人请求
     */
    static final String GETWORKPERSON = "app.employee.name.get";

    /**
     * 获取FIFO数据
     */
    static final String  GETACCORDINGFIFO = "app.item.fifo.new.get";

    /**
     * 工序报工提交
     */
    static final String  PROCESSREPORTCOMMIT = "app.process.submit";
    /**
     * 获取采购收货待检验事项
     */
    static final String  GETMATERIALTOCHECK = "app.get.material.tocheck";
    /**
     * 获取IQC质量记录
     */
    static final String  GETIQCLIST = "app.inspection.item.list";
    /**
     * 根据首字母获取不良原因
     */
    static final String  GETQCREASON = "app.get.qcereason";
    /**
     * 根据料号获取不良原因Top5
     */
    static final String  GETQCREASONTOP5 = "app.get.qcereason.top5";
    /**
     * 检验单数据提交
     */
    static final String  UPDATEIQCSTATUS = "app.upd.iqc.status";
    /**
     * 更新收货单
     */
    static final String  UPDRVBCHECKSTATUS = "app.update.rvb.check.status";
    /**
     * 获取图纸
     */
    static final String  GETDRAWING = "app.get.drawing";
    /**
     * (库存交易锁定)获取料号信息
     */
    static final String  GETITEMDETAIL = "app.item.check";
    /**
     * 包装箱号
     */
    static final String  GETPACKBOXNUMBER = "app.get.package.info";
    /**
     * 扫码出货保存
     */
    static final String  SCANOUTSAVE = "app.package.keep";
    /**
     * 扫码出货明细
     */
    static final String  SCANOUTDETAIL = "app.get.package.scandetail";
    /**
     * 删除扫码出货明细
     */
    static final String  DELETESCANOUTDETAIL = "app.del.package.scandetail";
    /**
     * 生产过程信息查询
     */
    static final String  PRODUCTPROCESS = "app.get.production.process";
    /**
     * 条码库存信息查询
     */
    static final String  CURRENTINVENTORY = "app.get.barcode.inv";
    /**
     * 工单信息查询
     */
    static final String  ORDERINFO = "app.get.wo.info.detail";
    /**
     * 出货流向信息查询
     */
    static final String  SHIPMENTTO = "app.get.barcode.delivery.info";
    /**
     * 质量追溯明细查询
     */
    static final String  TRACEPRODUCT = "app.get.item.in.detail";
    /**
     * 获取测量值
     */
    static final String PQCCHECKVALUE = "app.get.test.values";
    /**
     * 提交PQC
     */
    static final String POSTPQC = "app.upd.pqc.status";
    /**
     * 保存PQC
     */
    static final String SAVEPQC = "app.check.measure.data";
}
