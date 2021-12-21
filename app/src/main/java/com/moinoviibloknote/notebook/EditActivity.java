package com.moinoviibloknote.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.moinoviibloknote.notebook.Adapter.Listitem;
import com.moinoviibloknote.notebook.db.AppExecuter;
import com.moinoviibloknote.notebook.db.MyConstans;
import com.moinoviibloknote.notebook.db.MyDBManager;

public class EditActivity extends AppCompatActivity {

    EditText editTitle, editDesc;
    private MyDBManager myDBManager;
    private boolean isEditState = true;
    Listitem listitem;


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
        editDesc = findViewById(R.id.edDisc);
    }

    private void getMyIntents(){
        Intent intent = getIntent();
        if(intent!=null){
            listitem = (Listitem) intent.getSerializableExtra(MyConstans.LIST_ITEM_INTENT);
            isEditState = intent.getBooleanExtra(MyConstans.EDIT_STATE, true);

            if (!isEditState){
                editTitle.setText(listitem.getTitle());
                editDesc.setText(listitem.getDesc());
            }
        }
    }
    public void onClickSave(View view){
        final String title = editTitle.getText().toString();
        final String disc = editDesc.getText().toString();
        if(title.equals("") || disc.equals("")){
            Toast.makeText(this,R.string.empty, Toast.LENGTH_LONG).show();
        }else{
            if(isEditState) {
                AppExecuter.getInstance().getSubIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        myDBManager.insertToDb(title, disc);
                    }
                });
                Toast.makeText(this, R.string.truesave, Toast.LENGTH_LONG).show();

            }else {
                AppExecuter.getInstance().getSubIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        myDBManager.updateItem(title, disc, listitem.getId());
                    }
                });

                Toast.makeText(this, R.string.truesave, Toast.LENGTH_LONG).show();
            }
            myDBManager.closeDb();
            finish();
        }
    }
}