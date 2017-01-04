package com.fuicuiedu.idedemo.videonews.ui.likes;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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

    private UnCollectFragment unCollectFragment;
    private FragmentManager fragmentManager;

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
                //因为每次新闻Id不同，要根据新闻ID来取消收藏，所以每次new一个新的
                unCollectFragment = new UnCollectFragment(newsId, userId);
                unCollectFragment.setListener(onUnCollectSuccessListener);
                unCollectFragment.show(fragmentManager, "unCollectFragment");
            }
        });
        return likesItemView;
    }

    //退出登录时要清空收藏列表
    public void clear() {
        adapter.clear();
    }

    //设置fragmentManager
    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    //取消收藏的对话框要实现的回调方法
    private UnCollectFragment.OnUnCollectSuccessListener onUnCollectSuccessListener = new UnCollectFragment.OnUnCollectSuccessListener() {
        @Override
        public void UnCollectSuccess() {
            //取消收藏成功后，自动刷新收藏列表
            autoRefresh();
        }
    };
}
