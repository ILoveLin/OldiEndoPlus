package com.company.iendoplus.ui.activity.vlc;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.company.iendoplus.R;
import com.company.iendoplus.app.AppActivity;
import com.company.iendoplus.widget.ENDownloadView;
import com.company.iendoplus.widget.ENPlayView;
import com.company.iendoplus.widget.vlc.MyVlcVideoView;
import com.hjq.base.BaseDialog;
import com.vlc.lib.VlcVideoView;

import java.io.File;
import java.util.ArrayList;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2018/10/18
 * desc   : 可进行拷贝的副本
 */
public final class VlcLiveActivity extends AppActivity {
    private File recordFile = new File(Environment.getExternalStorageDirectory(), "CME");   //老徐手机 录像地址-内部存储/Pictures/
    private MyVlcVideoView player;
    private TextView mTitle;
    private TextView tv_current_time;
    private VlcVideoView vlcVideoView;
    private TextView mChangeFull;
    private TextView snapShot;
    private LinearLayout layout_top, linear_contral;
    private ImageView lock_screen;
    private TextView error_text;
    private ENPlayView startView;
    private ENDownloadView loading;
    private TextView recordStart;
    private ImageView back;
    private static final int Show_Lock = 111;
    private static final int Show_Unlock = 112;
    private static final int Type_Loading_Visible = 108;
    private static final int Type_Loading_InVisible = 109;
    private static final int Show_Control_InVisible = 113;
    private static final int Show_Control_Visible = 114;
    private Handler mHandler = new Handler() {
        @SuppressLint("NewApi")
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case Type_Loading_Visible:  //加载框 可见
                    loading.setVisibility(View.VISIBLE);
                    break;
                case Type_Loading_InVisible:
                    //隐藏 加载框
                    loading.setVisibility(View.INVISIBLE);
                    break;
                case Show_Lock: //设置锁屏显示
                    lock_screen.setImageDrawable(getResources().getDrawable(R.drawable.video_lock_close_ic));
                    lock_screen.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    layout_top.setVisibility(View.INVISIBLE);
                    mChangeFull.setVisibility(View.INVISIBLE);
                    linear_contral.setVisibility(View.INVISIBLE);
                    tv_current_time.setVisibility(View.INVISIBLE);
                    break;
                case Show_Unlock: //设置锁屏隐藏
                    lock_screen.setImageDrawable(getResources().getDrawable(R.drawable.video_lock_open_ic));
                    lock_screen.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    layout_top.setVisibility(View.VISIBLE);
                    mChangeFull.setVisibility(View.VISIBLE);
                    linear_contral.setVisibility(View.VISIBLE);
                    tv_current_time.setVisibility(View.VISIBLE);
                    break;
                case Show_Control_InVisible: //控制布局，锁屏显示
                    layout_top.setVisibility(View.INVISIBLE);
                    mChangeFull.setVisibility(View.INVISIBLE);
                    linear_contral.setVisibility(View.INVISIBLE);
                    tv_current_time.setVisibility(View.INVISIBLE);
                    break;
                case Show_Control_Visible: //控制布局，锁屏隐藏
                    layout_top.setVisibility(View.VISIBLE);
                    mChangeFull.setVisibility(View.VISIBLE);
                    linear_contral.setVisibility(View.VISIBLE);
                    tv_current_time.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vlc_live;
    }

    @Override
    protected void initView() {
        recordFile.mkdirs();
        player = findViewById(R.id.player);
        mTitle = findViewById(R.id.tv_top_title);
        tv_current_time = findViewById(R.id.tv_current_time);
        vlcVideoView = findViewById(R.id.vlc_video_view);
        mChangeFull = findViewById(R.id.change);
        lock_screen = findViewById(R.id.lock_screen);
        recordStart = findViewById(R.id.recordStart);
        layout_top = findViewById(R.id.layout_top);
        linear_contral = findViewById(R.id.linear_contral);
        error_text = findViewById(R.id.error_text);
        error_text.setVisibility(View.INVISIBLE);
        snapShot = findViewById(R.id.snapShot);
        startView = findViewById(R.id.start);
        loading = findViewById(R.id.loading);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        responseListener();
    }

    private void responseListener() {

    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return false;
    }
}