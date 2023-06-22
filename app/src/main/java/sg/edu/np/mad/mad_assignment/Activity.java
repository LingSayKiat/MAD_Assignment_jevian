package sg.edu.np.mad.mad_assignment;

import java.io.Serializable;

public class Activity implements Serializable {
    String activityId;
    int sportId;
    String desc;
    String area;
    String location;
    String date;
    String time;
    String skillLevel;
    String ageGrp;
    String gender;
    int pax;
    int currentPax;
    String userId;

    public Activity() {}

    public Activity(String activityId, int sportId, String desc, String area, String location, String date, String time, String skillLevel, String ageGrp, String gender, int pax, String userId) {
        this.activityId = activityId;
        this.sportId = sportId;
        this.desc = desc;
        this.area = area;
        this.location = location;
        this.date = date;
        this.time = time;
        this.skillLevel = skillLevel;
        this.ageGrp = ageGrp;
        this.gender = gender;
        this.pax = pax;
        this.currentPax = 0;
        this.userId = userId;
    }

    public String getId() { return activityId; }

    public void setId(String activityId) { this.activityId = activityId; }

    public int getSportId() {
        return sportId;
    }

    public void setSportId(int sportId) {
        this.sportId = sportId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() { return date; }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() { return time; }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getAgeGrp() {
        return ageGrp;
    }

    public void setAgeGrp(String ageGrp) {
        this.ageGrp = ageGrp;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getPax() { return pax; }

    public void setPax(int pax) { this.pax = pax; }

    public int getCurrentPax() { return currentPax; }

    public void setCurrentPax(int currentPax) { this.currentPax = currentPax; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }
}