package com.homofabers.shopezy.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.homofabers.shopezy.R;

/**
 * Class for getting and updating users settings
 */
public class UserSetting {

        // accessible to all classes
        public final String[] language_selection_options = { "English", "Hindi"} ;
        public final String[] theme_selection_options = { "Dark" , "Light"};

        public final String[] shop_category_options = {
                "Fashion" , "SuperMarket" , "Food And Beverages" , "Electronics" , "Books And Stationaries" ,
                "Malls" , "Grocery Stores" , "Pharmacy And Drug" , "Other"
        };

        // accessible using getters and setters
        private String language_selected;
        private String  theme_selected;
        private Boolean user_registered;

        private String shop_category_selected;
        private String shop_name;
        private String shop_email;
        private String shop_phone;
        private String shop_address;
        private String owner_name;
        private String shop_profile;


        public UserSetting(){
                Log.d("UserSetiing" , "object created");
        }

        // get data from local storage and update the data in object
        public void getStoredSetting( Context context){

                // shared preference
                SharedPreferences sharedPref = context.getSharedPreferences(
                        context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

                // receive all the data from shared preference and store
                String language_received = sharedPref.getString("language" , language_selection_options[0]);
                String theme_received = sharedPref.getString("theme" , theme_selection_options[1]);
                Boolean user_registered_received = sharedPref.getBoolean("user registered" , false);

                String shop_category_received = sharedPref.getString( "shop category" , shop_category_options[0]);
                String shop_name_received = sharedPref.getString( "shop name" , "a");
                String shop_email_received = sharedPref.getString( "shop email" , "b");
                String shop_phone_received = sharedPref.getString( "shop phone" , "c");
                String shop_address_received = sharedPref.getString( "shop address" , "d");
                String owner_name_received = sharedPref.getString( "owner name" , "e");
                String profile_data_received = sharedPref.getString("profile data", "default");


                this.language_selected = language_received;
                this.theme_selected = theme_received;
                this.user_registered = user_registered_received;
                this.shop_category_selected = shop_category_received;

                this.shop_name = shop_name_received;
                this.shop_email = shop_email_received;
                this.shop_phone = shop_phone_received;
                this.shop_address = shop_address_received;
                this.owner_name = owner_name_received;
                this.shop_profile = profile_data_received;

        }

        // get data from object and update the data in local storage
        public void updateStoredSetting(Context context){

                SharedPreferences sharedPref = context.getSharedPreferences(
                        context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                editor.putString("language" , this.language_selected);
                editor.putString("theme" , this.theme_selected);
                editor.putBoolean("user registered" , this.user_registered);

                editor.putString("shop category" , this.shop_category_selected);
                editor.putString("shop name" , this.shop_name);
                editor.putString("shop email" , this.shop_email);
                editor.putString("shop phone" , this.shop_phone);
                editor.putString("shop address" , this.shop_address);
                editor.putString("owner name" , this.owner_name);
                editor.putString("profile data", this.shop_profile);

                editor.apply();

        }


//------------------------------------ Ignore these are all getter and setters -------------------------


        public String getLanguage_selected() {
                return language_selected;
        }

        public void setLanguage_selected(String language_selected) {
                this.language_selected = language_selected;
        }

        public String getTheme_selected() {
                return theme_selected;
        }

        public void setTheme_selected(String theme_selected) {
                this.theme_selected = theme_selected;
        }

        public String getShop_category_selected() {
                return shop_category_selected;
        }

        public void setShop_category_selected(String shop_category_selected) {
                this.shop_category_selected = shop_category_selected;
        }

        public String getShop_name() {
                return shop_name;
        }

        public void setShop_name(String shop_name) {
                this.shop_name = shop_name;
        }

        public String getShop_email() {
                return shop_email;
        }

        public void setShop_email(String shop_email) {
                this.shop_email = shop_email;
        }

        public String getShop_phone() {
                return shop_phone;
        }

        public void setShop_phone(String shop_phone) {
                this.shop_phone = shop_phone;
        }

        public String getShop_address() {
                return shop_address;
        }

        public void setShop_address(String shop_address) {
                this.shop_address = shop_address;
        }

        public String getOwner_name() {
                return owner_name;
        }

        public void setOwner_name(String owner_name) {
                this.owner_name = owner_name;
        }

        public Boolean getUser_registered() {
                return user_registered;
        }

        public void setUser_registered(Boolean user_registered) {
                this.user_registered = user_registered;
        }

        public String getShop_profile() {
                return shop_profile;
        }

        public void setShop_profile(String shop_profile) {
                this.shop_profile = shop_profile;
        }
}
