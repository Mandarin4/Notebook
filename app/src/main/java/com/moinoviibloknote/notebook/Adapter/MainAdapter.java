package com.moinoviibloknote.notebook.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.moinoviibloknote.notebook.EditActivity;
import com.moinoviibloknote.notebook.R;
import com.moinoviibloknote.notebook.db.MyConstans;
import com.moinoviibloknote.notebook.db.MyDBManager;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private Context context;
    private List<Listitem> mianArray;

    public MainAdapter(Context context) {
        this.context = context;
        mianArray=new ArrayList<>();
    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_layout, parent, false);
        return new MyViewHolder(view, context, mianArray);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setData(mianArray.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mianArray.size();
    }

    public void updateAdapter(List<Listitem> newList){
        mianArray.clear();
        mianArray.addAll(newList);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        private TextView tvtitle;
        private Context context;
        private List<Listitem> mianArray;
        public MyViewHolder(@NonNull View itemView, Context context, List<Listitem> mianArray) {
            super(itemView);
            tvtitle = itemView.findViewById(R.id.tv_item_list);
            itemView.setOnClickListener(this);
            this.context = context;
            this.mianArray = mianArray;
        }
        public void setData(String title){
            tvtitle.setText(title);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, EditActivity.class);
            intent.putExtra(MyConstans.LIST_ITEM_INTENT, mianArray.get(getAdapterPosition()));
            intent.putExtra(MyConstans.EDIT_STATE, false);
            context.startActivity(intent);
        }
    }

    public void removeItem(int pos, MyDBManager dbManager){
        dbManager.delete(mianArray.get(pos).getId());
        mianArray.remove(pos);
        notifyItemRangeChanged(0,mianArray.size());
        notifyItemRemoved(pos);
    }


}
