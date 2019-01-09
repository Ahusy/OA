package code.spxt.cn.updata.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.common.utils.PreferencesUtils;
import com.common.utils.StringUtil;

import java.io.File;

import code.spxt.cn.updata.controller.UpdateActivity;


/**
 */

public class UpdateService extends Service{
    public static final String DOWNLOAD_FOLDER_NAME = "spxt";
    public static final String DOWNLOAD_FILE_NAME = "spxt_client_new.apk";
    public static final String APK_DOWNLOAD_ID = "apkDownloadId";
    private static final String TAG = "UpdateService";
    private String ApkUrl;
    private String notificationTitle;
    private String notificationDescription;
    private DownloadManager downloadManager;
    private CompleteReceiver completeReceiver;

    public UpdateService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            ApkUrl = intent.getStringExtra(UpdateActivity.KEY_UPDATE_URL);
            if (!StringUtil.isEmpty(ApkUrl)) {
                new DownloadApkHelper().execute();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private class DownloadApkHelper {

        DownloadApkHelper() {
            super();
            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            completeReceiver = new CompleteReceiver();
        }

        void execute() {
            //清除已下载的内容重新下载
            long downloadId = PreferencesUtils.getLong(UpdateService.this, APK_DOWNLOAD_ID);
            if (downloadId != -1) {
                downloadManager.remove(downloadId);
                PreferencesUtils.removeSharedPreferenceByKey(UpdateService.this, APK_DOWNLOAD_ID);
            }

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(ApkUrl));

            //设置Notification中显示的文字
            request.setTitle(notificationTitle);
            request.setDescription(notificationDescription);
            request.setNotificationVisibility(
                    (!StringUtil.isEmpty(notificationTitle) || !StringUtil.isEmpty(notificationDescription))
                            ? View.VISIBLE : View.GONE);
            //设置可用的网络类型
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            //设置状态栏中显示Notification
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            //不显示下载界面
            request.setVisibleInDownloadsUi(false);
            //设置下载后文件存放的位置
            File folder = Environment.getExternalStoragePublicDirectory(DOWNLOAD_FOLDER_NAME);
            if (!folder.exists() || !folder.isDirectory()) {
                folder.mkdirs();
            }
            File apkFile = new File(Environment.getExternalStoragePublicDirectory(DOWNLOAD_FOLDER_NAME) + File.separator + DOWNLOAD_FILE_NAME);
            if (apkFile.exists()) {
                apkFile.delete();
            }
            request.setDestinationInExternalPublicDir(DOWNLOAD_FOLDER_NAME, DOWNLOAD_FILE_NAME);
            //设置文件类型
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(ApkUrl));
            request.setMimeType(mimeString);
            long longId = downloadManager.enqueue(request);
            //保存返回唯一的downloadId
            PreferencesUtils.putLong(UpdateService.this, APK_DOWNLOAD_ID, longId);
            Toast.makeText(UpdateService.this, "开始下载更新，请稍后", Toast.LENGTH_LONG).show();

            UpdateService.this.registerReceiver(completeReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        }
    }

    class CompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            /**
             * get the id of download which have download success, if the id is my id and it's status is successful,
             * then install it
             **/
            long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            long downloadId = PreferencesUtils.getLong(context, APK_DOWNLOAD_ID);
            if (completeDownloadId == downloadId) {

                // if download successful
                if (queryDownloadStatus(downloadManager, downloadId) == DownloadManager.STATUS_SUCCESSFUL) {
                    PreferencesUtils.removeSharedPreferenceByKey(context, APK_DOWNLOAD_ID);
                    //install apk
                    String apkFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                            File.separator + DOWNLOAD_FOLDER_NAME + File.separator +
                            DOWNLOAD_FILE_NAME;
                    if (install(context, apkFilePath)) {
                        stopSelf();
                    }

                }
            }
        }
    }

    /**
     * 查询下载状态
     */
    public static int queryDownloadStatus(DownloadManager downloadManager, long downloadId) {
        int result = -1;
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = null;
        try {
            c = downloadManager.query(query);
            if (c != null && c.moveToFirst()) {
                result = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return result;
    }

    /**
     * install app
     * @return whether apk exist
     */
    public static boolean install(Context context, String filePath) {
        File file = new File(filePath);
        if (file.length() > 0 && file.exists() && file.isFile()) {
            if(Build.VERSION.SDK_INT>=24) {//判读版本是否在7.0以上
                Uri apkUri = FileProvider.getUriForFile(context, "code.spxt.cn.fileprovider",file);//在AndroidManifest中的android:authorities值
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                install.setDataAndType(apkUri, "application/vnd.android.package-archive");
                context.startActivity(install);
            } else{
                Intent install = new Intent(Intent.ACTION_VIEW);
                install.setDataAndType(Uri.parse("file://" + filePath), "application/vnd.android.package-archive");
                install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(install);
            }
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(completeReceiver);
    }
}

