package com.example.gpay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MessageActvity extends AppCompatActivity {
Button msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_actvity);
        msg=(Button)findViewById(R.id.msging);
        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permissioncheck = ContextCompat.checkSelfPermission(MessageActvity.this, Manifest.permission.SEND_SMS);
                if(permissioncheck== PackageManager.PERMISSION_GRANTED)

                {
                    MyMessage();
                }
                else
                {
                    ActivityCompat.requestPermissions(MessageActvity.this,new String[]{Manifest.permission.SEND_SMS},0);
                }

            }
        });
    }
    private void MyMessage()
    {
        SmsManager smsManager = SmsManager.getDefault();
        PendingIntent sentPI;
        String SENT="SMS_SENT";
        sentPI = PendingIntent.getBroadcast(MessageActvity.this,0,new Intent(SENT),0);
        smsManager.sendTextMessage("+919745399320",null,"text",sentPI,null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 0:
                if(grantResults.length>=0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    MyMessage();
                }
                else
                {
                    Toast.makeText(this,"You dont have permission",Toast.LENGTH_LONG);
                }
                    
                break;
        }
        
    }
}
