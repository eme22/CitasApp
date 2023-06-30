package com.eme22.citasApp.adapter;

import android.icu.text.SimpleDateFormat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eme22.citasApp.R;
import com.eme22.citasApp.model.pojo.appointments.Appointment;

import java.text.Format;
import java.text.MessageFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActiveAppointmentsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final OnItemClicked listener;
    private ArrayList<Appointment> AppointmentArrayList;

    public ActiveAppointmentsRecyclerViewAdapter(OnItemClicked listener) {
        this.listener = listener;
        this.AppointmentArrayList = new ArrayList<>();
    }

    public ActiveAppointmentsRecyclerViewAdapter(OnItemClicked listener, ArrayList<Appointment> appointmentArrayList) {
        this.listener = listener;
        this.AppointmentArrayList = appointmentArrayList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_active_appointments,parent,false);
        return new RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Appointment Appointment = AppointmentArrayList.get(position);
        RecyclerViewViewHolder viewHolder= (RecyclerViewViewHolder) holder;

        viewHolder.card_time.setText(convertFromEpochTime(Appointment.getDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));

        viewHolder.card_doctor_name.setText(Appointment.getMedicByMedicId().getName1()+ " "+ Appointment.getMedicByMedicId().getLastname1());
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());

        //String a = getText(R.string.history_message, format.format(Appointment.getDate()), Appointment.getMedicByMedicId().getAreaByAreaId().getName());
        //viewHolder.card_title.setText(a);

        //viewHolder.card_title.setText(MessageFormat.format("{0} - {1}", format.format(Appointment.getDate()), Appointment.getMedicByMedicId().getAreaByAreaId().getName()));
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
        TextView card_doctor_name,card_time;
        CircleImageView imageView;
        Button reschedule,delete;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
            card_doctor_name = itemView.findViewById(R.id.app_dr_name);
            card_time = itemView.findViewById(R.id.app_dr_time);
            imageView = itemView.findViewById(R.id.app_dr_image);
            reschedule = itemView.findViewById(R.id.reschedule);
            delete = itemView.findViewById(R.id.delete);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(AppointmentArrayList.get(position));
                }
            });

            reschedule.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onChangeDateClick(AppointmentArrayList.get(position));
                }
            });

            delete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onCancelClick(AppointmentArrayList.get(position));
                }
            });

        }
    }

    public interface OnItemClicked {
        void onItemClick(Appointment cartItem);

        void onChangeDateClick(Appointment cartItem);

        void onCancelClick(Appointment cartItem);
    }

    public String convertFromEpochTime (long timeLong) {
        long timeNow = System.currentTimeMillis();

        // get day in relative time
        CharSequence timeDayRelative;
        timeDayRelative = DateUtils.getRelativeTimeSpanString(timeLong, timeNow, DateUtils.DAY_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE);

        // get hour in 24 hour time
        Format hourFormatter = new SimpleDateFormat("HH:mm");
        String timeHour = hourFormatter.format(timeLong);

        // Log.d(DEBUG_TAG, "time of event: " + timeDayRelative + " at " + timeHour);

        String timeDayHour = timeDayRelative + " a las "+ timeHour;

        return timeDayHour;
    }

}