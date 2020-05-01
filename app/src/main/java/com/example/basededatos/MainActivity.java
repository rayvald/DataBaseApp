package com.example.basededatos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_code, et_description, et_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_code = (EditText)findViewById(R.id.txt_code);
        et_description = (EditText)findViewById(R.id.txt_description);
        et_price = (EditText)findViewById(R.id.txt_price);
    }

    public void insert(View view){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "administration", null, 1);
        SQLiteDatabase dataBase = admin.getWritableDatabase();

        String code = et_code.getText().toString();
        String description = et_description.getText().toString();
        String price = et_price.getText().toString();

        if(!code.isEmpty() && !description.isEmpty() && !price.isEmpty()){
            ContentValues reg  = new ContentValues();
            reg.put("code", code);
            reg.put("description", description);
            reg.put("price", price);

            dataBase.insert("articles", null, reg);
            dataBase.close();

            et_code.setText("");
            et_description.setText("");
            et_price.setText("");

            Toast.makeText(this, "Article successfully created", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Empty Fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void search(View view){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "administration", null, 1);
        SQLiteDatabase dataBase = admin.getWritableDatabase();

        String code = et_code.getText().toString();

        if (!code.isEmpty()){
            Cursor row = dataBase.rawQuery
                    ("select description, price from articles where code =" + code, null);

            if (row.moveToFirst()){ //moveToFirst valida que nuestra consulta contenga valores
                et_description.setText(row.getString(0));
                et_price.setText(row.getString(1));

                dataBase.close();
            } else {
                Toast.makeText(this, "Product does not exist", Toast.LENGTH_SHORT).show();
                dataBase.close();
            }

        } else {
            Toast.makeText(this, "Please Insert a Product Code", Toast.LENGTH_SHORT).show();
        }
    }

    public void delete(View view){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "administration", null, 1);
        SQLiteDatabase dataBase = admin.getWritableDatabase();

        String code = et_code.getText().toString();

        if (!code.isEmpty()){
            int cant = dataBase.delete("articles", "code="+code, null);
            dataBase.close();

            et_code.setText("");
            et_description.setText("");
            et_price.setText("");

            if (cant == 1){
                Toast.makeText(this, "Article successfully deleted  ", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, "The article could not be removed ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please Insert a Product Code", Toast.LENGTH_SHORT).show();
            dataBase.close();
        }
    }

    public void update(View view){
        AdminSQliteOpenHelper admin = new AdminSQliteOpenHelper(this, "administration", null, 1);
        SQLiteDatabase dataBase = admin.getWritableDatabase();

        String code = et_code.getText().toString();
        String description = et_description.getText().toString();
        String price = et_price.getText().toString();

        if(!code.isEmpty() && !description.isEmpty() && !price.isEmpty()){
            ContentValues reg  = new ContentValues();
            reg.put("code", code);
            reg.put("description", description);
            reg.put("price", price);

            int cant = dataBase.update("articles",reg, "code="+code,null);
            dataBase.close();

            if (cant == 1){
                Toast.makeText(this, "Article successfully updated  ", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, "The article could not be updated ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Empty Fields", Toast.LENGTH_SHORT).show();
        }


    }
}