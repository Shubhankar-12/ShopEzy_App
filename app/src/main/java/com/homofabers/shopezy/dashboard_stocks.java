package com.homofabers.shopezy;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.homofabers.shopezy.helpers.MyDBHandler;
import com.homofabers.shopezy.helpers.invoiceViewAdapter;
import com.homofabers.shopezy.helpers.stock_recycler;
import com.homofabers.shopezy.model.stockItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class dashboard_stocks extends Fragment {

    RecyclerView recyclerView ;
    List<stockItem> stockItems;
    RecyclerView.Adapter adapter;
    ImageView addNewProductBtn;

    LottieAnimationView emptyStateAnimation;
    TextView emptyStateTextView;

    public static dashboard_stocks newInstance(String param1, String param2) {
        dashboard_stocks fragment = new dashboard_stocks();
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

        emptyStateAnimation = getActivity().findViewById(R.id.empty_state_animation);
        emptyStateTextView = getActivity().findViewById(R.id.empty_state_txt);

        recyclerView = getActivity().findViewById(R.id.stocks_recycler);
        setRecycler(stockItems);

        addNewProductBtn = getActivity().findViewById(R.id.AddNewProduct);
        addNewProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext() , barcode_product_add.class);
                startActivity(intent);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setRecycler(List<stockItem> stockItems) {
        stockItems = new ArrayList<>();

        MyDBHandler myDBHandler = new MyDBHandler(getContext());

        List<stockItem> itemsList =  myDBHandler.getAllProductItems();
        for(stockItem item: itemsList){
            stockItems.add(item);
        }
        if(stockItems.size()<1){
            emptyStateTextView.setVisibility(View.VISIBLE);
            emptyStateAnimation.setVisibility(View.VISIBLE);
        }

        recyclerView = getView().findViewById(R.id.stocks_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        adapter = new stock_recycler(getContext() , stockItems);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard_stocks, container, false);
    }
}