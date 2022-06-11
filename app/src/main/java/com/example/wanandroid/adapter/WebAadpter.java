package com.example.wanandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanandroid.R;

import java.util.ArrayList;

public class WebAadpter extends RecyclerView.Adapter<WebAadpter.WebViewHolder> {
    private final Context mContext;
    private final ArrayList<String> data;
    private Itemclick mClick;

    public WebAadpter(Context context){
        this.mContext = context;
        data = new ArrayList<>();
    }
    @NonNull
    @Override
    public WebAadpter.WebViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_web, parent, false);
        return new WebViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull WebAadpter.WebViewHolder holder, int position) {
        String s = data.get(position);
        holder.textView.setText(s);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClick!=null){
                    mClick.click(data.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void add(String s){
        data.add(s);
        notifyDataSetChanged();
    }

    public void addall(ArrayList<String> list){
        data.addAll(list);
        notifyDataSetChanged();
    }

    class WebViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;
        public WebViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.web_text);
        }
    }

    public void setClick(Itemclick click){
        this.mClick = click;

    }

    public interface Itemclick{
        void click(String url);
    }
}
