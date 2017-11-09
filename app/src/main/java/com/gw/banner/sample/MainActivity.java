package com.gw.banner.sample;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gw.banner.adapter.BannerPagerAdapter;
import com.gw.banner.listener.OnBannerItemClickListener;
import com.gw.banner.loader.ImageLoader;
import com.gw.banner.view.BannerViewPager;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    final List<Integer> colors = Arrays.asList(Color.GRAY, Color.RED, Color.YELLOW/**/);
    private BannerViewPager viewPager;
    private TextView tvIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = findViewById(R.id.bannerViewPager);
        tvIndicator = findViewById(R.id.tvIndicator);

        final BannerPagerAdapter<Integer, ImageView> adapter = new BannerPagerAdapter(this, imageLoader, colors);
        viewPager.setAdapter(adapter);
        tvIndicator.setText("1/" + adapter.getRealCount());
        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tvIndicator.setText((position + 1) + "/" + adapter.getRealCount());
                Log.i("TAG", position + "");
            }
        });
        adapter.setOnBannerItemClickListener(new OnBannerItemClickListener() {
            @Override
            public void OnBannerItemClick(int position) {
                Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });
        viewPager.startAutoPlay();
    }

    ImageLoader imageLoader = new ImageLoader() {
        @Override
        public void fillData(Context context, Integer data, ImageView view) {
            view.setBackgroundColor(data);
        }
    };
}
