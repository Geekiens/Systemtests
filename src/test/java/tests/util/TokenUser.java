package tests.util;

public class TokenUser {
    private long iat;
    private String username;
    private String role;
    private int userId;
    private long exp;

    public TokenUser() {
    }

    public TokenUser(long iat, String username, String role, int userId, long exp) {
        this.iat = iat;
        this.username = username;
        this.role = role;
        this.userId = userId;
        this.exp = exp;
    }

    public long getIat() {
        return iat;
    }

    public void setIat(long iat) {
        this.iat = iat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "TokenUser{" +
                "iat=" + iat +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", userId=" + userId +
                ", exp=" + exp +
                '}';
    }
}
