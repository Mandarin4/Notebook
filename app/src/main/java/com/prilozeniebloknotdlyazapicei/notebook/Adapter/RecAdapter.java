package com.prilozeniebloknotdlyazapicei.notebook.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prilozeniebloknotdlyazapicei.notebook.EditActivity;
import com.prilozeniebloknotdlyazapicei.notebook.R;
import com.prilozeniebloknotdlyazapicei.notebook.db.MyConstans;
import com.prilozeniebloknotdlyazapicei.notebook.db.MyDBManager;

import java.util.ArrayList;
import java.util.List;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.MyViewHolder> {
    private Context context;
    private List<ListItem> mianArray;

    public RecAdapter(Context context) {
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

    public void updateAdapter(List<ListItem> newList){
        mianArray.clear();
        mianArray.addAll(newList);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        private TextView tvtit;
        private Context context;
        private List<ListItem> mianArray;
        public MyViewHolder(@NonNull View itemView, Context context, List<ListItem> mianArray) {
            super(itemView);
            tvtit = itemView.findViewById(R.id.tv_item);
            itemView.setOnClickListener(this);
            this.context = context;
            this.mianArray = mianArray;
        }
        public void setData(String title){
            tvtit.setText(title);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, EditActivity.class);
            intent.putExtra(MyConstans.LIST_ITEM_INTENT, mianArray.get(getAdapterPosition()));
            intent.putExtra(MyConstans.EDIT_ST, false);
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
