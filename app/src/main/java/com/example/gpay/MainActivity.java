package com.example.gpay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText amount, note, name, upivirtualid;
    Button send;
    String TAG ="main";
    final int UPI_PAYMENT = 0;
    Dialog dialog;
    Button otp1;
    public static int NOTIFICATION_ID = 154;
    private static String NOTIFICATION_CHANNEL_ID = "AtlasNotification";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // notifications(MainActivity.this);
        // below 2 lines for pop up on alert
dialog = new Dialog(this);
ShowPopup(this);

        send = (Button) findViewById(R.id.send);
        amount = (EditText)findViewById(R.id.amount_et);
        note = (EditText)findViewById(R.id.note);
        name = (EditText) findViewById(R.id.name);
        upivirtualid =(EditText) findViewById(R.id.upi_id);
otp1 = (Button) findViewById(R.id.otp);
otp1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        // notifications added
        notifications(MainActivity.this);
    }
});

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Getting the values from the EditTexts
                if (TextUtils.isEmpty(name.getText().toString().trim())){
                    Toast.makeText(MainActivity.this," Name is invalid", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(upivirtualid.getText().toString().trim())){
                    Toast.makeText(MainActivity.this," UPI ID is invalid", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(note.getText().toString().trim())){
                    Toast.makeText(MainActivity.this," Note is invalid", Toast.LENGTH_SHORT).show();

                }else if (TextUtils.isEmpty(amount.getText().toString().trim())){
                    Toast.makeText(MainActivity.this," Amount is invalid", Toast.LENGTH_SHORT).show();
                }else{

                    payUsingUpi(name.getText().toString(), upivirtualid.getText().toString(),
                            note.getText().toString(), amount.getText().toString());

                }


            }
        });
    }


    void payUsingUpi(  String name,String upiId, String note, String amount) {
        Log.e("main ", "name "+name +"--up--"+upiId+"--"+ note+"--"+amount);
        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                //.appendQueryParameter("mc", "")
                //.appendQueryParameter("tid", "02125412")
                //.appendQueryParameter("tr", "25584584")
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                //.appendQueryParameter("refUrl", "blueapp")
                .build();
// this code is only for google play
        String GOOGLE_PAY_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
        int GOOGLE_PAY_REQUEST_CODE = 123;

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setPackage(GOOGLE_PAY_PACKAGE_NAME);
        MainActivity.this.startActivityForResult(intent, GOOGLE_PAY_REQUEST_CODE);

        // this code is for all UPI id installed  in phone

        // Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        //upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        //Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        //if(null != chooser.resolveActivity(getPackageManager())) {
         //   startActivityForResult(chooser, UPI_PAYMENT);
        //} else {
        //    Toast.makeText(MainActivity.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
       // }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("main ", "response "+resultCode );
        /*
       E/main: response -1
       E/UPI: onActivityResult: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPIPAY: upiPaymentDataOperation: txnId=AXI4a3428ee58654a938811812c72c0df45&responseCode=00&Status=SUCCESS&txnRef=922118921612
       E/UPI: payment successfull: 922118921612
         */
        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        Log.e("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        Log.e("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    //when user simply back without payment
                    Log.e("UPI", "onActivityResult: " + "Return data is null");
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        if (isConnectionAvailable(MainActivity.this)) {
            String str = data.get(0);
            Log.e("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }

            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(MainActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "payment successfull: "+approvalRefNo);
            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(MainActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "Cancelled by user: "+approvalRefNo);

            }
            else {
                Toast.makeText(MainActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "failed payment: "+approvalRefNo);

            }
        } else {
            Log.e("UPI", "Internet issue: ");

            Toast.makeText(MainActivity.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
    public void ShowPopup(MainActivity v) {
        TextView txtclose;

        dialog.setContentView(R.layout.custom);
        txtclose =(TextView) dialog.findViewById(R.id.txtclose);


        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
public void notifications(Context context)
{ int no=2343;
    Intent notificationIntent = new Intent(context, MainActivity.class);
    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);
    Notification.Builder builder = new Notification.Builder(context).
            setSmallIcon(R.drawable.ic_textsms_black_24dp).
            setContentTitle("Atlas").
            setAutoCancel(false).setOngoing(true)
            .setContentText("otp for login"+no).setContentIntent(pendingIntent);
    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "ATLAS", importance);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.GREEN);
        notificationChannel.enableVibration(false);
        notificationChannel.setSound(null, null);
        notificationChannel.canShowBadge();
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        notificationManager.createNotificationChannel(notificationChannel);
    }

    notificationManager.notify(NOTIFICATION_ID, builder.build());

}

}
