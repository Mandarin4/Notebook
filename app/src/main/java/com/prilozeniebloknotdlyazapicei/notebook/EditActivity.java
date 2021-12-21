package com.prilozeniebloknotdlyazapicei.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.prilozeniebloknotdlyazapicei.notebook.Adapter.ListItem;
import com.prilozeniebloknotdlyazapicei.notebook.db.AppExecuter;
import com.prilozeniebloknotdlyazapicei.notebook.db.MyConstans;
import com.prilozeniebloknotdlyazapicei.notebook.db.MyDBManager;

public class EditActivity extends AppCompatActivity {

    EditText editTitle, editDescrip;
    private MyDBManager myDBManager;
    private boolean isEditState = true;
    ListItem listitem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();
        getMyIntents();
    }
    @Override
    protected void onResume() {
        super.onResume();

        myDBManager.openDb();
    }
    private void init(){
        myDBManager = new MyDBManager(this);
        editTitle = findViewById(R.id.edTitle);
        editDescrip = findViewById(R.id.edDiscrip);
    }

    private void getMyIntents(){
        Intent intent = getIntent();
        if(intent!=null){
            listitem = (ListItem) intent.getSerializableExtra(MyConstans.LIST_ITEM_INTENT);
            isEditState = intent.getBooleanExtra(MyConstans.EDIT_ST, true);

            if (!isEditState){
                editTitle.setText(listitem.getTitle());
                editDescrip.setText(listitem.getDescrip());
            }
        }
    }
    public void onClickSave(View view){
        final String title = editTitle.getText().toString();
        final String discrip = editDescrip.getText().toString();
        if(title.equals("") || discrip.equals("")){
            Toast.makeText(this,R.string.empty, Toast.LENGTH_LONG).show();
        }else{
            if(isEditState) {
                AppExecuter.getInstance().getSubIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        myDBManager.insertToDb(title, discrip);
                    }
                });
                Toast.makeText(this, R.string.save, Toast.LENGTH_LONG).show();

            }else {
                AppExecuter.getInstance().getSubIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        myDBManager.updateItem(title, discrip, listitem.getId());
                    }
                });

                Toast.makeText(this, R.string.save, Toast.LENGTH_LONG).show();
            }
            myDBManager.closeDb();
            finish();
        }
    }
}