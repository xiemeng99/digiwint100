package digiwin.smartdepott100.module.activity.board;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import digiwin.library.constant.SharePreKey;
import digiwin.library.utils.SharedPreferencesUtils;
import digiwin.library.utils.StringUtils;
import digiwin.library.voiceutils.VoiceUtils;
import digiwin.smartdepott100.R;
import digiwin.smartdepott100.core.appcontants.AddressContants;
import digiwin.smartdepott100.core.appcontants.ModuleCode;
import digiwin.smartdepott100.core.base.BaseTitleHActivity;
import digiwin.smartdepott100.core.coreutil.GetVoicer;
import digiwin.smartdepott100.module.adapter.board.TctsBoardAdapter;
import digiwin.smartdepott100.module.bean.board.TctsBoardBean;
import digiwin.smartdepott100.module.logic.board.TctsboardLogic;

/**
 * @author xiemeng
 * @des 检验完成待入库看板
 * @date 2017/3/8
 */
public class TctsBoardActivity extends BaseTitleHActivity {
   @BindView(R.id.toolbar_title)
    Toolbar toolbarTitle;
   // @BindView(R.id.ryboard)
    RecyclerView ryboard;
    /**
     * 定时器
     */
    Timer timer;
    TctsboardLogic tctsboardLogic;
    /**
     * 当前页
     */
    int pagenow;
    /**
     * 个数
     */
    String pagesize;
    /**
     * 间隔时间
     */
    private  long TIME ;

    @Override
    protected Toolbar toolbar() {
        return toolbarTitle;
    }

    @Override
    protected void initNavigationTitle() {
        super.initNavigationTitle();
        mName.setText(R.string.tcts_board);
    }

    @Override
    protected int bindLayoutId() {
        return R.layout.activity_tctsboard;
    }


    @Override
    protected void doBusiness() {
        ryboard= (RecyclerView) findViewById(R.id.ryboard);
        tctsboardLogic = TctsboardLogic.getInstance(context, module, mTimestamp.toString());
        pagenow = 1;
        String tiems_second = (String) SharedPreferencesUtils.get(context, SharePreKey.REPEATTIME, AddressContants.REPEATTIME);
        TIME= Long.valueOf(tiems_second)*1000;
        pagesize = (String) SharedPreferencesUtils.get(context, SharePreKey.PAGE_SETTING, AddressContants.PAGE_NUM);
        timer = new Timer();
        timer.schedule(new MyTask(), 0, TIME);
        ryboard.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public String moduleCode() {
        module= ModuleCode.RCCTBOARD;
        return module;
    }

    private void getDatas() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("pagesize",pagesize);
        map.put("pagenow", String.valueOf(pagenow));
        tctsboardLogic.getTctsBoard(map, new TctsboardLogic.GetTctsBoardListener() {
            @Override
            public void onSuccess(List<TctsBoardBean> list,String msg) {
                String voiceType = GetVoicer.getVoice(context);
                if (!StringUtils.isBlank(msg) && !StringUtils.isBlank(voiceType)) {
                    SpeakDailog.showChooseAllotDailog(context,voiceType);
                    VoiceUtils.getInstance(context, voiceType).speakText(msg, new VoiceUtils.VoiceComListener() {
                        @Override
                        public void isCom(boolean flag) {
                            SpeakDailog.dismissDialog();
                        }
                    });
                }
                if (list.size() == 0)
                {
                    timer.cancel();
                    pagenow = 1;
                    timer = new Timer();
                    timer.schedule(new MyTask(), 0, TIME);
                }
                else
                {
                    animotion();
                    TctsBoardAdapter adapter = new TctsBoardAdapter(context, list);
                    ryboard.setAdapter(adapter);
                    if (list.size() >= Integer.valueOf(pagesize))
                    {
                        pagenow++;
                    }
                    else
                    {
                        pagenow = 1;
                    }
                }
            }
            @Override
            public void onFailed(String error) {

            }
        });
    }

    /**
     * 动画效果
     */
    private void animotion()
    {
        try {
            Animation animation = (Animation) AnimationUtils.loadAnimation(this, R.anim.board_item);
            LayoutAnimationController lac = new LayoutAnimationController(animation);
            lac.setDelay(0.4f); // 设置动画间隔时间
            lac.setOrder(LayoutAnimationController.ORDER_NORMAL); // 设置列表的显示顺序
            ryboard.setLayoutAnimation(lac);
        }catch (Exception e){

        }
    }
    /**
     * 定时任务发送请求
     */
    private class MyTask extends TimerTask {
        public void run() {
            getDatas();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        timer.cancel();
    }
}
