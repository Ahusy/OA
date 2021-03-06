package code.spxt.cn.adapters;

import android.content.Context;
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

public class StepsOneAdapter extends RecyclerView.Adapter<StepsOneAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<AddressEntity> mDatas;
    private View.OnClickListener listener;

    public StepsOneAdapter(Context context, View.OnClickListener listener, ArrayList<AddressEntity> mDatas) {
        this.context = context;
        this.listener = listener;
        this.mDatas = mDatas;
    }

    public void addData(int position, AddressEntity entity) {
        mDatas.add(position, entity);
        notifyDataSetChanged();
    }

    public void removeData(int position) {
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StepsOneAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_steps_one_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StepsOneAdapter.MyViewHolder holder, int position) {
        holder.delete.setTag(position);
        holder.delete.setOnClickListener(v -> removeData(position));
        String address = mDatas.get(position).getAddress();
        if (TextUtils.isEmpty(address)) {
            holder.address.setText("");
        } else {
            holder.address.setText(address);
        }

        holder.address.setTag(position);
        holder.address.setOnClickListener(listener);
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

        TextView address, delete;

        MyViewHolder(View itemView) {
            super(itemView);

            address = itemView.findViewById(R.id.item_steps_one_choose_address);
            delete = itemView.findViewById(R.id.item_steps_one_choose_delete);
        }
    }
}
