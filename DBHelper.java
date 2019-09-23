package com.example.jaimi.invoice.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Abc on 9/19/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "Invoice", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String item_master="CREATE TABLE `item_master` (\n" +
//                "\t`i_id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +  //0
//                "\t`i_name`\tTEXT,\n" +                             //1
//                "\t`i_price`\tREAl,\n" +                            //2
//                "\t`i_quantity`\tREAL,\n" +                         //3
//                "\t`i_gst_price`\tREAL,\n" +                        //4
//                "\t`i_total_price`\tREAL,\n" +                      //5
//                "\t`i_gst_per`\tREAL\n" +                           //6
//                ");";
//        db.execSQL(item_master);
//
//        String temp="CREATE TABLE `temp` (\n" +
//                "\t`t_id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +  //0
//                "\t`t_name`\tTEXT,\n" +                             //1
//                "\t`t_price`\tREAl,\n" +                            //2
//                "\t`t_quantity`\tREAL,\n" +                         //3
//                "\t`t_total_gst_price`\tREAL,\n" +                  //4
//                "\t`t_total_price`\tREAL,\n" +                      //5
//                "\t`t_gst_per`\tREAL,\n" +                          //6
//                "\t`t_gst_price`\tREAL,\n" +                        //7
//                "\t`t_total_pricewithgst`\tREAL\n" +                   //8
//                ");";
//        db.execSQL(temp);
//
//        String bill="CREATE TABLE `bill_record` (\n" +
//                "\t`rb_id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +  //0
//                "\t`rb_number`\tTEXT,\n" +                           //1
//                "\t`rb_date`\tTEXT,\n" +                             //2
//                "\t`rb_total`\tREAL,\n" +                            //3
//                "\t`rb_time`\tTEXT\n" +
//                ");";
//        db.execSQL(bill);



        String item_master="CREATE TABLE `item_master` (\n" +
                "\t`i_id`\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t`i_name`\tTEXT,\n" +
                "\t`i_type_code`\tINTEGER,\n" +
                "\t`i_hsn_code`\tTEXT,\n" +
                "\t`i_price`\tTEXT,\n" +
                "\t`i_gst_price`\tTEXT,\n" +
                "\t`i_gst_per`\tTEXT,\n" +
                "\t`i_total_price`\tTEXT\n" +
                ");";
        db.execSQL(item_master);

        String type_code="CREATE TABLE `type_code` (\n" +
                "\t`t_code`\tINTEGER,\n" +
                "\t`t_name`\tTEXT\n" +
                ");";
        db.execSQL(type_code);


        String bill_temp ="CREATE TABLE `bill_temp_data` (\n" +
                "\t`item_name`\tTEXT,\n" +
                "\t`item_type_code`\tTEXT,\n" +
                "\t`item_unit`\tTEXT,\n" +
                "\t`item_unit_rate`\tTEXT,\n" +
                "\t`item__unit_gst_rate`\tTEXT,\n" +
                "\t`item_gst_per`\tTEXT,\n" +
                "\t`item_unit_total_rate`\tTEXT,\n" +
                "\t`item_total_amount`\tTEXT,\n" +
                "\t`item_gst_amount`\tTEXT,\n" +
                "\t`item_type`\tTEXT,\n" +
                "\t`item_hsn_code`\tTEXT\n" +
                ");";
        db.execSQL(bill_temp);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void insertInType()
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put("t_code",1);
        contentValues.put("t_name","Qty");
        insertRecord("type_code",contentValues);
        contentValues.put("t_code",21);
        contentValues.put("t_name","Kg");
        insertRecord("type_code",contentValues);
        contentValues.put("t_code",22);
        contentValues.put("t_name","Gm");
        insertRecord("type_code",contentValues);
        contentValues.put("t_code",31);
        contentValues.put("t_name","Ltr");
        insertRecord("type_code",contentValues);
        contentValues.put("t_code",32);
        contentValues.put("t_name","Ml");
        insertRecord("type_code",contentValues);
        contentValues.put("t_code",41);
        contentValues.put("t_name","Inch");
        insertRecord("type_code",contentValues);
        contentValues.put("t_code",42);
        contentValues.put("t_name","Ft");
        insertRecord("type_code",contentValues);
        contentValues.put("t_code",43);
        contentValues.put("t_name","Mtr");
        insertRecord("type_code",contentValues);
        contentValues.put("t_code",5);
        contentValues.put("t_name","Price Not Defined");
        insertRecord("type_code",contentValues);
    }

    public  void insertRecord(String tb_name, ContentValues cv)
    {
        SQLiteDatabase db=getWritableDatabase();
        db.insert(tb_name,null,cv);
        db.close();
    }
    public ArrayList<HashMap<String,String>> selectRecord(String q)
    {
        ArrayList<HashMap<String,String>> arr =new ArrayList<>();
        SQLiteDatabase db= getReadableDatabase();
        Cursor c=db.rawQuery(q,null);
        c.moveToFirst();

        for(int i=0;i<c.getCount();i++)
        {
            HashMap<String,String> h= new HashMap<>();
            for (int j=0;j<c.getColumnCount();j++)
            {
                h.put(c.getColumnName(j),c.getString(j));
            }
            arr.add(h);
            c.moveToNext();
        }
        c.close();
        db.close();
        return arr;
    }
    public void deleteRecord(String tb_name,String col_name,String col_value)
    {
      SQLiteDatabase db=getWritableDatabase();
        String del="DELETE from "+tb_name+" where `"+col_name+"`='"+col_value+"'";
        db.execSQL(del);
        db.close();
    }

    public void updateRecord(String tb_name, String col_name, String col_value, ContentValues contentValues)
    {
        SQLiteDatabase db=getWritableDatabase();
        db.update(tb_name,contentValues,"`"+col_name+"`='"+col_value+"'",null);
        db.close();
    }

    public void clearTable(String tablename)
    {
        SQLiteDatabase database=getWritableDatabase();
        database.execSQL("DELETE from "+tablename+"");
    }
    public void BackupProductCVCDataBase(Context context) throws IOException {
        SQLiteDatabase database=getReadableDatabase();
        Cursor c=database.rawQuery("Select * from item_master",null);

        int rowCout=0;
        int colCout=0;
        String filename="ProductList.csv";

        File sdCard = null;
        sdCard=Environment.getExternalStorageDirectory();
        File saveFile = new File(sdCard, filename);

        FileWriter fw=new FileWriter(saveFile);

        BufferedWriter bw=new BufferedWriter(fw);
        rowCout=c.getCount();
        colCout=c.getColumnCount();
        bw.write("item_id,item_name,item_price,item_quantity,item_alert_quantity,item_gst_price,item_total_price,item_gst_per(%)");
        bw.newLine();
        bw.flush();
        if(rowCout>0) {
            c.moveToFirst();
            for (int i=0;i<rowCout;i++){
                c.moveToPosition(i);
                for(int j=0;j<colCout;j++){
                    if(j!=colCout-1){
                        bw.write(c.getString(j)+",");
                    }else {
                        bw.write(c.getString(j));
                    }
                }
                bw.newLine();
                bw.flush();
            }
        }
    }

    public void BackupRecordCVCDataBase(Context context) throws IOException {
        SQLiteDatabase database=getReadableDatabase();
        Cursor c=database.rawQuery("Select * from bill_record",null);

        int rowCout=0;
        int colCout=0;
        String filename="RecordList.csv";

        File sdCard = null;
        sdCard=Environment.getExternalStorageDirectory();
        File saveFile = new File(sdCard, filename);

        FileWriter fw=new FileWriter(saveFile);

        BufferedWriter bw=new BufferedWriter(fw);
        rowCout=c.getCount();
        colCout=c.getColumnCount();
        bw.write("record_id,record_bill_no,record_date,record_total,record_time");
        bw.newLine();
        bw.flush();
        if(rowCout>0) {
            c.moveToFirst();
            for (int i=0;i<rowCout;i++){
                c.moveToPosition(i);
                for(int j=0;j<colCout;j++){
                    if(j!=colCout-1){
                        bw.write(c.getString(j)+",");
                    }else {
                        bw.write(c.getString(j));
                    }
                }
                bw.newLine();
                bw.flush();
            }
        }

    }



    public String getDatabaseBackedUp(Context context) {
        try {


            File sd = new File(Environment.getExternalStorageDirectory(), "Invoice");
            if (!sd.exists()){
                sd.mkdirs();
            }

            Log.d("Demo111", "getDatabaseBackedUp: "+sd.getPath()  + " "+sd.canWrite());
            if (sd.canWrite()) {
                String currentDBPath = context.getDatabasePath("Invoice").getPath();
                String backupDBPath = "Invoice.db";
                File currentDB = new File(currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                Log.d("Demo111", "getDatabaseBackedUp: 111 "+currentDB.exists());

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
                return backupDB.getPath();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void emeregencyRestore(Context context) {
        try {
            File sd = new File(Environment.getExternalStorageDirectory(), "Invoice");
            String backupDBPath = "Invoice.db";
            File backupDB = new File(sd, backupDBPath);
            copyDataBase(context, backupDB.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void copyDataBase(Context context, String path) throws IOException {

        File file = new File(context.getDatabasePath("Invoice").getPath());
        file.mkdirs();
        InputStream myInput = new FileInputStream(path);
        String outFileName = context.getDatabasePath("Invoice").getPath();
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
       // System.out.println("coppppppppppyyyyyyyyyy");
    }
}
