package com.lm.slide;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;

import com.lm.slide.base.BaseActivity;
import com.lm.slide.base.recyclerview.CommonAdapter;
import com.lm.slide.base.recyclerview.base.ViewHolder;
import com.lm.slide.databinding.ActivityScrollViewBinding;

import java.util.ArrayList;
import java.util.List;

public class ScrollViewActivity extends BaseActivity<ActivityScrollViewBinding> {

    private List<String> mDataList = new ArrayList<>();
    private CommonAdapter<String> mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scroll_view;
    }

    @Override
    protected void initData() {
        super.initData();
        for (int i = 0; i < 50; i++) {
            mDataList.add("");
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mAdapter = new CommonAdapter<String>(aty, R.layout.item_layout, mDataList) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                TextView view = holder.getView(R.id.tv_txt);
                view.setText("这个是第" + (position + 1) + "项");
            }
        };
        mBinding.rcBody.setLayoutManager(new LinearLayoutManager(aty));
        mBinding.rcBody.setNestedScrollingEnabled(false);
        mBinding.rcBody.setAdapter(mAdapter);


    }
}
