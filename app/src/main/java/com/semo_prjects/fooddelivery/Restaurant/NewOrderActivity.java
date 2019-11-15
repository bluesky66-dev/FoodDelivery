package com.semo_prjects.fooddelivery.Restaurant;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.semo_prjects.fooddelivery.Database.FirebaseDatabaseHelper;
import com.semo_prjects.fooddelivery.Database.Order;
import com.semo_prjects.fooddelivery.R;

import java.io.IOException;
import java.util.List;

public class NewOrderActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference myRef;
    FirebaseDatabase mDatabase;
    private EditText mNameEditText;
    private EditText mNumberEditText;
    private EditText mLocation;
    private EditText mPrice;
    private Button mAddButton;
    private int orderNumber;
    private Button mButton;
    private SurfaceView mSurfaceView;
    private BarcodeDetector mBarcodeDetector;
    private CameraSource mCameraSource;
    private boolean isPressed = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance();
        mNameEditText=findViewById(R.id.nameEditText);
        mNumberEditText =findViewById(R.id.numberEditText);
        mLocation =findViewById(R.id.locationEditText);
        mPrice=findViewById(R.id.priceEditText);
        mAddButton =findViewById(R.id.addButton);
        mButton =findViewById(R.id.button);
        myRef=mDatabase.getReference();
        mSurfaceView = findViewById(R.id.surfaceView);
        mBarcodeDetector = new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats(Barcode.QR_CODE).build();

        String id=FirebaseDatabase.getInstance().getReference().push().getKey();


        mCameraSource = new CameraSource.Builder(getApplicationContext(), mBarcodeDetector).setAutoFocusEnabled(true)
                .setRequestedPreviewSize(480, 480).build();


        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewOrder();
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CAMERA)
                        !=PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.CAMERA},10001);
                }else {
                    if (!isPressed) {
                        mNameEditText.setVisibility(View.INVISIBLE);
                        mNumberEditText.setVisibility(View.INVISIBLE);
                        mPrice.setVisibility(View.INVISIBLE);
                        mSurfaceView.setVisibility(View.VISIBLE);

                        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                            @Override
                            public void surfaceCreated(SurfaceHolder holder) {
                                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                    return;
                                }
                                try {
                                    mCameraSource.start(holder);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                            }

                            @Override
                            public void surfaceDestroyed(SurfaceHolder holder) {
                                mCameraSource.stop();

                            }
                        });

                        mBarcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
                            @Override
                            public void release() {
                                mBarcodeDetector.release();
                                Toast.makeText(getApplicationContext(), "Barcode was relesed", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void receiveDetections(Detector.Detections<Barcode> detections) {
                                final SparseArray<Barcode> qrCode = detections.getDetectedItems();
                                try {
                                    if (!qrCode.valueAt(0).displayValue.equals(mLocation.getText().toString().trim())) {

                                        mLocation.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                                                vibrator.vibrate(100);
                                                mLocation.setText(qrCode.valueAt(0).displayValue);
                                                mLocation.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.fade_out));
                                                //mMediaPlayer=null;

                                            }
                                        });
                                    }


                                } catch (Exception e) {

                                }

                            }
                        });
                        isPressed = true;
                    } else if (isPressed == true) {
                        mSurfaceView.setVisibility(View.INVISIBLE);
                        mNameEditText.setVisibility(View.VISIBLE);
                        mNumberEditText.setVisibility(View.VISIBLE);
                        mPrice.setVisibility(View.VISIBLE);
                        mCameraSource.stop();

                        isPressed = false;
                    }
                }

            }
        });

    }



   //Add restaurant name from database to the order along with the name,number and location of the client
    private void addNewOrder() {
        mDatabase.getReference().child("Accounts").child(currentUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Order order=new Order();

                order.setRestaurantName(dataSnapshot.child("name").getValue(String.class));
                order.setName(mNameEditText.getText().toString());
                order.setLocation(mLocation.getText().toString());
                order.setRestaurantUID(currentUser.getUid());
                order.setPrice(mPrice.getText().toString());
                order.setNumber(mNumberEditText.getText().toString());
                order.setOrderNumber(orderNumber);
                order.setOrderStatuse("Pending");
                order.setDriverUID("Pending");
                order.setDriverAccepted(false);
                order.setCancellationReason("None");
                String id=myRef.push().getKey();

                new FirebaseDatabaseHelper().addNewOrder(order, id, new FirebaseDatabaseHelper.OrdersDataStatus() {
                    @Override
                    public void DataIsLoaded(List<Order> orders, List<String> keys) {

                    }

                    @Override
                    public void DataIsInserted() {
                        Toast.makeText(NewOrderActivity.this, "Order added", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void DataIsDeleted() {

                    }

                    @Override
                    public void DataIsUpdated() {

                    }
                });
                /*myRef.child(id).setValue(order);
                myRef=mDatabase.getReference().child("My_Orders").child(currentUser.getUid());
                myRef.child(id).setValue(order);*/



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
