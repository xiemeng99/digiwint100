# digiwin
鼎捷软件库文件

复制项目注意事项：
	.gradle 和.git目录不要复制
	修改project名称
	1.关闭Android Studio 
	2.修改project所在路径的文件夹名字为[NewName]，执行2后3，4，5可以忽略
	3.打开Android Stuido，import新的[NewName]路径工程(很重要,重新import工程，Android Studio会自动修改部分相关的project名字引用)
	4.修改根目录下的.iml文件名为[NewName].iml，及该文件中的external.linked.project.id=[NewName]
	5.修改.idea/modules.xml里面的
	<module fileurl="file://$PROJECT_DIR$/[NewName].iml" filepath="$PROJECT_DIR$/[NewName].iml" />
			
	6.修改app的build中applicationId为自己项目的名称
	修改清单文件和MainLogic中的隐式跳转名，原格式android.intent.action.digiwin.xxxActivity
	替换为android.intent.action.客户简称.xxxActivity统一查找替换即可
	清单文件中TODO地方统一修改为客户简称
	后续操作

	修改当前Module的AndroidManifest.xml文件中的manifest节点里的package属性值，改为跟你的包名一致。


已改：
1. 扫码入库 A005 ：PurchaseInStoreActivity->PurchaseInStoreScanFg
	条码 储位

2.扫码收货 A001 : PurchaseGoodsScanActivity->PurchaseGoodsSumFg
	条码

3.采购仓库退料 A008: PurchaseStoreActivity->PurchaseStoreScanFg
	条码 储位

4.供应商平台扫码收货 A003 ：PurchaseSupplierActivity->PurChaseSupplierScanFg
	条码

5.依成品入库 B012 : FinishedStorageActivity->FinishedStorageScanFg
	条码 储位

6.依成品发料 B014 :AccordingMaterialScanActivity AccordingMaterialScanNewActivity
	条码 储位

7.生产配货 B013 : DistributeScanActivity
	条码 储位

8.工单发料 B003 : WorkOrderScanActivity
	条码 储位

9.工单退料 B022 : WorkOrderReturnActivity->WorkOrderReturnScanFg
	条码 储位

10.依退料补料 B023 : WorkSupplementActivity->WorkSupplementScanFg
	条码 储位

11.装箱入库 B028 : InBinningActivity->InBinningScanFg
	储位

12.依成品调拨 B001 : EndProductAllotScanActivity
 	条码 储位

13.退料过账 B010 : MaterialReturnActivity->MaterialReturnScanFg
	条码 储位

14.生产超领 B011 : ProductionLeaderActivity->ProductionLeaderScanFg
	条码 储位

15.入库上架 B007 : ZPutInStoreActivity->ZPutInStoreSecondActivity->ZPutInStoreScanFg
	储位

16.领料过账 B016 : SuitPickingActivity->SuitPickingScanFg
	条码 储位

17.半成品领料过账 B017 ： SuitPickingHalfActivity->SuitPickingHalfScanFg
	条码 储位

18.杂项发料 C007 : MiscellaneousissuesOutActivity->MiscellaneousIssueScanFg
	条码 储位

19.杂项收料 C008 :MiscellaneousissuesInActivity->MiscellaneousIssueInScanFg
	条码 储位

20.条码移库 C003 : MoveStoreActivity->MoveStoreScanFg
	条码 储位

21.通知出货 D001 : SaleOutletActivity->SaleOutletScanFg
	条码 储位

22.出货过账 D003 : PickUpShipmentActivity->PickUpShipmentScanFg
	条码 储位

23.销售退货 D004 : SaleReturnSecondActivity->SaleReturnScanFg
	条码 储位




未改

1.快速入库 A007 QuickStorageActivity QuickStorageListActivity

2.IQC检验 A004：IQCListActivity IQCommitActivity

3.原材料标签打印 E010 ：RawMaterialPrintActivity

4.PQC检验 A060 : PQCCheckOutActivity

5.配货复核 B015 :TransfersToReviewActivity TransfersToReviewComActivity

6.快速入库 B005 : DirectStorageActivity

7.装箱打印 B027 : EnchasePrintActivity

8.线边发料 B002 : LineSendActivity

9.完工入库 B006 : WipStorageActivity->WipStorageScanFg

10.无来源调拨 C010 : StoreAllotActivity->StoreAllotScanFg

11.库存查询 C004 : StoreQueryActivity

12.流转标签打印 E009 : PrintLabelFlowActivity->PrintLabelFlowScanFg

13.成品标签打印 E010 : PrintLabelFinishActivity

14.库存交易锁定 C012 : StoreTransLockActivity

15.库存交易解锁 C015 : StoreTransUnlockActivity

16.产品装箱 C016 : ProductBinningActivity->ProductBinningScanFg

17.产品出箱 C017 : ProductOutBoxActivity->ProductOutBoxScanFg

18.库存盘点 C005 : StockCheckActivity

19.调拨过账 C002 : PostAllocateScanActivity

20.标签补打 E009 : PrintLabelFillActivity

21.扫箱码出货 D005 : ScanOutStoreActivity->ScanOutStoreScanFg

22.成品质量追溯 D006 : TraceProductActivity

23.工序转移movein E007 ： ProcedureMoveinActivity ProcedureMoveoutActivity

24.生产报工checkin E008 ：

25.生产报工checkout E008

26.工序转移moveout E007

扫描之后锁定输入框： 完工入库， 入库上架