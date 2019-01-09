package code.spxt.cn.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.common.viewinject.annotation.ViewInject;

import code.spxt.cn.R;
import code.spxt.cn.base.ToolBarActivity;
import code.spxt.cn.utils.ViewInjectUtils;

/**
 * 公告详情
 */
public class NoticeDetailActivity extends ToolBarActivity {

    @ViewInject(R.id.notice_detail_title)
    private TextView detailTitle;
    @ViewInject(R.id.notice_detail_time)
    private TextView detailTime;
    @ViewInject(R.id.notice_detail_content)
    private TextView detailContent;
    @ViewInject(R.id.web_view)
    private WebView webView;

    public static void startNoticeDetail(Context context,String title,String content,String time){
        Intent intent = new Intent(context,NoticeDetailActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        intent.putExtra("time",time);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);
        ViewInjectUtils.inject(this);
        setCenterTitle("公告详情");
        initView();
    }

    private void initView() {
//        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");
//        String time = getIntent().getStringExtra("time");
//        detailTitle.setText(title);
        detailContent.setText(content);
//        String times = DateUtils.getDateToString(Long.parseLong(time));
//        detailTime.setText(times);
        webView.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
    }
}
