package com.example.isepdevappmobilestudent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.isepdevappmobilestudent.R;
import com.example.isepdevappmobilestudent.classes.DBtable.Admin;
import com.example.isepdevappmobilestudent.classes.DBtable.AdminRole;
import com.example.isepdevappmobilestudent.classes.DatabaseManager;

import java.util.ArrayList;

public class AdminContact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.admin_contact);

        // We get the AdminId from the intent
        int adminId = Integer.parseInt(getIntent().getStringExtra("adminId"));

        // We set the previous Page Button Activity
        ImageButton previousPageImageButton = findViewById(R.id.back_to_component_score_details);
        previousPageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPreviousPage = new Intent(getApplicationContext(), StudentActivity.class);
                startActivity(intentPreviousPage);
            }
        });

        // We set the profile Page Activity
        ImageButton profilePageImageButton = findViewById(R.id.profile_image_button_in_admin_contact);
        profilePageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilePage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentProfilePage);
            }
        });

        // We display the Admin full name
        TextView textViewAdminName = findViewById(R.id.admin_name_in_admin_contact);
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Admin> allAdminsInDB = databaseManager.getAllAdmins();
        Admin currentAdmin = new Admin();
        for (int adminIndex = 0; adminIndex < allAdminsInDB.size(); adminIndex++) {
            if (allAdminsInDB.get(adminIndex).getId() == adminId) {
                currentAdmin = allAdminsInDB.get(adminIndex);
            }
        }
        String adminName = currentAdmin.getFirstName() + " " + currentAdmin.getLastName();
        textViewAdminName.setText(adminName);

        // We display the Admin role
        TextView textViewAdminRole = findViewById(R.id.admin_role_in_admin_contact);
        String adminRole = "";
        ArrayList<AdminRole> allAdminRolesInDB = databaseManager.getAllAdminRoles();
        for (int adminRoleIndex = 0; adminRoleIndex < allAdminRolesInDB.size(); adminRoleIndex++) {
            if (allAdminRolesInDB.get(adminRoleIndex).getId() == currentAdmin.getAdminRoleId()) {
                adminRole = allAdminRolesInDB.get(adminRoleIndex).getName();
            }
        }
        textViewAdminRole.setText(adminRole);

        // We display the Admin email
        TextView textViewAdminEmail = findViewById(R.id.admin_email_in_admin_contact);
        textViewAdminEmail.setText(currentAdmin.getEmail());
    }
}