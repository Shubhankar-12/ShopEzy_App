package com.homofabers.shopezy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.homofabers.shopezy.helpers.ImageFormatConversion;
import com.homofabers.shopezy.helpers.MyDBHandler;
import com.homofabers.shopezy.model.InvoiceDetailParams;
import com.homofabers.shopezy.model.ItemData;
import com.homofabers.shopezy.model.UserSetting;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Invoice_preview extends AppCompatActivity {

    private WebView webView;
    ImageView printBtn , shareBtn;

    String customer_name="" , customer_phone="" , customer_email="", accountant_name="" ,
            payment_mode="", invoice_number="" , invoice_date="", accountant_sign_data="" , totalAmmount ="" , totalItem = "";
    List<ItemData> itemData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_preview);
        loadPreview();
    }

    private void loadPreview() {
        customer_name = getIntent().getStringExtra("customer_name").toString();
        customer_email = getIntent().getStringExtra("customer_email").toString();
        customer_phone = getIntent().getStringExtra("customer_phone").toString();
        accountant_name = getIntent().getStringExtra("accountant_name").toString();
        payment_mode = getIntent().getStringExtra("payment_mode").toString();
        invoice_date = getIntent().getStringExtra("invoice_date").toString();
        accountant_sign_data = getIntent().getStringExtra("accountant_sign_data").toString();
        totalAmmount = getIntent().getStringExtra("grand_total").toString();
        totalItem = getIntent().getStringExtra("total_items").toString();
        itemData = (ArrayList<ItemData>) getIntent().getSerializableExtra("data");
        String itemDataTable = "";
        int i = 0;
        for (ItemData items: itemData){
            itemDataTable += "<tr><td>"+i+"</td><td>"
                    + items.getName() + "</td><td>"
            +items.getSP()+"</td><td>"
            +items.getQuantity()+"</td><td>N/A</td><td>"
                    +items.getSP()+"</td></tr>";
            i++;
        }


        UserSetting userSetting = new UserSetting();
        userSetting.getStoredSetting(this);
        webView = findViewById(R.id.Invoice_webview);
        webView.loadUrl("file:///android_asset/index.html");
        WebSettings webSetting = webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        String finalItemDataTable = itemDataTable;

        webView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url){

                String imageUri  = "mail.svg";
                try {
                    imageUri = imageToUri(userSetting.getShop_profile());
                }catch (Exception e){}

                webView.loadUrl("javascript:var shopName ='"+ userSetting.getShop_name() +"';" +
                        "var shopAddress='" + userSetting.getShop_address() + "';"+
                        "var shopPhone='" + userSetting.getShop_phone() + "';"+
                        "var shopEmail='" + userSetting.getShop_email() + "';"+
                        "var shopPicData='"+ imageUri + "';"+
                        "var customerName='" + customer_name + "';" +
                        "var customerPhone ='" +customer_phone + "';" +
                        "var customerEmail ='" +customer_email + "';" +
                        "var invoiceNumber ='" +invoice_number + "';" +
                        "var invoiceDate ='" +invoice_date + "';" +
                        "var payment_mode ='" +payment_mode + "';" +
                        "var GrandTotal ='" +totalAmmount + "';" +
                        "var totalItem ='" +totalItem + "';" +
                        "var accountantName ='" +accountant_name + "';" +
                        "var accountantSignData ='" +accountant_sign_data + "';" +
                        "var data='"+ finalItemDataTable +"';"+
                        " setAllData();");
            }
        });

        printBtn = findViewById(R.id.print_btn);
        printBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createWebPagePrint(webView);
            }
        });

        shareBtn = findViewById(R.id.shareBtn);
        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = screenshot(webView);
                sharePalette(bitmap);
            }
        });

    }
    public  void createWebPagePrint(WebView webView) {
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();
        String jobName = "helloDocument";
        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        builder.setMediaSize(PrintAttributes.MediaSize.ISO_A4);
        PrintJob printJob = printManager.print(jobName, printAdapter, builder.build());

        if(printJob.isCompleted()){
            Toast.makeText(getApplicationContext(), "printed", Toast.LENGTH_LONG).show();
        }
        else if(printJob.isFailed()){
            Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
        }
    }



    public static Bitmap screenshot(WebView webView) {
        webView.measure(View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        webView.layout(0, 0, webView.getMeasuredWidth(), webView.getMeasuredHeight());
        webView.setDrawingCacheEnabled(true);
        webView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(webView.getMeasuredWidth(),
                webView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        int iHeight = bitmap.getHeight();
        canvas.drawBitmap(bitmap, 0, iHeight, paint);
        webView.draw(canvas);
        return bitmap;
    }

    private void sharePalette(Bitmap bitmap) {
        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,
                "Invoice", "share invoice");
        Uri bitmapUri = Uri.parse(bitmapPath);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
        startActivity(Intent.createChooser(intent, "Share"));
    }

    private String imageToUri(String profile_pic_data){

        ImageFormatConversion imageFormatConversion = new ImageFormatConversion();
        Bitmap imageBitmap = imageFormatConversion.StringToBitMap(profile_pic_data);
        BitmapDrawable imageBitmapDrawable = new BitmapDrawable(getResources(),imageBitmap);

        String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap,
                "Invoice", "share invoice");
        Uri bitmapUri = Uri.parse(bitmapPath);
        return bitmapUri.toString();

    }

}