package com.carlisle.incubators.UpdateApp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;

import com.carlisle.incubators.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadService extends Service {
    private final static String TAG = DownloadService.class.getName();

    private final static int DOWNLOAD_FAIL = 0;
    private final static int DOWNLOAD_COMPLETE = 1;
    private final static String DOWNLOAD_URL = "app_url";
    private final static String SAVE_PATH = "/Download/";
    private final static String FILE_SUFFIX = ".apk";

    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;

    private String downloadUrl;
    private File saveDir;
    private File saveFile;

    private int downloadCount = 0;
    private int currentSize = 0;
    private long totalSize = 0;
    private int updateTotalSize = 0;
    private int notifyId = 102;
    private Thread downloadThread;

    public static Intent buildService(Context context, String downloadUrl) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(DownloadService.DOWNLOAD_URL, downloadUrl);
        context.startService(intent);
        return intent;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        downloadUrl = intent.getStringExtra(DOWNLOAD_URL);
        if (!downloadUrl.contains("http://")) {
            downloadUrl = "http://" + downloadUrl;
        }
        initSavePathAndFileName();
        initNotify();
        startDownloadNotify();
        return super.onStartCommand(intent, flags, startId);
    }

    private void initSavePathAndFileName() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            saveDir = new File(Environment.getExternalStorageDirectory(), SAVE_PATH);
            saveFile = new File(saveDir.getPath(), getString(R.string.app_name) + FILE_SUFFIX);
        }
    }

    private PendingIntent getDefalutIntent(int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, getInstallIntent(), flags);
        return pendingIntent;
    }

    private Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (DOWNLOAD_COMPLETE == msg.what) {
                builder.setContentText(getString(R.string.download_complete))
                        .setContentIntent(getDefalutIntent(0))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setProgress(0, 0, false)
                        .setAutoCancel(true);
                notificationManager.notify(notifyId, builder.build());
                installAPK();
            } else if (DOWNLOAD_FAIL == msg.what) {
                builder.setContentText(getString(R.string.download_failed))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setProgress(0, 0, false);
                notificationManager.notify(notifyId, builder.build());
            }

            stopSelf();
        }
    };

    private void initNotify() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(this);
        builder.setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(false)
                .setSmallIcon(android.R.drawable.stat_sys_download);
    }

    private void startDownloadNotify() {
        if (downloadThread != null && downloadThread.isAlive()) {
//			downloadThread.start();
        } else {
            downloadThread = new Thread(new updateRunnable());
            downloadThread.start();
        }
    }

    private void setNotify(int progress) {
        builder.setProgress(100, progress, false);
        notificationManager.notify(notifyId, builder.build());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private long downloadUpdateFile(String downloadUrl, File saveFile) throws Exception {
        HttpURLConnection httpConnection = null;
        InputStream is = null;
        FileOutputStream fos = null;

        try {
            URL url = new URL(downloadUrl);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestProperty("User-Agent", "PacificHttpClient");

            if (currentSize > 0) {
                httpConnection.setRequestProperty("RANGE", "bytes=" + currentSize + "-");
            }

            httpConnection.setConnectTimeout(10000);
            httpConnection.setReadTimeout(20000);
            updateTotalSize = httpConnection.getContentLength();

            if (httpConnection.getResponseCode() == 404) {
                throw new Exception("fail!");
            }

            is = httpConnection.getInputStream();
            fos = new FileOutputStream(saveFile, false);
            byte buffer[] = new byte[4096];
            int readsize = 0;

            while ((readsize = is.read(buffer)) > 0) {
                fos.write(buffer, 0, readsize);
                totalSize += readsize;
                if ((downloadCount == 0) || (int) (totalSize * 100 / updateTotalSize) - 10 >= downloadCount) {
                    downloadCount += 10;
                    setNotify(downloadCount);
                }
            }
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
            if (is != null) {
                is.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
        return totalSize;
    }

    class updateRunnable implements Runnable {
        Message message = updateHandler.obtainMessage();

        public void run() {
            message.what = DOWNLOAD_COMPLETE;

            try {
                long downloadSize = downloadUpdateFile(downloadUrl, saveFile);
                if (downloadSize > 0) {
                    updateHandler.sendMessage(message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                message.what = DOWNLOAD_FAIL;
                updateHandler.sendMessage(message);
            }
        }
    }

    private Intent getInstallIntent() {
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        installIntent.setDataAndType(Uri.fromFile(saveFile), "application/vnd.android.package-archive");
        installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return installIntent;
    }

    private void installAPK() {
        startActivity(getInstallIntent());
    }

    private boolean isExist() {
        boolean temp = false;

        try {
            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            } else {
                temp = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return temp;
    }
}