package com.company.iendoplus.ui.fragment;


import android.view.View;

import com.company.iendoplus.R;
import com.company.iendoplus.action.StatusAction;
import com.company.iendoplus.app.TitleBarFragment;
import com.company.iendoplus.ui.activity.MainActivity;
import com.company.iendoplus.widget.StatusLayout;


/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 可进行拷贝的副本
 */
public final class Fragment04 extends TitleBarFragment<MainActivity> implements StatusAction {
    private StatusLayout mStatusLayout;

    public static Fragment04 newInstance() {
        return new Fragment04();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_04;
    }

    @Override
    protected void initView() {
        mStatusLayout = findViewById(R.id.fragment_04_statuslayout);

    }

    @Override
    protected void initData() {
        showError(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("我被点击了~");
            }
        });
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