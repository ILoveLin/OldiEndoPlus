package com.company.iendoplus.ui.fragment;


import android.view.View;

import com.company.iendoplus.R;
import com.company.iendoplus.action.StatusAction;
import com.company.iendoplus.aop.SingleClick;
import com.company.iendoplus.app.TitleBarFragment;
import com.company.iendoplus.manager.ActivityManager;
import com.company.iendoplus.ui.activity.MainActivity;
import com.company.iendoplus.ui.activity.login.LoginActivity;
import com.company.iendoplus.ui.dialog.MessageDialog;
import com.company.iendoplus.ui.dialog.ShareDialog;
import com.company.iendoplus.utils.SharePreferenceUtil;
import com.company.iendoplus.widget.StatusLayout;
import com.hjq.base.BaseDialog;
import com.hjq.demo.wxapi.WXEntryActivity;
import com.hjq.umeng.Platform;
import com.hjq.umeng.UmengShare;
import com.hjq.widget.layout.SettingBar;

import java.util.ArrayList;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 可进行拷贝的副本
 */
public final class MineFragment extends TitleBarFragment<MainActivity> implements StatusAction {
    private StatusLayout mStatusLayout;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        mStatusLayout = findViewById(R.id.fragment_case_report_status);
        setOnClickListener(R.id.sb_mine_exit);

    }

    @Override
    protected void initData() {
    }


    //点击事件
    @Override
    @SingleClick
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sb_mine_exit:
                showExitDialog();
                break;
        }
    }

    //推出登录
    private void showExitDialog() {
        // 消息对话框
        new MessageDialog.Builder(getActivity())
                // 标题可以不用填写
                .setTitle("提示!")
                // 内容必须要填写
                .setMessage("退出登录?")
                // 确定按钮文本
                .setConfirm(getString(R.string.common_confirm))
                // 设置 null 表示不显示取消按钮
                .setCancel(getString(R.string.common_cancel))
                // 设置点击按钮后不关闭对话框
                //.setAutoDismiss(false)
                .setListener(new MessageDialog.OnListener() {

                    @Override
                    public void onConfirm(BaseDialog dialog) {
                        startActivity(LoginActivity.class);
                        // 进行内存优化，销毁除登录页之外的所有界面
                        ActivityManager.getInstance().finishAllActivities(LoginActivity.class);
                        //退出登录，把true改成false
                        SharePreferenceUtil.put(getActivity(), SharePreferenceUtil.is_login, false);
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {
                    }
                })
                .show();

    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    public StatusLayout getStatusLayout() {
        return mStatusLayout;
    }

}