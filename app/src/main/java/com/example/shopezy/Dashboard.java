package com.homofabers.shopezy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.homofabers.shopezy.helpers.DashboardViewModel;

public class Dashboard extends AppCompatActivity {

    ImageView home_icon_img, analytics_icon_img, invoice_icon_img,  stocks_icon_img;
    TextView home_icon_text, analytics_icon_text,invoice_icon_text,stocks_icon_text;
    LinearLayout home_container,analytics_container,invoice_container,stocks_container;
    private DashboardViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.dashboard_fragment, dashboard_home.class, null)
                    .commit();
        }

        initializeAll();
        manageColorForTab(0);

        home_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageColorForTab(0);
            }
        });

        analytics_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageColorForTab(1);
            }
        });

        invoice_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageColorForTab(2);
            }
        });

        stocks_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageColorForTab(3);
            }
        });

        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        viewModel.getSelectedItem().observe(this, item -> {
            if(item.equals("invoice_history")){
                Log.d("Sell Card: " , "item_recieved");
                invoice_container.performClick();
            }
        });

    }

    private void initializeAll() {

        home_icon_img = findViewById(R.id.home_icon_img);
        analytics_icon_img = findViewById(R.id.analytics_icon_img);
        invoice_icon_img = findViewById(R.id.invoice_icon_img);
        stocks_icon_img = findViewById(R.id.stocks_icon_img);

        home_icon_text = findViewById(R.id.home_icon_text);
        analytics_icon_text = findViewById(R.id.analytics_icon_text);
        invoice_icon_text = findViewById(R.id.invoice_icon_text);
        stocks_icon_text = findViewById(R.id.stocks_icon_text);
        
        home_container = findViewById(R.id.home_container);
        analytics_container = findViewById(R.id.analytics_container);
        invoice_container = findViewById(R.id.invoice_container);
        stocks_container = findViewById(R.id.stocks_container);
    }

    public void manageColorForTab(int selectedTab){

        // create a fragment manager that manages transaction of fragments
        FragmentManager fragmentManager = getSupportFragmentManager();


        switch (selectedTab){
            case 0:

                home_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.green)));

                home_icon_text.setVisibility(View.VISIBLE);
                analytics_icon_text.setVisibility(View.GONE);
                invoice_icon_text.setVisibility(View.GONE);
                stocks_icon_text.setVisibility(View.GONE);

                if(AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES){
                    analytics_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.disabled_gray)));
                    invoice_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.disabled_gray)));
                    stocks_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.disabled_gray)));
                }else{
                    analytics_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.white)));
                    invoice_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.white)));
                    stocks_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.white)));
                }

                fragmentManager.beginTransaction()
                        .replace(R.id.dashboard_fragment, dashboard_home.class, null)
                        .setReorderingAllowed(true)
                        .commit();

                break;
            case 1:

                analytics_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.green)));

                home_icon_text.setVisibility(View.GONE);
                analytics_icon_text.setVisibility(View.VISIBLE);
                invoice_icon_text.setVisibility(View.GONE);
                stocks_icon_text.setVisibility(View.GONE);

                if(AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES){
                    home_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.disabled_gray)));
                    invoice_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.disabled_gray)));
                    stocks_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.disabled_gray)));
                }else{
                    home_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.white)));
                    invoice_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.white)));
                    stocks_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.white)));
                }

                fragmentManager.beginTransaction()
                        .replace(R.id.dashboard_fragment, dashboard_analytics.class, null)
                        .setReorderingAllowed(true)
                        
                        .commit();

                break;
            case 2:

                invoice_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.green)));

                home_icon_text.setVisibility(View.GONE);
                analytics_icon_text.setVisibility(View.GONE);
                invoice_icon_text.setVisibility(View.VISIBLE);
                stocks_icon_text.setVisibility(View.GONE);

                if(AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES){
                    home_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.disabled_gray)));
                    analytics_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.disabled_gray)));
                    stocks_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.disabled_gray)));
                }else{
                    home_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.white)));
                    analytics_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.white)));
                    stocks_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.white)));
                }

                fragmentManager.beginTransaction()
                        .replace(R.id.dashboard_fragment, dashboard_invoice.class, null)
                        .setReorderingAllowed(true)
                        
                        .commit();

                break;
            case 3:

                stocks_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.green)));

                home_icon_text.setVisibility(View.GONE);
                analytics_icon_text.setVisibility(View.GONE);
                invoice_icon_text.setVisibility(View.GONE);
                stocks_icon_text.setVisibility(View.VISIBLE);

                if(AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES){
                    home_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.disabled_gray)));
                    analytics_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.disabled_gray)));
                    invoice_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.disabled_gray)));
                }else{
                    home_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.white)));
                    analytics_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.white)));
                    invoice_icon_img.setImageTintList(ColorStateList.valueOf(getColor(R.color.white)));
                }

                fragmentManager.beginTransaction()
                        .replace(R.id.dashboard_fragment, dashboard_stocks.class, null)
                        .setReorderingAllowed(true)
                        .commit();

                break;
        }

    }

    public  void manageNavigation(){
        invoice_container.performClick();
    }
}