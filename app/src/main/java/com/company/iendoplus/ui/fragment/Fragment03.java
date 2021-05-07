package com.company.iendoplus.ui.fragment;


import com.company.iendoplus.R;
import com.company.iendoplus.action.StatusAction;
import com.company.iendoplus.app.TitleBarFragment;
import com.company.iendoplus.ui.activity.MainActivity;
import com.company.iendoplus.widget.StatusLayout;


/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 可进行拷贝的副本
 */
public final class Fragment03 extends TitleBarFragment<MainActivity> implements StatusAction {
    private StatusLayout mStatusLayout;

    public static Fragment03 newInstance() {
        return new Fragment03();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_03;
    }

    @Override
    protected void initView() {
        mStatusLayout = findViewById(R.id.fragment_03_statuslayout);

    }

    @Override
    protected void initData() {
        showLoading();

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