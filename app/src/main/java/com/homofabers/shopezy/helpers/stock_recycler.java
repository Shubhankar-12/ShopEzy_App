package com.homofabers.shopezy.helpers;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.homofabers.shopezy.R;
import com.homofabers.shopezy.model.stockItem;

import java.util.List;

public class stock_recycler extends RecyclerView.Adapter<stock_recycler.viewHolder> {
    Context context;
    List<stockItem> stockItemsList;

    public stock_recycler(Context context, List<stockItem> stockItemsList) {
        this.context = context;
        this.stockItemsList = stockItemsList;
    }

    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stock_item_layout,parent,false);
        return new viewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(viewHolder holder, int position) {

        stockItem stockItem = stockItemsList.get(position);
        holder.item_name.setText(stockItem.getProduct_name());
        holder.cp_text.setText("Cost Price: $"+stockItem.getCP());
        holder.sp_text.setText("Selling Price: $" + stockItem.getSP());
        holder.mrp_text.setText("MRP: $"+stockItem.getMRP());
        holder.quantity_text.setText("Quantity: " + stockItem.getQuantity());
        String dateFormatted = stockItem.getdateUpdated().getDayOfMonth() + "/" + stockItem.getdateUpdated().getMonthValue() +"/" +
                stockItem.getdateUpdated().getYear()+ " | " +
                stockItem.getdateUpdated().getHour() + ":" + stockItem.getdateUpdated().getMinute();
        holder.date_text.setText(dateFormatted);

        holder.profit_text.setText("Profit : $" + (stockItem.getSP() - stockItem.getCP()));
        holder.off_text.setText("Offer: $" + (stockItem.getMRP()-stockItem.getSP()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("error","erro");
            }
        });

    }

    @Override
    public int getItemCount() {
        return stockItemsList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView item_name , cp_text,profit_text,sp_text,
                date_text, mrp_text, off_text,quantity_text;
        public viewHolder(View itemView) {
            super(itemView);
            item_name = itemView.findViewById(R.id.item_name);
            cp_text = itemView.findViewById(R.id.cp_text);
            profit_text = itemView.findViewById(R.id.profit_text);
            sp_text = itemView.findViewById(R.id.sp_text);
            date_text = itemView.findViewById(R.id.date_text);
            mrp_text = itemView.findViewById(R.id.mrp_text);
            off_text = itemView.findViewById(R.id.off_text);
            quantity_text = itemView.findViewById(R.id.quantity_text);
        }
    }
}
