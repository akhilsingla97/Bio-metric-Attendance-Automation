package com.example.akhilsingla.biometricattendancesystem;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import SecuGen.FDxSDKPro.JSGFPLib;
import SecuGen.FDxSDKPro.SGAutoOnEventNotifier;
import SecuGen.FDxSDKPro.SGDeviceInfoParam;
import SecuGen.FDxSDKPro.SGFDxDeviceName;
import SecuGen.FDxSDKPro.SGFDxErrorCode;
import SecuGen.FDxSDKPro.SGFingerInfo;
import SecuGen.FDxSDKPro.SGFingerPosition;

import static SecuGen.FDxSDKPro.SGImpressionType.SG_IMPTYPE_LP;


public class scanfinger extends AppCompatActivity implements View.OnClickListener{

    String name, sid, fid, sem, course;
    EditText fingerprint;
    private DatabaseReference mDatabase;

    private static final String TAG = "AKHIL";

    private Button mButtonRegister = null;

    private PendingIntent mPermissionIntent;
    private ImageView mImageViewFingerprint;
    private IntentFilter filter;
    private boolean mLed;
    private boolean mAutoOnEnabled;
    private SGAutoOnEventNotifier autoOn;
    hexConversion conversion;

    private JSGFPLib sgfplib;
    FingerPrintReader fingerPrintReader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanfinger);
        Bundle bundle = getIntent().getExtras();
        sem = bundle.getString("sem");
        course = bundle.getString("course");
        name = bundle.getString("name");
        sid = bundle.getString("sid");

        setContentView(R.layout.activity_scanfinger);
        mButtonRegister = (Button) findViewById(R.id.button1);
        mButtonRegister.setOnClickListener(this);
        mImageViewFingerprint = (ImageView) findViewById(R.id.imageView);
        conversion = new hexConversion();
    }

    public void onClick(View v) {

        usbPermission();

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                fingerPrintReader.readFingerPrint();
                mImageViewFingerprint.setImageBitmap(fingerPrintReader
                        .toGrayscale(fingerPrintReader.getFPBitMap()));

                byte[] abc = fingerPrintReader.getHexTemplate();
                String Temp = conversion.getHexString(abc);
                Log.d(TAG, "Template" + Temp);

            }
        });

    }

    private void usbPermission() {
        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(
                ACTION_USB_PERMISSION), 0);
        filter = new IntentFilter(ACTION_USB_PERMISSION);
        registerReceiver(mUsbReceiver, filter);
        sgfplib = new JSGFPLib((UsbManager) getSystemService(Context.USB_SERVICE));
        mLed = false;
        mAutoOnEnabled = false;
        long error = sgfplib.Init(SGFDxDeviceName.SG_DEV_AUTO);
        UsbDevice usbDevice = sgfplib.GetUsbDevice();
        sgfplib.GetUsbManager().requestPermission(usbDevice, mPermissionIntent);
        error = sgfplib.OpenDevice(0);
        SecuGen.FDxSDKPro.SGDeviceInfoParam deviceInfo = new SecuGen.FDxSDKPro.SGDeviceInfoParam();
        // error = sgfplib.GetDeviceInfo(deviceInfo);

        fingerPrintReader = new FingerPrintReader(mImageViewFingerprint,
                sgfplib);
    }

    // USB Device Attach Permission
    private static final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice) intent
                            .getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(
                            UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {

                            Log.d(TAG, "Vender ID" + device.getVendorId());

                            Log.d(TAG, "Producat ID " + device.getProductId());

                        } else
                            Log.e(TAG,
                                    "mUsbReceiver.onReceive() Device is null");
                    } else
                        Log.e(TAG,
                                "mUsbReceiver.onReceive() permission denied for device "
                                        + device);
                }
            }
        }
    };


    @Override
    public void onBackPressed(){
        System.gc();
        finish();
    }

    public class student{
        public String name, sid, fid;
        public student(){

        }
        public student(String Name, String sid, String fid){
            this.name = Name;
            this.sid = sid;
            this.fid= fid;
        }
    }

    public void insertStudent(View v){
        try{

            student t = new student(name, sid, fingerprint.getText().toString());
            mDatabase.child("courses").child(course).child("Semester").setValue(sem);
            mDatabase.child("courses").child(course).child("Students").child(sid).setValue(t);
            Toast.makeText(getApplicationContext(), "Student Added Successfully !", Toast.LENGTH_SHORT).show();
            finish();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
