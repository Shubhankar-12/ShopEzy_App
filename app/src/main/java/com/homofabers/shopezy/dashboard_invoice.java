package com.homofabers.shopezy;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.homofabers.shopezy.helpers.MyDBHandler;
import com.homofabers.shopezy.helpers.invoiceViewAdapter;
import com.homofabers.shopezy.model.InvoiceDetailParams;

import java.util.List;

public class dashboard_invoice extends Fragment  {

    private RecyclerView invoiceRecyclerView;
    RecyclerView.Adapter adapter;
    private View new_invoice_btn;

    LottieAnimationView emptyStateAnimation;
    TextView emptyStateTextView;
    public dashboard_invoice() {

    }

    public static dashboard_invoice newInstance() {
        dashboard_invoice fragment = new dashboard_invoice();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new_invoice_btn = getActivity().findViewById(R.id.new_invoice_btn);

        emptyStateAnimation = getActivity().findViewById(R.id.empty_state_animation2);
        emptyStateTextView = getActivity().findViewById(R.id.empty_state_txt2);

        new_invoice_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),barcodeScannerAc.class);
                startActivity(intent);
            }
        });

        MyDBHandler db = new MyDBHandler(getContext());
        InvoiceDetailParams invoiceDetail = new InvoiceDetailParams(0 , 100,
                "UPI",1,23,"custb", "AccountantA");
        db.addInvoice(invoiceDetail);

        List<InvoiceDetailParams> invoiceDetailList = db.getAllInvoices();
        for(InvoiceDetailParams invoice: invoiceDetailList){
            Log.d("Query : " , "id" + invoice.getId() +
                    "cust_id : " + invoice.getCustom_id() +
                    "payment mode :" + invoice.getPay_mode() +
                    "customer name" + invoice.getCustom_name() +
                    "Accountant name" + invoice.getAccountant_name()
                    );
        }
        if(invoiceDetailList.size()<1){
            emptyStateTextView.setVisibility(View.VISIBLE);
            emptyStateAnimation.setVisibility(View.VISIBLE);
        }

        createInvoiceRecycler(invoiceDetailList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_dashboard_invoice, container, false);
    }

    private void createInvoiceRecycler(List<InvoiceDetailParams> invoiceList) {

        invoiceRecyclerView = getView().findViewById(R.id.invoice_recycler);
        invoiceRecyclerView.setHasFixedSize(true);
        invoiceRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        adapter = new invoiceViewAdapter(getContext() , invoiceList);
        invoiceRecyclerView.setAdapter(adapter);
    }


}