package com.example.isepdevappmobilestudent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.isepdevappmobilestudent.R;
import com.example.isepdevappmobilestudent.classes.DBtable.Student;
import com.example.isepdevappmobilestudent.classes.DatabaseManager;

import java.util.ArrayList;

public class ModifyProfile extends AppCompatActivity {
    private Student currentStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.modify_profile);

        // We set the previous Page Button Activity
        ImageButton previousPageImageButton = findViewById(R.id.back_profile_details_from_modify_profile);
        previousPageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPreviousPage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentPreviousPage);
            }
        });

        // We set the EditText Hints
        EditText editTextFirstName = findViewById(R.id.first_name_modify_profile);
        EditText editTextLastName = findViewById(R.id.last_name_modify_profile);
        EditText editTextEmail = findViewById(R.id.email_modify_profile);
        EditText editTextPassword = findViewById(R.id.password_modify_profile);

        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Student> allStudentsInDB = databaseManager.getAllStudents();
        for (int studentIndex = 0; studentIndex < allStudentsInDB.size(); studentIndex++) {
            if (allStudentsInDB.get(studentIndex).getId() == SignIn.STUDENT_ID) {
                currentStudent = allStudentsInDB.get(studentIndex);
            }
        }
        editTextFirstName.setHint(currentStudent.getFirstName());
        editTextLastName.setHint(currentStudent.getLastName());
        editTextEmail.setHint(currentStudent.getEmail());
        editTextPassword.setHint(currentStudent.getPassword());

        Button buttonModifyProfile = findViewById(R.id.modify_profile_to_database_button);
        buttonModifyProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = editTextFirstName.getText().toString();
                String lastName = editTextLastName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (!firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    databaseManager.updateStudentProfile(currentStudent.getId(), firstName, lastName, email, password);

                    Intent intentModifiedProfile = new Intent(getApplicationContext(), ProfilePage.class);
                    startActivity(intentModifiedProfile);
                }
            }
        });
    }
}