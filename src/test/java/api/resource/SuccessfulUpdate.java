package api.resource;

public class SuccessfulUpdate {
    private String updatedAt;

    public SuccessfulUpdate() {
    }

    public SuccessfulUpdate(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
