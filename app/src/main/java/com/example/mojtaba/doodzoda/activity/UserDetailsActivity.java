package com.example.mojtaba.doodzoda.activity;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mojtaba.doodzoda.R;
import com.example.mojtaba.doodzoda.model.Comment;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class UserDetailsActivity extends AppCompatActivity {

    @BindView(R.id.comment_rate_up)
    ImageView mCommentsRateUpImage;

    @BindView(R.id.comment_rate_up_text)
    TextView mCommentsRateUpText;

    @BindView(R.id.comment_rate_down)
    ImageView mCommentsRateDownImage;

    @BindView(R.id.comment_rate_down_text)
    TextView mCommentsRateDownText;

    @BindView(R.id.user_avatar)
    ImageView mCommentUserAvatar;

    private final String COMMENT_DATA_OBJECT = "comment_data_object";

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        ButterKnife.bind(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getResources().getString(R.string.font_address))
                .setFontAttrId(R.attr.fontPath).build()
        );

//        getWindow().setStatusBarColor(android.R.color.transparent);
        getSupportActionBar().hide();

        Comment commentData = getIntent().getParcelableExtra(COMMENT_DATA_OBJECT);

        mCommentsRateDownText.setText(String.valueOf(commentData.getDownRate()));
        mCommentsRateUpText.setText(String.valueOf(commentData.getUpRate()));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mCommentsRateUpImage.setTransitionName(getResources().getString(R.string.rate_up_shared_transition_name));
            mCommentsRateUpText.setTransitionName(getResources().getString(R.string.rate_up_text_shared_transition_name));
            mCommentsRateDownImage.setTransitionName(getResources().getString(R.string.rate_down_shared_transition_name));
            mCommentsRateDownText.setTransitionName(getResources().getString(R.string.rate_down_text_shared_transition_name));
            mCommentUserAvatar.setTransitionName(getResources().getString(R.string.user_avatar_text_shared_transition_name));

            getWindow().setSharedElementEnterTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element));

            Transition fadeInTransition = TransitionInflater.from(this).inflateTransition(R.transition.fadein);
            Transition fadeOutTransition = TransitionInflater.from(this).inflateTransition(R.transition.fadeout);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
