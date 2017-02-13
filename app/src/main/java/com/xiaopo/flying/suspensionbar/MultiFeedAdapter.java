package com.xiaopo.flying.suspensionbar;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by snowbean on 16-11-21.
 */
public class MultiFeedAdapter extends RecyclerView.Adapter {
  private static final String TAG = "MultiFeedAdapter";
  public static final int TYPE_TIME = 0;
  public static final int TYPE_FEED = 1;

  @Override public int getItemViewType(int position) {
    if (position % 4 == 0) return TYPE_TIME;
    return TYPE_FEED;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView;
    if (viewType == TYPE_TIME) {
      itemView =
          LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time, parent, false);
      return new TimeHolder(itemView);
    } else {
      itemView =
          LayoutInflater.from(parent.getContext()).inflate(R.layout.item_feed, parent, false);
      return new FeedAdapter.FeedHolder(itemView);
    }
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof TimeHolder) {
      ((TimeHolder) holder).mTvTime.setText(getTime(position));
    } else if (holder instanceof FeedAdapter.FeedHolder) {
      Picasso.with(holder.itemView.getContext())
          .load(getAvatarResId(position))
          .centerInside()
          .fit()
          .into(((FeedAdapter.FeedHolder) holder).mIvAvatar);

      Picasso.with(holder.itemView.getContext())
          .load(getContentResId(position))
          .centerInside()
          .fit()
          .into(((FeedAdapter.FeedHolder) holder).mIvContent);

      ((FeedAdapter.FeedHolder) holder).mTvNickname.setText("Taeyeon " + position);
    }
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

  private int getContentResId(int position) {
    switch (position % 4) {
      case 0:
        return R.drawable.taeyeon_one;
      case 1:
        return R.drawable.taeyeon_two;
      case 2:
        return R.drawable.taeyeon_three;
      case 3:
        return R.drawable.taeyeon_four;
    }
    return 0;
  }

  private String getTime(int position) {
    return "NOVEMBER " + (1 + position / 4);
  }

  @Override public int getItemCount() {
    return 100;
  }

  public static class TimeHolder extends RecyclerView.ViewHolder {
    TextView mTvTime;

    public TimeHolder(View itemView) {
      super(itemView);
      mTvTime = (TextView) itemView.findViewById(R.id.tv_time);
    }
  }
}
