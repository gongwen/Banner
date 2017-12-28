package com.gw.banner.sample;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gw.banner.BannerViewPager;
import com.gw.banner.NumberIndicatorBanner;
import com.gw.banner.NumberTitleIndicatorBanner;
import com.gw.banner.SimpleIndicatorBanner;
import com.gw.banner.SimpleTitleIndicatorBanner;
import com.gw.banner.SpecialSimpleIndicatorBanner;
import com.gw.banner.adapter.BannerPagerAdapter;
import com.gw.banner.loader.ImageViewLoader;
import com.gw.banner.loader.ViewLoaderInterface;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final List<String> datas = Arrays.asList(
            "http://img0.imgtn.bdimg.com/it/u=4279113259,4201621527&fm=27&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=52063953,1065301345&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=2746561339,2034367002&fm=27&gp=0.jpg"

    );
    final List<String> titleList = Arrays.asList("标题标题标题标题标题标题标题标题标题标题1", "标题标题标题标题标题标题标题标题标题标题2", "标题标题标题标题标题标题标题标题标题标题3");
    final List<Integer> colors = Arrays.asList(Color.LTGRAY, Color.DKGRAY, Color.GREEN);
    private BannerViewPager noIndicatorBanner;
    private SimpleIndicatorBanner<String, ImageView> simpleRectIndicatorBanner;
    private SimpleIndicatorBanner<String, TextView> simpleCircleIndicatorBanner;
    private NumberIndicatorBanner<String, ImageView> numberIndicatorBanner;
    private NumberTitleIndicatorBanner<String, ImageView> numberTitleIndicatorBanner;
    private SimpleTitleIndicatorBanner<String, ImageView> simpleTitleIndicatorBanner;
    private SpecialSimpleIndicatorBanner<String, ImageView> specialSimpleIndicatorBanner;
    private ImageViewLoader imageLoader = new ImageViewLoader() {
        @Override
        public void fillData(Context mContext, ImageView mView, String mData, int mPosition) {
            Glide.with(MainActivity.this).load(mData).into(mView);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //无指示器Banner
        noIndicatorBanner = findViewById(R.id.noIndicatorBanner);
        BannerPagerAdapter<String, ImageView> noAdapter = new BannerPagerAdapter(this, imageLoader, datas);
        noAdapter.setOnBannerItemClickListener((mView, data, position) -> ToastUtil.toastShort(position + "-->noIndicatorBanner"));
        noIndicatorBanner.setAdapter(noAdapter);

        //矩形指示器Banner
        simpleRectIndicatorBanner = findViewById(R.id.simpleRectIndicatorBanner);
        simpleRectIndicatorBanner.setViewLoader(imageLoader);
        simpleRectIndicatorBanner.setData(datas);
        simpleRectIndicatorBanner.setOnBannerItemClickListener((mView, data, position) -> ToastUtil.toastShort(position + "-->simpleIndicatorBanner"));

        //圆形指示器Banner
        simpleCircleIndicatorBanner = findViewById(R.id.simpleCircleIndicatorBanner);
        simpleCircleIndicatorBanner.setViewLoader(new ViewLoaderInterface<String, TextView>() {
            @Override
            public void fillData(Context mContext, TextView mView, String mData, int mPosition) {
                mView.setBackgroundColor(colors.get(mPosition));
                mView.setText(String.format("This is text %d !", mPosition));
            }

            @Override
            public TextView createView(Context context) {
                TextView textView = new TextView(context);
                textView.setGravity(Gravity.CENTER);
                return textView;
            }
        });
        simpleCircleIndicatorBanner.setData(datas);
        simpleCircleIndicatorBanner.setOnBannerItemClickListener((mView, data, position) -> ToastUtil.toastShort(position + "-->simpleIndicatorBanner"));

        //数字指示器模式
        numberIndicatorBanner = findViewById(R.id.numberIndicatorBanner);
        numberIndicatorBanner.setViewLoader(imageLoader);
        numberIndicatorBanner.setData(datas);
        numberIndicatorBanner.setOnBannerItemClickListener((mView, data, position) -> ToastUtil.toastShort(position + "-->numberIndicatorBanner"));

        //圆形+标题 指示器模式
        simpleTitleIndicatorBanner = findViewById(R.id.simpleTitleIndicatorBanner);
        simpleTitleIndicatorBanner.setViewLoader(imageLoader);
        simpleTitleIndicatorBanner.setData(datas, titleList);
        simpleTitleIndicatorBanner.setOnBannerItemClickListener((mView, data, position) -> ToastUtil.toastShort(position + "-->numberTitleIndicatorBanner"));

        //数字+标题 指示器模式
        numberTitleIndicatorBanner = findViewById(R.id.numberTitleIndicatorBanner);
        numberTitleIndicatorBanner.setViewLoader(imageLoader);
        numberTitleIndicatorBanner.setData(datas, titleList);
        numberTitleIndicatorBanner.setOnBannerItemClickListener((mView, data, position) -> ToastUtil.toastShort(position + "-->numberTitleIndicatorBanner"));

        //特殊指示器模式
        specialSimpleIndicatorBanner = findViewById(R.id.specialSimpleIndicatorBanner);
        specialSimpleIndicatorBanner.setViewLoader(imageLoader);
        specialSimpleIndicatorBanner.setData(datas);
        specialSimpleIndicatorBanner.setOnBannerItemClickListener((mView, data, position) -> ToastUtil.toastShort(position + "-->simpleIndicatorBanner"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        noIndicatorBanner.startPlay();
        simpleRectIndicatorBanner.startPlay();
        simpleCircleIndicatorBanner.startPlay();
        numberIndicatorBanner.startPlay();
        simpleTitleIndicatorBanner.startPlay();
        numberTitleIndicatorBanner.startPlay();
        specialSimpleIndicatorBanner.startPlay();
    }

    @Override
    protected void onPause() {
        super.onPause();
        noIndicatorBanner.stopPlay();
        simpleRectIndicatorBanner.stopPlay();
        simpleCircleIndicatorBanner.stopPlay();
        numberIndicatorBanner.stopPlay();
        simpleTitleIndicatorBanner.stopPlay();
        numberTitleIndicatorBanner.stopPlay();
        specialSimpleIndicatorBanner.stopPlay();
    }
}
