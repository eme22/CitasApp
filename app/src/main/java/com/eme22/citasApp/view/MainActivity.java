package com.eme22.citasApp.view;

import static com.eme22.citasApp.util.Constants.EXTRA_MODE;
import static com.eme22.citasApp.util.Constants.EXTRA_USER;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import com.eme22.citasApp.databinding.ActivityMainBinding;
import com.eme22.citasApp.model.pojo.User;
import com.eme22.citasApp.model.pojo.medics.Medic;
import com.eme22.citasApp.model.pojo.patients.Patient;
import com.eme22.citasApp.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private static com.eme22.citasApp.model.pojo.medics.Medic doctor;
    private static com.eme22.citasApp.model.pojo.patients.Patient patient;

    private static Integer mode = 0;
    MainViewModel mainViewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setContentView(binding.getRoot());

        User user = (User) getIntent().getSerializableExtra(EXTRA_USER);

        if (user instanceof Medic) {
            doctor = (Medic) user;
        } else if (user instanceof Patient) {
            patient = (Patient) user;
            mode = 1;
        } else {
            throw new IllegalStateException();
        }

        initListeners();
    }

    private void initListeners() {

        binding.myApp.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ActiveAppointmentsActivity.class);
            sendUser(intent);
            startActivity(intent);
        });

        binding.myHistory.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                sendUser(intent);
                startActivity(intent);
        });


        binding.mySchedule.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
            sendUser(intent);
            startActivity(intent);
        });


        binding.specialities.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SpecialityActivity.class);
            sendUser(intent);
            startActivity(intent);
        });

        binding.medics.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DoctorListActivity.class);
            sendUser(intent);
            startActivity(intent);
        });

        //TODO: ALL


    }

    private static void sendUser(Intent intent) {
        intent.putExtra(EXTRA_USER, patient != null ? patient : doctor);
        intent.putExtra(EXTRA_MODE, mode);
    }

    public void ClickMenu(View view) {
        openDrawer(binding.drawerLayout);
    }

    static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {
        closeDrawer(binding.drawerLayout);
    }

    public void ClickMore(View view) {}

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
    }

    public void ClickHome(View view)  {
        recreate();
    }

    public void ClickUpdateProfile(View view) {
    }

    public void ClickLogout(View view) {
        logout(this);
    }

    public static void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("¿ Deseas deslogearte ?");

        builder.setPositiveButton("Si", (dialog, which) -> {
            Toast.makeText(activity, "Deslogeado", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(activity, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            activity.startActivity(intent);
            System.exit(100);
        });

        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aclass) {
        Intent intent = new Intent(activity, aclass);
        sendUser(intent);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(binding.drawerLayout);
    }
}