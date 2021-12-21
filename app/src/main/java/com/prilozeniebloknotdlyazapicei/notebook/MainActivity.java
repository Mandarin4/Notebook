package com.prilozeniebloknotdlyazapicei.notebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SearchView;

import com.prilozeniebloknotdlyazapicei.notebook.Adapter.ListItem;
import com.prilozeniebloknotdlyazapicei.notebook.Adapter.RecAdapter;
import com.prilozeniebloknotdlyazapicei.notebook.db.AppExecuter;
import com.prilozeniebloknotdlyazapicei.notebook.db.MyDBManager;
import com.prilozeniebloknotdlyazapicei.notebook.db.OnDataReceived;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDataReceived {
    private MyDBManager myDBM;
    private EditText edTitle, edDiscrip;
    private RecyclerView rc_view_activity;
    private RecAdapter recAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem item = menu.findItem(R.id.id_search);
        SearchView sv = (SearchView) item.getActionView();
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String s) {
                reedFromDB(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void init() {
        myDBM = new MyDBManager(this);
        edTitle = findViewById(R.id.edTitle);
        edDiscrip = findViewById(R.id.edDiscrip);
        rc_view_activity = findViewById(R.id.rc_view_activity);
        recAdapter = new RecAdapter(this);
        rc_view_activity.setLayoutManager(new LinearLayoutManager(this));
        getItemTouchHelper().attachToRecyclerView(rc_view_activity);
        rc_view_activity.setAdapter(recAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        myDBM.openDb();
        reedFromDB("");
    }

    public void onClickAdd(View view) {
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDBM.closeDb();
    }

    private ItemTouchHelper getItemTouchHelper(){
        return new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                recAdapter.removeItem(viewHolder.getAdapterPosition(),myDBM);
            }
        });
    }
    private void reedFromDB(final String text){
        AppExecuter.getInstance().getSubIO().execute(new Runnable() {
            @Override
            public void run() {
                myDBM.getFromDb(text, MainActivity.this);
            }
        });
    }

    @Override
    public void onReceived(List<ListItem> list) {

        AppExecuter.getInstance().getMainIO().execute(new Runnable() {
            @Override
            public void run() {
                recAdapter.updateAdapter(list);
            }
        });
    }
}