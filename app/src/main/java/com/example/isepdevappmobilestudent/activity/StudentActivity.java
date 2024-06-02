package com.example.isepdevappmobilestudent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.isepdevappmobilestudent.R;
import com.example.isepdevappmobilestudent.adapter.SummaryStudentAdapter;
import com.example.isepdevappmobilestudent.classes.DBtable.Admin;
import com.example.isepdevappmobilestudent.classes.DBtable.ComponentScore;
import com.example.isepdevappmobilestudent.classes.DBtable.ModuleManager;
import com.example.isepdevappmobilestudent.classes.DBtable.Student;
import com.example.isepdevappmobilestudent.classes.DatabaseManager;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {
    public static ComponentScore COMPONENT_SCORE;
    private String adminIdIntentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.student);

        // We create the Profile Page Activity
        ImageButton imageButtonProfilePage = findViewById(R.id.profile_button_for_student_menu);
        imageButtonProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilePage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentProfilePage);
            }
        });


        // We get all ComponentScores for this Student
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<ComponentScore> allComponentScoresInDB = databaseManager.getAllComponentScores();
        ArrayList<ComponentScore> componentScoresForThisStudent = new ArrayList<>();
        for (int index = 0; index < allComponentScoresInDB.size(); index++) {
            if (allComponentScoresInDB.get(index).getStudentId() == SignIn.STUDENT_ID) {
                componentScoresForThisStudent.add(allComponentScoresInDB.get(index));
            }
        }

        // We create the Team Details Activity
        Button buttonTeamDetails = findViewById(R.id.go_to_team_details_list_button);
        buttonTeamDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentTeamDetails = new Intent(getApplicationContext(), TeamDetails.class);
                startActivity(intentTeamDetails);
            }
        });

        // We display the name of the Module Manager
        TextView textViewModuleManagerName = findViewById(R.id.module_manager_name_text_view_in_component_details);
        ArrayList<ModuleManager> allModuleManagersInDB = databaseManager.getAllModuleManagers();
        ModuleManager moduleManager = allModuleManagersInDB.get(0);
        ArrayList<Admin> allAdminsInDB = databaseManager.getAllAdmins();
        String moduleManagerName = "";
        for (int adminIndex = 0; adminIndex < allAdminsInDB.size(); adminIndex++) {
            if (allAdminsInDB.get(adminIndex).getId() == moduleManager.getAdminId()) {
                moduleManagerName = allAdminsInDB.get(adminIndex).getFirstName() + " " + allAdminsInDB.get(adminIndex).getLastName();
                adminIdIntentData = String.valueOf(allAdminsInDB.get(adminIndex).getId());
            }
        }
        textViewModuleManagerName.setText(moduleManagerName);

        // We create the Activity for Admin Contact
        Button buttonAdminContact = findViewById(R.id.module_manager_contact_button);
        buttonAdminContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdminContact = new Intent(getApplicationContext(), AdminContact.class);
                intentAdminContact.putExtra("adminId", adminIdIntentData);
                startActivity(intentAdminContact);
            }
        });

        // We display the Summary for the Student
        ListView listViewSummary = findViewById(R.id.summary_list_view_in_student_details);
        SummaryStudentAdapter summaryStudentAdapter = new SummaryStudentAdapter(getApplicationContext(), componentScoresForThisStudent);
        listViewSummary.setAdapter(summaryStudentAdapter);

        // We create the activity for a selected item
        listViewSummary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                COMPONENT_SCORE = (ComponentScore) parent.getItemAtPosition(position);
                Intent intentComponentScoreDetails = new Intent(getApplicationContext(), ComponentScoreDetails.class);
                startActivity(intentComponentScoreDetails);
            }
        });
    }
}