package com.homofabers.shopezy.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.homofabers.shopezy.R;
import com.homofabers.shopezy.barcodeScannerAc;
import com.homofabers.shopezy.interf.scanItemInterface;
import com.homofabers.shopezy.model.ItemData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class scanItemRecycler extends RecyclerView.Adapter<scanItemRecycler.viewHolder> {
    Context context;
    List<ItemData> itemDataList;
    private scanItemInterface scanInterface;

    public scanItemRecycler(Context context, List<ItemData> itemDataList, scanItemInterface scanInterface) {
        this.context = context;
        this.itemDataList = itemDataList;
        this.scanInterface = scanInterface;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.barcode_item, parent , false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        ItemData itemData = itemDataList.get(position);
        String nameOfProduct = itemData.getName();

        if(nameOfProduct.length() > 12){
            nameOfProduct = nameOfProduct.substring(0,12) + ".." ;
        }

        holder.name_txt.setText(nameOfProduct);
        holder.quantity.setText(String.valueOf(itemData.getQuantity()));
        holder.selling_price_txt.setText(String.valueOf(itemData.getSP()));

        Picasso.get()
                .load("https://shopezymobile.azurewebsites.net/image?id="+ itemData.getId())
                .placeholder(R.drawable.background_radial)
                .into(holder.product_img);

        holder.button_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemData.setQuantity(itemData.getQuantity()+1);
                holder.quantity.setText(String.valueOf(itemData.getQuantity()));
                holder.selling_price_txt.setText(String.valueOf(itemData.getSP() * itemData.getQuantity()));
                scanInterface.AddToList(itemDataList.indexOf(itemData),1);
            }
        });
        holder.button_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemData.getQuantity()>1) {
                    itemData.setQuantity(itemData.getQuantity() - 1);
                    holder.quantity.setText(String.valueOf(itemData.getQuantity() - 1));
                    holder.selling_price_txt.setText(String.valueOf(itemData.getSP() * itemData.getQuantity()));
                    scanInterface.AddToList(itemDataList.indexOf(itemData),-1);

                }
            }
        });


        holder.quantity.setEnabled(true);

    }

    @Override
    public int getItemCount() {
        return itemDataList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView name_txt , selling_price_txt;
        TextView quantity ;
        ImageView button_plus , button_minus , product_img;

        public viewHolder(View itemView) {
            super(itemView);

            name_txt = itemView.findViewById(R.id.product_name);
            selling_price_txt = itemView.findViewById(R.id.selling_price);
            quantity = itemView.findViewById(R.id.number_qty);
            button_minus = itemView.findViewById(R.id.decrese_btn);
            button_plus = itemView.findViewById(R.id.increase_btn);
            product_img = itemView.findViewById(R.id.product_img);

        }


    }
}
