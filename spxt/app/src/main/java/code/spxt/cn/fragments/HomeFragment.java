package code.spxt.cn.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.common.network.FProtocol;
import com.common.viewinject.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import code.spxt.cn.R;
import code.spxt.cn.activitys.MyReviewActivity;
import code.spxt.cn.activitys.MySubmitActivity;
import code.spxt.cn.activitys.NoticeActivity;
import code.spxt.cn.activitys.WaitMeHandleActivity;
import code.spxt.cn.base.BaseFragment;
import code.spxt.cn.network.Constants;
import code.spxt.cn.network.Parsers;
import code.spxt.cn.network.entity.NoticeListEntity;
import code.spxt.cn.network.entity.SelectCount;
import code.spxt.cn.utils.DialogUtils;
import code.spxt.cn.utils.SafeSharePreferenceUtil;
import code.spxt.cn.utils.Screen;
import code.spxt.cn.utils.ViewInjectUtils;
import code.spxt.cn.view.BadgeView;
import code.spxt.cn.view.BarChartView;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;

import static code.spxt.cn.common.CommonConstant.REQUEST_NET_ONE;
import static code.spxt.cn.common.CommonConstant.REQUEST_NET_TWO;

/**
 * Created by dell on 2018/12/13
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    @ViewInject(R.id.columnChartView)
    private BarChartView barChartView;
    @ViewInject(R.id.ll_dwsp)
    private LinearLayout llDwsp;
    @ViewInject(R.id.ll_wfqd)
    private LinearLayout llWfqd;
    @ViewInject(R.id.ll_wspd)
    private LinearLayout llWspd;
    @ViewInject(R.id.ll_gzrz)
    private LinearLayout llGzrz;
    @ViewInject(R.id.img_dwsp)
    private ImageView imgDwsp;
    @ViewInject(R.id.img_wfqd)
    private ImageView imgWfqd;
    @ViewInject(R.id.img_wspd)
    private ImageView imgWspd;
    @ViewInject(R.id.img_gzrz)
    private ImageView imgGzrz;
    private BadgeView badgeView;
    @ViewInject(R.id.flipper)
    private ViewFlipper flipper;
    @ViewInject(R.id.home_more)
    private ImageView more;


    @ViewInject(R.id.column_chart_cc)
    private ColumnChartView mColumnChartCc;
    private ColumnChartData data; // 柱形图对应的各种属性
    private boolean hasAxes = true; // 是否要添加横纵轴的属性
    private boolean hasAxesNames = true; // 是否设置横纵轴的名字
    private boolean hasLabels = false; // 是否显示柱形图的数据
    private boolean hasLabelForSelected = true; // 是否点中显示数据
    private static final int COLOR_ONE = Color.parseColor("#b9ceeb");
    private static final int COLOR_TWO = Color.parseColor("#aedefc");
    private static final int COLOR_THREE = Color.parseColor("#87a8d0");
    private static final int COLOR_FOUR = Color.parseColor("#deecfc");
    private boolean isShow = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ViewInjectUtils.inject(this, view);
        Screen.initScreen(getActivity());
        noticeList();
        initView();
        generateSubcolumnsData();
        initBagView();
        return view;
    }

    private void initBagView() {
        badgeView = new BadgeView(getActivity(), imgDwsp);
        badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badgeView.setShadowLayer(0, 0, 0, Color.WHITE);
    }

    @Override
    public void onResume() {
        super.onResume();
        selectCount();
    }

    private void generateSubcolumnsData() {

        int numSubcolumns = 1;
        int numColumns = 4; // 表示总共有四根柱子
        List<Column> columns = new ArrayList<>();
        List<SubcolumnValue> values; // 柱子的属性
        List<AxisValue> axisValueList = new ArrayList<>();
        Float[] floats = {30f, 5f, 35f, 15f}; // 包含柱形图的数值的数组
        int[] colors = {COLOR_ONE, COLOR_TWO, COLOR_THREE, COLOR_FOUR};
        String[] selecedNames = {"员工数", "已审批数", "待审批数", "平均审批速度"}; // 包含柱子的名称的数组

        for (int i = 0; i < numColumns; ++i) {

            values = new ArrayList<>();
            values.add(new SubcolumnValue(floats[i], colors[i])); // 将柱子的数据以及颜色设置给 SubcolumnValue
            axisValueList.add(new AxisValue(i).setLabel(selecedNames[i]));
            Column column = new Column(values); // 设置整根柱子的属性
            column.setHasLabels(hasLabels); // 是否显示柱子的数据
            column.setHasLabelsOnlyForSelected(hasLabelForSelected); // 是否选中显示数据，一般为false
            columns.add(column);
        }
        data = new ColumnChartData(columns);
        data.setFillRatio(0.4f);
        data.setAxisXBottom(new Axis(axisValueList)); // 设置 Y 轴的属性
        data.setAxisYLeft(new Axis()); // 设置 X 轴的属性
        mColumnChartCc.setColumnChartData(data); // 将数据设置给显示柱形图的控件

    }

    private void initView() {
        List<Double> data = new ArrayList<>();
        List<String> monthList = new ArrayList<>();
        data.add(1820.00);
        data.add(1130.00);
        data.add(1253.10);
        data.add(2000.00);
        monthList.add("员工数");
        monthList.add("已审批数");
        monthList.add("待审批数");
        monthList.add("平均审批速度");
        barChartView.setMonthList(monthList);
        barChartView.setData(data);
        barChartView.setOnDraw(true);
        barChartView.start();

        llDwsp.setOnClickListener(this);
        llWfqd.setOnClickListener(this);
        llGzrz.setOnClickListener(this);
        llWspd.setOnClickListener(this);
        more.setOnClickListener(this);
        imgGzrz.setOnClickListener(this);
    }

    @Override
    protected void parseData(int requestCode, String data) {
        super.parseData(requestCode, data);
        switch (requestCode) {
            case REQUEST_NET_ONE:
                NoticeListEntity noticeList = Parsers.getNoticeList(data);
                if (noticeList != null) {
                    List<NoticeListEntity.NoticeListBean> listBeans = noticeList.getNotice_list();
                    if (listBeans != null && listBeans.size() > 0) {
                        for (int i = 0; i < listBeans.size(); i++) {
                            final View view = View.inflate(getContext(), R.layout.item_flipper, null);
                            TextView tv_content = view.findViewById(R.id.item_flipper_text);
                            tv_content.setText(listBeans.get(i).getName());
                            flipper.addView(view);
                        }
                        if (listBeans.size() == 1) {
                            flipper.stopFlipping();
                            flipper.setAutoStart(false);
                        }
                    }
                }
                break;
            case REQUEST_NET_TWO:
                SelectCount selectCount = Parsers.getSelectCount(data);
                SafeSharePreferenceUtil.saveString("foodAllowance", selectCount.getFoodAllowance());
                SafeSharePreferenceUtil.saveString("travelAllowance", selectCount.getTravelAllowance());
                if (selectCount.getCount() != 0) {
                    badgeView.setText(String.valueOf(selectCount.getCount()));
                    badgeView.show();
                    if (isShow == false) {
                        DialogUtils.showHomeDialog(getContext(), "(" + selectCount.getCount() + ")");
                        isShow = true;
                    }
                } else {
                    badgeView.hide();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_dwsp:
                startActivity(new Intent(getActivity(), WaitMeHandleActivity.class));
                break;
            case R.id.ll_wfqd:
                MySubmitActivity.startMySubmitActivity(getActivity());
                break;
            case R.id.ll_wspd:
                startActivity(new Intent(getActivity(), MyReviewActivity.class));
                break;
            case R.id.ll_gzrz:
                break;
            case R.id.home_more:
                NoticeActivity.startNoticeActivity(getContext());
                break;
            case R.id.img_gzrz:
                break;
        }
    }

    public void noticeList() {
        showProgressDialog();
        requestHttpData(Constants.Urls.URL_POST_NOTICELIST, REQUEST_NET_ONE, FProtocol.HttpMethod.POST, null);
    }

    public void selectCount() {
        showProgressDialog();
        requestHttpData(Constants.Urls.URL_POST_SELECT_COUNT, REQUEST_NET_TWO, FProtocol.HttpMethod.POST, null);
    }
}
