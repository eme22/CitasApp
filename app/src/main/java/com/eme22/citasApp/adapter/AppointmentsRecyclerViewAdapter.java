package com.eme22.citasApp.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eme22.citasApp.R;
import com.eme22.citasApp.model.pojo.medics.Medic;
import com.squareup.picasso.Picasso;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class AppointmentsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final AppointmentsRecyclerViewAdapter.OnItemClicked listener;
    private ArrayList<Pair<LocalDateTime, Boolean>> AppointmentArrayList;

    public AppointmentsRecyclerViewAdapter(AppointmentsRecyclerViewAdapter.OnItemClicked listener) {
        this.listener = listener;
        this.AppointmentArrayList = new ArrayList<>();
    }

    public AppointmentsRecyclerViewAdapter(AppointmentsRecyclerViewAdapter.OnItemClicked listener, ArrayList<Pair<LocalDateTime, Boolean>> appointmentArrayList) {
        this.listener = listener;
        this.AppointmentArrayList = appointmentArrayList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.slot_design,parent,false);
        return new RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Pair<LocalDateTime, Boolean> Appointment = AppointmentArrayList.get(position);
        RecyclerViewViewHolder viewHolder= (RecyclerViewViewHolder) holder;

        viewHolder.itemView.setEnabled(Appointment.second);

        viewHolder.date.setText(Appointment.first.getHour()+":"+Appointment.first.getMinute());

    }

    @Override
    public int getItemCount() {
        return AppointmentArrayList.size();
    }
    
   public void updateAppointmentList(final ArrayList<Pair<LocalDateTime, Boolean>> AppointmentArrayList) {
        this.AppointmentArrayList.clear();
        this.AppointmentArrayList = AppointmentArrayList;
        notifyDataSetChanged();
   }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder {

        TextView date;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.text_view);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {


                    listener.onItemClick(AppointmentArrayList.get(position).first, itemView);
                }
            });

        }
    }

    public interface OnItemClicked {
        void onItemClick(LocalDateTime cartItem, View current);
    }

}