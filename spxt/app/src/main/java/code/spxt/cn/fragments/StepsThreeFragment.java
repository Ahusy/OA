package code.spxt.cn.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.network.FProtocol;
import com.common.utils.BitmapUtil;
import com.common.utils.ToastUtil;
import com.common.viewinject.annotation.ViewInject;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;

import code.spxt.cn.R;
import code.spxt.cn.adapters.GridImageAdapter;
import code.spxt.cn.adapters.StepsThreeAdapter;
import code.spxt.cn.base.BaseFragment;
import code.spxt.cn.network.Constants;
import code.spxt.cn.network.Parsers;
import code.spxt.cn.network.entity.PhotoCopyEntity;
import code.spxt.cn.network.entity.PhotoEntity;
import code.spxt.cn.network.entity.PicEntity;
import code.spxt.cn.network.entity.StepsThreeEntity;
import code.spxt.cn.utils.ViewInjectUtils;

import static android.app.Activity.RESULT_OK;
import static code.spxt.cn.common.CommonConstant.REQUEST_NET_ONE;
import static code.spxt.cn.common.CommonConstant.REQUEST_NET_TWO;

/**
 * 出差旅费步骤三
 */

public class StepsThreeFragment extends BaseFragment implements View.OnClickListener {

    @ViewInject(R.id.steps_three_money_t)
    private TextView tMoney;
    @ViewInject(R.id.steps_three_money_b)
    private TextView bMoney;
    @ViewInject(R.id.steps_three_add)
    private TextView add;
    @ViewInject(R.id.steps_three_recycler)
    private RecyclerView recyclerView;
    private int chooseMode = PictureMimeType.ofAll();
    private StepsThreeAdapter adapter;
    //    private GridImageAdapter adapter;
    private int indext;
    private ArrayList<PhotoEntity> entities;
    private String flowNo;
    private LocalBroadcastManager instance;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spes_three, container, false);
        ViewInjectUtils.inject(this, view);
        initView();
        registerReceiver();
        return view;
    }

    private void initView() {
        add.setOnClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        entities = new ArrayList<>();
        adapter = new StepsThreeAdapter(getActivity(), entities, onAddPicClickListener);
        recyclerView.setAdapter(adapter);
//        adapter = new GridImageAdapter(getActivity(), onAddPicClickListener);
        Bundle bundle = getArguments();
        assert bundle != null;
        String lowerCaseTotal = bundle.getString("lowerCaseTotal");
        String capitalTotal = bundle.getString("capitalTotal");
        ArrayList<PhotoCopyEntity> picList = bundle.getParcelableArrayList("pic_list");
        if (picList != null) {
            for (PhotoCopyEntity pce : picList) {
                PhotoEntity entity = new PhotoEntity();
                entity.setTypeName(pce.getTypeName());
                entity.setUrlList(pce.getUrlList());
                entities.add(entity);
            }
            adapter.notifyDataSetChanged();
        }

        flowNo = bundle.getString("flow_no");
        tMoney.setText(lowerCaseTotal);
        bMoney.setText(capitalTotal);
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.steps_three_add:
                PhotoEntity entity = new PhotoEntity();
                adapter.addData(0, entity);
                break;
        }
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick(int positions) {
            indext = positions;
            PictureSelector.create(StepsThreeFragment.this)
                    .openGallery(chooseMode)
                    .maxSelectNum(100)
                    .minSelectNum(1)
                    .selectionMode(PictureConfig.MULTIPLE)
                    .glideOverride(160, 160)
                    .previewEggs(true)
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        }
    };

    // 注册广播接收器
    private void registerReceiver() {
        instance = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("clear");
        instance.registerReceiver(mAdDownLoadReceiver, intentFilter);
    }

    private BroadcastReceiver mAdDownLoadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String change = intent.getStringExtra("change");
            if ("three".equals(change)) {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        entities.clear();
                        adapter.notifyDataSetChanged();
                        ToastUtil.show(getContext(), "清除成功");
                    }
                });
            }
        }
    };

    // 注销
    @Override
    public void onDetach() {
        super.onDetach();
        instance.unregisterReceiver(mAdDownLoadReceiver);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    List<LocalMedia> localMedia = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种 path
                    // 1.media.getPath(); 为原图 path
                    // 2.media.getCutPath();为裁剪后 path，需判断 media.isCut();是否为 true
                    // 3.media.getCompressPath();为压缩后 path，需判断 media.isCompressed();是否为 true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    StringBuffer buffer = new StringBuffer();
                    for (LocalMedia media : localMedia) {
                        String bitmapToString = BitmapUtil.bitmapToString(media.getPath(), 1024, 1024);
                        buffer.append(bitmapToString).append(",");
                    }

                    requestUploadImage(buffer.toString());
                    break;
            }
        }
    }

    public ArrayList<PhotoEntity> threeList() {
        if (entities.size() == 0) {
            ToastUtil.show(getContext(), "请添加发票");
            return null;
        }
        for (int i = 0; i < entities.size(); i++) {
            if (TextUtils.isEmpty(entities.get(i).getTypeName())) {
                ToastUtil.show(getContext(), "请选择发票类型");
                return null;
            }
            if (entities.get(i).getUrlList().size() == 0) {
                ToastUtil.show(getContext(), "请上传发票图片");
                return null;
            }
        }

        return entities;
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode) {
            case REQUEST_NET_ONE:
                PicEntity picEntity = Parsers.getPicEntity(data);
                ArrayList<String> urlList = picEntity.getUrlList();
                entities.get(indext).getUrlList().addAll(urlList);
                adapter.notifyDataSetChanged();
                break;
            case REQUEST_NET_TWO:
                StepsThreeEntity stepsThree = Parsers.getStepsThree(data);
                if (stepsThree != null) {
                    List<StepsThreeEntity.PicListBean> picList = stepsThree.getPicList();
                    if (picList != null && picList.size() > 0) {
                        for (int i = 0; i < picList.size(); i++) {
                            PhotoEntity entity = new PhotoEntity();
                            entity.setTypeName(picList.get(i).getType());
                            adapter.addData(0, entity);
                        }

                    }
                }
                break;
        }
    }

    private void selectPicListThree() {
        showProgressDialog();
        IdentityHashMap<String, String> params = new IdentityHashMap<>();
        params.put("flow_no", flowNo);
        requestHttpData(Constants.Urls.URL_POST_SELECT_THREE, REQUEST_NET_TWO, FProtocol.HttpMethod.POST, params);
    }

    private void requestUploadImage(String alipayCode) {
        showProgressDialog();
        IdentityHashMap<String, String> params = new IdentityHashMap<>();
        params.put("images_path", alipayCode);
        requestHttpData(Constants.Urls.URL_PICTUREUPLOAD, REQUEST_NET_ONE, FProtocol.HttpMethod.POST, params);
    }

}
