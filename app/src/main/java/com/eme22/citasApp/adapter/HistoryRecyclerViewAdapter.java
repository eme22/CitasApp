package com.eme22.citasApp.adapter;

import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eme22.citasApp.R;
import com.eme22.citasApp.model.pojo.appointments.Appointment;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Locale;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final HistoryRecyclerViewAdapter.OnItemClicked listener;
    private ArrayList<Appointment> AppointmentArrayList;

    public HistoryRecyclerViewAdapter(HistoryRecyclerViewAdapter.OnItemClicked listener) {
        this.listener = listener;
        this.AppointmentArrayList = new ArrayList<>();
    }

    public HistoryRecyclerViewAdapter(HistoryRecyclerViewAdapter.OnItemClicked listener, ArrayList<Appointment> appointmentArrayList) {
        this.listener = listener;
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

        Appointment Appointment = AppointmentArrayList.get(position);
        RecyclerViewViewHolder viewHolder= (RecyclerViewViewHolder) holder;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        //String a = getText(R.string.history_message, format.format(Appointment.getDate()), Appointment.getMedicByMedicId().getAreaByAreaId().getName());
        //viewHolder.card_title.setText(a);

        viewHolder.card_title.setText(MessageFormat.format("{0} - {1}", format.format(Appointment.getDate()), Appointment.getMedicByMedicId().getAreaByAreaId().getName()));
    }

    @Override
    public int getItemCount() {
        return AppointmentArrayList.size();
    }
    
   public void updateAppointmentList(final ArrayList<Appointment> AppointmentArrayList) {
        this.AppointmentArrayList.clear();
        this.AppointmentArrayList = AppointmentArrayList;
        notifyDataSetChanged();
   }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        TextView card_title;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            card_title = itemView.findViewById(R.id.card_title);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(AppointmentArrayList.get(position));
                }
            });

        }
    }

    public interface OnItemClicked {
        void onItemClick(Appointment cartItem);
    }

}