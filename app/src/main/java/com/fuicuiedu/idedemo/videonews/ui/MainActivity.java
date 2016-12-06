package com.fuicuiedu.idedemo.videonews.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.fuicuiedu.idedemo.videonews.R;
import com.fuicuiedu.idedemo.videonews.ui.likes.LikesFragment;
import com.fuicuiedu.idedemo.videonews.ui.local.LocalVideoFragment;
import com.fuicuiedu.idedemo.videonews.ui.news.NewsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewPager)ViewPager viewPager;
    @BindView(R.id.btnLikes)Button btnLikes;
    @BindView(R.id.btnLocal)Button btnLocal;
    @BindView(R.id.btnNews)Button btnNews;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    //初始化视图
    private void initView(){
        unbinder = ButterKnife.bind(this);
        //viewpager适配器
        viewPager.setAdapter(adapter);
        //viewpager监听->Button的切换
        viewPager.addOnPageChangeListener(listener);
        // 首次进入默认选中在线新闻
        btnNews.setSelected(true);
    }

    //viewpager适配器
    private FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            switch (position){
                //在线新闻
                case 0:
                    return new NewsFragment();
                //本地视频
                case 1:
                    return new LocalVideoFragment();
                //我的收藏
                case 2:
                    return new LikesFragment();
                default:
                    throw new RuntimeException("未知错误");
            }
        }

        @Override
        public int getCount() {
            //一共三页，写死
            return 3;
        }
    };

    //viewpager监听->Button的切换
    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            //UI改变
            btnNews.setSelected(position == 0);
            btnLocal.setSelected(position == 1);
            btnLikes.setSelected(position == 2);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    //button点击事件
    @OnClick({R.id.btnNews,R.id.btnLikes,R.id.btnLocal})
    public void chooseFragment(Button button){
        switch (button.getId()){
            case R.id.btnNews:
                //不要平滑效果，第二个参数传false
                viewPager.setCurrentItem(0,false);
                break;
            case R.id.btnLocal:
                viewPager.setCurrentItem(1,false);
                break;
            case R.id.btnLikes:
                viewPager.setCurrentItem(2,false);
                break;
            default:
                throw new RuntimeException("未知错误");
        }
    }

    //解绑butterknife
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
