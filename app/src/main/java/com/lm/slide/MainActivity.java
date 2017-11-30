package com.lm.slide;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.lm.slide.base.BaseActivity;
import com.lm.slide.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);


        mBinding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ScrollViewActivity.class);
            }
        });

        mBinding.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RecycleViewActivity.class);
              /*  //发送文件
                Intent intent = ChooseFileActivity.newIntent(aty, true);//第二个参数为是否多选
                startActivityForResult(intent, 1);*/
            }
        });

        mBinding.btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ShouShiActivity.class);
            }
        });
        mBinding.btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MoreActivity.class);
            }
        });
        mBinding.wvBody.addJavascriptInterface(new Object() {
            @JavascriptInterface // 这两个函数可以在JavaScript中调用
            public void showToast() {
                mHandler.sendEmptyMessage(1);
            }
        }, "Android");
        mBinding.wvBody.loadUrl("http://api-static.weikejinfu.com/carousel/app/yaoqing/yaoqing.html");


    }


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    //加载网页失败
                    Toast.makeText(aty, "获取到了！", Toast.LENGTH_SHORT).show();
                    break;


            }
            super.handleMessage(msg);
        }

    };
}
