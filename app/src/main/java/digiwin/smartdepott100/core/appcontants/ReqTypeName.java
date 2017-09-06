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
     *java 成功时的返回码
     */
    static final String SUCCCESSCODEJAVA = "200";
    /**
     * 登录验证
     */
    static final String GETAC = "als.user.login.get";
    /**
     * 解绑设备
     */
    static  final  String GETAP="als.dev.ope.get";
    /**
     * 验证是否可以登录
     */
    static final String DEVICHECK = "als.user.device.check";
    /**
     * 获取营用中心
     */
    static final String GETOC = "als.ent.site.get";
    /**
     * 版本更新
     */
    static final String GETVERUP = "als.ver.up.get";

    /**
     * 获取仓库
     */
    static final String GETWARE = "als.user.ware.get";
    /**
     * 扫描物料条码
     */
    static final String BARCODE = "als.barcode.no.get";
    /**
     * 扫描托盘
     */
    static final String TRAY = "als.tray.no.get";
    /**
     * 扫描库位
     */
    static final String STORAGE = "als.ware.storage.get";
    /**
     * 保存
     */
    static final String SCANINFOSAVE = "als.data.save";
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
     * 快速入库提交
     */
    static final String QUICKSTORAGE_COMMIT = "als.a007.submit";
    /**
     * 获取明细
     */
    static final String GETDETAIL = "als.scan.list.get";
    /**
     * 修改删除
     */
    static final String UPDATEDELETE = "als.scan.list.upd";
    /**
     * 修改删除(产品装箱)
     */
    static final String INSERTDELETE = "app.upd.package.detail";

    /**
     * 退出调用
     */
    static final String EXIT = "als.return.clear";
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
     * 获取FIFO数据-根据料号
     */
    static final String  GET_ALS_FIFO = "als.item.fifo.get";
    /**
     *  获取FIFO数据
     */
    static final String  POSTMATERIALFIFO = "app.doc.no.fifo.get";

    /**
     * 领料过账 获取待办事项
     */
    static final String POSTMATERIALGETORDERLIST = "app.get.data.list";
    /**
     * 快速入库 获取待办事项
     */
    static final String QUICKSTORAGELIST = "als.a007.list.get";
    /**
     * 领料过账 获取汇总数据
     */
    static final String POSTMATERIALSUMDATA = "app.get.data.list.detail";
    /**
     *  快速入库 获取汇总数据
     */
    static final String QUICKSTORAGESUMDATA = "als.a007.list.detail.get";

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
     * 扫描包装箱号
     */
    static final String  GETPACKAGENO = "als.package.no.get";
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

    /**
     * move 工序转移 对接人员
     */
    static final String SCANEMPMOVE="als.e007.employee_name.get";
    /**
     * 查询（流转标签补打）
     */
    static final String PRINTERQUERY="als.e009.plot_print.get";
    /**
     * 查询(成品标签补打)
     */
    static final String PRINTEFINISHRQUERY="als.e010.product_print.get";
    /**
     *move check模块 扫描生产批次
     */
    static final String SCANCIR = "als.e007.lot_no.get";
    /**
     * move 扫描工序号
     */
    static final String SCANPROCEMOVE="als.e007.op_seq.get";

    /**
     * check 扫描员工号
     */
    static final String SCANEMPLYEECHECK="als.e008.employee_name.get";
    /**
     * check 扫描工序号
     */
    static final String SCANPROCECHECK = "als.e008.op_seq.get";
    /**
     * 扫描工单号
     */
    static final String SCANORDER="als.e008.wo_basis.get";
    /**
     * PQC列表筛选
     */
    static final String FILTRATE="als.a060.list.get";
    /**
     * 采购仓退列表
     */
    static final String PURCHASE_STORE="als.a008.list.get";
    /**
     * 采购仓退汇总
     */
    static final String PURCHASE_SUM="als.a008.list.detail.get";
    /**
     * PQC提交
     */
    static final String PQCCOMMIT="als.a060.submit";

    /**
     * 扫描资源
     */
    static final String SCANRESOURCE="als.e008.machine_no.get";
    /**
     * 扫描不良原因
     */
    static final String DEFECTRES="als.e008.defect_reason.get";
    /**
     * 生产报工check in 提交
     */
    static final String CHECKINCOMMIT="als.e008.check_in.submit";

    /**
     * move in提交
     */
    static final String MOVEINCOMMIT = "als.e007.move_in.submit";
    /**
     * move out提交
     */
    static final String MOVEOUTCOMMIT = "als.e007.move_out.submit";
    /**
     * 生产报工check out 提交
     */
    static final String CHECKOUTCOMMIT="als.e008.check_out.submit";

    /**
     * 扫描批次
     */
    static final String SCANPLOTNO = "als.b006.plot.get";
    /**
     * IQC获取扫描数据
     */
    static final String IQCINSPECTSCAN = "als.a009.list.get";
    /**
     * IQC获取扫描item数据
     */
    static final String IQCINSPECTSCANITEM = "als.a009.test.item.get";
    /**
     * IQC获取不良原因
     */
    static final String IQCINSPECTBADREASON = "als.a009.reason.get";
    /**
     * IQC新增/修改/删除不良原因
     */
    static final String IQCINSPECTUPDATEBADREASON = "als.a009.reason.save";
    /**
     * IQC首字符查询不良原因
     */
    static final String IQCSEARCHBADREASON = "als.a009.initial.reason.get";
    /**
     * IQC查询测量值
     */
    static final String IQCSEARCHCHECKVALUE = "als.a009.measure.get";
    /**
     * IQC查询测量值
     */
    static final String IQCUPDATECHECKVALUE = "als.a009.measure.save";
    /**
     * IQC提交
     */
    static final String IQCINSPECTCOMMIT = "als.a009.submit";

}
