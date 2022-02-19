package com.homofabers.shopezy;

import static com.homofabers.shopezy.helpers.imageFilters.removeBackgroundFilter;
import static com.homofabers.shopezy.helpers.imageFilters.removeFilter;
import static com.homofabers.shopezy.helpers.imageFilters.setBackgroundFilter;
import static com.homofabers.shopezy.helpers.imageFilters.setFilter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.homofabers.shopezy.model.UserSetting;

public class language_selection extends AppCompatActivity {

    Boolean english_selected = true;
    Boolean light_theme_selected = true;

    ImageView english_illustration_img , hindi_illustration_img ,dark_theme_select , light_theme_select,
            hindi_selected_indicator, english_selected_indicator , theme_light_tick , theme_dark_tick , language_submit_btn;
    TextView english_label , hindi_label , choose_language_lbl;

    CardView english_card , hindi_card;

    UserSetting userSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        initializeToGrayScale();
    }

    private void initializeToGrayScale(){

        // initialize variables with ids
        english_illustration_img = findViewById(R.id.english_illustration_img);
        hindi_illustration_img = findViewById(R.id.hindi_illustration_img);
        dark_theme_select = findViewById(R.id.dark_theme_select);
        light_theme_select = findViewById(R.id.light_theme_select);
        english_label = findViewById(R.id.english_label);
        hindi_label = findViewById(R.id.hindi_label);
        choose_language_lbl = findViewById(R.id.choose_language_lbl);
        english_card = findViewById(R.id.english_card);
        hindi_card = findViewById(R.id.hindi_card);
        language_submit_btn = findViewById(R.id.language_submit_btn);

        hindi_selected_indicator = findViewById(R.id.hindi_selected_indicator);
        english_selected_indicator = findViewById(R.id.english_selected_indicator);
        theme_dark_tick = findViewById(R.id.theme_dark_tick);
        theme_light_tick = findViewById(R.id.theme_light_tick);

        // add filter to images to make it grayscale
        setFilter(english_illustration_img);
        setFilter(hindi_illustration_img);
        setBackgroundFilter(dark_theme_select);
        setBackgroundFilter(light_theme_select);

        // hide tick marks of non-selected items
        hindi_selected_indicator.setVisibility(View.GONE);
        english_selected_indicator.setVisibility(View.GONE);
        theme_dark_tick.setVisibility(View.GONE);
        theme_light_tick.setVisibility(View.GONE);

        userSetting = new UserSetting();
        userSetting.getStoredSetting(this);

        // change variables if the value in stored setting is different
        if(userSetting.getTheme_selected().equals("Dark")) light_theme_selected = false;
        if(userSetting.getLanguage_selected().equals("Hindi")) english_selected = false;

        language_selected();
        theme_selected();

        english_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                english_selected = true;
                language_selected();
            }
        });

        hindi_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                english_selected = false;
                language_selected();
            }
        });

        light_theme_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                light_theme_selected = true;
                theme_selected();
            }
        });

        dark_theme_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                light_theme_selected = false;
                theme_selected();
            }
        });

        // when everything is done set the user settings parameters
        language_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSetting.updateStoredSetting(language_selection.this);
                Intent intent;
                if(userSetting.getUser_registered()){
                    intent = new Intent(language_selection.this , settings_main.class);
                }else{
                    intent = new Intent(language_selection.this , RegistrationScreen.class);
                }
                startActivity(intent);
            }
        });


    }

    public void language_selected(){
        if(english_selected){

            removeFilter(english_illustration_img);
            setFilter(hindi_illustration_img);
            english_selected_indicator.setVisibility(View.VISIBLE);
            hindi_selected_indicator.setVisibility(View.GONE);
            english_label.setTextColor(getColor(R.color.purple_english));
            // to get exact color on both themes
            hindi_label.setTextColor(choose_language_lbl.getTextColors());
            userSetting.setLanguage_selected("English");
            language_submit_btn.setImageTintList(getColorStateList(R.color.purple_english));

        }else{

            setFilter(english_illustration_img);
            removeFilter(hindi_illustration_img);
            hindi_selected_indicator.setVisibility(View.VISIBLE);
            english_selected_indicator.setVisibility(View.GONE);
            hindi_label.setTextColor(getColor(R.color.maroon_hindi));
            // to get exact color on both themes
            english_label.setTextColor(choose_language_lbl.getTextColors());
            userSetting.setLanguage_selected("Hindi");
            language_submit_btn.setImageTintList(getColorStateList(R.color.maroon_hindi));

        }
    }

    public void theme_selected(){
        if(light_theme_selected){

            removeBackgroundFilter(light_theme_select);
            setBackgroundFilter(dark_theme_select);
            theme_light_tick.setVisibility(View.VISIBLE);
            theme_dark_tick.setVisibility(View.GONE);
            userSetting.setTheme_selected("Light");

        }else{

            setBackgroundFilter(light_theme_select);
            removeBackgroundFilter(dark_theme_select);
            theme_dark_tick.setVisibility(View.VISIBLE);
            theme_light_tick.setVisibility(View.GONE);
            userSetting.setTheme_selected("Dark");

        }
    }


}