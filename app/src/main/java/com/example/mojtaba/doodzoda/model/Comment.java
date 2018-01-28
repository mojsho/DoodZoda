package com.example.mojtaba.doodzoda.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment implements Parcelable {
    @SerializedName("id")
    @Expose
    private int commentId;

    @SerializedName("user_id")
    @Expose
    private int userId;

    @SerializedName("text")
    @Expose
    private String text;

    @SerializedName("rate_up")
    @Expose
    private int upRate;

    @SerializedName("rate_down")
    @Expose
    private int downRate;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUpRate() {
        return upRate;
    }

    public void setUpRate(int upRate) {
        this.upRate = upRate;
    }

    public int getDownRate() {
        return downRate;
    }

    public void setDownRate(int downRate) {
        this.downRate = downRate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.commentId);
        dest.writeInt(this.userId);
        dest.writeString(this.text);
        dest.writeInt(this.upRate);
        dest.writeInt(this.downRate);
    }

    public Comment() {
    }

    protected Comment(Parcel in) {
        this.commentId = in.readInt();
        this.userId = in.readInt();
        this.text = in.readString();
        this.upRate = in.readInt();
        this.downRate = in.readInt();
    }

    public static final Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel source) {
            return new Comment(source);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };
}
