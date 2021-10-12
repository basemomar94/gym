package com.example.gym;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.Viewholder> {
    private Context context;
    private List<Notificationitem> notificationitems;

    public NotificationAdapter(Context context, List<Notificationitem> notificationitems) {
        this.context = context;
        this.notificationitems = notificationitems;

    }

    @NonNull
    @Override
    public NotificationAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notificationitem, parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.Viewholder holder, int position) {

        Notificationitem notificationitem = notificationitems.get(position);
        holder.notification_text.setText(notificationitem.Notification_Text);

    }

    @Override
    public int getItemCount() {
        return notificationitems.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        TextView notification_text;


        public Viewholder(@NonNull View itemView) {

            super(itemView);
            notification_text = itemView.findViewById(R.id.notification_text);
        }
    }
}
