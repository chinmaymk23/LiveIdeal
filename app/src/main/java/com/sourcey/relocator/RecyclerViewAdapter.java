package com.sourcey.relocator;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> placeNames;
    private ArrayList<String> buttonNames = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(Context mContext, ArrayList<String> placeNames, ArrayList<String> buttonNames) {
        this.placeNames = placeNames;
        this.buttonNames = buttonNames;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.place.setText(placeNames.get(position));
        holder.delete.setText("Delete");
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "delete clicjked", Toast.LENGTH_LONG).show();
                delete_bookmark(holder.getAdapterPosition());
            }
        });
    }

    private void delete_bookmark(int s) {
        placeNames.remove(s);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return placeNames.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{

        TextView place;
        Button delete;
        public ViewHolder(View itemView) {
            super(itemView);
            place = itemView.findViewById(R.id.text);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
