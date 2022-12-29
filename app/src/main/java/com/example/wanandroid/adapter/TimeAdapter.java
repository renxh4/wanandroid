package com.example.wanandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wanandroid.R;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TimeAdapter  extends RecyclerView.Adapter<TimeAdapter.TimeViewHolder> {
    private final Context con;
    private ArrayList<String> mData  = new ArrayList<>();
    public TimeAdapter(Context context){
        this.con = context;
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(con).inflate(R.layout.time_item, parent, false);
        return new TimeViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder holder, int position) {
        String s = mData.get(position);
        String strDateFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String format = sdf.format(new Date(Long.valueOf(s)));
        holder.time.setText(format);

        if (position!=mData.size()-1){
            long during= Long.valueOf(s) - Long.valueOf(mData.get(position+1));
            int day = (int) (during/(1000*60*60*24));
            holder.preTime.setText("距上次"+day+"天时间");
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(ArrayList<String> data){
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    class TimeViewHolder extends RecyclerView.ViewHolder {

        private final TextView time;
        private final TextView preTime;

        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.time);
            preTime = itemView.findViewById(R.id.pretime);
        }

    }
}
