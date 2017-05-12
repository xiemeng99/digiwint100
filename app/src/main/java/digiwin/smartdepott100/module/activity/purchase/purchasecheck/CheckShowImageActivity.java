package digiwin.smartdepott100.module.activity.purchase.purchasecheck;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.polites.android.GestureImageView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleHActivity;
import digiwin.smartdepott100.module.bean.purchase.ImageUrl;
import digiwin.smartdepott100.module.logic.purchase.PurcahseCheckLogic;

/**
 * 收货检验 查看图纸
 * @author 唐孟宇
 *
 */
public class CheckShowImageActivity extends BaseTitleHActivity {
	CheckShowImageActivity pactivity;

	PurcahseCheckLogic logic;

	ViewpageAdapter adapter;
	/**
	 * 收货检验界面 带过来的料号
	 */
	String item_no = "";
	@BindView(R.id.checkimg_vp)
	ViewPager checkimg_vp;
	
	@BindView(R.id.toolbar_title)
	Toolbar toolbar_title;

	@Override
	protected int bindLayoutId() {
		return R.layout.activity_check_showimage;
	}

	@Override
	protected void doBusiness() {
		pactivity = (CheckShowImageActivity) activity;
		logic = PurcahseCheckLogic.getInstance(pactivity,module,mTimestamp.toString());
		item_no = getIntent().getExtras().getString(AddressContants.ITEM_NO);
		Message msg = new Message();
		msg.what = 1;
		handler.sendMessage(msg);
	}

	@Override
	protected Toolbar toolbar() {
		return toolbar_title;
	}

	@Override
	public String moduleCode() {
		module = ModuleCode.PURCHASECHECK;
		return module;
	}

	@Override
	protected void initNavigationTitle() {
		super.initNavigationTitle();
		mName.setText(R.string.check_image);
		mBack.setVisibility(View.VISIBLE);
	}

	public class executeLoaderImage implements Runnable{
		private List<ImageUrl> list;

		public executeLoaderImage(List<ImageUrl> list){
			this.list = list;
		}
		@Override
		public void run() {
			showLoadingDialog();
			List<Bitmap> listBm = new ArrayList<Bitmap>();
			Bitmap bitmap;
			for(int i = 0;i < list.size();i++){
				try {
					 bitmap = Picasso.with(pactivity).load(list.get(i).getHttp()).placeholder(R.drawable.common_google_signin_btn_icon_dark).error(R.drawable.common_google_signin_btn_icon_dark).get();
					 if(null != bitmap){
						 listBm.add(bitmap);
					 }else{
						showFailedDialog(R.string.no_pic);
					 }
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			Message message = new Message();
			message.what = 2;
			message.obj = listBm;
			handler.sendMessage(message);
		}
		
	}

	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what){
				case 1:
					Map<String,String> map = new HashMap<>();
					map.put(AddressContants.ITEM_NO,item_no);
					showLoadingDialog();
					logic.getDrawing(map, new PurcahseCheckLogic.GetDrawingListener() {
						@Override
						public void onSuccess(List<ImageUrl> list) {
							dismissLoadingDialog();
							executeLoaderImage executeLoaderImage = new executeLoaderImage(list);
							new Thread(executeLoaderImage).start();
						}

						@Override
						public void onFailed(String error)
						{
							dismissLoadingDialog();
							showFailedDialog(error);
						}
					});
					break;
				case 2:
					List<Bitmap> list = (List<Bitmap>) msg.obj;
					adapter = new ViewpageAdapter(pactivity);
					adapter.setData(list);
					checkimg_vp.setAdapter(adapter);
			}
		}
	};

	public class ViewpageAdapter extends PagerAdapter {
		
		private Context context;
		
		private List<Bitmap> list;
		
		public ViewpageAdapter(Context context){
			this.context = context;
		}
		
		public void setData(List<Bitmap> list){
			this.list=list;
			notifyDataSetChanged();
		}
		
		@Override
		public int getCount() {
			if(list!=null && !list.isEmpty()){
				return list.size();
			}
			return 0;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1 ;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager)container).removeView((View) object);
		}
		
		@Override
		public Object instantiateItem(View container, int position) {
			View imageLayout = getLayoutInflater().from(context).inflate(R.layout.view_banner, null);
			GestureImageView gestureImageView = (GestureImageView) imageLayout.findViewById(R.id.image_gesture);
			gestureImageView.setImageBitmap(list.get(position));
			((ViewPager)container).addView(imageLayout, 0);
			return imageLayout;
		}
	}

}
