package com.studentlifemanager.helper;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.studentlifemanager.R;
import com.studentlifemanager.application.MyApplication;
import com.studentlifemanager.fragment.TaskFragment;
import com.studentlifemanager.receiver.MyNotificationPublisher;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by Owner on 08-11-2016.
 */
public class CommonUtils {

    //declare static variables
    public static final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[a-zA-Z0-9+._%-+]{1,256}" + "@" + "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "." + "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");
    public static final Pattern NO_PATTERN = Pattern.compile("[0-9]");

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 111;

    //methods
    public static boolean isConnectedToInternet(Context context) {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate) {
        Date parsed = null;
        String outputDate = "";
        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, Locale.getDefault());

        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);
        } catch (ParseException e) {
            Log.d("", "ParseException - dateFormat");
        }
        return outputDate;
    }

    public static String checkDate(String enddate) {
        String output = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(enddate));
            c.add(Calendar.DAY_OF_MONTH, 30);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            output = sdf1.format(c.getTime());
        } catch (Exception e) {
            Log.e("endDate", e.toString());
        }

        return output;
    }

    public static boolean checkAndRequestPermissions(final Activity activity) {

        int cameraPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        int calendarRdPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CALENDAR);
        int calendarWrPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_CALENDAR);
        int storagePermissionWrtExt = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int storagePermissionReadExt = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int internetPermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.INTERNET);
        int networkStatePermission = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_NETWORK_STATE);
        int finelocation = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        int phonecall = ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (storagePermissionWrtExt != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (storagePermissionReadExt != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (internetPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET);
        }
        if (networkStatePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }
        if (calendarRdPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CALENDAR);
        }
        if (calendarWrPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_CALENDAR);
        }
        if (finelocation != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (phonecall != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return true;
        }
        return false;
    }

    public static boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    public static String getTodaysDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-M-dd");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static String setTodaysDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/M/yyyy");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static String getTimeNow() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("hh:mm a");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static long getInMilliSecond(String input) {
        Date date = null;
        long millisecondsFromNow = 0;
        try {
            date = new SimpleDateFormat("yyyy-M-dd hh:mm a", Locale.ENGLISH).parse(input);
            long milliseconds = date.getTime();
            millisecondsFromNow = milliseconds - (new Date()).getTime();
            return millisecondsFromNow;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return millisecondsFromNow;
    }

    public static String getTom() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        Log.e(MyApplication.TAG, "tomorrow" + tomorrow);
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-M-dd", Locale.ENGLISH);
        String date = sdf3.format(tomorrow);

        return date.toString();
    }

    public static String getOverdue() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        Date Overdue = calendar.getTime();
        Log.e(MyApplication.TAG, "Overdue" + Overdue);
        SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-M-dd", Locale.ENGLISH);
        String date = sdf3.format(Overdue);

        return date.toString();
    }

    public static void showAlert(Context context, String alert) {
        new AlertDialog.Builder(context)
                .setTitle("")
                .setMessage(alert)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public static String getFormatedDate(String strDate, String sourceFormate,
                                         String destinyFormate) {
        SimpleDateFormat df;
        df = new SimpleDateFormat(sourceFormate);

        try {
            Date date = df.parse(strDate);
            df = new SimpleDateFormat(destinyFormate);
            return df.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;


    }

    public static void scheduleNotification(Context context, long delay, int notificationId, String content) {//delay is after how much time(in millis) from current time you want to schedule the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle("Task Today")
                .setContentText(content)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_logo_gray)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent intent = new Intent(context, TaskFragment.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);

        Notification notification = builder.build();

        Intent notificationIntent = new Intent(context, MyNotificationPublisher.class);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    //create image file
    public static File getOutputMediaFile(String directoryName) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory().getPath(), directoryName);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(directoryName, "Oops! Failed create "
                        + directoryName + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("MMMyyyy",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + timeStamp + "_Backup" + ".txt");

        return mediaFile;
    }


}
