package code.spxt.cn.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import code.spxt.cn.R;
import code.spxt.cn.network.entity.ChooseType;
import code.spxt.cn.network.entity.PhotoEntity;
import code.spxt.cn.utils.DialogUtils;

public class StepsThreeAdapter extends RecyclerView.Adapter<StepsThreeAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<PhotoEntity> mDatas;
    GridImageAdapter.onAddPicClickListener listener;

    ArrayList<ChooseType> typeList = new ArrayList<>();

    public StepsThreeAdapter(Context context, ArrayList<PhotoEntity> mDatas, GridImageAdapter.onAddPicClickListener listener) {
        this.context = context;
        this.listener = listener;
        this.mDatas = mDatas;
    }

    public void addData(int position, PhotoEntity entity) {
        mDatas.add(position, entity);
        notifyDataSetChanged();
    }

    public void removeData(int position) {
        mDatas.remove(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StepsThreeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StepsThreeAdapter.MyViewHolder holder, int position) {
        holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        GridImageAdapter adapter = new GridImageAdapter(context, listener, position);
        holder.recyclerView.setAdapter(adapter);
        ArrayList<String> urlList = mDatas.get(position).getUrlList();
        adapter.setUrlList(urlList);
        adapter.setOnItemClickListener((position12, v) -> {
            DialogUtils.showImgDialog(context,urlList.get(position12));
        });
        String name = mDatas.get(position).getTypeName();
        if (TextUtils.isEmpty(name)) {
            holder.chooseType.setText("");
        } else {
            holder.chooseType.setText(mDatas.get(position).getTypeName());
        }
        holder.chooseType.setOnClickListener(v -> {
            typeList.clear();
            View contentView = LayoutInflater.from(context).inflate(R.layout.pop_choose_type, null, false);
            ListView listView = contentView.findViewById(R.id.list_view);
            PopupWindow popupWindow = new PopupWindow(contentView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
//                popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupWindow.setOutsideTouchable(true);
            popupWindow.setTouchable(true);
            popupWindow.showAsDropDown(holder.chooseType, 0, 0);
            ChooseType chooseType = new ChooseType("车船");
            typeList.add(chooseType);
            ChooseType chooseType1 = new ChooseType("住宿");
            typeList.add(chooseType1);
            ChooseType chooseType2 = new ChooseType("餐饮");
            typeList.add(chooseType2);
            ChooseTypeAdapter chooseTypeAdapter = new ChooseTypeAdapter();
            listView.setAdapter(chooseTypeAdapter);

            listView.setOnItemClickListener((parent, view, position1, id) -> {
                mDatas.get(position).setTypeName(typeList.get(position1).getTypeName());
                popupWindow.dismiss();
                notifyDataSetChanged();
            });

        });

        holder.delete.setOnClickListener(v -> {
            mDatas.remove(position);
            notifyDataSetChanged();
        });
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
        TextView chooseType, delete;

        MyViewHolder(View itemView) {
            super(itemView);
            chooseType = itemView.findViewById(R.id.item_choose_type);
            recyclerView = itemView.findViewById(R.id.recycler);
            delete = itemView.findViewById(R.id.item_steps_three_delete);
        }
    }

    class ChooseTypeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return typeList.size();
        }

        @Override
        public Object getItem(int position) {
            return typeList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(context, R.layout.pop_item_list, null);
            TextView mTextView = convertView.findViewById(R.id.text);
            mTextView.setText(typeList.get(position).getTypeName());

            return convertView;
        }

    }

}
