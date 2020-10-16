package com.example.mad_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editTextID;
    EditText editNote;
    Button BtnAddData;
    Button btnViewAll;
    Button btnViewUpdate;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb  = new DatabaseHelper(this);

        editNote = (EditText)findViewById(R.id.editText);
        BtnAddData = (Button) findViewById(R.id.button_add);
        btnViewAll = (Button) findViewById(R.id.button_view) ;
        btnViewUpdate =(Button) findViewById(R.id.button_update);
        editTextID = (EditText) findViewById(R.id.editText_id);
        btnDelete = (Button) findViewById(R.id.button_delete);
        AddData();
        viewAll();
        UpdateData();
        DeleteData();
    }

        public void DeleteData(){
            btnDelete.setOnClickListener(

                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Integer deletedRows = myDb.deleteData(editTextID.getText(). toString());
                            if(deletedRows > 0)
                                Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(MainActivity.this, "Data Not Deleted", Toast.LENGTH_LONG).show();
                        }
                    }
            );

        }




    public void UpdateData(){
        btnViewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdate = myDb.updateData(editTextID.getText().toString(), editNote.getText().toString());
                        if(isUpdate == true)
                            Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data Not Updated", Toast.LENGTH_LONG).show();

                        }

                }
        );

    }

   public void AddData(){

        BtnAddData.setOnClickListener(
                new View.OnClickListener (){

                    @Override
                    public void onClick(View v) {
                       boolean isInserted =  myDb.insertData(editNote.getText() .toString());
                    if(isInserted = true)
                        Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(MainActivity.this, "Data Not Inserted", Toast.LENGTH_LONG).show();

                    }


                }

        );

   }

    public void viewAll(){
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0){
                            //message
                            showMessage("Error", "Nothing can found");

                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("id: "+ res.getString(0) +"\n");
                            buffer.append("name: "+ res.getString(1) +"\n");

                        }

                            //show all data
                        showMessage("Data", buffer.toString());
                    }
                }
        );

    }

    public void showMessage(String title, String Message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}