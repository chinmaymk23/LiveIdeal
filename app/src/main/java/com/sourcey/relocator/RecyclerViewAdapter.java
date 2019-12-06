package com.sourcey.relocator;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private List<String> placeNames;
    private ArrayList<String> buttonNames = new ArrayList<>();
    private int userId;
    private String locationType;
    private Context mContext;

    public RecyclerViewAdapter(Context mContext, List<String> placeNames, ArrayList<String> buttonNames, int userId, String locationType) {
        this.placeNames = placeNames;
        this.buttonNames = buttonNames;
        this.userId = userId;
        this.locationType = locationType;
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
                Toast.makeText(mContext, "Deleted", Toast.LENGTH_LONG).show();
                delete_bookmark(holder.getAdapterPosition());
            }
        });
    }

    private void delete_bookmark(int s) {
        try {
            String url = "https://mcprojectauth.herokuapp.com/deleteBookMark";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", userId);
            jsonObject.put("place", placeNames.get(s));
            jsonObject.put("type", locationType);
            DeleteBookmark deleteBookmark = new DeleteBookmark(url, jsonObject);
            deleteBookmark.execute();
            placeNames.remove(s);
            notifyDataSetChanged();
        }catch (JSONException e){
            e.printStackTrace();
        }
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

    public class DeleteBookmark extends AsyncTask<String, Void, Void>{
        private final String url;
        private final JSONObject jsonObject;

        public DeleteBookmark(String url, JSONObject jsonObject){
            this.url = url;
            this.jsonObject = jsonObject;
        }

        @Override
        protected Void doInBackground(String... strings) {
            try {
                    URL myUrl = new URL(url);

                    HttpURLConnection connection = (HttpURLConnection)myUrl.openConnection();

                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    connection.setRequestProperty("Accept", "application/json");
                    if(this.jsonObject != null){
                        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                        writer.write(jsonObject.toString());
                        writer.flush();
                    }
                    int statusCode = connection.getResponseCode();

                    if(statusCode == 200){
                        InputStream inputStream = new BufferedInputStream(connection.getInputStream());

                        String response = convertInputStreamToString(inputStream);
                    }else {
                        return null;
                    }
                }catch (Exception e){
                Log.d(TAG, e.getLocalizedMessage());
            }
            return null;
        }
    }

    private String convertInputStreamToString(InputStream inputStream){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        try{
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}
