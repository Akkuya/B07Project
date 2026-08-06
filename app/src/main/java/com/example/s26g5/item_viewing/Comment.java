package com.example.s26g5.item_viewing;

public class Comment {
    String lotNumber;
    String UID;
    String username;
    Long timestamp;
    String content;

    Comment() { };
    public Comment(String lotNumber, String UID, String username, Long timestamp, String content) {
        this.lotNumber = lotNumber;
        this.UID = UID;
        this.username = username;
        this.timestamp = timestamp;
        this.content = content;
    }

    public boolean postComment() {
        return false;
    }

    public String getUID() { return UID; }
    public String getUsername() { return username; }
    public Long getTimestamp() { return timestamp; }
    public String getContent() { return content; }
    public String getLotNumber() { return lotNumber; }

    public void setLotNumber(String lotNumber) { this.lotNumber = lotNumber; }
    public void setUID(String UID) { this.UID = UID; }
    public void setUsername(String username) { this.username = username; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
    public void setContent(String content) { this.content = content; }
}
