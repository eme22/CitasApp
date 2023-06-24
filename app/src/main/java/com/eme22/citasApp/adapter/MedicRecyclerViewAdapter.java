package com.eme22.citasApp.adapter;

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

import java.util.ArrayList;

public class MedicRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final MedicRecyclerViewAdapter.OnItemClicked listener;
    private ArrayList<Medic> AppointmentArrayList;

    public MedicRecyclerViewAdapter(MedicRecyclerViewAdapter.OnItemClicked listener) {
        this.listener = listener;
        this.AppointmentArrayList = new ArrayList<>();
    }

    public MedicRecyclerViewAdapter(MedicRecyclerViewAdapter.OnItemClicked listener, ArrayList<Medic> appointmentArrayList) {
        this.listener = listener;
        this.AppointmentArrayList = appointmentArrayList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_doctor_list,parent,false);
        return new RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Medic Appointment = AppointmentArrayList.get(position);
        RecyclerViewViewHolder viewHolder= (RecyclerViewViewHolder) holder;

        viewHolder.dr_card_name.setText(Appointment.getName1()+" "+Appointment.getLastname1());

        viewHolder.dr_card_speciality.setText(Appointment.getSpecialityBySpecId().getName());

        viewHolder.dr_card_exp.setText(String.format("%d", Appointment.getAge()));

        Picasso.get().load(Appointment.getImage()).into(((RecyclerViewViewHolder) holder).dr_card_image);
    }

    @Override
    public int getItemCount() {
        return AppointmentArrayList.size();
    }
    
   public void updateAppointmentList(final ArrayList<Medic> AppointmentArrayList) {
        this.AppointmentArrayList.clear();
        this.AppointmentArrayList = AppointmentArrayList;
        notifyDataSetChanged();
   }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder {

        ImageView dr_card_image;
        TextView dr_card_name, dr_card_speciality, dr_card_exp;

        Button button;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            dr_card_name = itemView.findViewById(R.id.dr_card_name);
            dr_card_speciality = itemView.findViewById(R.id.dr_card_place);
            dr_card_image = itemView.findViewById(R.id.dr_card_image);
            dr_card_exp = itemView.findViewById(R.id.dr_card_exp);
            button = itemView.findViewById(R.id.book_btn);

            button.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(AppointmentArrayList.get(position));
                }
            });

        }
    }

    public interface OnItemClicked {
        void onItemClick(Medic cartItem);
    }

}