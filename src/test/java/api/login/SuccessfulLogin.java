package api.login;

public class SuccessfulLogin {
    private String token;

    public SuccessfulLogin() {
    }
    public SuccessfulLogin(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
