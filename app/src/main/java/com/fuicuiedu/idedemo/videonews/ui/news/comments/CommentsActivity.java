package com.fuicuiedu.idedemo.videonews.ui.news.comments;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.fuicuiedu.idedemo.videonews.R;
import com.fuicuiedu.idedemo.videonews.bombapi.BombClient;
import com.fuicuiedu.idedemo.videonews.bombapi.entity.NewsEntity;
import com.fuicuiedu.idedemo.videonews.bombapi.result.CollectResult;
import com.fuicuiedu.idedemo.videonews.commons.CommonUtils;
import com.fuicuiedu.idedemo.videonews.commons.ToastUtils;
import com.fuicuiedu.idedemo.videonews.ui.UserManager;
import com.fuicuiedu.idedemo.videoplayer.part.SimpleVideoPlayer;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentsActivity extends AppCompatActivity implements EditCommentFragment.OnCommentSuccessListener{
    private static final String KEY_NEWS = "KEY_NEWS";

    // 对外公开一个跳转方法
    public static void open(Context context, NewsEntity newsEntity) {
        Intent intent = new Intent(context, CommentsActivity.class);
        intent.putExtra(KEY_NEWS, newsEntity);
        context.startActivity(intent);
    }

    private NewsEntity newsEntity;
    private EditCommentFragment editCommentFragment;

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.simpleVideoPlayer)
    SimpleVideoPlayer simpleVideoPlayer;
    @BindView(R.id.commentsListView)
    CommentsListView commentsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //拿到新闻数据
        newsEntity = (NewsEntity) getIntent().getSerializableExtra(KEY_NEWS);
        //设置toolbar
        setSupportActionBar(toolbar);
        //给左上角加返回图标（返回按钮）
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //设置标题
        tvTitle.setText(newsEntity.getNewsTitle());
        // 设置播放源
        String videoPath = CommonUtils.encodeUrl(newsEntity.getVideoUrl());
        simpleVideoPlayer.setVideoPath(videoPath);
        // 初始化评论表
        commentsListView.setNewsId(newsEntity.getObjectId());
        commentsListView.autoRefresh();
    }

    // #########################  视频相关 start ########################
    @Override
    protected void onResume() {
        super.onResume();
        simpleVideoPlayer.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        simpleVideoPlayer.onPause();
    }
    // #########################  视频相关 end ########################

    // #############  actionbar相关 start ############################
    // 创建一个菜单栏
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.activity_comments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //左上角返回按钮，对应id，home
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        // 判断是否"离线"
        if (UserManager.getInstance().isOffline()) {
            ToastUtils.showShort(R.string.please_login_first);
            return true;
        }
        // 收藏
        if (item.getItemId() == R.id.menu_item_like) {
            String userId = UserManager.getInstance().getObjectId();
            String newsId = newsEntity.getObjectId();
            Call<CollectResult> call = BombClient.getInstance().getNewsApi_cloud().collectNews(newsId, userId);
            call.enqueue(callback);
        }
        // 评论
        if (item.getItemId() == R.id.menu_item_comment) {
            if (editCommentFragment == null) {
                String newsId = newsEntity.getObjectId();
                editCommentFragment = EditCommentFragment.getInstance(newsId);
                editCommentFragment.setListener(this);
            }
            editCommentFragment.show(getSupportFragmentManager(),"Edit Comment");
        }
        return super.onOptionsItemSelected(item);
    }
    // #############  actionbar相关 end ############################

    private Callback<CollectResult> callback = new Callback<CollectResult>() {
        @Override
        public void onResponse(Call<CollectResult> call, Response<CollectResult> response) {
            CollectResult collectResult = response.body();
            if (collectResult.isSuccess()){
                ToastUtils.showShort(R.string.like_success);
            }else{
                ToastUtils.showShort(R.string.like_failure + collectResult.getError());
            }
        }

        @Override
        public void onFailure(Call<CollectResult> call, Throwable t) {
            ToastUtils.showShort(t.getMessage());
        }
    };

    @Override
    public void onCommentSuccess() {
        editCommentFragment.dismiss();
        // 刷新视图，获取最新评论
        commentsListView.autoRefresh();
    }
}
