package com.eme22.citasApp.util;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.material.datepicker.CalendarConstraints;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CustomDatePickerDialog implements CalendarConstraints.DateValidator {

    int mYear, mMonth, mDayOfWeek;
    ArrayList<LocalDate> mExcluded;

    public CustomDatePickerDialog(int year, int month, int dayOfWeek, ArrayList<LocalDate> excluded) {
        mYear = year;
        mMonth = month;
        mDayOfWeek = dayOfWeek;
        mExcluded = excluded;
    }

    CustomDatePickerDialog(Parcel parcel) {
        mYear = parcel.readInt();
        mMonth = parcel.readInt();
        mDayOfWeek = parcel.readInt();
    }


    @Override
    public boolean isValid(long date) {

        if (date < Instant.now().toEpochMilli()) return false;

        return mExcluded.stream().noneMatch(date1 -> date1.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() == date);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mYear);
        dest.writeInt(mMonth);
        dest.writeInt(mDayOfWeek);
    }

    public static final Parcelable.Creator<CustomDatePickerDialog> CREATOR = new Parcelable.Creator<>() {

        @Override
        public CustomDatePickerDialog createFromParcel(Parcel parcel) {
            return new CustomDatePickerDialog(parcel);
        }

        @Override
        public CustomDatePickerDialog[] newArray(int size) {
            return new CustomDatePickerDialog[size];
        }
    };


}