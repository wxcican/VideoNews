package com.fuicuiedu.idedemo.videonews.ui.likes;

import android.content.Context;
import android.util.AttributeSet;

import com.fuicuiedu.idedemo.videonews.bombapi.BombClient;
import com.fuicuiedu.idedemo.videonews.bombapi.BombConst;
import com.fuicuiedu.idedemo.videonews.bombapi.entity.NewsEntity;
import com.fuicuiedu.idedemo.videonews.bombapi.other.InQuery;
import com.fuicuiedu.idedemo.videonews.bombapi.result.CollectResult;
import com.fuicuiedu.idedemo.videonews.bombapi.result.QueryResult;
import com.fuicuiedu.idedemo.videonews.commons.ToastUtils;
import com.fuicuiedu.idedemo.videonews.ui.UserManager;
import com.fuicuiedu.idedemo.videonews.ui.base.BaseResourceView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 我的收藏列表视图
 */

public class LikesListView extends BaseResourceView<NewsEntity, LikesItemView> {
    public LikesListView(Context context) {
        super(context);
    }

    public LikesListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LikesListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected Call<QueryResult<NewsEntity>> queryData(int limit, int skip) {
        String userId = UserManager.getInstance().getObjectId();
        InQuery where = new InQuery(BombConst.FIELD_LIKES, BombConst.TABLE_USER, userId);
        return newsApi.getLikedList(limit, skip, where);
    }

    @Override
    protected int getLimit() {
        return 15;
    }

    @Override
    protected LikesItemView createItemView() {
        //给itemView设置长按监听
        LikesItemView likesItemView = new LikesItemView(getContext());
        likesItemView.setOnItemLongClickListener(new LikesItemView.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(String newsId, String userId) {
                cancel(newsId, userId);
            }
        });
        return likesItemView;
    }

    //退出登录时要清空收藏列表
    public void clear() {
        adapter.clear();
    }

    //给itemView设置长按监听
    private void cancel(String newsId, String userId) {
        Call<CollectResult> call = BombClient.getInstance().getNewsApi().unCollectNews(
                newsId,
                userId);
        call.enqueue(new Callback<CollectResult>() {
            @Override
            public void onResponse(Call<CollectResult> call, Response<CollectResult> response) {
                CollectResult collectResult = response.body();
                if (collectResult.isSuccess()) {
                    ToastUtils.showShort("取消收藏成功");
                    autoRefresh();
                } else {
                    ToastUtils.showShort("取消收藏失败：" + collectResult.getError());
                }
            }

            @Override
            public void onFailure(Call<CollectResult> call, Throwable t) {
                ToastUtils.showShort(t.getMessage());
            }
        });
    }


}
