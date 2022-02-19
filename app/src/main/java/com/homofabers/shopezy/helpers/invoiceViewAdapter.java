package com.homofabers.shopezy.helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.homofabers.shopezy.Invoice_preview;
import com.homofabers.shopezy.R;
import com.homofabers.shopezy.model.DBParams;
import com.homofabers.shopezy.model.InvoiceDetailParams;
import com.homofabers.shopezy.model.ItemData;
import com.homofabers.shopezy.model.customerTable;

import java.io.Serializable;
import java.util.List;

public class invoiceViewAdapter extends RecyclerView.Adapter<invoiceViewAdapter.viewHolder> {

    List<InvoiceDetailParams> invoiceDetailList;
    Context context;

    public invoiceViewAdapter(Context context , List<InvoiceDetailParams> invoiceDetailList) {
        this.invoiceDetailList = invoiceDetailList;
        this.context = context;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.invoice_item_layout, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        InvoiceDetailParams invoice = invoiceDetailList.get(position);
        holder.customer_name_txt.setText(invoice.getCustom_name());
        holder.total_price_txt.setText(String.valueOf(invoice.getTotal_amount()));
        holder.invoice_number_txt.setText("#" + invoice.getId());
        holder.date_time_txt.setText(String.valueOf(invoice.getDateTime()));

        holder.viewCard.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Log.e("run", "test");

                InvoiceDetailParams invoice = invoiceDetailList.get(holder.getAdapterPosition());
                MyDBHandler myDBHandler = new MyDBHandler(context);
                List<customerTable> customer = myDBHandler.getCustomer(DBParams.CUST_KEY_ID,
                        String.valueOf(invoice.getCustom_id()));
                String customer_phone = customer.get(0).getCustomer_phone();
                String customer_email = customer.get(0).getCust_email();

                List<ItemData> itemDataList =  myDBHandler.getItemWithInvoiceId(invoice.getId());
                Log.d("id",String.valueOf(invoice.getId()));

                Intent intent = new Intent(context , Invoice_preview.class);
                intent.putExtra("customer_name",invoice.getCustom_name());
                intent.putExtra("customer_phone",customer_phone);
                intent.putExtra("customer_email",customer_email);
                intent.putExtra("accountant_name",invoice.getAccountant_name());
                intent.putExtra("payment_mode",invoice.getPay_mode());
                intent.putExtra("invoice_number",invoice.getId());
                intent.putExtra("invoice_date","k");
                intent.putExtra("grand_total",String.valueOf(invoice.getTotal_amount()));
                intent.putExtra("total_items",String.valueOf(invoice.getTotal_items()));
                intent.putExtra("accountant_sign_data","");
                intent.putExtra("data", (Serializable) itemDataList);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return invoiceDetailList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView customer_name_txt;
        TextView total_price_txt;
        TextView invoice_number_txt;
        TextView date_time_txt;
        View viewCard;


        public viewHolder(View itemView) {
            super(itemView);

            customer_name_txt = itemView.findViewById(R.id.customer_name_txt);
            total_price_txt = itemView.findViewById(R.id.total_price_txt);
            invoice_number_txt = itemView.findViewById(R.id.invoice_number_txt);
            date_time_txt = itemView.findViewById(R.id.date_time_txt);
            viewCard = itemView.findViewById(R.id.mainCard);

        }
    }
}
