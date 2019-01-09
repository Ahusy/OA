package code.spxt.cn.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

import code.spxt.cn.R;
import code.spxt.cn.utils.DialogUtils;
import code.spxt.cn.utils.ImageUtils;

public class GridImageCopyAdapter extends
        RecyclerView.Adapter<GridImageCopyAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<String> urlList;

    private Context context;


    public GridImageCopyAdapter(Context context, ArrayList<String> urlList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.urlList = urlList;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImg;

        public ViewHolder(View view) {
            super(view);
            mImg = view.findViewById(R.id.fiv);
        }
    }

    @Override
    public int getItemCount() {
        return urlList == null ? 0 : urlList.size();
    }


    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(mInflater.inflate(R.layout.gv_filter_image_copy, viewGroup, false));
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        ImageUtils.setImgUrl(viewHolder.mImg, urlList.get(position));
        viewHolder.mImg.setOnClickListener(v -> DialogUtils.showImgDialog(context, urlList.get(position)));
    }

}
