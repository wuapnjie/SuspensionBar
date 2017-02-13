package com.xiaopo.flying.suspensionbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MultiActivity extends AppCompatActivity {
  private RecyclerView mFeedList;
  private RelativeLayout mSuspensionBar;
  private TextView mSuspensionTv;
  private int mCurrentPosition = 0;

  private int mSuspensionHeight;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_multi);

    mSuspensionBar = (RelativeLayout) findViewById(R.id.suspension_bar);
    mSuspensionTv = (TextView) findViewById(R.id.tv_time);

    final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    final MultiFeedAdapter adapter = new MultiFeedAdapter();

    mFeedList = (RecyclerView) findViewById(R.id.feed_list);
    mFeedList.setLayoutManager(linearLayoutManager);
    mFeedList.setAdapter(adapter);
    mFeedList.setHasFixedSize(true);

    mFeedList.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        mSuspensionHeight = mSuspensionBar.getHeight();
      }

      @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (adapter.getItemViewType(mCurrentPosition + 1) == MultiFeedAdapter.TYPE_TIME) {
          View view = linearLayoutManager.findViewByPosition(mCurrentPosition + 1);
          if (view != null) {
            if (view.getTop() <= mSuspensionHeight) {
              mSuspensionBar.setY(-(mSuspensionHeight - view.getTop()));
            } else {
              mSuspensionBar.setY(0);
            }
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
    mSuspensionTv.setText(getTime(mCurrentPosition));
  }

  private String getTime(int position) {
    return "NOVEMBER " + (1 + position / 4);
  }
}
