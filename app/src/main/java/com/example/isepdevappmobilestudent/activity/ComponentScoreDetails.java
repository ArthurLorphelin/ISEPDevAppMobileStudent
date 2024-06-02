package com.example.isepdevappmobilestudent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.isepdevappmobilestudent.R;
import com.example.isepdevappmobilestudent.adapter.SkillScoreAdapter;
import com.example.isepdevappmobilestudent.classes.DBtable.Admin;
import com.example.isepdevappmobilestudent.classes.DBtable.Component;
import com.example.isepdevappmobilestudent.classes.DBtable.ComponentManager;
import com.example.isepdevappmobilestudent.classes.DBtable.ComponentScore;
import com.example.isepdevappmobilestudent.classes.DBtable.SkillScore;
import com.example.isepdevappmobilestudent.classes.DatabaseManager;

import java.util.ArrayList;

public class ComponentScoreDetails extends AppCompatActivity {
    public static SkillScore SKILL_SCORE;
    private String ADMIN_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.component_score_details);

        // We display the Student Name at the top
        TextView textViewComponentNameInTitle = findViewById(R.id.component_name_for_student_in_skills_list);
        ComponentScore currentComponentScore = StudentActivity.COMPONENT_SCORE;


        // We get the name of the Component
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        Component currentComponent = new Component();
        ArrayList<Component> allComponentsInDB = databaseManager.getAllComponents();
        for (int componentIndex = 0; componentIndex < allComponentsInDB.size(); componentIndex++) {
            if (allComponentsInDB.get(componentIndex).getId() == currentComponentScore.getComponentId()) {
                currentComponent = allComponentsInDB.get(componentIndex);
            }
        }
        textViewComponentNameInTitle.setText(currentComponent.getName());

        // We create the Previous Page Activity
        ImageButton imageButtonPreviousPage = findViewById(R.id.back_to_student_page_from_list_of_skills_in_component);
        imageButtonPreviousPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPreviousPage = new Intent(getApplicationContext(), StudentActivity.class);
                startActivity(intentPreviousPage);

            }
        });

        // We create the Profile Page Activity
        ImageButton imageButtonProfilePage = findViewById(R.id.profile_image_button_for_student_in_skills_list_in_component_details);
        imageButtonProfilePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilePage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentProfilePage);
            }
        });

        // We display the Component Name
        TextView textViewComponentName = findViewById(R.id.component_name_text_view_in_component_details);
        textViewComponentName.setText(currentComponent.getName());

        // We make the Component Manager Contact Button appear if there is a Component Manager and we redirect to the Component Manager details
        Button buttonComponentManagerContact = findViewById(R.id.component_manager_contact_button);
        buttonComponentManagerContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdminContact = new Intent(getApplicationContext(), AdminContact.class);
                intentAdminContact.putExtra("adminId", ADMIN_ID);
                startActivity(intentAdminContact);
            }
        });

        // We display the Component Manager Name
        TextView textViewComponentManagerName = findViewById(R.id.component_manager_text_view_in_component_details);
        ArrayList<ComponentManager> allComponentManagersInDB = databaseManager.getAllComponentManagers();
        int adminId = 0;
        for (int componentManagerIndex = 0; componentManagerIndex < allComponentManagersInDB.size(); componentManagerIndex++) {
            if (allComponentManagersInDB.get(componentManagerIndex).getId() == currentComponent.getComponentManagerId()) {
                adminId = allComponentManagersInDB.get(componentManagerIndex).getAdminId();
            }
        }
        String componentManagerName = "";
        if (adminId == 0) {
            componentManagerName = " - ";
        } else {
            ArrayList<Admin> allAdminsInDB = databaseManager.getAllAdmins();
            for (int adminIndex = 0; adminIndex < allAdminsInDB.size(); adminIndex++) {
                if (allAdminsInDB.get(adminIndex).getId() == adminId) {
                    componentManagerName = allAdminsInDB.get(adminIndex).getFirstName() + " " + allAdminsInDB.get(adminIndex).getLastName();
                    ADMIN_ID = String.valueOf(adminId);
                    buttonComponentManagerContact.setVisibility(View.VISIBLE);
                }
            }
        }
        textViewComponentManagerName.setText(componentManagerName);

        // We get the list of all SkillScores for this student and this Component
        ArrayList<SkillScore> allSkillScoresInDB = databaseManager.getAllSkillScores();
        ArrayList<SkillScore> skillScoresForThisComponentScore = new ArrayList<>();
        for (int scoreIndex = 0; scoreIndex < allSkillScoresInDB.size(); scoreIndex++) {
            if (allSkillScoresInDB.get(scoreIndex).getComponentScoreId() == currentComponentScore.getId()) {
                skillScoresForThisComponentScore.add(allSkillScoresInDB.get(scoreIndex));
            }
        }

        // We display the List View items
        ListView listViewSkillScores = findViewById(R.id.skill_scores_list_view_in_component_scores_details);
        SkillScoreAdapter skillScoreAdapter = new SkillScoreAdapter(getApplicationContext(), skillScoresForThisComponentScore);
        listViewSkillScores.setAdapter(skillScoreAdapter);

        // We create the Activity when the user selects a skillScore
        listViewSkillScores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SKILL_SCORE = (SkillScore) parent.getItemAtPosition(position);
                Intent intentSkillScoreDetails = new Intent(getApplicationContext(), SkillScoreDetails.class);
                startActivity(intentSkillScoreDetails);
            }
        });
    }
}