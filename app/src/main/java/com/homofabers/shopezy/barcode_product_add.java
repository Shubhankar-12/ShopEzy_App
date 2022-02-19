package com.homofabers.shopezy;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.Image;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.homofabers.shopezy.helpers.MyDBHandler;
import com.homofabers.shopezy.model.ItemData;
import com.homofabers.shopezy.model.stockItem;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class barcode_product_add extends AppCompatActivity {

    ImageView overlay_rect;
    int screenWidth , screenHeight;
    LinearLayout fakeView;
    ImageView back_btn , flashlight_btn;
    Boolean FlashOn = false;

    TextView barcode_number_txt , barcode_value_txt;

    LinearLayout topControl;
    ConstraintLayout product_data_layout;

    TextInputLayout  product_name, cost_price, sell_price,
            mrp_price,profit_price,Off_percent,prod_quantity;

    ImageView update_stock_btn , product_image;

    PreviewView cameraPreviewView ;
    private  final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA" , "android.permission.WRITE_EXTERNAL_STORAGE"};
    private int REQUEST_CODE_PERMISSIONS = 1001;
    ImageAnalysis imageAnalysis;

    Boolean runCamera = true;

    // camera x api refer documentation https://developer.android.com/training/camerax/
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_product_add);

        initializeVars();

        // check for all the permissions
        if (allPermissionsGranted()) {
            startCamera(); //start camera if permission has been granted by user
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }
    }

    private void initializeVars(){

        product_name = findViewById(R.id.product_name);
        cost_price = findViewById(R.id.cost_price);
        sell_price = findViewById(R.id.sell_price);
        mrp_price = findViewById(R.id.mrp_price);
        profit_price = findViewById(R.id.profit_price);
        Off_percent = findViewById(R.id.Off_percent);
        prod_quantity = findViewById(R.id.prod_quantity);
        update_stock_btn = findViewById(R.id.update_stock_btn);
        barcode_value_txt = findViewById(R.id.barcode_value2);
        product_image = findViewById(R.id.product_image);

        update_stock_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                validateAndSaveProduct();
            }
        });

        cost_price.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                float profit_calc = Float.parseFloat(sell_price.getEditText().getText().toString()) -
                        Float.parseFloat(cost_price.getEditText().getText().toString());
                profit_price.getEditText().setText(String.valueOf(profit_calc));
            }
        });
        sell_price.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                float profit_calc = Float.parseFloat(sell_price.getEditText().getText().toString()) -
                        Float.parseFloat(cost_price.getEditText().getText().toString());
                profit_price.getEditText().setText(String.valueOf(profit_calc));

                float off_per = (Float.parseFloat(mrp_price.getEditText().getText().toString()) -
                        Float.parseFloat(sell_price.getEditText().getText().toString())) / Float.parseFloat(mrp_price.getEditText().getText().toString());
                Off_percent.getEditText().setText(String.valueOf(off_per));
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void validateAndSaveProduct() {

        String prod_barcode ="" , prod_text = "" ;
        float CP_text = 0 , SP_text = 0 , MRP_text = 0 , quantity_text = 0;
        try {
            prod_barcode = barcode_value_txt.getText().toString();
            prod_text = product_name.getEditText().getText().toString();
            CP_text = Float.valueOf(cost_price.getEditText().getText().toString());
            SP_text = Float.parseFloat(sell_price.getEditText().getText().toString());
            MRP_text = Float.parseFloat(mrp_price.getEditText().getText().toString());
            quantity_text = Integer.parseInt(prod_quantity.getEditText().getText().toString());
        }catch (Exception e){
            Toast.makeText(this,"failed to add" , Toast.LENGTH_SHORT).show();
        }
        MyDBHandler dbHandler = new MyDBHandler(this);
        stockItem newStockItem = new stockItem(prod_barcode , prod_text , CP_text, SP_text, quantity_text,MRP_text, LocalDateTime.now());
        dbHandler.addStockData(newStockItem);
        Toast.makeText(this,"added successfully", Toast.LENGTH_SHORT).show();
        runCamera = true;
        product_data_layout.setVisibility(View.INVISIBLE);
        topControl.setVisibility(View.VISIBLE);
    }

    private void startCamera() {



        // start camera as well as image analyzer
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraPreviewView = findViewById(R.id.cameraPreviewView);
        overlay_rect = findViewById(R.id.overlay_rect2);
        barcode_number_txt = findViewById(R.id.barcode_number);
        flashlight_btn = findViewById(R.id.flashlight_btn);
        back_btn = findViewById(R.id.back_btn);

        topControl = findViewById(R.id.top_control);
        product_data_layout = findViewById(R.id.product_data_layout);


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        fakeView = findViewById(R.id.fakeView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fakeView.requestLayout();
                screenWidth = fakeView.getMeasuredWidth();
                screenHeight = fakeView.getMeasuredHeight();

            }
        },500);


        imageAnalysis =
                new ImageAnalysis.Builder()
                        // enable the following line if RGBA output is needed.
                        //.setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                        .setTargetResolution(new Size(216, 426))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this), new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(@NonNull ImageProxy imageProxy) {
                int rotationDegrees = imageProxy.getImageInfo().getRotationDegrees();
                // insert your code here.

                @SuppressLint("UnsafeOptInUsageError")
                Image mediaImage = imageProxy.getImage();
                if (mediaImage != null) {
                    InputImage image =
                            InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
                    if(runCamera) {
                        try {
                            barcodeAnalysis(image, imageProxy);
                        }catch (Exception e){
                            Toast.makeText(barcode_product_add.this,"model not yet loaded" , Toast.LENGTH_LONG).show();
                        }
                    }else{
                        imageProxy.close();
                    }
                }
                // after done, release the ImageProxy object


            }
        });

        cameraProviderFuture.addListener(() -> {

            try{
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            }catch (ExecutionException | InterruptedException e){
                Log.e("barcode_prod", "onCreate: ", e );
            }

        } , ContextCompat.getMainExecutor(this));

    }

    // pass the image over the preview
    private void bindPreview(ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector;

        cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(cameraPreviewView.getSurfaceProvider());
        Camera camera =cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, imageAnalysis, preview);

        flashlight_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlashOn = (!FlashOn);
                Log.d("Flash", FlashOn.toString());
                if(camera.getCameraInfo().hasFlashUnit()){
                    camera.getCameraControl().enableTorch(FlashOn);
                }
            }
        });


        preview.setSurfaceProvider(
                cameraPreviewView.getSurfaceProvider());
    }

    //functions run on permission given or denied if given start camera else show a toast message
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera();
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        }
    }

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private int runNumber =0;
    private void barcodeAnalysis(InputImage image , ImageProxy imageProxy){
        runNumber++;
        BarcodeScanner scanner = BarcodeScanning.getClient();
        Task<List<Barcode>> result = scanner.process(image)
                .addOnSuccessListener(barcodes -> {
                    for (Barcode barcode: barcodes) {
                        overlay_rect.setVisibility(View.VISIBLE);
                        barcode_value_txt.setVisibility(View.VISIBLE);
                        barcode_value_txt.setText(barcode.getRawValue());
                        int x_multiplier = screenWidth/Math.min(image.getWidth(), image.getHeight());
                        overlay_rect.setX(barcode.getBoundingBox().left * x_multiplier - 20);
                        barcode_value_txt.setX(barcode.getBoundingBox().left * x_multiplier);
                        overlay_rect.setY(barcode.getBoundingBox().top * x_multiplier - 20);
                        barcode_value_txt.setY(barcode.getBoundingBox().top * x_multiplier - 50);
                        overlay_rect.getLayoutParams().width = barcode.getBoundingBox().width() * x_multiplier + 40 ;
                        overlay_rect.getLayoutParams().height = barcode.getBoundingBox().height() * x_multiplier + 40;
                        overlay_rect.requestLayout();
                        createPopUp(barcode.getRawValue());

                    }

                    imageProxy.close();
                    if(runNumber==10) {
                        overlay_rect.setVisibility(View.INVISIBLE);
                        barcode_value_txt.setVisibility(View.INVISIBLE);
                        runNumber=0;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Task failed with an exception
                        Log.e("barcode_product", "Failed " , e);
                        overlay_rect.setVisibility(View.GONE);
                        imageProxy.close();
                    }
                });
    }

    private void createPopUp(String barRawValue) {

        MyDBHandler db = new MyDBHandler(this);
        ItemData item = db.getItemByBarcode(barRawValue);

        if(item.getId()==null) {
            ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);
            toneGen1.startTone(ToneGenerator.TONE_CDMA_HIGH_SS, 80);

            runCamera = false;
            product_data_layout.setVisibility(View.VISIBLE);
            topControl.setVisibility(View.INVISIBLE);
            barcode_number_txt.setText(barRawValue);

            Picasso.get()
                    .load("https://shopezymobile.azurewebsites.net/image?id=" + barRawValue)
                    .placeholder(R.drawable.background_radial)
                    .into(product_image);
            initializeVolleyNetworkOperation(barRawValue);
        }





    }

    private void initializeVolleyNetworkOperation(String barcode) {



        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "https://shopezymobile.azurewebsites.net/read?barcode=" + barcode;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            product_name.getEditText().setText(response.getString("name"));
                            cost_price.getEditText().setText(response.getString("cost_price"));
                            sell_price.getEditText().setText(response.getString("selling_price"));
                            mrp_price.getEditText().setText(response.getString("maximum_retail_price"));
                            prod_quantity.getEditText().setText("1");
                            sell_price.callOnClick();

                            Toast.makeText(barcode_product_add.this,"fetched from server",Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
            }
        });

        requestQueue.add(jsonObjectRequest);

    }

}