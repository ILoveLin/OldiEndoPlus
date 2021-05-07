package com.company.iendoplus.ui.fragment;


import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.company.iendoplus.R;
import com.company.iendoplus.action.StatusAction;
import com.company.iendoplus.app.TitleBarFragment;
import com.company.iendoplus.ui.activity.MainActivity;
import com.company.iendoplus.ui.adapter.StatusAdapter;
import com.company.iendoplus.widget.StatusLayout;
import com.hjq.base.BaseAdapter;
import com.hjq.widget.layout.WrapRecyclerView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 可进行拷贝的副本
 */
public final class CaseReportFragment extends TitleBarFragment<MainActivity> implements BaseAdapter.OnItemClickListener,
        OnRefreshLoadMoreListener, StatusAction {
    private SmartRefreshLayout mRefreshLayout;
    private WrapRecyclerView mRecyclerView;
    private StatusAdapter mAdapter;
    private StatusLayout mStatusLayout;

    public static CaseReportFragment newInstance() {
        return new CaseReportFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_case;
    }

    @Override
    protected void initView() {
        mStatusLayout = findViewById(R.id.fragment_case_status);
        mRefreshLayout = findViewById(R.id.rl_status_refresh);
        mRecyclerView = findViewById(R.id.rv_status_list);

        mAdapter = new StatusAdapter(getAttachActivity());
        mAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        TextView headerView = mRecyclerView.addHeaderView(R.layout.picker_item);
        headerView.setText("我是头部");
        headerView.setOnClickListener(v -> toast("点击了头部"));

        TextView footerView = mRecyclerView.addFooterView(R.layout.picker_item);
        footerView.setText("我是尾部");
        footerView.setOnClickListener(v -> toast("点击了尾部"));

        mRefreshLayout.setOnRefreshLoadMoreListener(this);
    }

    @Override
    protected void initData() {
        mAdapter.setData(analogData());

    }
    /**
     * 模拟数据
     */
    private List<String> analogData() {
        List<String> data = new ArrayList<>();
        for (int i = mAdapter.getItemCount(); i < mAdapter.getItemCount() + 50; i++) {
            data.add("我是第" + i + "条目");
        }
        return data;
    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
//        getAttachActivity().getStatusBarConfig().statusBarDarkFont(false).init();
        return !super.isStatusBarEnabled();
//        return !super.isStatusBarEnabled();
    }

    @Override
    public StatusLayout getStatusLayout() {
        return mStatusLayout;
    }


    /**
     * {@link BaseAdapter.OnItemClickListener}
     *
     * @param recyclerView RecyclerView对象
     * @param itemView     被点击的条目对象
     * @param position     被点击的条目位置
     */
    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        toast(mAdapter.getItem(position));
    }

    /**
     * {@link OnRefreshLoadMoreListener}
     */

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        postDelayed(() -> {
            mAdapter.clearData();
            mAdapter.setData(analogData());
            mRefreshLayout.finishRefresh();
            toast("刷新完成");
        }, 1000);

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        postDelayed(() -> {
            mAdapter.addData(analogData());
            mRefreshLayout.finishLoadMore();
            toast("加载完成");

        }, 1000);

    }

}