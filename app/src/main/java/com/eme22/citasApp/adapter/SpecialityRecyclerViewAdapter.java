package com.eme22.citasApp.adapter;

import android.icu.text.SimpleDateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eme22.citasApp.R;
import com.eme22.citasApp.model.pojo.appointments.Appointment;
import com.eme22.citasApp.model.pojo.specialities.Speciality;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Locale;

public class SpecialityRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final SpecialityRecyclerViewAdapter.OnItemClicked listener;
    private ArrayList<Speciality> AppointmentArrayList;

    public SpecialityRecyclerViewAdapter(SpecialityRecyclerViewAdapter.OnItemClicked listener) {
        this.listener = listener;
        this.AppointmentArrayList = new ArrayList<>();
    }

    public SpecialityRecyclerViewAdapter(SpecialityRecyclerViewAdapter.OnItemClicked listener, ArrayList<Speciality> appointmentArrayList) {
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

        Speciality Appointment = AppointmentArrayList.get(position);
        RecyclerViewViewHolder viewHolder= (RecyclerViewViewHolder) holder;

        viewHolder.card_title.setText(Appointment.getName());

        Picasso.get().load(Appointment.getImage()).into(((RecyclerViewViewHolder) holder).card_image);
    }

    @Override
    public int getItemCount() {
        return AppointmentArrayList.size();
    }
    
   public void updateAppointmentList(final ArrayList<Speciality> AppointmentArrayList) {
        this.AppointmentArrayList.clear();
        this.AppointmentArrayList = AppointmentArrayList;
        notifyDataSetChanged();
   }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder {

        ImageView card_image;
        TextView card_title;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            card_title = itemView.findViewById(R.id.card_title);
            card_image = itemView.findViewById(R.id.card_image);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(AppointmentArrayList.get(position));
                }
            });

        }
    }

    public interface OnItemClicked {
        void onItemClick(Speciality cartItem);
    }

}