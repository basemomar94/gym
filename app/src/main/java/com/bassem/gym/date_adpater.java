package com.bassem.gym;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class date_adpater extends RecyclerView.Adapter<date_adpater.ViewHolder> {
    private List<date_item> Date_list;

    public date_adpater(List<date_item> date_list) {
        this.Date_list = date_list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String date = Date_list.get(position).getDate();
        String time = Date_list.get(position).getTime();
        holder.time.setText(time);
        holder.date.setText(date);

    }

    @Override
    public int getItemCount() {
        return Date_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date_item);
            time = itemView.findViewById(R.id.time_item);
        }
    }
}
