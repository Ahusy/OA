package code.spxt.cn.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import code.spxt.cn.R;


/**
 * Android on 2018/9/19.
 */

public abstract class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
     boolean isInit = true;

    public void notifyChanged() {
        isInit = false;
        notifyDataSetChanged();
    }

    class EmptyHolder extends RecyclerView.ViewHolder {

        final LinearLayout llEmpty;

        EmptyHolder(View itemView) {
            super(itemView);
            llEmpty = itemView.findViewById(R.id.ll_empty);
        }
    }

}
