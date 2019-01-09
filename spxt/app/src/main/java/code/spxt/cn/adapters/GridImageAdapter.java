package code.spxt.cn.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import code.spxt.cn.R;
import code.spxt.cn.utils.ImageUtils;

public class GridImageAdapter extends
        RecyclerView.Adapter<GridImageAdapter.ViewHolder> {
    public static final int TYPE_CAMERA = 1;
    public static final int TYPE_PICTURE = 2;
    private LayoutInflater mInflater;
    private ArrayList<String> urlList = new ArrayList<>();

    public ArrayList<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(ArrayList<String> urlList) {
        this.urlList = urlList;
    }

    private int selectMax = 100;
    private Context context;
    private int positions;
    /**
     * 点击添加图片跳转
     */
    private onAddPicClickListener mOnAddPicClickListener;

    public interface onAddPicClickListener {
        void onAddPicClick(int positions);
    }

    public GridImageAdapter(Context context, onAddPicClickListener mOnAddPicClickListener, int position) {
        this.context = context;
        this.positions = position;
        mInflater = LayoutInflater.from(context);
        this.mOnAddPicClickListener = mOnAddPicClickListener;
    }

    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImg;
        LinearLayout ll_del;
        TextView tv_duration;

        public ViewHolder(View view) {
            super(view);
            mImg = (ImageView) view.findViewById(R.id.fiv);
            ll_del = (LinearLayout) view.findViewById(R.id.ll_del);
            tv_duration = (TextView) view.findViewById(R.id.tv_duration);
        }
    }

    @Override
    public int getItemCount() {
        if (urlList == null) {
            return 1;
        } else if (urlList.size() < selectMax) {
            return urlList.size() + 1;
        } else {
            return urlList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isShowAddItem(position)) {
            return TYPE_CAMERA;
        } else {
            return TYPE_PICTURE;
        }
    }

    /**
     * 创建ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(mInflater.inflate(R.layout.gv_filter_image,
                viewGroup, false));
    }

    private boolean isShowAddItem(int position) {
        if (urlList == null) {
            return position == 0;
        } else {
            int size = urlList.size() == 0 ? 0 : urlList.size();
            return position == size;
        }
    }

    /**
     * 设置值
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        //少于8张，显示继续添加的图标
        if (getItemViewType(position) == TYPE_CAMERA) {
            viewHolder.mImg.setImageResource(R.drawable.addimg_1x);
            viewHolder.mImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnAddPicClickListener.onAddPicClick(positions);
                }
            });
            viewHolder.ll_del.setVisibility(View.INVISIBLE);
        } else {
            if (urlList.size() != 0) {
                ImageUtils.setImgUrl(viewHolder.mImg, urlList.get(position));
            }
            viewHolder.ll_del.setVisibility(View.VISIBLE);
            viewHolder.ll_del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = viewHolder.getAdapterPosition();
                    // 这里有时会返回-1造成数据下标越界,具体可参考getAdapterPosition()源码，
                    // 通过源码分析应该是bindViewHolder()暂未绘制完成导致，知道原因的也可联系我~感谢
                    if (index != RecyclerView.NO_POSITION) {
                        urlList.remove(index);
                        notifyItemRemoved(index);
                        notifyItemRangeChanged(index, urlList.size());
                    }
                }
            });
//            LocalMedia media = list.get(position);
//            int mimeType = media.getMimeType();
//            String path = "";
//            if (media.isCut() && !media.isCompressed()) {
//                // 裁剪过
//                path = media.getCutPath();
//            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
//                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
//                path = media.getCompressPath();
//            } else {
//                // 原图
//                path = media.getPath();
//            }
//            // 图片
//            if (media.isCompressed()) {
//                Log.i("compress image result:", new File(media.getCompressPath()).length() / 1024 + "k");
//                Log.i("压缩地址::", media.getCompressPath());
//            }
//
//            Log.i("原图地址::", media.getPath());
//            int pictureType = PictureMimeType.isPictureType(media.getPictureType());
//            if (media.isCut()) {
//                Log.i("裁剪地址::", media.getCutPath());
//            }
//            long duration = media.getDuration();
//            viewHolder.tv_duration.setVisibility(pictureType == PictureConfig.TYPE_VIDEO
//                    ? View.VISIBLE : View.GONE);
//            if (mimeType == PictureMimeType.ofAudio()) {
//                viewHolder.tv_duration.setVisibility(View.VISIBLE);
//                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.picture_audio);
//                StringUtils.modifyTextViewDrawable(viewHolder.tv_duration, drawable, 0);
//            } else {
//                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.video_icon);
//                StringUtils.modifyTextViewDrawable(viewHolder.tv_duration, drawable, 0);
//            }
//            viewHolder.tv_duration.setText(DateUtils.timeParse(duration));
//            if (mimeType == PictureMimeType.ofAudio()) {
//                viewHolder.mImg.setImageResource(R.drawable.audio_placeholder);
//            } else {
//                RequestOptions options = new RequestOptions()
//                        .centerCrop()
//                        .placeholder(R.color.color_f6)
//                        .diskCacheStrategy(DiskCacheStrategy.ALL);
//                Glide.with(viewHolder.itemView.getContext())
//                        .load(path)
//                        .apply(options)
//                        .into(viewHolder.mImg);
//            }
            //itemView 的点击事件
            if (mItemClickListener != null) {
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int adapterPosition = viewHolder.getAdapterPosition();
                        mItemClickListener.onItemClick(adapterPosition, v);
                    }
                });
            }
        }
    }

    protected OnItemClickListener mItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position, View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }
}
