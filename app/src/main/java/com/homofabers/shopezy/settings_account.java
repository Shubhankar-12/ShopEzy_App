package com.homofabers.shopezy;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.homofabers.shopezy.helpers.DataInputValidator;
import com.homofabers.shopezy.helpers.ImageFormatConversion;
import com.homofabers.shopezy.model.UserSetting;

public class settings_account extends AppCompatActivity {

    ImageView back_btn , done_btn, userProfilePic, upload_img_btn;
    TextInputLayout bussiness_shop_name_input, bussiness_category_selector,user_email_input,
            user_phone_input,user_name_input,bussiness_address_input;

    Button req_verified_btn,remove_passcode_btn,change_passcode_btn;

    AutoCompleteTextView bussiness_category_menu;

    Boolean passcodeExist = false;
    String profile_pic_data = "default";

    // receive data from camera activity
    ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        Bundle extras = data.getExtras();
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        BitmapDrawable imageBitmapDrawable = new BitmapDrawable(getResources(),imageBitmap);
                        userProfilePic.setBackground(imageBitmapDrawable);
                        profile_pic_data = new ImageFormatConversion().BitMapToString(imageBitmap);
                    }
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_account);

        initializeVar();

    }

    private void initializeVar() {

        back_btn = findViewById(R.id.back_btn);
        done_btn = findViewById(R.id.done_btn);

        userProfilePic = findViewById(R.id.userProfilePic);
        upload_img_btn = findViewById(R.id.upload_img_btn);

        bussiness_shop_name_input = findViewById(R.id.bussiness_shop_name_input);
        bussiness_category_selector = findViewById(R.id.bussiness_category_selector);
        user_email_input = findViewById(R.id.user_email_input);
        user_phone_input = findViewById(R.id.user_phone_input);
        user_name_input = findViewById(R.id.user_name_input);
        bussiness_address_input = findViewById(R.id.bussiness_address_input);

        req_verified_btn = findViewById(R.id.req_verified_btn);
        remove_passcode_btn = findViewById(R.id.remove_passcode_btn);
        change_passcode_btn = findViewById(R.id.change_passcode_btn);

        bussiness_category_menu = findViewById(R.id.bussiness_category_menu);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        UserSetting userSetting = new UserSetting();
        userSetting.getStoredSetting(this);

        if(!userSetting.getShop_profile().equals("default")) {
            Bitmap profile_pic = ImageFormatConversion.StringToBitMap(userSetting.getShop_profile());
            userProfilePic.setBackground(new BitmapDrawable(getResources(), profile_pic));
            profile_pic_data = userSetting.getShop_profile();
        }

        // set menu to the category selection view
        ArrayAdapter<String> category_adapter = new ArrayAdapter<>( settings_account.this ,
                R.layout.support_simple_spinner_dropdown_item , userSetting.shop_category_options);
        bussiness_category_menu.setAdapter(category_adapter);
        bussiness_category_menu.setInputType(InputType.TYPE_NULL);


        bussiness_category_menu.setText(userSetting.getShop_category_selected(),false);
        Log.d("manageRegistration: ", String.valueOf(userSetting.getShop_category_selected()));


        //get position of item on click
        bussiness_category_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                userSetting.setShop_category_selected(userSetting.shop_category_options[position]);
            }
        });

        //update image view and add click listener
        upload_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    cameraLauncher.launch(intent);
                }catch (ActivityNotFoundException e){
                    Log.e("IMage", "issue ", e);
                }
            }
        });

        // update data field if already exist
        bussiness_shop_name_input.getEditText().setText(userSetting.getShop_name());
        user_email_input.getEditText().setText(userSetting.getShop_email());
        user_phone_input.getEditText().setText(userSetting.getShop_phone());
        user_name_input.getEditText().setText(userSetting.getOwner_name());
        bussiness_address_input.getEditText().setText(userSetting.getShop_address());

        // update user setting object on button click and setStoredSetting
        done_btn.setOnClickListener(v -> {

            //validate data before adding to settings
            Boolean dataValidationFlag = true;
            DataInputValidator dataInputValidator = new DataInputValidator();

            if(dataInputValidator.textValidator(bussiness_shop_name_input.getEditText().getText().toString()))
                userSetting.setShop_name(bussiness_shop_name_input.getEditText().getText().toString());
            else{
                setInputError(bussiness_shop_name_input , "less than 3 character");
                dataValidationFlag = false;
            };

            if(dataInputValidator.emailValidator(user_email_input.getEditText().getText().toString()))
                userSetting.setShop_email(user_email_input.getEditText().getText().toString());
            else{
                setInputError(user_email_input , "use valid mail");
                dataValidationFlag = false;
            };

            if(dataInputValidator.mobileValidator(user_phone_input.getEditText().getText().toString()))
                userSetting.setShop_phone(user_phone_input.getEditText().getText().toString());
            else{
                setInputError(user_phone_input , "use country code with valid number");
                dataValidationFlag = false;
            }

            if(dataInputValidator.textValidator(user_name_input.getEditText().getText().toString()))
                userSetting.setOwner_name(user_name_input.getEditText().getText().toString());
            else{
                setInputError(user_name_input , "less than 3 character");
                dataValidationFlag = false;
            }

            if(dataInputValidator.textValidator(bussiness_address_input.getEditText().getText().toString()))
                userSetting.setShop_address(bussiness_address_input.getEditText().getText().toString());
            else{
                setInputError(bussiness_address_input , "less than 3 character");
                dataValidationFlag = false;
            }

            if(dataValidationFlag){
                userSetting.setUser_registered(true);
                userSetting.setShop_profile(profile_pic_data);
                userSetting.updateStoredSetting(settings_account.this);
                Toast.makeText(settings_account.this,"Successfully Registered",Toast.LENGTH_SHORT);
                Intent intent = new Intent(settings_account.this , Dashboard.class);
                startActivity(intent);
            }

        });



        if(passcodeExist == false){
            change_passcode_btn.setText("Add Passcode");
            remove_passcode_btn.setVisibility(View.INVISIBLE);

            change_passcode_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(settings_account.this,"This feature is not yet available" ,
                            Toast.LENGTH_SHORT).show();
                }
            });

        }

        req_verified_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(settings_account.this,"This feature is not yet available" ,
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setInputError(TextInputLayout textInputLayout,String message){
        textInputLayout.setError(message);
    }
}