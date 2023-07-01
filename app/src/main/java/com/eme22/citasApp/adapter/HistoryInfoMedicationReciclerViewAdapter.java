package com.eme22.citasApp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eme22.citasApp.R;
import com.eme22.citasApp.model.pojo.prescriptions.Prescription;

import java.util.ArrayList;

public class HistoryInfoMedicationReciclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Prescription> AppointmentArrayList;

    public void setAppointmentArrayList(ArrayList<Prescription> appointmentArrayList) {
        AppointmentArrayList = appointmentArrayList;
        notifyDataSetChanged();
    }

    public HistoryInfoMedicationReciclerViewAdapter() {
        this.AppointmentArrayList = new ArrayList<>();
    }

    public HistoryInfoMedicationReciclerViewAdapter(ArrayList<Prescription> appointmentArrayList) {
        this.AppointmentArrayList = appointmentArrayList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_history,parent,false);
        return new RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Prescription Appointment = AppointmentArrayList.get(position);
        RecyclerViewViewHolder viewHolder= (RecyclerViewViewHolder) holder;


        viewHolder.card_title.setText(Appointment.getMedicationByMedicationId().getName()+" x"+Appointment.getMedicationByMedicationId().getNumber());
    }

    @Override
    public int getItemCount() {
        return AppointmentArrayList.size();
    }

    static class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        TextView card_title;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            card_title = itemView.findViewById(R.id.card_title);

        }
    }


}