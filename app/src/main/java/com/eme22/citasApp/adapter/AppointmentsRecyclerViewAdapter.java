package com.eme22.citasApp.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eme22.citasApp.R;
import com.eme22.citasApp.util.Pair;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AppointmentsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final AppointmentsRecyclerViewAdapter.OnItemClicked listener;
    private ArrayList<Pair<LocalDateTime, Pair<Boolean, Boolean>>> AppointmentArrayList;

    public ArrayList<Pair<LocalDateTime, Pair<Boolean, Boolean>>> getAppointmentArrayList() {
        return AppointmentArrayList;
    }

    public void setAppointmentArrayList(ArrayList<Pair<LocalDateTime, Pair<Boolean, Boolean>>> appointmentArrayList) {
        AppointmentArrayList = appointmentArrayList;
    }

    public AppointmentsRecyclerViewAdapter(AppointmentsRecyclerViewAdapter.OnItemClicked listener) {
        this.listener = listener;
        this.AppointmentArrayList = new ArrayList<>();
    }

    public AppointmentsRecyclerViewAdapter(AppointmentsRecyclerViewAdapter.OnItemClicked listener, ArrayList<Pair<LocalDateTime, Pair<Boolean, Boolean>>> appointmentArrayList) {
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

        Pair<LocalDateTime, Pair<Boolean, Boolean>> Appointment = AppointmentArrayList.get(position);
        RecyclerViewViewHolder viewHolder= (RecyclerViewViewHolder) holder;

        System.out.println(Appointment);
        System.out.println(Appointment.getSecond());
        System.out.println(Appointment.getSecond().getFirst());
        viewHolder.itemView.setEnabled(Appointment.getSecond().getFirst());

        viewHolder.date.setText(String.format("%02d:%02d", Appointment.first.getHour(),Appointment.first.getMinute()));

        if (Appointment.second.second)
            viewHolder.itemView.setBackgroundColor(Color.GRAY);
        else
            viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);

    }

    @Override
    public int getItemCount() {
        return AppointmentArrayList.size();
    }
    
   public void updateAppointmentList(final ArrayList<Pair<LocalDateTime, Pair<Boolean, Boolean>>> AppointmentArrayList) {
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


                    listener.onItemClick(position,AppointmentArrayList.get(position).first, itemView);
                }
            });

        }
    }

    public interface OnItemClicked {
        void onItemClick(int position, LocalDateTime cartItem, View current);
    }

}