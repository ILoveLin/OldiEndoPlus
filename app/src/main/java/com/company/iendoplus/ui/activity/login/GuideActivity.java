package com.company.iendoplus.ui.activity.login;

import android.content.Intent;
import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

import com.company.iendoplus.R;
import com.company.iendoplus.aop.SingleClick;
import com.company.iendoplus.app.AppActivity;
import com.company.iendoplus.ui.activity.MainActivity;
import com.company.iendoplus.ui.adapter.GuideAdapter;
import com.company.iendoplus.utils.SharePreferenceUtil;

import me.relex.circleindicator.CircleIndicator3;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/09/21
 * desc   : 应用引导页
 */
public final class GuideActivity extends AppActivity {

    private ViewPager2 mViewPager;
    private CircleIndicator3 mIndicatorView;
    private View mCompleteView;

    private GuideAdapter mAdapter;
    private Boolean is_login;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        mViewPager = findViewById(R.id.vp_guide_pager);
        mIndicatorView = findViewById(R.id.cv_guide_indicator);
        mCompleteView = findViewById(R.id.btn_guide_complete);
        is_login = (Boolean) SharePreferenceUtil.get(this, SharePreferenceUtil.is_login, false);

        setOnClickListener(mCompleteView);
    }

    @Override
    protected void initData() {
        mAdapter = new GuideAdapter(this);
        mAdapter.addItem(R.drawable.guide_1_bg);
        mAdapter.addItem(R.drawable.guide_2_bg);
        mAdapter.addItem(R.drawable.guide_3_bg);
        mViewPager.setAdapter(mAdapter);
        mViewPager.registerOnPageChangeCallback(mCallback);
        mIndicatorView.setViewPager(mViewPager);
    }

    @SingleClick
    @Override
    public void onClick(View view) {
        if (view == mCompleteView) {
            WhereGoing();
        }
    }

    private void WhereGoing() {
        if (!is_login) {  //未登入,跳转登入界面
            Intent intent = new Intent();
            intent.setClass(GuideActivity.this, LoginActivity.class);
            startActivity(intent);
//            SharePreferenceUtil.put(GuideActivity.this, SharePreferenceUtil.is_First_in, false);
        } else {
            SharePreferenceUtil.put(GuideActivity.this, SharePreferenceUtil.is_First_in, false);   //false 不是第一次登入了
//            SharePreferenceUtil.put(GuideActivity.this, SharePreferenceUtil.is_login, false);
            startActivity(new Intent(GuideActivity.this, MainActivity.class));
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager.unregisterOnPageChangeCallback(mCallback);
    }

    private final ViewPager2.OnPageChangeCallback mCallback = new ViewPager2.OnPageChangeCallback() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (mViewPager.getCurrentItem() == mAdapter.getItemCount() - 1 && positionOffsetPixels > 0) {
                mIndicatorView.setVisibility(View.VISIBLE);
                mCompleteView.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager2.SCROLL_STATE_IDLE) {
                boolean last = mViewPager.getCurrentItem() == mAdapter.getItemCount() - 1;
                mIndicatorView.setVisibility(last ? View.INVISIBLE : View.VISIBLE);
                mCompleteView.setVisibility(last ? View.VISIBLE : View.INVISIBLE);
            }
        }
    };
}