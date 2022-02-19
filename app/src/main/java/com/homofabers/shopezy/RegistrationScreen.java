package com.homofabers.shopezy;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.homofabers.shopezy.helpers.DataInputValidator;
import com.homofabers.shopezy.helpers.ImageFormatConversion;
import com.homofabers.shopezy.model.UserSetting;

public class RegistrationScreen extends AppCompatActivity {

    private ImageView upload_img_btn;
    private ImageView userProfilePic;
    private ImageView user_register_btn;
    private TextInputLayout bussiness_shop_name_input;
    private TextInputLayout bussiness_category_selector;
    private AutoCompleteTextView bussiness_category_menu;
    private TextInputLayout user_email_input;
    private TextInputLayout user_phone_input;
    private TextInputLayout user_name_input;
    private TextInputLayout bussiness_address_input;

    private LinearLayout privacyText;

    private String profile_pic_data = "default";
    private  final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA" , "android.permission.WRITE_EXTERNAL_STORAGE"};
    private int REQUEST_CODE_PERMISSIONS = 1001;

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
        setContentView(R.layout.activity_registration_screen);

        //set activity in full screen hiding system notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        // check for all the permissions
        if (allPermissionsGranted()) {
            manageRegistration();
            manageNavigation(); //start camera if permission has been granted by user
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }


    }

    // this function return true or false by checking if permission is given
    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    //functions run on permission given or denied if given start camera else show a toast message
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                manageRegistration();
                manageNavigation();
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        }
    }

    private void manageNavigation() {
        privacyText = findViewById(R.id.privacy_text);
        privacyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationScreen.this , privacy_policy.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    // manages the process of registration using UserSettingClass
    private void manageRegistration() {

        // initialize all the variables with id
        upload_img_btn = findViewById(R.id.upload_img_btn);
        userProfilePic = findViewById(R.id.userProfilePic);


        user_register_btn = findViewById(R.id.user_register_btn);
        bussiness_shop_name_input = findViewById(R.id.bussiness_shop_name_input);
        bussiness_category_selector = findViewById(R.id.bussiness_category_selector);
        bussiness_category_menu = findViewById(R.id.bussiness_category_menu);
        user_email_input = findViewById(R.id.user_email_input);
        user_phone_input = findViewById(R.id.user_phone_input);
        user_name_input = findViewById(R.id.user_name_input);
        bussiness_address_input = findViewById(R.id.bussiness_address_input);


        // create an object of UserSetting to set and get all the data
        UserSetting userSetting = new UserSetting();
        userSetting.getStoredSetting(RegistrationScreen.this);

        if(userSetting.getTheme_selected().equals("Dark") && AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_YES){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else if(userSetting.getTheme_selected().equals("Light") && AppCompatDelegate.getDefaultNightMode() != AppCompatDelegate.MODE_NIGHT_NO){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        //set the image if already exist
        profile_pic_data = userSetting.getShop_profile();
        if(profile_pic_data!="default"){

            // change profile_pic_data string to bitmap drawable
            ImageFormatConversion imageFormatConversion = new ImageFormatConversion();
            Bitmap imageBitmap = imageFormatConversion.StringToBitMap(profile_pic_data);
            BitmapDrawable imageBitmapDrawable = new BitmapDrawable(getResources(),imageBitmap);
            userProfilePic.setBackground(imageBitmapDrawable);

        }

        // set menu to the category selection view
        ArrayAdapter<String> category_adapter = new ArrayAdapter<>( RegistrationScreen.this ,
                R.layout.support_simple_spinner_dropdown_item , userSetting.shop_category_options);
        bussiness_category_menu.setAdapter(category_adapter);
        bussiness_category_menu.setInputType(InputType.TYPE_NULL);

        // set value of bussiness category spinner if already there on disk
        bussiness_category_menu.setText(userSetting.getShop_category_selected(),false);


        //get position of item on click
        bussiness_category_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // get the string and set it to usersetting shopcategory
                userSetting.setShop_category_selected(userSetting.shop_category_options[position]);
            }
        });

        //update image view and add click listener
        upload_img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //ask camera intent to send back image
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
        user_register_btn.setOnClickListener(v -> {

            //validate data before adding to settings
            Boolean dataValidationFlag = true;

            // class for validating / checking sanity of data
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
                userSetting.updateStoredSetting(RegistrationScreen.this);
                Toast.makeText(RegistrationScreen.this,"Successfully Registered",Toast.LENGTH_SHORT);
                Intent intent = new Intent(RegistrationScreen.this , Dashboard.class);
                startActivity(intent);
            }

        });


    }

    private void setInputError(TextInputLayout textInputLayout,String message){
        textInputLayout.setError(message);
    }

}