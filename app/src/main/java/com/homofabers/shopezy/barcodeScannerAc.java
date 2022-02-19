package com.homofabers.shopezy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.Image;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraInfoUnavailableException;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.homofabers.shopezy.helpers.MyDBHandler;
import com.homofabers.shopezy.helpers.invoiceViewAdapter;
import com.homofabers.shopezy.helpers.scanItemRecycler;
import com.homofabers.shopezy.interf.scanItemInterface;
import com.homofabers.shopezy.model.InvoiceDetailParams;
import com.homofabers.shopezy.model.ItemData;
import com.homofabers.shopezy.model.customerTable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class barcodeScannerAc extends AppCompatActivity implements scanItemInterface {

    ImageView overlay_rect;
    int screenWidth , screenHeight;
    LinearLayout fakeView;
    ImageView back_btn , flashlight_btn, done_btn;
    ImageView generateBillBtn;
    Boolean FlashOn = false;

    TextView barcode_value_txt , ItemPriceTxt;
    float totalAmount = 0;
    float totalItems = 0;

    LinearLayout topControl;
    ConstraintLayout user_data_layout;

    String customer_name="" , customer_phone="" , customer_email="", accountant_name="" ,
            payment_mode="", invoice_number="" , invoice_date="" , accountant_sign_data="";

    TextInputLayout payment_mode_layout , accountant_director_layout , customer_name_layout , customer_phone_layout,
                    customer_email_layout ;
    private AutoCompleteTextView payment_mode_menu , accountant_director_menu;

    private RecyclerView item_cart_recycler;
    RecyclerView.Adapter adapter;
    List<ItemData> itemData;
    List<String> barcodeList;

    // for camerX
    public static final String TAG = "shopezyBarcodeScanner";
    PreviewView cameraPreviewView ;
    private  final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA" , "android.permission.WRITE_EXTERNAL_STORAGE"};
    private int REQUEST_CODE_PERMISSIONS = 1001;
    ImageAnalysis imageAnalysis;

    // camera x api refer documentation https://developer.android.com/training/camerax/
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_barcode_scanner);


        // check for all the permissions
        if (allPermissionsGranted()) {
            startCamera(); //start camera if permission has been granted by user
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

        createRecycler();

    }

    private void createRecycler() {

        barcodeList = new ArrayList<String>();

        itemData = new ArrayList<>();

        item_cart_recycler = findViewById(R.id.barcode_item_recycler);
        item_cart_recycler.setHasFixedSize(false);
        item_cart_recycler.setItemViewCacheSize(100);
        item_cart_recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new scanItemRecycler(this,itemData, this);
        item_cart_recycler.setAdapter(adapter);

      }


    private void startCamera() {



        // start camera as well as image analyzer
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraPreviewView = findViewById(R.id.cameraPreviewView);
        overlay_rect = findViewById(R.id.overlay_rect);
        barcode_value_txt = findViewById(R.id.barcode_value);
        flashlight_btn = findViewById(R.id.flashlight_btn);
        back_btn = findViewById(R.id.back_btn);
        done_btn = findViewById(R.id.done_btn);
        topControl = findViewById(R.id.top_control);
        user_data_layout = findViewById(R.id.user_data_layout);
        generateBillBtn = findViewById(R.id.generate_bill_btn);

        payment_mode_layout = findViewById(R.id.payment_mode);
        accountant_director_layout = findViewById(R.id.accountant_director);
        customer_name_layout = findViewById(R.id.customer_name);
        customer_email_layout = findViewById(R.id.customer_email);
        customer_phone_layout = findViewById(R.id.customer_phone);
        ItemPriceTxt = findViewById(R.id.ItemPriceTxt);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_data_layout.setVisibility(View.VISIBLE);
                topControl.setVisibility(View.INVISIBLE);
            }
        });

        setSpinnersValues();
        generateBillBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                verifyAndGenerateBill();
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
                    try {
                        barcodeAnalysis(image, imageProxy);
                    }catch(Exception e){
                        Toast.makeText(barcodeScannerAc.this, "Barcode Model not loaded yet, Restart", Toast.LENGTH_LONG).show();
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
                Log.e(TAG, "onCreate: ", e );
            }

        } , ContextCompat.getMainExecutor(this));

    }

    private void setSpinnersValues() {
        payment_mode_menu = findViewById(R.id.payment_mode_menu);
        accountant_director_menu = findViewById(R.id.accountant_dir_menu);

        final String[] payment_mode_array = {"VISA","UPI","CASH"};
        ArrayAdapter<String> paymentModes = new ArrayAdapter<>(this , R.layout.support_simple_spinner_dropdown_item,
                payment_mode_array);
        payment_mode_menu.setAdapter(paymentModes);
        payment_mode_menu.setInputType(InputType.TYPE_NULL);

        final String[] accountant_dir_array = {"Shivay","Sashwat","Shivam"};
        ArrayAdapter<String> accountantDirs = new ArrayAdapter<>(this , R.layout.support_simple_spinner_dropdown_item,
                accountant_dir_array);
        accountant_director_menu.setAdapter(accountantDirs);
        accountant_director_menu.setInputType(InputType.TYPE_NULL);

        payment_mode_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                payment_mode = payment_mode_array[position];
            }
        });
        accountant_director_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                accountant_name = accountant_dir_array[position];
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void verifyAndGenerateBill() {

        customer_name = customer_name_layout.getEditText().getText().toString();
        customer_email = customer_email_layout.getEditText().getText().toString();
        customer_phone = customer_phone_layout.getEditText().getText().toString();

        if(customer_name == ""){
            customer_phone_layout.setError("empty ?");
        }else if(customer_email == ""){
            customer_email_layout.setError("empty ?");
        }else if(customer_phone == ""){
            customer_phone_layout.setError("empty ?");
        }else{
            Intent intent = new Intent(barcodeScannerAc.this,Invoice_preview.class);
            intent.putExtra("customer_name",customer_name);
            intent.putExtra("customer_phone",customer_phone);
            intent.putExtra("customer_email",customer_email);
            intent.putExtra("accountant_name",accountant_name);
            intent.putExtra("payment_mode",payment_mode);
            intent.putExtra("invoice_number",invoice_number);
            intent.putExtra("invoice_date",invoice_date);
            intent.putExtra("grand_total",String.valueOf(totalAmount));
            intent.putExtra("total_items",String.valueOf(totalItems));
            intent.putExtra("accountant_sign_data",accountant_sign_data);

            intent.putExtra("data", (Serializable) itemData);


            MyDBHandler dbHandler = new MyDBHandler(this);

            InvoiceDetailParams invoiceDetail = new InvoiceDetailParams();
            customerTable customer = new customerTable();


            int invoiceId = dbHandler.getNewInvoiceId();
            int customId = dbHandler.getNewCustomerId();
            int accountantId = 0;
            LocalDateTime localDateTime = LocalDateTime.now();

            invoiceDetail.setId(invoiceId);
            invoiceDetail.setCustom_id(customId);
            invoiceDetail.setTotal_amount((int) totalAmount);
            invoiceDetail.setPay_mode(payment_mode);
            invoiceDetail.setAccountant(accountantId);
            invoiceDetail.setTotal_items((int) totalItems);
            invoiceDetail.setDateTime(localDateTime);
            invoiceDetail.setAccountant_name(accountant_name);
            invoiceDetail.setCustom_name(customer_name);

            customer.setCust_id(accountantId);
            customer.setCustomer_phone(customer_phone);
            customer.setCust_name(customer_name);
            customer.setCust_email(customer_email);



            dbHandler.addInvoice(invoiceDetail);
            dbHandler.addCustomer(customer);


            for(ItemData item: itemData){
                item.setDateSold(localDateTime);
                item.setInvoiceId(invoiceId);
                dbHandler.addIndividualProduct(item);
            }

            startActivity(intent);
        }


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
            if (ContextCompat.checkSelfPermission(barcodeScannerAc.this, permission) != PackageManager.PERMISSION_GRANTED) {
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


                    }
                    if(barcodes.size()>0) {
                        sendToList(barcodes.get(0).getRawValue());
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
                        Log.e(TAG, "Failed " , e);
                        overlay_rect.setVisibility(View.GONE);
                    imageProxy.close();
                    }
                });
    }
String lastbarcode = "";
    private void sendToList(String barcode) {

        if(!(barcodeList.contains(barcode)) && (!lastbarcode.equals(barcode))) {
            barcodeList.add(barcode);
            MyDBHandler db = new MyDBHandler(this);
            ItemData item = db.getItemByBarcode(barcode);
            if(item.getSP()>0) {
                itemData.add(item);
                adapter.notifyDataSetChanged();
                totalAmount += item.getSP();
                totalItems += 1;
                ItemPriceTxt.setText(totalItems + " added worth " + totalAmount);
                ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, ToneGenerator.MAX_VOLUME);
                toneGen1.startTone(ToneGenerator.TONE_CDMA_HIGH_SS, 80);

                Log.e("new bar", barcode);
            }
        }
        lastbarcode = barcode;
    }

    @Override
    public void AddToList(int position, int quantity) {
        ItemData item = itemData.get(position);
        item.setQuantity(quantity);
        totalAmount += quantity * item.getSP();
        totalItems += quantity;
        ItemPriceTxt.setText(totalItems + " added worth " + totalAmount);
    }
}