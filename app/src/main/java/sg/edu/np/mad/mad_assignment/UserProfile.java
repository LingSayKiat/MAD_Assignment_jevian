package sg.edu.np.mad.mad_assignment;

import java.io.Serializable;

public class UserProfile implements Serializable {
    int id;
    String username;
    String email;
    int pfp;
    String bio;
    private String password;

    public UserProfile() {}

    public UserProfile(int id, String username, String email, int pfp, String bio, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.pfp = pfp;
        this.bio = bio;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPfp() {
        return pfp;
    }

    public void setPfp(int pfp) {
        this.pfp = pfp;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}