package sample;

public class CreateActivityTable {
    int id;
    String timestamp, username, state;

    public CreateActivityTable(int id, String username, String state, String timestamp) {
        this.id = id;
        this.timestamp = timestamp;
        this.username = username;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
