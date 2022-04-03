package academy.mindswap.flight.gatewayemail;

public enum EmailTemplate {
    WELCOME_EMAIL(  "d-37128938d12b4b2786d85bb3d0434d38"),
    BOOKED_FLIGHT("d-65c70b9a13fb4931a8e484deb7a394ef"),
    CANCELED_FLIGHT("d-5ec213b741d748d19d25d2d1f1abac91");

    private String name;

    EmailTemplate(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
