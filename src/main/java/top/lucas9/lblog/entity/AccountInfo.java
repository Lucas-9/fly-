package top.lucas9.lblog.entity;

import java.io.Serializable;

/**
 * @author lucas
 */
public class AccountInfo extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String avatar;
    private String email;
    private String token;

    public AccountInfo() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
