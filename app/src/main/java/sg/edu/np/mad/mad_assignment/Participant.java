package sg.edu.np.mad.mad_assignment;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Participant implements Serializable {
    String participantId;
    String userId;
    String activityId;
    String joinedAt;

    public Participant() {}

    public Participant(String participantId, String userId, String activityId, String joinedAt) {
        this.participantId = participantId;
        this.userId = userId;
        this.activityId = activityId;
        this.joinedAt = joinedAt;
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getJoinedAt() { return joinedAt; }

    public void setJoinedAt(String joinedAt) {
        this.joinedAt = joinedAt;
    }
}
