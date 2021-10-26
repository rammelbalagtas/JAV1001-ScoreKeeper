package com.rammelbalagtas.rammel_score;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    // Constants declaration for team selection
    private static final String PH_TEAM = "PH";
    private static final String CA_TEAM = "CA";

    // Variable declarations
    private int teamPHScore; // Philippine team score
    private int teamCAScore; // Canada team score
    private int scoreValue; // Score value to add (default value is 1 point)
    private String teamSelected; //  Toggle team selection (default is ON)

    private TextView teamPHScoreText; // Text view for PH score
    private TextView teamCAScoreText; // Text view for CA score

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set initial default values
        teamPHScore = 0;
        teamCAScore = 0;
        scoreValue = 1;
        teamSelected = PH_TEAM;

        // Get the instance of each UI elements upon loading
        teamPHScoreText = findViewById(R.id.text_score_ph);
        teamCAScoreText = findViewById(R.id.text_score_canada);
        Button increaseScoreButton = findViewById(R.id.button_plus);
        Button decreaseScoreButton = findViewById(R.id.button_minus);
        Button resetScoreButton = findViewById(R.id.button_reset);
        RadioGroup scoreRadioGroup = findViewById(R.id.radiogroup_score_options);
        scoreRadioGroup.check(R.id.rb_score_1);     // set default score selection in the UI
        ToggleButton teamToggle = findViewById(R.id.toggle_team);
        teamToggle.setChecked(true);                // set default team selection in the UI

        // Set onClick listener for each button
        increaseScoreButton.setOnClickListener(increaseBtnListener);
        decreaseScoreButton.setOnClickListener(decreaseBtnListener);
        resetScoreButton.setOnClickListener(resetBtnListener);
        // Set onCheckedChange listener for the toggle button
        teamToggle.setOnCheckedChangeListener(toggleBtnListener);
        // Set onCheckedChange listener for the radio group
        scoreRadioGroup.setOnCheckedChangeListener(radioScoreListener);
    }

    /**
     * onClickListener for add button
     */
    private View.OnClickListener increaseBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (teamSelected.equals(PH_TEAM)) {
                teamPHScore += scoreValue;
                teamPHScoreText.setText(Integer.toString(teamPHScore));
            } else if (teamSelected.equals(CA_TEAM)) {
                teamCAScore += scoreValue;
                teamCAScoreText.setText(Integer.toString(teamCAScore));
            }
        }
    };

    /**
     * onClickListener for minus button
     */
    private View.OnClickListener decreaseBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // check is score is valid
            if (!isScoreValid()) {
                return;
            }

            // decrease the score based on the score and team selection
            if (teamSelected.equals(PH_TEAM)) {
                teamPHScore -= scoreValue;
                teamPHScoreText.setText(Integer.toString(teamPHScore));
            } else if (teamSelected.equals(CA_TEAM)) {
                teamCAScore -= scoreValue;
                teamCAScoreText.setText(Integer.toString(teamCAScore));
            }
        }
    };

    /**
     * onClickListener for reset button
     */
    private View.OnClickListener resetBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // reset all the scores and update the UI
            teamPHScore = 0;
            teamCAScore = 0;
            teamPHScoreText.setText(Integer.toString(teamPHScore));
            teamCAScoreText.setText(Integer.toString(teamCAScore));

            // reset default toggle and score selection and update the UI
            scoreValue = 1;
            teamSelected = PH_TEAM;
            RadioGroup scoreRadioGroup = findViewById(R.id.radiogroup_score_options);
            scoreRadioGroup.check(R.id.rb_score_1);
            ToggleButton teamToggle = findViewById(R.id.toggle_team);
            teamToggle.setChecked(true);

        }
    };

    /**
     * OnCheckedChangeListener for toggle button when switching teams
     */
    private CompoundButton.OnCheckedChangeListener toggleBtnListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton toggleButton, boolean isToggled) {
            teamSelected = isToggled ? PH_TEAM : CA_TEAM;
        }
    };

    /**
     * OnCheckedChangeListener for score selection radio group
     */
    private RadioGroup.OnCheckedChangeListener radioScoreListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup toggleButton, int checkedId) {
            switch (checkedId) {
                case R.id.rb_score_2: // 2 points
                    scoreValue = 2;
                    break;
                case R.id.rb_score_3: // 3 points
                    scoreValue = 3;
                    break;
                default:
                    scoreValue = 1; // 1 point (default)
                    break;
            }
        }
    };

    /**
     * Method for validating the score
     * @return Boolean value - indicates whether the score is valid
     */
    private boolean isScoreValid() {

        boolean isNegative = false;
        if (teamSelected.equals(PH_TEAM)) {
            isNegative = (teamPHScore - scoreValue) < 0;
        } else if (teamSelected.equals(CA_TEAM)) {
            isNegative = (teamCAScore - scoreValue) < 0;
        }

        if (isNegative) {
            Toast toast = Toast.makeText(this, R.string.negative_score, Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
        return true;
    }

}