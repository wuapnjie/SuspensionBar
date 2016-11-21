package com.xiaopo.flying.suspensionbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mFeedList;
    private RelativeLayout mSuspensionBar;
    private TextView mSuspensionTv;
    private ImageView mSuspensionIv;
    private int mCurrentPosition = 0;

    private int mSuspensionHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSuspensionBar = (RelativeLayout) findViewById(R.id.suspension_bar);
        mSuspensionTv = (TextView) findViewById(R.id.tv_nickname);
        mSuspensionIv = (ImageView) findViewById(R.id.iv_avatar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.item_jump) {
                    Intent intent = new Intent(MainActivity.this, MultiActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        final FeedAdapter adapter = new FeedAdapter();


        mFeedList = (RecyclerView) findViewById(R.id.feed_list);
        mFeedList.setLayoutManager(linearLayoutManager);
        mFeedList.setAdapter(adapter);
        mFeedList.setHasFixedSize(true);

        mFeedList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mSuspensionHeight = mSuspensionBar.getHeight();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                View view = linearLayoutManager.findViewByPosition(mCurrentPosition + 1);
                if (view != null) {
                    if (view.getTop() <= mSuspensionHeight) {
                        mSuspensionBar.setY(-(mSuspensionHeight - view.getTop()));
                    } else {
                        mSuspensionBar.setY(0);
                    }
                }

                if (mCurrentPosition != linearLayoutManager.findFirstVisibleItemPosition()) {
                    mCurrentPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    mSuspensionBar.setY(0);

                    updateSuspensionBar();
                }
            }
        });

        updateSuspensionBar();
    }

    private void updateSuspensionBar() {
        Picasso.with(MainActivity.this)
                .load(getAvatarResId(mCurrentPosition))
                .centerInside()
                .fit()
                .into(mSuspensionIv);

        mSuspensionTv.setText("Taeyeon " + mCurrentPosition);
    }

    private int getAvatarResId(int position) {
        switch (position % 4) {
            case 0:
                return R.drawable.avatar1;
            case 1:
                return R.drawable.avatar2;
            case 2:
                return R.drawable.avatar3;
            case 3:
                return R.drawable.avatar4;
        }
        return 0;
    }
}
