package com.company.iendoplus.ui.activity.login;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.company.iendoplus.R;
import com.company.iendoplus.aop.SingleClick;
import com.company.iendoplus.app.AppActivity;
import com.company.iendoplus.manager.ActivityManager;
import com.company.iendoplus.other.KeyboardWatcher;
import com.company.iendoplus.ui.activity.MainActivity;
import com.company.iendoplus.utils.SharePreferenceUtil;
import com.hjq.bar.TitleBar;
import com.hjq.widget.view.SwitchButton;

import org.angmarch.views.NiceSpinner;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 可进行拷贝的副本
 */
public final class LoginActivity extends AppActivity implements KeyboardWatcher.SoftKeyboardStateListener {
    private TitleBar mTitleBar;
    private SwitchButton mSwithRemeber;
    private ImageView mAdd;
    private NiceSpinner mIfOnLine;
    private NiceSpinner mSection;
    private ImageView mLogoView;
    private ViewGroup mBodyLayout;
    private EditText mPhoneView;
    private EditText mPasswordView;
    private Button mCommitView;
    private View mBlankView;
    private View mOtherView;
    private String mChooseSection;
    private boolean isRemember;
    private String username;
    private String password;
    private String endoType = "3";
    /**
     * logo 缩放比例
     */
    private final float mLogoScale = 0.8f;
    /**
     * 动画时间
     */
    private final int mAnimTime = 300;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mLogoView = findViewById(R.id.iv_login_logo);
        mBodyLayout = findViewById(R.id.ll_login_body);
        mPhoneView = findViewById(R.id.et_login_phone);
        mPasswordView = findViewById(R.id.et_login_password);
        mCommitView = findViewById(R.id.btn_login_commit);
        mBlankView = findViewById(R.id.v_login_blank);
        mOtherView = findViewById(R.id.ll_login_other);
        mTitleBar = findViewById(R.id.titlebar);
        mSwithRemeber = findViewById(R.id.sb_setting_switch);
        mIfOnLine = findViewById(R.id.niceSpinnerGetOnLine);
        mSection = findViewById(R.id.niceSpinnerSection);
        mAdd = findViewById(R.id.iv_add);
        setOnClickListener(mCommitView, mTitleBar.getLeftView(), mAdd, mSwithRemeber);

    }

    @Override
    protected void initData() {
        postDelayed(() -> {
            // 因为在小屏幕手机上面，因为计算规则的因素会导致动画效果特别夸张，所以不在小屏幕手机上面展示这个动画效果
            if (mBlankView.getHeight() > mBodyLayout.getHeight()) {
                // 只有空白区域的高度大于登录框区域的高度才展示动画
                KeyboardWatcher.with(LoginActivity.this).setListener(this);
            }
        }, 500);
    }


    @SingleClick
    @Override
    public void onClick(View v) {
        if (v == mTitleBar.getLeftView()) { //退出
            ActivityManager.getInstance().finishAllActivities();
        }
        switch (v.getId()) {
            case R.id.iv_add:////跳转到添加服务器界面
//                startActivity(new Intent(this, AddServersActivity.class));
                break;
            case R.id.sb_setting_switch://是否记住密码
//                SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.is_Remember_Password, mSwithRemeber.isChecked());  //是否记住密码
//                if (mSwithRemeber.isChecked()) {
//                    mSwithRemeber.setChecked(true);
//                } else {
//                    mSwithRemeber.setChecked(false);
//                }
                break;
            case R.id.btn_login_commit://登入
                checkData();
                if (true) {  //登录成功
                    //false 不是第一次登入了
                    SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.is_First_in, false);
                    //false  登录的标志 true表示登录了
                    SharePreferenceUtil.put(LoginActivity.this, SharePreferenceUtil.is_login, true);
                    startActivity(MainActivity.class);
                }
                break;
        }
    }

    private void checkData() {
        username = mPhoneView.getText().toString().trim();
        password = mPasswordView.getText().toString().trim();
//        if (username.equals("")) {
//            toast("账号不能为空");
//            return;
//        }
//        if (password.equals("")) {
////            toast("密码不能为空");
////            return;
//        }


    }
    /**
     * 实现方法
     *
     * @param keyboardHeight 软键盘高度
     */
    @Override
    public void onSoftKeyboardOpened(int keyboardHeight) {
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int[] location = new int[2];
        // 获取这个 View 在屏幕中的坐标（左上角）
        mBodyLayout.getLocationOnScreen(location);
        //int x = location[0];
        int y = location[1];
        int bottom = screenHeight - (y + mBodyLayout.getHeight());
        if (keyboardHeight > bottom) {
            // 执行位移动画
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBodyLayout, "translationY", 0, -(keyboardHeight - bottom));
            objectAnimator.setDuration(mAnimTime);
            objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            objectAnimator.start();
            // 执行缩小动画
            mLogoView.setPivotX(mLogoView.getWidth() / 2f);
            mLogoView.setPivotY(mLogoView.getHeight());
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(mLogoView, "scaleX", 1.0f, mLogoScale);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(mLogoView, "scaleY", 1.0f, mLogoScale);
            ObjectAnimator translationY = ObjectAnimator.ofFloat(mLogoView, "translationY", 0.0f, -(keyboardHeight - bottom));
            animatorSet.play(translationY).with(scaleX).with(scaleY);
            animatorSet.setDuration(mAnimTime);
            animatorSet.start();
        }
    }

    @Override
    public void onSoftKeyboardClosed() {
        // 执行位移动画
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBodyLayout, "translationY", mBodyLayout.getTranslationY(), 0);
        objectAnimator.setDuration(mAnimTime);
        objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        objectAnimator.start();
        if (mLogoView.getTranslationY() == 0) {
            return;
        }
        // 执行放大动画
        mLogoView.setPivotX(mLogoView.getWidth() / 2f);
        mLogoView.setPivotY(mLogoView.getHeight());
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mLogoView, "scaleX", mLogoScale, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(mLogoView, "scaleY", mLogoScale, 1.0f);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(mLogoView, "translationY", mLogoView.getTranslationY(), 0);
        animatorSet.play(translationY).with(scaleX).with(scaleY);
        animatorSet.setDuration(mAnimTime);
        animatorSet.start();
    }


}