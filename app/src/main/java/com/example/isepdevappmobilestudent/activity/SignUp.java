package com.example.isepdevappmobilestudent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.isepdevappmobilestudent.R;
import com.example.isepdevappmobilestudent.classes.DBtable.Component;
import com.example.isepdevappmobilestudent.classes.DBtable.ComponentScore;
import com.example.isepdevappmobilestudent.classes.DBtable.Skill;
import com.example.isepdevappmobilestudent.classes.DBtable.Student;
import com.example.isepdevappmobilestudent.classes.DatabaseManager;

import java.util.ArrayList;
import java.util.Objects;

public class SignUp extends AppCompatActivity {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private int studentNumber;
    private DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.sign_up);

        // We now create the Activity related to the user pressing the Sign Up button
        Button signUpButton = findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // We recover the values of the different variables
                EditText emailEditText = findViewById(R.id.sign_up_email_edit_text);
                email = emailEditText.getText().toString();
                EditText passwordEditText = findViewById(R.id.sign_up_password_edit_text);
                password = passwordEditText.getText().toString();
                EditText firstNameEditText = findViewById(R.id.sign_up_first_name_edit_text);
                firstName = firstNameEditText.getText().toString();
                EditText lastNameEditText = findViewById(R.id.sign_up_last_name_edit_text);
                lastName = lastNameEditText.getText().toString();
                EditText studentNumberEditText = findViewById(R.id.sign_up_student_number_edit_text);
                studentNumber = Integer.parseInt(studentNumberEditText.getText().toString());

                // We get the list of all Student in the Database
                databaseManager = new DatabaseManager(getApplicationContext());
                ArrayList<Student> allStudentsInDB = databaseManager.getAllStudents();

                // We create a Boolean variable to know if the user is already in the Admin Table
                boolean isUserInDatabase = false;
                if (!allStudentsInDB.isEmpty()) {
                    for (int i = 0; i < allStudentsInDB.size(); i++) {
                        Student student = allStudentsInDB.get(i);
                        if (Objects.equals(student.getEmail(), email)) {
                            isUserInDatabase = true;
                        }
                    }
                }
                if (!isUserInDatabase) {
                    // We store in the Database the new Student
                    databaseManager.insertNewStudent(email, password, firstName, lastName, studentNumber);
                    int registeredStudentId = 0;
                    allStudentsInDB = databaseManager.getAllStudents();
                    for (int studentIndex = 0; studentIndex < allStudentsInDB.size(); studentIndex++) {
                        if (Objects.equals(allStudentsInDB.get(studentIndex).getEmail(), email)) {
                            registeredStudentId = allStudentsInDB.get(studentIndex).getId();
                        }
                    }

                    // We create the Component Scores for this Student in the Database
                    ArrayList<Component> allComponentsInDB = databaseManager.getAllComponents();
                    ArrayList<Skill> allSkillsInDB = databaseManager.getAllSkills();
                    for (int componentIndex = 0; componentIndex < allComponentsInDB.size(); componentIndex++) {
                        databaseManager.insertComponentScore(componentIndex+1, registeredStudentId);

                        // We get the id of the Component Score just inserted in the DB
                        int lastInsertedComponentScoreId = 0;
                        ArrayList<ComponentScore> allComponentScoresInDB = databaseManager.getAllComponentScores();
                        for (int componentScoreIndex = 0; componentScoreIndex < allComponentScoresInDB.size(); componentScoreIndex++) {
                            if(allComponentScoresInDB.get(componentScoreIndex).getComponentId() == componentIndex+1
                                    && allComponentScoresInDB.get(componentScoreIndex).getStudentId() == registeredStudentId) {
                                lastInsertedComponentScoreId = allComponentScoresInDB.get(componentScoreIndex).getId();
                            }
                        }

                        // We create the Skill Scores for this Student for this Component Score in the Database
                        for (int skillIndex = 0; skillIndex < allSkillsInDB.size(); skillIndex++) {
                            if (allSkillsInDB.get(skillIndex).getComponentId() == allComponentsInDB.get(componentIndex).getId()) {
                                databaseManager.insertSkillScore(skillIndex+1, lastInsertedComponentScoreId);
                            }
                        }
                    }

                    Intent userRegisteredInDB = new Intent(getApplicationContext(), UserRegisteredInDatabase.class);
                    startActivity(userRegisteredInDB);
                } else {
                    Intent userAlreadyInDBIntent = new Intent(getApplicationContext(), UserAlreadyInDB.class);
                    startActivity(userAlreadyInDBIntent);
                }
            }
        });

        // We create the action when a user clicks on the Back to Sign In Page button
        Button backToSignInPageButton = findViewById(R.id.return_to_sign_in_page_from_sign_up_page);
        backToSignInPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToSignInPageIntent = new Intent(getApplicationContext(), SignIn.class);
                startActivity(backToSignInPageIntent);
            }
        });
    }
}