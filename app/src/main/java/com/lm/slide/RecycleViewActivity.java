package com.lm.slide;

import com.lm.slide.base.BaseActivity;
import com.lm.slide.databinding.ActivityRecycleViewBinding;

public class RecycleViewActivity extends BaseActivity<ActivityRecycleViewBinding> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_recycle_view;
    }

    @Override
    protected void initData() {
        super.initData();
        mBinding.rvContent.setRulerListener(new RulerView.RulerListener() {
            @Override
            public void onScaleChange(float scale) {
                mBinding.tvNum.setText(scale + "");
            }
        });
    }
}
