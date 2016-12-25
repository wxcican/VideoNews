package com.fuicuiedu.idedemo.videonews.ui.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fuicuiedu.idedemo.videonews.R;
import com.fuicuiedu.idedemo.videoplayer.list.MediaPlayerManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/6 0006.
 */

public class NewsFragment extends Fragment {

    @BindView(R.id.newsListView) NewsListView newsListView;

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.fragment_news,container,false);
        }
        return view;
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        // 首次进入，自动刷新
        newsListView.post(new Runnable() {
            @Override public void run() {
                newsListView.autoRefresh();
            }
        });
    }

    @Override public void onResume() {
        super.onResume();
        // 当Fragment-onResume, MediaPlayer进行初始工作
        MediaPlayerManager.getsInstance(getContext()).onResume();
    }

    @Override public void onPause() {
        super.onPause();
        // 当Fragment-onPause, MediaPlayer进行释放工作
        MediaPlayerManager.getsInstance(getContext()).onPause();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        ((ViewGroup) view.getParent()).removeView(view);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        // 当Fragment-onDestroy, 清除所有监听(不再需要UI交互)
        MediaPlayerManager.getsInstance(getContext()).removeAllListeners();
    }
}
