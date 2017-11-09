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
import com.gw.banner.adapter.BannerPagerAdapter;
import com.gw.banner.NumberIndicatorBanner;
import com.gw.banner.SimpleIndicatorBanner;
import com.gw.banner.loader.ImageLoader;
import com.gw.banner.loader.ViewLoaderInterface;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final List<String> datas = Arrays.asList(
            "http://img4.imgtn.bdimg.com/it/u=52063953,1065301345&fm=27&gp=0.jpg",
            "http://img3.imgtn.bdimg.com/it/u=2746561339,2034367002&fm=27&gp=0.jpg",
            "http://img0.imgtn.bdimg.com/it/u=4279113259,4201621527&fm=27&gp=0.jpg"
    );
    final List<Integer> colors = Arrays.asList(Color.LTGRAY, Color.DKGRAY, Color.GREEN);
    private BannerViewPager noIndicatorBanner;
    private SimpleIndicatorBanner<String, ImageView> simpleRectIndicatorBanner;
    private SimpleIndicatorBanner<String, TextView> simpleCircleIndicatorBanner;
    private NumberIndicatorBanner numberIndicatorBanner;
    private ImageLoader imageLoader = new ImageLoader() {
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
        simpleRectIndicatorBanner.setViewLoader(new ImageLoader() {
            @Override
            public void fillData(Context context, ImageView view, String data, int position) {
                Glide.with(MainActivity.this).load(data).into(view);
            }
        });
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
        numberIndicatorBanner.setViewLoader(new ImageLoader() {
            @Override
            public void fillData(Context mContext, ImageView mView, String mData, int mPosition) {
                Glide.with(MainActivity.this).load(mData).into(mView);
            }
        });
        numberIndicatorBanner.setData(datas);
        numberIndicatorBanner.setOnBannerItemClickListener((mView, data, position) -> ToastUtil.toastShort(position + "-->numberIndicatorBanner"));

    }
}
