package com.example.mojtaba.doodzoda.activity;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionInflater;

import com.example.mojtaba.doodzoda.R;
import com.example.mojtaba.doodzoda.util.ServerExchange;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CommentsActivity extends AppCompatActivity {

    @BindView(R.id.comments_recycler_view)
    RecyclerView mCommentsRecyclerView;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.bottom_out, R.anim.top_in);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.custom_action_bar_background_color));
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.activity_comments_action_bar_custom_layout);

        ButterKnife.bind(this);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getResources().getString(R.string.font_address))
                .setFontAttrId(R.attr.fontPath).build()
        );

        ServerExchange serverExchange = new ServerExchange(this);
        serverExchange.requestAllComments();

        getSupportActionBar().setTitle(R.string.comment_main_container_title_text);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            getWindow().setSharedElementExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.shared_element));

            Transition explodeTransition = TransitionInflater.from(this).inflateTransition(R.transition.explode);
            Transition fadeInTransition = TransitionInflater.from(this).inflateTransition(R.transition.fadein);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick(R.id.action_bar_back_button)
    public void backButtonPressed() {
        super.onBackPressed();
    }

}
