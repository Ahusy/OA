package code.spxt.cn.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import code.spxt.cn.R;
import code.spxt.cn.network.entity.AddressEntity;

public class StepsOneCopyAdapter extends RecyclerView.Adapter<StepsOneCopyAdapter.MyViewHolder> {

    private ArrayList<AddressEntity> mDatas;

    public StepsOneCopyAdapter( ArrayList<AddressEntity> mDatas) {
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public StepsOneCopyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_steps_one_recycler_copy, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StepsOneCopyAdapter.MyViewHolder holder, int position) {
        String address = mDatas.get(position).getAddress();
        if (TextUtils.isEmpty(address)) {
            holder.address.setText("");
        } else {
            holder.address.setText(address);
        }
        holder.tvView.setText(String.valueOf("地点:"));
    }

    @Override
    public int getItemCount() {
        if (mDatas != null && mDatas.size() > 0) {
            return mDatas.size();
        } else {
            return 0;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView address;
        private final TextView tvView;

        MyViewHolder(View itemView) {
            super(itemView);
            tvView = itemView.findViewById(R.id.tv_view);
            address = itemView.findViewById(R.id.item_steps_one_choose_address);
        }
    }
}
