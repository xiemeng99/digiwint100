package digiwin.smartdepott100.module.activity.common;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import digiwin.library.dialog.OnDialogTwoListener;
import digiwin.smartdepott100.core.coreutil.AlertDialogUtils;
import digiwin.library.utils.LogUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.utils.TelephonyUtils;
import digiwin.library.utils.UriToPathUtils;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.BaseRecyclerAdapter;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemClickListener;
import digiwin.pulltorefreshlibrary.recyclerviewAdapter.OnItemLongClickListener;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleActivity;
import digiwin.smartdepott100.module.adapter.common.ChoosePicAdapter;
import digiwin.smartdepott100.module.bean.common.ChoosePicBean;
import digiwin.smartdepott100.module.logic.common.CommonJsonLogic;

/**
 * @author xiemeng
 * @des 选择图片界面
 * @date 2017/4/19 14:46
 */

public class ChoosePicActivity extends BaseTitleActivity {
    @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
    @BindView(R.id.et_picdes)
    EditText etPicdes;
    @BindView(R.id.ry_list)
    RecyclerView ryList;
    @OnClick(R.id.iv_title_setting)
    void surePic(){

    }

    /**
     * 图片
     */
    List<ChoosePicBean> list;

    BaseRecyclerAdapter adapter;
    /**
     * 默认的
     */

    /**
     * 模组名
     */
    public static final String MODULECODE = "code";

    /**
     * 拍照
     */
    private final int CAMERA_WITH_DATA = 1;
    /**
     * 本地
     */
    private final int IMAGE_OPEN=2;
    /**
     * 临时图片名称
     */
    public static  String TMP_PATH;

    private String INTENT_TYPE  = "image/*";

    private String fileName="/mnt/sdcard/1digiwin/";

    private String pathImage;

    File file;

    CommonJsonLogic commonJsonLogic;

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_choosepic;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.choose_pic);
        ivScan.setVisibility(View.GONE);
        iv_title_setting.setImageResource(R.drawable.check_num_on);
    }

    @Override
    protected void doBusiness() {
        pathImage="";
         commonJsonLogic = CommonJsonLogic.getInstance(context, module, mTimestamp.toString());
        list=new ArrayList<>();
        ChoosePicBean defaultPic= new ChoosePicBean();
        defaultPic.setDrawId(R.drawable.right);
        list.add(defaultPic); //打开照相机

        ChoosePicBean defaultPic2= new ChoosePicBean();
        defaultPic2.setDrawId(R.drawable.right);
        list.add(defaultPic2); //打开本地图片

        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        ryList.setLayoutManager(layoutManager);
        adapter=new ChoosePicAdapter(activity,list);
        ryList.setAdapter(adapter);
        itemClick();
        deletePic();
    }

    /**
     * 删除图片
     */
    private void deletePic() {
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View itemView, final int position) {
                if (position!=0&&position!=list.size()-1){
                    AlertDialogUtils.showSureOrQuitDialogAndCall(activity, R.string.remove_pic, new OnDialogTwoListener() {
                        @Override
                        public void onCallback1() {
                            list.remove(position);
                            adapter.notifyDataSetChanged();
                        }
                        @Override
                        public void onCallback2() {
                        }
                    });
                }
            }
        });
    }

    /**
     *点击事件
     */
    private void itemClick() {
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                //打开照相机
                if (position==0){
                    TMP_PATH = "digiwin"+ TelephonyUtils.getTime()+".jpg";
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                     file = new File(fileName);
                    if (!file.exists()){file.mkdirs();}// 创建文件夹
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,  Uri.fromFile(new File(fileName, TMP_PATH)));
                    startActivityForResult(intent, CAMERA_WITH_DATA);
                }
                //打开本地图片ACTION_GET_CONTENT
                else if(position==list.size()-1){
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType(INTENT_TYPE);
                    startActivityForResult(intent, IMAGE_OPEN);
                }else {
                    list.remove(0);
                    list.remove(list.size()-1);
                    commonJsonLogic.update(list, new CommonJsonLogic.UpdateImgListener() {
                        @Override
                        public void onProgressCallBack(long progress, long total) {

                        }

                        @Override
                        public void onSuccess(String msg) {

                        }

                        @Override
                        public void onFailed(long progress, long total) {

                        }
                    });
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //打开照相机
        pathImage="";
        if(resultCode==RESULT_OK&&requestCode==CAMERA_WITH_DATA){
            pathImage= fileName+ TMP_PATH;
        }
        //打开图片
         else  if(resultCode==RESULT_OK && requestCode==IMAGE_OPEN) {
            Uri uri = data.getData();
            pathImage = UriToPathUtils.getImageAbsolutePath(activity, uri);
            LogUtils.i(TAG,"pathImage"+pathImage);
         }
        if (!StringUtils.isBlank(pathImage)) {
            ChoosePicBean tempPicBean = new ChoosePicBean();
            tempPicBean.setPicPath(pathImage);
            list.remove(list.size()-1);
            list.add(tempPicBean);

            ChoosePicBean  defaultPic= new ChoosePicBean();
            defaultPic.setDrawId(R.drawable.right);
            list.add(defaultPic); //打开本地图片
            adapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    public String moduleCode() {
        Bundle bundle = getIntent().getExtras();
        if (null!=bundle)
        module = bundle.getString(MODULECODE, ModuleCode.OTHER);
        return module;
    }

}
