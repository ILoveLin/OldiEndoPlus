package com.company.iendoplus.ui.activity.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.view.View;

import androidx.annotation.NonNull;

import com.airbnb.lottie.LottieAnimationView;
import com.company.iendoplus.R;
import com.company.iendoplus.app.AppActivity;
import com.company.iendoplus.other.AppConfig;
import com.company.iendoplus.ui.activity.MainActivity;
import com.company.iendoplus.utils.SharePreferenceUtil;
import com.gyf.immersionbar.BarHide;
import com.gyf.immersionbar.ImmersionBar;
import com.hjq.widget.view.SlantedTextView;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 闪屏界面
 */
public final class SplashActivity extends AppActivity {
    private LottieAnimationView mLottieView;
    private SlantedTextView mDebugView;
    private Boolean isFirstIn;
    private Boolean isLogin;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        mLottieView = findViewById(R.id.lav_splash_lottie);
        mDebugView = findViewById(R.id.iv_splash_debug);
        //是否第一次进入app ---------默认是第一次登入  true
        isFirstIn = (Boolean) SharePreferenceUtil.get(this, SharePreferenceUtil.is_First_in, true);
        //是否登入-------------------默认是未登录     false
        isLogin = (Boolean) SharePreferenceUtil.get(this, SharePreferenceUtil.is_login, false);


        // 设置动画监听
        mLottieView.addAnimatorListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                mLottieView.removeAnimatorListener(this);
                WhereGoing();
            }
        });
    }

    private void WhereGoing() {
        if (isFirstIn) {
//            SharePreferenceUtil.put(SplashActivity.this, SharePreferenceUtil.is_First_in, true);
            Intent intent = new Intent();
            intent.setClass(SplashActivity.this, GuideActivity.class);
            startActivity(intent);
            finish();
        } else {  //不是第一次进App,判断是否登陆过
            Intent intent = new Intent();
            if (!isLogin) {  //登入成功 ,false==未登录
                intent.setClass(SplashActivity.this, LoginActivity.class);
            } else {   //已经登陆
                intent.setClass(SplashActivity.this, MainActivity.class);
            }
            startActivity(intent);
            finish();
        }


    }

    @Override
    protected void initData() {
        mDebugView.setText(AppConfig.getBuildType().toUpperCase());
        if (AppConfig.isDebug()) {
            mDebugView.setVisibility(View.VISIBLE);
        } else {
            mDebugView.setVisibility(View.INVISIBLE);
        }

        if (true) {
            return;
        }
//        // 刷新用户信息
//        EasyHttp.post(this)
//                .api(new UserInfoApi())
//                .request(new HttpCallback<HttpData<UserInfoBean>>(this) {
//
//                    @Override
//                    public void onSucceed(HttpData<UserInfoBean> data) {
//
//                    }
//                });
    }

    @NonNull
    @Override
    protected ImmersionBar createStatusBarConfig() {
        return super.createStatusBarConfig()
                // 隐藏状态栏和导航栏
                .hideBar(BarHide.FLAG_HIDE_BAR);
    }

    @Override
    public void onBackPressed() {
        //禁用返回键
        //super.onBackPressed();
    }

    @Override
    protected void initActivity() {
        // 问题及方案：https://www.cnblogs.com/net168/p/5722752.html
        // 如果当前 Activity 不是任务栈中的第一个 Activity
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            // 如果当前 Activity 是通过桌面图标启动进入的
            if (intent != null && intent.hasCategory(Intent.CATEGORY_LAUNCHER)
                    && Intent.ACTION_MAIN.equals(intent.getAction())) {
                // 对当前 Activity 执行销毁操作，避免重复实例化入口
                finish();
                return;
            }
        }
        super.initActivity();
    }

    @Deprecated
    @Override
    protected void onDestroy() {
        // 因为修复了一个启动页被重复启动的问题，所以有可能 Activity 还没有初始化完成就已经销毁了
        // 所以如果需要在此处释放对象资源需要先对这个对象进行判空，否则可能会导致空指针异常
        super.onDestroy();
    }
}