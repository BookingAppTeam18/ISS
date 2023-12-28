package rest.domain.enumerations;

public enum Benefit {
    NONE,
    WIFI,
    AIR_CONDITIONER,
    KITCHEN,
    FREE_PARKING,
    TV,
    BALCONY,
    BACKYARD,
    PET_FRIENDLY,
    ELEVATOR;


    @Override
    public String toString() {
        switch(this.ordinal()){
            case 0:
                return "None";
            case 1:
                return "WiFi";
            case 2:
                return "Air conditioner";
            case 3:
                return "Kitchen";
            case 4:
                return "Free parking";
            case 5:
                return "TV";
            case 6:
                return "Balcony";
            case 7:
                return "Backyard";
            case 8:
                return "Pet friendly";
            case 9:
                return "Elevator";
            default:
                return "";
        }
    }
}
