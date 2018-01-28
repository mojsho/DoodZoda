package com.example.mojtaba.doodzoda.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mojtaba.doodzoda.R;
import com.example.mojtaba.doodzoda.activity.MainActivity;
import com.example.mojtaba.doodzoda.activity.UserDetailsActivity;
import com.example.mojtaba.doodzoda.model.Comment;
import com.example.mojtaba.doodzoda.model.User;
import com.example.mojtaba.doodzoda.util.ServerExchange;

import java.util.ArrayList;
import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {
    private Context mContext;
    private int mItemCount;
    private List<Comment> mCommentList = new ArrayList<>();

    private ServerExchange mServerExchange;
    private User mUser;

    private final String COMMENT_DATA_OBJECT = "comment_data_object";

    public CommentsAdapter(Context context, int itemCount) {
        this.mContext = context;
        this.mItemCount = itemCount;
    }

    public CommentsAdapter(Context context, List<Comment> commentList) {
        this.mContext = context;
        this.mCommentList = commentList;
        this.mItemCount = commentList.size();
        this.mServerExchange = new ServerExchange(this.mContext);
    }

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.comment_item, parent, false);
        return new CommentsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CommentsViewHolder holder, final int position) {
        final Comment commentData = mCommentList.get(position);

        mUser = mServerExchange.requestUser(commentData.getUserId());

        holder.mUserAvatar.setImageResource(R.mipmap.man);
        holder.mCommentUserName.setText(mUser.getName());
        holder.mCommentText.setText(commentData.getText());
        holder.mRateUpText.setText(String.valueOf(commentData.getUpRate()));
        holder.mRateDownText.setText(String.valueOf(commentData.getDownRate()));

        if (!(mContext instanceof MainActivity)) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, UserDetailsActivity.class);

                    Pair<View, String> voteUp = Pair.create((View) holder.mRateUpImage, mContext.getResources().getString(R.string.rate_up_shared_transition_name));
                    Pair<View, String> voteTextUp = Pair.create((View) holder.mRateUpText, mContext.getResources().getString(R.string.rate_up_text_shared_transition_name));
                    Pair<View, String> voteDown = Pair.create((View) holder.mRateDownImage, mContext.getResources().getString(R.string.rate_down_shared_transition_name));
                    Pair<View, String> voteTextDown = Pair.create((View) holder.mRateDownText, mContext.getResources().getString(R.string.rate_down_text_shared_transition_name));
                    Pair<View, String> avatar = Pair.create((View) holder.mUserAvatar, mContext.getResources().getString(R.string.user_avatar_text_shared_transition_name));

                    intent.putExtra(COMMENT_DATA_OBJECT, commentData);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ActivityOptionsCompat options = ActivityOptionsCompat.
                                makeSceneTransitionAnimation((Activity) mContext, voteUp, voteDown, avatar);
                        mContext.startActivity(intent, options.toBundle());
                    } else {
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

    class CommentsViewHolder extends RecyclerView.ViewHolder {
        ImageView mUserAvatar, mRateUpImage, mRateDownImage;
        TextView mCommentText, mRateUpText, mRateDownText, mCommentUserName;

        CommentsViewHolder(View itemView) {
            super(itemView);
            mUserAvatar = itemView.findViewById(R.id.comment_user_avatar);
            mCommentUserName = itemView.findViewById(R.id.comment_user_name);
            mCommentText = itemView.findViewById(R.id.comment_text);
            mRateUpText = itemView.findViewById(R.id.comment_rate_up_text);
            mRateDownText = itemView.findViewById(R.id.comment_rate_down_text);
            mRateUpImage = itemView.findViewById(R.id.comment_rate_up);
            mRateDownImage = itemView.findViewById(R.id.comment_rate_down);
        }
    }
}
