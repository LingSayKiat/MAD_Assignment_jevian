package sg.edu.np.mad.mad_assignment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class DetailSportAdapter extends RecyclerView.Adapter<DetailSportAdapter.ViewHolder> {
    private List<Activity> dataList;

    private List<Participant> participantList = new ArrayList<>();
    private String userId;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("ParticipantInfo");
    DatabaseReference activityDatabaseReference = FirebaseDatabase.getInstance().getReference("ActivityInfo");

    public DetailSportAdapter(List<Activity> dataList) {
        this.dataList = dataList;
        initParticipants();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sport_category_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailSportAdapter.ViewHolder holder, int position) {
        final Activity data = dataList.get(position);
        if(currentUser != null) {
            userId = currentUser.getUid();
        }
        if(data.getCurrentPax() < data.getPax()) {
            if(data.getSportId() == 1) {
                holder.imageView.setImageResource(R.mipmap.badminton_round);
                holder.nameView.setText("Badminton" + " - " + data.getArea());
            }
            else if(data.getSportId() == 2) {
                holder.imageView.setImageResource(R.mipmap.netball_round);
                holder.nameView.setText("Netball" + " - " + data.getArea());
            }
            else if(data.getSportId() == 3) {
                holder.imageView.setImageResource(R.mipmap.football_round);
                holder.nameView.setText("Football" + " - " + data.getArea());
            }
            else if(data.getSportId() == 4) {
                holder.imageView.setImageResource(R.mipmap.tennis_round);
                holder.nameView.setText("Tennis" + " - " + data.getArea());
            }
            else {
                holder.imageView.setImageResource(R.mipmap.basketball_round);
                holder.nameView.setText("Basketball" + " - " + data.getArea());
            }
            holder.descView.setText(data.getDate() + ", " + data.getTime());
            final Button joinBtn = holder.joinBtn;

            if(participantList!=null) {
                for (Participant participant : participantList) {
                    if(participant.getActivityId().equals(data.getId()) && participant.getUserId().equals(userId)) {
                        joinBtn.setText("Joined");
                    }
                }
            }

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    intent.putExtra("activity", data);
                    v.getContext().startActivity(intent);
                }
            });

            joinBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String joinText = joinBtn.getText().toString();
                    if(joinText.equalsIgnoreCase("joined")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("You have already joined this activity.");
                        builder.setCancelable(true);
                        LocalDateTime joinedAt = LocalDateTime.now();
                        for (Participant participant : participantList) {
                            if(participant.getActivityId() != null && participant.getUserId() != null){
                                if(participant.getActivityId().equals(data.getId()) && participant.getUserId().equals(userId)) {
                                    joinedAt = LocalDateTime.parse(participant.getJoinedAt());
                                }
                            }
                        }
                        long daysDifference = ChronoUnit.DAYS.between(joinedAt, LocalDateTime.now());
                        if(daysDifference >= 3) {
                            builder.setMessage("Are you sure you want to cancel your booking?");
                            builder.setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String participantId = "";
                                    for (Participant participant : participantList) {
                                        if(participant.getActivityId().equals(data.getId()) && participant.getUserId().equals(userId)) {
                                            participantId = participant.getParticipantId();
                                        }
                                    }
                                    DatabaseReference dataToDelete = databaseReference.child(participantId);
                                    dataToDelete.removeValue();
                                    joinBtn.setText("join");
                                    Toast.makeText(v.getContext(), "Canceled!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            builder.setMessage("You can only cancel 3 days after joining an event.");
                            builder.setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                        }
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Confirm Join Activity?");
                        if(data.getSportId() == 1) { builder.setMessage("Badminton" + " - " + data.getArea()); }
                        else if(data.getSportId() == 2) { builder.setMessage("Netball" + " - " + data.getArea()); }
                        else if(data.getSportId() == 3) { builder.setMessage("Football" + " - " + data.getArea()); }
                        else if(data.getSportId() == 4) { builder.setMessage("Tennis" + " - " + data.getArea()); }
                        else { builder.setMessage("Basketball" + " - " + data.getArea()); }
                        builder.setCancelable(true);
                        builder.setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activityDatabaseReference.child(data.getId()).child("currentPax").setValue(data.getCurrentPax()+1);
                                joinBtn.setText("Joined");
                                Toast.makeText(v.getContext(), "Joined!", Toast.LENGTH_SHORT).show();
                                String key = databaseReference.push().getKey();
                                Participant tempParticipant = new Participant(key, userId, data.getId(), LocalDateTime.now().toString());
                                databaseReference.child(key).setValue(tempParticipant);
                                participantList.add(tempParticipant);
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameView, descView;
        Button joinBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.detailedActivityImage);
            descView = itemView.findViewById(R.id.detailedDescView);
            nameView = itemView.findViewById(R.id.detailedNameView);
            joinBtn = itemView.findViewById(R.id.detailedJoinButton);
        }
    }

    public void initParticipants() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                participantList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Participant tempParticipant = dataSnapshot.getValue(Participant.class);
                    if(!participantList.contains(tempParticipant))  participantList.add(tempParticipant);
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
