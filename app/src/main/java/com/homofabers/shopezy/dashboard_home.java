package com.homofabers.shopezy;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.homofabers.shopezy.helpers.DashboardViewModel;
import com.homofabers.shopezy.helpers.ImageFormatConversion;
import com.homofabers.shopezy.model.UserSetting;

import java.time.LocalDateTime;

public class dashboard_home extends Fragment {

    private TextView shop_name_txt , shop_email_txt ;
    private CardView sell_card ,  settings_card, newInvoiceCard, addProductCard;
    private DashboardViewModel viewModel;
    ImageView userProfilePic;

    ImageView notification_icon , logout_icon , edit_profile_icon;

    public dashboard_home() {
    }

    public static dashboard_home newInstance() {
        dashboard_home fragment = new dashboard_home();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated( View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getUserData();
        createNavigation();
    }


    private void createNavigation() {

        notification_icon = getActivity().findViewById(R.id.notification_icon);
        logout_icon = getActivity().findViewById(R.id.logout_icon);
        edit_profile_icon = getActivity().findViewById(R.id.edit_icon);
        addProductCard = getActivity().findViewById(R.id.addProductCard);

        // open notification activity
        notification_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext() , notification.class);
                startActivity(intent);
            }
        });

//         logout the user out of the app
        logout_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create an alert dialog
                new AlertDialog.Builder(getContext())
                        .setTitle("Logout")
                        .setMessage("Do you really want to logout?\nYou can log in again with your passcode.")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int w) {
                                Toast.makeText(getContext(),"You have not set a passcode yet" , Toast.LENGTH_SHORT).show();
                            }})
                        .setNegativeButton("No", null).show();
            }
        });

        edit_profile_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext() , settings_account.class);
                startActivity(intent);
            }
        });

        addProductCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),barcode_product_add.class);
                startActivity(intent);
            }
        });

        sell_card = getActivity().findViewById(R.id.sell_card);
        Log.d("Sell Card: " , "oy");
        viewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
        sell_card.setOnClickListener(v -> {
            Log.d("Sell Card: " , "Clicked");
            viewModel.selectItem("invoice_history");
        });



        settings_card = getActivity().findViewById(R.id.settingCard);
        settings_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext() , settings_main.class);
                startActivity(intent);
            }
        });

        newInvoiceCard = getActivity().findViewById(R.id.newInvoiceCard);
        newInvoiceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext() , barcodeScannerAc.class);
                startActivity(intent);

            }
        });

    }

    private void getUserData() {
        shop_name_txt = getActivity().findViewById(R.id.shop_name_txt);
        shop_email_txt = getActivity().findViewById(R.id.shop_email_txt);
        userProfilePic = getActivity().findViewById(R.id.userProfilePic2);


        UserSetting userSetting = new UserSetting();
        userSetting.getStoredSetting(getContext());
        shop_name_txt.setText(userSetting.getShop_name());
        shop_email_txt.setText(userSetting.getShop_email());

        if(!userSetting.getShop_profile().equals("default")) {
            Bitmap profile_pic = ImageFormatConversion.StringToBitMap(userSetting.getShop_profile());
            userProfilePic.setBackground(new BitmapDrawable(getResources(), profile_pic));
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard_home, container, false);
    }
}