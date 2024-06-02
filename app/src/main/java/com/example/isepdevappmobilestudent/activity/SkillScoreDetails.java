package com.example.isepdevappmobilestudent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.isepdevappmobilestudent.R;
import com.example.isepdevappmobilestudent.classes.DBtable.Rating;
import com.example.isepdevappmobilestudent.classes.DBtable.Skill;
import com.example.isepdevappmobilestudent.classes.DBtable.SkillScore;
import com.example.isepdevappmobilestudent.classes.DatabaseManager;

import java.util.ArrayList;
import java.util.Objects;

public class SkillScoreDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.skill_score_details);

        // We set the previous Page Button Activity
        ImageButton previousPageImageButton = findViewById(R.id.back_to_component_scores_details_page_from_skill_scores_details_page);
        previousPageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPreviousPage = new Intent(getApplicationContext(), ComponentScoreDetails.class);
                startActivity(intentPreviousPage);
            }
        });

        // We set the profile Page Activity
        ImageButton profilePageImageButton = findViewById(R.id.profile_image_button_for_module_manager_in_skill_scores_details_page);
        profilePageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfilePage = new Intent(getApplicationContext(), ProfilePage.class);
                startActivity(intentProfilePage);
            }
        });

        // We modify the EditText hint with the current title of the skill
        TextView textViewSkillTitle = findViewById(R.id.skill_title_in_skill_scores_details_page);
        SkillScore currentSkillScore = ComponentScoreDetails.SKILL_SCORE;
        DatabaseManager databaseManager = new DatabaseManager(getApplicationContext());
        ArrayList<Skill> allSkillsInDB = databaseManager.getAllSkills();
        String skillTitle = "";
        for (int index = 0; index < allSkillsInDB.size(); index++) {
            if (allSkillsInDB.get(index).getId() == currentSkillScore.getSkillId()) {
                skillTitle = allSkillsInDB.get(index).getTitle();
            }
        }
        textViewSkillTitle.setText(skillTitle);

        // We display the skill Observation
        TextView textViewSkillObservation = findViewById(R.id.skill_observation_in_skill_scores_details_page);
        String skillObservation = "";
        if (currentSkillScore.getSkillObservation() == null) {
            skillObservation = "No Observation yet";
        } else {
            skillObservation = currentSkillScore.getSkillObservation();
        }
        textViewSkillObservation.setText(skillObservation);

        // We display the skill Rating
        TextView textViewSkillRating = findViewById(R.id.skill_rating_in_skill_scores_details_page);
        String skillRating = "";
        ArrayList<Rating> allRatingsInDB = databaseManager.getAllRatings();
        for (int ratingIndex = 0; ratingIndex < allRatingsInDB.size(); ratingIndex++) {
            if (allRatingsInDB.get(ratingIndex).getId() == currentSkillScore.getRatingId()) {
                skillRating = allRatingsInDB.get(ratingIndex).getName() + " - " + allRatingsInDB.get(ratingIndex).getValue() + "/20";
            } else {
                skillRating = "No Rating yet";
            }
        }
        textViewSkillRating.setText(skillRating);

        // We create the Modify Button Activity
        Button buttonRequestReassessment = findViewById(R.id.modify_skill_score_to_database_button);
        buttonRequestReassessment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRequestReassessment = new Intent(getApplicationContext(), RequestReassessment.class);
                startActivity(intentRequestReassessment);
            }
        });

    }
}