package com.example.isepdevappmobilestudent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.isepdevappmobilestudent.R;
import com.example.isepdevappmobilestudent.classes.DBtable.Student;
import com.example.isepdevappmobilestudent.classes.DatabaseManager;

import java.util.ArrayList;

public class ProfilePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.profile_page);

        // We display the First Name of the Student
        TextView textViewFirstName = findViewById(R.id.first_name_in_profile_page);
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Student> allStudentsInDB = databaseManager.getAllStudents();
        Student currentStudent = new Student();
        for (int studentIndex = 0; studentIndex < allStudentsInDB.size(); studentIndex++) {
            if (allStudentsInDB.get(studentIndex).getId() == SignIn.STUDENT_ID) {
                currentStudent = allStudentsInDB.get(studentIndex);
            }
        }
        textViewFirstName.setText(currentStudent.getFirstName());

        // We create the Previous Page Activity
        ImageButton previousPageImageButton = findViewById(R.id.back_to_menu_from_profile_page);
        previousPageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPreviousPage = new Intent(getApplicationContext(), StudentActivity.class);
                startActivity(intentPreviousPage);
            }
        });

        // We display the Last Name of the Student
        TextView textViewLastName = findViewById(R.id.last_name_in_profile_page);
        textViewLastName.setText(currentStudent.getLastName());

        // We display the Email of the Student
        TextView textViewEmail = findViewById(R.id.email_in_profile_page);
        textViewEmail.setText(currentStudent.getEmail());

        // We display the Password of the Student
        TextView textViewPassword = findViewById(R.id.password_in_profile_page);
        textViewPassword.setText(currentStudent.getPassword());

        // We create the Activity for the modify Button
        Button buttonModifyProfile = findViewById(R.id.modify_profile_details_button);
        buttonModifyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentModifyProfile = new Intent(getApplicationContext(), ModifyProfile.class);
                startActivity(intentModifyProfile);
            }
        });

        // We create the Activity for the modify Button
        Button buttonLogOut = findViewById(R.id.log_out_button);
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLogOut = new Intent(getApplicationContext(), LogOut.class);
                startActivity(intentLogOut);
            }
        });
    }
}