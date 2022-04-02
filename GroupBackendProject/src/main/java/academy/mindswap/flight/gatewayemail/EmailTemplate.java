package academy.mindswap.flight.gatewayemail;

public enum EmailTemplate {
    WELCOME_EMAIL("welcome-email"),
//    CHANGE_PASSWORD("password-changed-email"),
//    RESET_PASSWORD("reset-password-email"),
    BOOKED_FLIGHT("booked-flight-email"),
    CACELED_FLIGHT("canceled-flight-email");

    private String name;

    EmailTemplate(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
