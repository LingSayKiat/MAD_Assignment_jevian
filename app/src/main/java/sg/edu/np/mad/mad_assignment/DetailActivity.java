package sg.edu.np.mad.mad_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        Intent intent = getIntent();
        Activity activity = (Activity) intent.getSerializableExtra("activity");

        ImageView userPfp = findViewById(R.id.userPfp);
        TextView name = findViewById(R.id.activityName);
        TextView pax = findViewById(R.id.paxText);
        TextView desc = findViewById(R.id.descInfo);
        TextView location = findViewById(R.id.locationInfo);
        TextView gender = findViewById(R.id.genderInfo);
        TextView skillLvl = findViewById(R.id.skillInfo);
        TextView date = findViewById(R.id.dateInfo);
        TextView time = findViewById(R.id.timeInfo);


        //name.setText(activity.sport.getName() + " - " + activity.getLocation());
        if(activity.getSportId() == 1) {
            userPfp.setImageResource(R.mipmap.badminton_round);
            name.setText("Badminton" + " - " + activity.getLocation());
        }
        else if(activity.getSportId() == 2) {
            userPfp.setImageResource(R.mipmap.netball_round);
            name.setText("Netball" + " - " + activity.getLocation());
        }
        else if(activity.getSportId() == 3) {
            userPfp.setImageResource(R.mipmap.football_round);
            name.setText("Football" + " - " + activity.getLocation());
        }
        else if(activity.getSportId() == 4) {
            userPfp.setImageResource(R.mipmap.tennis_round);
            name.setText("Tennis" + " - " + activity.getLocation());
        }
        else {
            userPfp.setImageResource(R.mipmap.basketball_round);
            name.setText("Basketball" + " - " + activity.getLocation());
        }

        int paxLeft = activity.getPax() - activity.getCurrentPax();
        pax.setText(activity.getPax() + " slots, " + paxLeft + " left");
        desc.setText(activity.getDesc());
        location.setText(activity.getLocation());
        gender.setText(activity.getGender());
        skillLvl.setText(activity.getSkillLevel());
        date.setText(activity.getDate());
        time.setText(activity.getTime());
        Button back = findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}