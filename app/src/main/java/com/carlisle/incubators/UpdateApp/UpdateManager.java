package com.carlisle.incubators.UpdateApp;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import com.carlisle.incubators.BuildConfig;
import com.carlisle.incubators.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class UpdateManager {
    private static final String TAG = UpdateManager.class.getName();

    private final static String CAN_UPDATE = "canUpdate";
    private final static String IGNORE_UPDATE = "ignoreUpdate";
    private String UPDATE_URL = "http://tt.device.baidao.com/jry-device/canUpdateApp.do?";
    private String UPDATE_URL_TEST = "http://tt.device.baidao.com/jry-device/canUpdateApp.do?";

    private static UpdateManager instance;
    private boolean checkUpdateByUser = false;
    private Context context;
    private AsyncTask task;
    private String params;

    public static UpdateManager getInstance(Context context) {
        if (instance == null) {
            instance = new UpdateManager();
            instance.context = context;
        }
        return instance;
    }

    public void checkUpdateWithParams(int marketType, String versionId) {
        params = "marketType=" + marketType + "&versionId=" + versionId;
        checkUpdate();
    }

    public void checkUpdateWithParams(boolean checkUpdateByUser, int marketType, String versionId) {
        this.checkUpdateByUser = checkUpdateByUser;
        params = "marketType=" + marketType + "&versionId=" + versionId;
        checkUpdate();
    }

    private void checkUpdate() {
        if (isServiceRunning(context, DownloadService.class.getName())) {
            Toast.makeText(context, R.string.downloading, Toast.LENGTH_SHORT).show();
            return;
        }

        task = new AsyncTask() {
            @Override
            protected String doInBackground(Object[] params) {
                return sendRequest();
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                handleResponse((String) o);
            }
        };
        task.execute();
    }

    private String sendRequest() {
        String result = null;
        try {
            URL obj = new URL((BuildConfig.DEBUG ? UPDATE_URL_TEST : UPDATE_URL) + params);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                result = response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private void handleResponse(String response) {
        if (TextUtils.isEmpty(response)) {
            Toast.makeText(context, R.string.connect_error, Toast.LENGTH_SHORT).show();
            return;
        }

        UpdateAppResult result = new UpdateAppResult();

        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("description")) {
                result.description = jsonObject.getString("description");
            }
            if (jsonObject.has("versionId")) {
                result.versionId = jsonObject.getString("versionId");
            }
            if (jsonObject.has("forceUpdate")) {
                result.forceUpdate = jsonObject.getBoolean("forceUpdate");
            }
            if (jsonObject.has("canUpdate")) {
                result.canUpdate = jsonObject.getBoolean("canUpdate");
            }
            if (jsonObject.has("appUrl")) {
                result.appUrl = jsonObject.getString("appUrl");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!result.forceUpdate) {
            if (result.canUpdate) {
                showNoticeDialog(result);
            } else {
                if (checkUpdateByUser) {
                    Toast.makeText(context, R.string.no_need_update, Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            context.startService(DownloadService.buildService(context, result.appUrl));
        }
    }

    private void showNoticeDialog(final UpdateAppResult result) {
//        if (!checkUpdateByUser) {
//            if (getIgnoreVersionId(context).equals(result.versionId)) {
//                return;
//            }
//        }

        AlertDialog dlg = new AlertDialog.Builder(context)
                .setTitle(R.string.have_new_version)
                .setMessage(result.description)
                .setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startService(DownloadService.buildService(context, result.appUrl));
                        Toast.makeText(context, R.string.downloading, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.ignore, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        saveIgnoreVersionId(context, result.versionId);
                        dialog.dismiss();
                    }
                }).create();
        dlg.show();
    }

    private void saveIgnoreVersionId(Context context, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).edit();
        editor.putString(IGNORE_UPDATE, value);
        editor.commit();
    }

    private String getIgnoreVersionId(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE).getString(IGNORE_UPDATE, null);
    }

    private boolean isServiceRunning(Context mContext, String className) {
        boolean isRun = false;
        ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(40);
        int size = serviceList.size();
        for (int i = 0; i < size; i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRun = true;
                break;
            }
        }
        return isRun;
    }

}