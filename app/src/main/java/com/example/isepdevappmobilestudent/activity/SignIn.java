package com.example.isepdevappmobilestudent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.isepdevappmobilestudent.classes.DBtable.Student;
import com.example.isepdevappmobilestudent.classes.DatabaseManager;
import com.example.isepdevappmobilestudent.R;
import com.example.isepdevappmobilestudent.classes.DatabaseManager;

import java.util.ArrayList;
import java.util.Objects;

public class SignIn extends AppCompatActivity {
    // We instantiate the variable to access the local Database
    private DatabaseManager databaseManager;

    // We instantiate the public variables that will store the adminId, the adminRole and the roleId;
    public static int STUDENT_ID;
    public static String ADMIN_ROLE_NAME;
    public static int ROLE_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.sign_in);

        // We connect to the local Database
        databaseManager = new DatabaseManager(getApplicationContext());

        // Once the user has clicked on the Sign In button
        Button signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We create the two variables to store the email and password entered by the user
                EditText emailEditText = findViewById(R.id.sign_in_email_edit_text);
                EditText passwordEditText = findViewById(R.id.sign_in_password_edit_text);
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // We get the list of all Admins in Database and verify that the user is in the list
                ArrayList<Student> allStudentsInDB = databaseManager.getAllStudents();
                boolean isUserInDatabase = false;
                int currentStudentIndex = 0;
                for (int i = 0; i < allStudentsInDB.size(); i++) {
                    Student student = allStudentsInDB.get(i);
                    if (Objects.equals(student.getEmail(), email)) {
                        isUserInDatabase = true;
                        currentStudentIndex = i;
                    }
                }

                // If the user is in the list, we advance to the next stage
                if (isUserInDatabase) {
                    Student currentStudent = allStudentsInDB.get(currentStudentIndex);
                    STUDENT_ID = currentStudent.getId();

                    // We verify that the password is the correct one for this admin
                    if (Objects.equals(currentStudent.getPassword(), password)) {
                        Intent intentSignIn = new Intent(getApplicationContext(), StudentActivity.class);
                        startActivity(intentSignIn);
                    } else {
                        Intent incorrectPasswordIntent = new Intent(getApplicationContext(), IncorrectPassword.class);
                        startActivity(incorrectPasswordIntent);
                    }
                } else {
                    Intent userNotInDBIntent = new Intent(getApplicationContext(), UserNotInDB.class);
                    startActivity(userNotInDBIntent);
                }
            }
        });

        // Once the user has clicked on the Sign Up button
        Button redirectToSignUpButton = findViewById(R.id.redirect_to_sign_up_page_button);
        redirectToSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We create a second page on which the user can sign up in the app
                Intent signUpIntent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(signUpIntent);
            }
        });
    }
}