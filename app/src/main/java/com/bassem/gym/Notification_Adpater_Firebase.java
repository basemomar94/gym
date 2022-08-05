package com.bassem.gym;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class Notification_Adpater_Firebase extends FirestoreRecyclerAdapter<Notificationitem, Notification_Adpater_Firebase.ViewHolder> {

    private OnitemClick listener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public Notification_Adpater_Firebase(@NonNull FirestoreRecyclerOptions<Notificationitem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull Notification_Adpater_Firebase.ViewHolder holder, int position, @NonNull Notificationitem model) {
        holder.title.setText(model.getTitle());
        holder.body.setText(model.getBody());


    }

    @NonNull
    @Override
    public Notification_Adpater_Firebase.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notificationitem, parent, false);
        return new ViewHolder(view);
    }

    public void setOnitemClick(OnitemClick listener) {

        this.listener = listener;


    }

    public interface OnitemClick {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, body;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_noti);
            body = itemView.findViewById(R.id.body_noti);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);

                    }

                }
            });
        }


    }
}
