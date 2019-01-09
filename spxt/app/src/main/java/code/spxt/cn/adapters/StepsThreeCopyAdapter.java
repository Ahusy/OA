package code.spxt.cn.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import code.spxt.cn.R;
import code.spxt.cn.network.entity.PhotoCopyEntity;

public class StepsThreeCopyAdapter extends RecyclerView.Adapter<StepsThreeCopyAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<PhotoCopyEntity> mDatas;

    public StepsThreeCopyAdapter(Context context, ArrayList<PhotoCopyEntity> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public StepsThreeCopyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_recycler_copy, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StepsThreeCopyAdapter.MyViewHolder holder, int position) {
        holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        GridImageCopyAdapter adapter = new GridImageCopyAdapter(context, mDatas.get(position).getUrlList());
        holder.recyclerView.setAdapter(adapter);
        String name = mDatas.get(position).getTypeName();
        if (TextUtils.isEmpty(name)) {
            holder.chooseType.setText("");
        } else {
            holder.chooseType.setText(mDatas.get(position).getTypeName());
        }
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

        RecyclerView recyclerView;
        TextView chooseType;

        MyViewHolder(View itemView) {
            super(itemView);
            chooseType = itemView.findViewById(R.id.item_choose_type);
            recyclerView = itemView.findViewById(R.id.recycler);
        }
    }

}
