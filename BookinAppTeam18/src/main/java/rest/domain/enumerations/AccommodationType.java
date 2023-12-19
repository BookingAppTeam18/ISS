package rest.domain.enumerations;

public enum AccommodationType {
    NONE,
    ROOM,
    APARTMENT,
    STUDIO,
    HOTEL,
    ENTIRE_HOUSE;

    @Override
    public String toString() {
        switch(this.ordinal()){
            case 0:
                return "None";
            case 1:
                return "Room";
            case 2:
                return "Apartment";
            case 3:
                return "Studio";
            case 4:
                return "Hotel";
            case 5:
                return "Entire house";
            default:
                return "";
        }
    }
}
