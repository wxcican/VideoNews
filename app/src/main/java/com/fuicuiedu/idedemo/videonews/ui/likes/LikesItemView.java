package com.fuicuiedu.idedemo.videonews.ui.likes;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.fuicuiedu.idedemo.videonews.R;
import com.fuicuiedu.idedemo.videonews.bombapi.BombClient;
import com.fuicuiedu.idedemo.videonews.bombapi.entity.NewsEntity;
import com.fuicuiedu.idedemo.videonews.bombapi.result.CollectResult;
import com.fuicuiedu.idedemo.videonews.commons.CommonUtils;
import com.fuicuiedu.idedemo.videonews.commons.ToastUtils;
import com.fuicuiedu.idedemo.videonews.ui.UserManager;
import com.fuicuiedu.idedemo.videonews.ui.base.BaseItemView;
import com.fuicuiedu.idedemo.videonews.ui.news.comments.CommentsActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/12/27 0027.
 */

public class LikesItemView extends BaseItemView<NewsEntity>{

    public LikesItemView(Context context) {
        super(context);
    }

    @BindView(R.id.ivPreview)
    ImageView ivPreview;
    @BindView(R.id.tvNewsTitle)
    TextView tvNewsTitle;
    @BindView(R.id.tvCreatedAt)
    TextView tvCreatedAt;
    private NewsEntity newsEntity;

    @Override
    protected void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.item_likes,this,true);
        ButterKnife.bind(this);
    }

    @Override
    protected void bindModel(NewsEntity newsEntity) {
        this.newsEntity = newsEntity;
        tvNewsTitle.setText(newsEntity.getNewsTitle());
        tvCreatedAt.setText(CommonUtils.format(newsEntity.getCreatedAt()));
        String url = CommonUtils.encodeUrl(newsEntity.getPreviewUrl());
        Picasso.with(getContext()).load(url).into(ivPreview);
    }

    @OnClick
    public void navigateToComments() {
        CommentsActivity.open(getContext(), newsEntity);
    }

    @OnLongClick
    public boolean unCollectNews(){
        listener.onItemLongClick(newsEntity.getObjectId(),UserManager.getInstance().getObjectId());
        return true;
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(String newsId,String userId);
    }

    private OnItemLongClickListener listener;

    public void setOnItemLongClickListener(OnItemLongClickListener listener){
        this.listener = listener;
    }

}
