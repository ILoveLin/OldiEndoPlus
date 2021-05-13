package com.company.iendoplus.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;


import com.company.iendoplus.R;
import com.company.iendoplus.app.AppActivity;
import com.company.iendoplus.app.AppFragment;
import com.company.iendoplus.manager.ActivityManager;
import com.company.iendoplus.other.DoubleClickHelper;
import com.company.iendoplus.other.IntentKey;
import com.company.iendoplus.ui.activity.login.LoginActivity;
import com.company.iendoplus.ui.activity.vlc.VlcLiveActivity;
import com.company.iendoplus.ui.dialog.MessageDialog;
import com.company.iendoplus.ui.fragment.MineFragment;
import com.company.iendoplus.ui.fragment.Fragment01;
import com.company.iendoplus.ui.fragment.CaseReportFragment;
import com.company.iendoplus.utils.SharePreferenceUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hjq.base.BaseDialog;
import com.hjq.base.FragmentPagerAdapter;
import com.hjq.toast.ToastUtils;
import com.hjq.xtoast.XToast;
import com.hjq.xtoast.draggable.MovingDraggable;
import com.hjq.xtoast.draggable.SpringDraggable;


public final class MainActivity extends AppActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private XToast<XToast<?>> xToastXToast;
    private ViewPager mViewPager;
    private BottomNavigationView mBottomNavigationView;
    private FragmentPagerAdapter<AppFragment<?>> mPagerAdapter;

    public static void start(Context context) {
        start(context, Fragment01.class);
    }

    public static void start(Context context, Class<? extends AppFragment<?>> fragmentClass) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(IntentKey.INDEX, fragmentClass);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mViewPager = findViewById(R.id.vp_home_pager);

        //底部tab动画实现   https://juejin.cn/post/6867895624025997320
        mBottomNavigationView = findViewById(R.id.bv_home_navigation);

        // 不使用图标默认变色
        mBottomNavigationView.setItemIconTintList(null);
        // 设置导航栏条目点击事件
        mBottomNavigationView.setOnNavigationItemSelectedListener(this);

        // 屏蔽底部导航栏长按文本提示
        Menu menu = mBottomNavigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            mBottomNavigationView.findViewById(menu.getItem(i).getItemId()).setOnLongClickListener(v -> true);
        }
    }

    @Override
    protected void initData() {
        mPagerAdapter = new FragmentPagerAdapter<>(this);
        mPagerAdapter.addFragment(CaseReportFragment.newInstance());
        mPagerAdapter.addFragment(MineFragment.newInstance());

//        mPagerAdapter.addFragment(Fragment03.newInstance());
//        mPagerAdapter.addFragment(Fragment04.newInstance());
        mViewPager.setAdapter(mPagerAdapter);

        onNewIntent(getIntent());

        postDelayed(new Runnable() {
            @Override
            public void run() {
                getGlobalToastBall();
            }
        }, 300);

    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int fragmentIndex = mPagerAdapter.getFragmentIndex(getSerializable(IntentKey.INDEX));
        if (fragmentIndex == -1) {
            return;
        }

        mViewPager.setCurrentItem(fragmentIndex);
        switch (fragmentIndex) {
            case 0:
                mBottomNavigationView.setSelectedItemId(R.id.home_case);
                break;
            case 1:
                mBottomNavigationView.setSelectedItemId(R.id.home_me);
                break;
//            case 2:
//                mBottomNavigationView.setSelectedItemId(R.id.home_message);
//                break;
//            case 3:
//                mBottomNavigationView.setSelectedItemId(R.id.home_me);
//                break;
            default:
                break;
        }
    }

    /**
     * {@link BottomNavigationView.OnNavigationItemSelectedListener}
     */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.home_case) {
            mViewPager.setCurrentItem(0);
            return true;
        } else if (itemId == R.id.home_me) {
            mViewPager.setCurrentItem(1);
            return true;
        }
//        else if (itemId == R.id.home_message) {
//            mViewPager.setCurrentItem(2);
//            return true;
//        } else if (itemId == R.id.home_me) {
//            mViewPager.setCurrentItem(3);
//            return true;
//        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (!DoubleClickHelper.isOnDoubleClick()) {
            toast(R.string.home_exit_hint);
            return;
        }

        // 移动到上一个任务栈，避免侧滑引起的不良反应
        moveTaskToBack(false);
        postDelayed(() -> {
            // 进行内存优化，销毁掉所有的界面
            ActivityManager.getInstance().finishAllActivities();
            // 销毁进程（注意：调用此 API 可能导致当前 Activity onDestroy 方法无法正常回调）
            // System.exit(0);
        }, 300);
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.setAdapter(null);
        mBottomNavigationView.setOnNavigationItemSelectedListener(null);
    }

    private void getGlobalToastBall() {
        // 传入 Application 表示这个是一个全局的 Toast
        xToastXToast = new XToast<>(MainActivity.this);  //getApplication()  所有App界面显示需要权限，activity就是当前界面显示
        xToastXToast.setView(R.layout.toast_phone)
                .setGravity(Gravity.END | Gravity.BOTTOM)
                .setYOffset(200)
                // 设置指定的拖拽规则
                .setDraggable(new SpringDraggable())
                .setOnClickListener(android.R.id.icon, new XToast.OnClickListener<ImageView>() {

                    @Override
                    public void onClick(XToast<?> toast, ImageView view) {
                        goingVlvLive();
                        // 点击后跳转到拨打电话界面
                        // Intent intent = new Intent(Intent.ACTION_DIAL);
                        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        // toast.startActivity(intent);
                        // 安卓 10 在后台跳转 Activity 需要额外适配
                        // https://developer.android.google.cn/about/versions/10/privacy/changes#background-activity-starts
                    }
                })
                .show();
    }

    private void goingVlvLive() {
        // 消息对话框
        new MessageDialog.Builder(getActivity())
                // 标题可以不用填写
                .setTitle("提示!")
                // 内容必须要填写
                .setMessage("Are you ok, 确定观看视频直播?")
                .setCanceledOnTouchOutside(false)
                // 确定按钮文本
                .setConfirm(getString(R.string.common_confirm))
                // 设置 null 表示不显示取消按钮
                .setCancel(getString(R.string.common_cancel))
                // 设置点击按钮后不关闭对话框
                //.setAutoDismiss(false)
                .setListener(new MessageDialog.OnListener() {

                    @Override
                    public void onConfirm(BaseDialog dialog) {
                        startActivity(VlcLiveActivity.class);
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {
                    }
                })
                .show();
    }


}