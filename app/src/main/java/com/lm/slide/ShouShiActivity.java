package com.lm.slide;

import android.text.Html;
import android.util.Log;
import android.view.View;

import com.lm.slide.base.BaseActivity;
import com.lm.slide.databinding.ActivityShouShiBinding;

import java.util.List;

public class ShouShiActivity extends BaseActivity<ActivityShouShiBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shou_shi;
    }

    @Override
    protected void initData() {
        super.initData();
        mBinding.lidBody.setPath("1235789");
        mBinding.lpvBody.setOnPatternListener(new LockPatternView.OnPatternListener() {
            @Override
            public void onPatternStart() {
                Log.e("msg","onPatternStart");
            }

            @Override
            public void onPatternCleared() {
                Log.e("msg","onPatternCleared");
            }

            @Override
            public void onPatternCellAdded(List<Integer> pattern) {

                Log.e("msg","onPatternCellAdded");
            }

            @Override
            public void onPatternDetected(List<Integer> pattern) {
                Log.e("msg","onPatternDetected");


                if (pattern.size() < 4) {
                    mBinding.tvShow.setText(Html.fromHtml("<font color='#c70c1e'>最少链接4个点, 请重新输入</font>"));
                    //清除
                    mBinding.lpvBody.clearPattern();
                    return;
                }
                String pwd = "";
                for (Integer i : pattern) {
                    pwd = pwd + (i + 1);
                }
                if (pwd.equals("1235789")) {

                    mBinding.tvShow.setText("密码校验成功!");


                } else {

                    mBinding.tvShow.setVisibility(View.VISIBLE);
                    mBinding.tvShow.setText(Html.fromHtml("<font color='#c70c1e'>密码错误</font>"));
              /*      // 左右移动动画
                    Animation shakeAnimation = AnimationUtils.loadAnimation(GestureVerifyActivity.this, R.anim.shake);
                    mBinding.tvShow.startAnimation(shakeAnimation);*/
                    //清除
                    mBinding.lpvBody.clearPattern();
                }
            }
        });


    }
}
