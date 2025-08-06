package api.login;

public class ErrorLogin {
    private String error;

    public ErrorLogin() {
    }
    public ErrorLogin(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
