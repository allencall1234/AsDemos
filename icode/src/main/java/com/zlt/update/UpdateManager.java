package com.zlt.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import com.zlt.icode.IcodeApplication;
import com.zlt.icode.R;
import com.zlt.icode.common.SharePreferenceUtil;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class UpdateManager {

    private static final String BASE_DOWN_URL = "https://raw.githubusercontent.com/allencall1234/UpdateZone/master";
    private static final String DOWN_URL = BASE_DOWN_URL + File.separator + "version.xml";
    private static final String SAVE_PATH = Environment.getExternalStorageDirectory() + File.separator + "download";
    private static final int MSG_PROGRESS = 100;
    private static final int MSG_FINISH = 101;
    private static final int MSG_SUCCESS = 102;
    private static final int MSG_SHOW_DIALOG = 103;
    private static final int MSG_DISMISS_DIALOG = 104;
    private static final int MSG_DOWNLOAD_FIX_PATCH = 105;
    private static final int MSG_DOWNLOAD_FIX_PATCH_FINISH = 106;
    private Context mContext;
    private HashMap<String, String> mHashMap;
    private Dialog mDialog;
    private TextView titleTextView;
    private TextView process_Text;
    private TextView volume_Text;
    private ProgressBar mProgressBar;
    private LinearLayout loadingLayout;
    public int progress;
    public int MODE = 1;
    private static final int APK = 1;
    private static final int PATCH = 2;


    public Handler mHandler = new Handler(IcodeApplication.getContext().getMainLooper()) {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_PROGRESS:
                    mProgressBar.setProgress(progress);
                    volume_Text
                            .setText(getVolumeBySize(msg.arg2)
                                    + "/"
                                    + getVolumeBySize(msg.arg1));
                    process_Text.setText(progress + "%");
                    break;
                case MSG_FINISH:
                    dismissDialog();
                    installApk();
                    break;
                case MSG_SHOW_DIALOG:
                    showNoticeDialog();
                    break;
                case MSG_DISMISS_DIALOG:
                    dismissDialog();
                    break;
                case MSG_DOWNLOAD_FIX_PATCH:
                    String name = mHashMap.get("patchname");
                    new DownloadThread(name, PATCH).start();
                    break;
                case MSG_DOWNLOAD_FIX_PATCH_FINISH:
                    String patchName = SAVE_PATH + File.separator + mHashMap.get("patchname");
                    try {
                        Log.d("zlt", "patchName = " + patchName);
                        IcodeApplication.mPatchManager.addPatch(patchName);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("zlt", "patchName = " + e.getMessage());
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private static UpdateManager sInstance;

    private UpdateManager(Context context) {
        // TODO Auto-generated constructor stub
        mContext = context;
    }

    public static UpdateManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new UpdateManager(context);
        }
        return sInstance;
    }

    public HashMap getUpdateMap() {
        return mHashMap;
    }

    /**
     * 检测软件更新
     *
     * @throws IOException
     * @throws NotFoundException
     */
    public void checkUpdate(boolean showDialog, final UpdateFailedListener onUpdateFailedListener) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    if (mHashMap == null || mHashMap.size() == 0) {
                        getUpdateMessageFromServer();
                    }

                    if (isVersionUpdate()) {
                        // 显示提示对话框
                        mHandler.sendEmptyMessage(MSG_SHOW_DIALOG);

                    } else {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (onUpdateFailedListener != null) {
                                    onUpdateFailedListener.onFailed();
                                }
                            }
                        });
                    }

                    if (isFixUpdate()) {
                        mHandler.sendEmptyMessage(MSG_DOWNLOAD_FIX_PATCH);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }
    }


    protected void showNoticeDialog() {
        mDialog = new Dialog(mContext, R.style.CommonDialog);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setCancelable(false);
        final View dialogView = LayoutInflater.from(mContext).inflate(
                R.layout.no_app_dialog, null);

        titleTextView = (TextView) dialogView.findViewById(R.id.no_app_title);
        process_Text = (TextView) dialogView.findViewById(R.id.loading_process);
        volume_Text = (TextView) dialogView.findViewById(R.id.loading_volume);
        mProgressBar = (ProgressBar) dialogView
                .findViewById(R.id.loading_progress);
        loadingLayout = (LinearLayout) dialogView
                .findViewById(R.id.loading_layout);

        String title = String.format("检测到新版本(版本号%1$s),是否更新?", mHashMap.get("versionName"));

        titleTextView.setText(title);

        dialogView.findViewById(R.id.no_app_ok).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // // 判断手机中是否已经下载了更新文件，如果下载了直接安装,没有下载就开启线程下载
                        // File file = new File(SAVE_PATH,
                        // mHashMap.get("name").toString());
                        // if (file.exists()) {
                        // installApk();
                        // mDialog.dismiss();
                        // } else {
                        v.setVisibility(View.GONE);
                        ((Button) dialogView.findViewById(R.id.no_app_cancel))
                                .setText("隐藏下载");
                        titleTextView.setText("正在下载更新版本...");
                        mProgressBar.setVisibility(View.VISIBLE);
                        loadingLayout.setVisibility(View.VISIBLE);
                        new DownloadThread(mHashMap.get("name").toString(), APK).start();// 开启下载线程
                    }
                    // }
                });
        dialogView.findViewById(R.id.no_app_cancel).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        mDialog.dismiss();
                    }
                });

        mDialog.setContentView(dialogView);
        mDialog.show();
    }

    protected boolean isVersionUpdate() throws Exception {
        // 获取当前软件版本
        int versionCode = getVersionCode(mContext);// 把version.xml放到网络上，然后获取文件信息
//
        Log.i("zlt", "version = " + mHashMap.get("version") + ",name = " + mHashMap.get("name"));
        if (null != mHashMap) {
            int serviceCode = Integer.valueOf(mHashMap.get("version"));
            // 版本判断
            if (serviceCode > versionCode) {
                return true;
            }
        }
        return false;
    }

    protected boolean isFixUpdate() {
        if (null != mHashMap) {
            int patchVersion = Integer.valueOf(mHashMap.get("patchversion"));
            if (patchVersion > SharePreferenceUtil.getFixPatchVersion()) {
                return true;
            }
        }
        return false;
    }

    protected void getUpdateMessageFromServer() {
        try {
            URL url = new URL(DOWN_URL);
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            InputStream inStream = urlConn.getInputStream();

            // 解析XML文件。 由于XML文件比较小，因此使用DOM方式进行解析
            ParseXmlService service = new ParseXmlService();
            mHashMap = service.parseXml(inStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 下载文件线程
     */
    class DownloadThread extends Thread {

        String fileName;
        int mode;

        DownloadThread(String fileName, int mode) {
            this.fileName = fileName;
            this.mode = mode;
        }

        @Override
        public void run() {
            FileOutputStream fos = null;
            InputStream is = null;
            try {
                // 判断SD卡是否存在，并且是否具有读写权限
//                String mountState = Environment.getExternalStorageState();
//                if (mountState.equals(
//                        Environment.MEDIA_MOUNTED)) {
                // 获得存储卡的路径
                URL url = new URL(BASE_DOWN_URL + File.separator + fileName);
                // 创建连接
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.connect();
                // 获取文件大小
                int length = conn.getContentLength();

                // 创建输入流
                is = conn.getInputStream();

                File file = new File(SAVE_PATH);
                // 判断文件目录是否存在
                if (!file.exists()) {
                    file.mkdir();
                }
                File apkFile = new File(SAVE_PATH, fileName);

                fos = new FileOutputStream(apkFile);
                int count = 0;
                // 缓存
                byte buf[] = new byte[1024];
                // 写入到文件中
                do {
                    int numread = is.read(buf);
                    count += numread;
                    // 计算进度条位置
                    progress = (int) (((float) count / length) * 100);
                    // 更新进度
                    if (mode == APK) {
                        Message message = new Message();
                        message.what = MSG_PROGRESS;
                        message.arg1 = length;
                        message.arg2 = count;
                        mHandler.sendMessage(message);
                    }
                    if (numread <= 0) {
                        // 下载完成
                        if (mode == APK) {
                            mHandler.sendEmptyMessage(MSG_FINISH);
                        } else {
                            mHandler.sendEmptyMessage(MSG_DOWNLOAD_FIX_PATCH_FINISH);
                        }
                        break;
                    }
                    // 写入文件
                    fos.write(buf, 0, numread);
                } while (true);// 点击取消就停止下载.
//                }
            } catch (Exception e) {
                if (mode == APK) {
                    mHandler.sendEmptyMessage(MSG_FINISH);
                } else {
                    mHandler.sendEmptyMessage(MSG_DOWNLOAD_FIX_PATCH_FINISH);
                }
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 安装APK文件
     */
    private void installApk() {
        File apkfile = new File(SAVE_PATH, "ICode.apk");
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        Uri data;
        // 判断版本大于等于7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
            data = FileProvider.getUriForFile(mContext, "zlt.icode.fileprovider", apkfile);
            // 给目标应用一个临时授权
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(apkfile);
        }
        i.setDataAndType(data,
                "application/vnd.android.package-archive");
        mContext.startActivity(i);
    }

    private String getVolumeBySize(int size) {
        String volume;
        String unit = "b";
        if (size > 1000000) {
            volume = String.format("%.1f", (float) size / 1000000);
            unit = "M";
        } else if (size > 1000) {
            volume = String.valueOf(size / 1000);
            unit = "K";
        } else {
            volume = String.valueOf(size);
        }
        return volume + unit;
    }
}
