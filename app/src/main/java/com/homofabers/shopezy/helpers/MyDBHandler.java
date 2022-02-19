package com.homofabers.shopezy.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.homofabers.shopezy.model.AccountantTable;
import com.homofabers.shopezy.model.DBParams;
import com.homofabers.shopezy.model.InvoiceDetailParams;
import com.homofabers.shopezy.model.ItemData;
import com.homofabers.shopezy.model.customerTable;
import com.homofabers.shopezy.model.stockItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper {
    public MyDBHandler(Context context) {
        super(context, DBParams.NAME, null, DBParams.VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + DBParams.IN_TABLE_NAME + "(" +
                DBParams.IN_KEY_ID + " INTEGER PRIMARY KEY," +
                DBParams.CUSTOMER_KEY_ID + " INTEGER , " +
                DBParams.IN_DATE_TIME + " DATETIME, " +
                DBParams.IN_TOTAL_AMOUNT + " INTEGER ," +
                DBParams.IN_PAYMENT_MODE + " TEXT ," +
                DBParams.IN_ACCOUNTANT + " INTEGER ," +
                DBParams.IN_TOTAL_ITEMS + " INTEGER ," +
                DBParams.IN_ACCOUNTANT_NAME + " TEXT ," +
                DBParams.CUSTOMER_NAME + " TEXT )";

        String create2 = "CREATE TABLE " + DBParams.ST_TABLE_NAME + "(" +
                DBParams.ST_KEY_ID + " TEXT PRIMARY KEY, " +
                DBParams.ST_PROD_NAME + " TEXT, " +
                DBParams.ST_CP + " REAL, " +
                DBParams.ST_SP + " REAL, " +
                DBParams.ST_Qty + " INTEGER, " +
                DBParams.ST_MRP + " REAL, " +
                DBParams.ST_DATE_TIME + " DATETIME) ";

        String create3 = "CREATE TABLE "+ DBParams.CUST_TABLE_NAME + "(" +
                DBParams.CUST_KEY_ID + " INTEGER PRIMARY KEY, " +
                DBParams.CUST_NAME + " TEXT, " +
                DBParams.CUST_PHONE + " TEXT, " +
                DBParams.CUST_EMAIL + " TEXT) ";

        String create4 = "CREATE TABLE " + DBParams.PROD_TABLE_NAME + "(" +
                DBParams.PROD_ITEM_ID + " INTEGER PRIMARY KEY, " +
                DBParams.PROD_Barcode_ID + " TEXT, " +
                DBParams.PROD_NAME + " TEXT, " +
                DBParams.PROD_SP + " REAL, " +
                DBParams.PROD_QTY + " INTEGER, " +
                DBParams.INVOICE_ID + " INTEGER, "+
                DBParams.DATE_SOLD + " DATETIME )" ;
                ;
        String create5 = "CREATE TABLE " + DBParams.ACCOUNTANT_TABLE + "(" +
                DBParams.ACCOUNTANT_ID + " INTEGER PRIMARY KEY, " +
                DBParams.ACCOUNTANT_NAME + " TEXT, " +
                DBParams.ACCOUNTANT_PIC_DATA + " TEXT) ";

        db.execSQL(create);
        db.execSQL(create2);
        db.execSQL(create3);
        db.execSQL(create4);
        db.execSQL(create5);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addInvoice(InvoiceDetailParams invoiceDetail){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        LocalDateTime dateTime= LocalDateTime.now();
        values.put(DBParams.IN_KEY_ID , invoiceDetail.getId());
        values.put(DBParams.CUSTOMER_KEY_ID , invoiceDetail.getCustom_id());
        values.put(DBParams.IN_DATE_TIME , String.valueOf(dateTime));
        values.put(DBParams.IN_TOTAL_AMOUNT , invoiceDetail.getTotal_amount());
        values.put(DBParams.IN_PAYMENT_MODE , invoiceDetail.getPay_mode());
        values.put(DBParams.IN_ACCOUNTANT , invoiceDetail.getAccountant());
        values.put(DBParams.IN_TOTAL_ITEMS , invoiceDetail.getTotal_items());
        values.put(DBParams.IN_ACCOUNTANT_NAME , invoiceDetail.getAccountant_name());
        values.put(DBParams.CUSTOMER_NAME , invoiceDetail.getCustom_name());
        db.insert(DBParams.IN_TABLE_NAME,null,values);
        Log.d("Query : ", "Successfully Inserted");
        db.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addStockData(stockItem stockItems){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        LocalDateTime dateTime= LocalDateTime.now();
        values.put(DBParams.ST_KEY_ID,stockItems.getBarcode());
        values.put(DBParams.ST_PROD_NAME,stockItems.getProduct_name());
        values.put(DBParams.ST_CP,stockItems.getCP());
        values.put(DBParams.ST_SP,stockItems.getSP());
        values.put(DBParams.ST_MRP,stockItems.getMRP());
        values.put(DBParams.ST_Qty,stockItems.getQuantity());
        values.put(DBParams.ST_DATE_TIME,String.valueOf(stockItems.getdateUpdated()));
        db.insert(DBParams.ST_TABLE_NAME , null, values);
        Log.d("Query: " , "2Successfully Inserted");
        db.close();
    }

    public void addCustomer(customerTable customerItem){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBParams.CUST_NAME , customerItem.getCust_name());
        values.put(DBParams.CUST_PHONE, customerItem.getCustomer_phone());
        values.put(DBParams.CUST_EMAIL , customerItem.getCust_email());
        db.insert(DBParams.CUST_TABLE_NAME , null , values);
        db.close();


    }

    public void addIndividualProduct(ItemData item){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBParams.PROD_Barcode_ID , item.getId());
        values.put(DBParams.PROD_NAME, item.getSP() );
        values.put(DBParams.PROD_QTY, item.getQuantity());
        values.put(DBParams.INVOICE_ID , item.getInvoiceId());
        values.put(DBParams.DATE_SOLD , String.valueOf(item.getDateSold()));
        db.insert(DBParams.PROD_TABLE_NAME , null , values);
        db.close();

    }

    public void addAccountantName(AccountantTable accountant){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBParams.ACCOUNTANT_NAME, accountant.getName());
        values.put(DBParams.ACCOUNTANT_PIC_DATA, accountant.getPicDataText());
        db.insert(DBParams.ACCOUNTANT_TABLE , null , values);
        db.close();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<InvoiceDetailParams> getAllInvoices(){
        Log.d("Query : ", "getting invoice");
        List<InvoiceDetailParams> invoiceDetailList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + DBParams.IN_TABLE_NAME;
        Cursor cursor = db.rawQuery(select,null);

        if(cursor.moveToFirst()){
            do{
                InvoiceDetailParams invoice = new InvoiceDetailParams();
                invoice.setId(cursor.getInt(0));
                invoice.setCustom_id(cursor.getInt(1));
                invoice.setDateTime(LocalDateTime.parse(cursor.getString(2)));
                invoice.setTotal_amount(cursor.getInt(3));
                invoice.setPay_mode(cursor.getString(4));
                invoice.setAccountant(cursor.getInt(5));
                invoice.setTotal_items(cursor.getInt(6));
                invoice.setAccountant_name(cursor.getString(7));
                invoice.setCustom_name(cursor.getString(8));
                invoiceDetailList.add(invoice);
            }while (cursor.moveToNext());
        }
        db.close();
        Log.d("Size: ", String.valueOf(invoiceDetailList.size()));
        return invoiceDetailList;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<stockItem> getAllProductItems(){

        List<stockItem> stockItemsList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String select = "SELECT * FROM " + DBParams.ST_TABLE_NAME;
        Cursor cursor = db.rawQuery(select,null);

        if(cursor.moveToFirst()){
            do{
                stockItem item = new stockItem();
                item.setBarcode(cursor.getString(0));
                item.setProduct_name(cursor.getString(1));
                item.setCP(cursor.getFloat(2));
                item.setSP(cursor.getFloat(3));
                item.setQuantity(cursor.getInt(4));
                item.setMRP(cursor.getFloat(5));
                item.setdateUpdated(LocalDateTime.parse(cursor.getString(6)));
                stockItemsList.add(item);
            }while (cursor.moveToNext());
        }
        db.close();
        return stockItemsList;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<ItemData> getItemWithInvoiceId(int invoiceId){
        List<ItemData> itemDataList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String select = "SELECT * FROM " + DBParams.PROD_TABLE_NAME + " WHERE " +
                DBParams.INVOICE_ID + " = " + invoiceId ;
        Cursor cursor = db.rawQuery(select,null);

        if(cursor.moveToFirst()){
            do {
                ItemData item = new ItemData(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getFloat(3),
                        cursor.getInt(4));
                item.setInvoiceId(cursor.getInt(5));
                item.setDateSold(LocalDateTime.parse(cursor.getString(6)));
            }while (cursor.moveToNext());
        }
        db.close();
        return itemDataList;
    }

    public List<AccountantTable> getAllAccountant(){

        List<AccountantTable> accountantList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM " + DBParams.ACCOUNTANT_TABLE ;
        Cursor cursor = db.rawQuery(select,null);

        if(cursor.moveToFirst()){
            do{
                AccountantTable accountant = new AccountantTable(
                        cursor.getString(1),
                        cursor.getString(2)
                );
                accountant.setId(cursor.getInt(0));

            }while (cursor.moveToNext());
        }

        return accountantList;

    }

    public List<customerTable> getCustomer(String Params , String detail){
        List<customerTable> customerList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM " + DBParams.CUST_TABLE_NAME
                + " WHERE " + Params + " = " + detail;
        Cursor cursor = db.rawQuery(select,null);
        if(cursor.moveToFirst()){
            do{
                customerTable customer = new customerTable();
                customer.setCust_id(cursor.getInt(0));
                customer.setCust_name(cursor.getString(1));
                customer.setCustomer_phone(cursor.getString(2));
                customer.setCust_email(cursor.getString(3));
                customerList.add(customer);
            }while (cursor.moveToNext());
        }
        db.close();
        return customerList;
    }

    public ItemData getItemByBarcode(String barcode){
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM " + DBParams.ST_TABLE_NAME +
                " WHERE " + DBParams.ST_KEY_ID + " = " + barcode;
        Cursor cursor = db.rawQuery(select , null);
        ItemData itemData = new ItemData();
        if(cursor.moveToFirst()){
            itemData.setId(cursor.getString(0));
            itemData.setName(cursor.getString(1));
            itemData.setSP(cursor.getFloat(3));
            itemData.setQuantity(1);
        }
        db.close();
        return itemData;
    }

    public int getNewInvoiceId(){
        int lastKey = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM " + DBParams.IN_TABLE_NAME;
        Cursor cursor = db.rawQuery(select,null);

        if(cursor.moveToLast()){
            lastKey = cursor.getInt(0) + 1;
        }
        db.close();
        return lastKey;
    }

    public int getNewCustomerId(){
        int lastKey = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM " + DBParams.CUST_TABLE_NAME;
        Cursor cursor = db.rawQuery(select,null);

        if(cursor.moveToLast()){
            lastKey = cursor.getInt(0) + 1;
        }
        db.close();
        return lastKey;
    }

    public int getNewAccountantId(){
        int lastKey = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        String select = "SELECT * FROM " + DBParams.ACCOUNTANT_TABLE;
        Cursor cursor = db.rawQuery(select,null);
        if(cursor.moveToLast()){
            lastKey = cursor.getInt(0)+1;
        }
        db.close();
        return lastKey;
    }
}
