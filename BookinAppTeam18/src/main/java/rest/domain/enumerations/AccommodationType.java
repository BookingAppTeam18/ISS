package rest.domain.enumerations;

public enum AccommodationType {
    ROOM,
    APARTMENT,
    STUDIO,
    HOTEL,
    ENTIRE_HOUSE;

    @Override
    public String toString() {
        switch(this.ordinal()){
            case 0:
                return "Room";
            case 1:
                return "Apartment";
            case 2:
                return "Studio";
            case 3:
                return "Hotel";
            case 4:
                return "Entire house";
            default:
                return "";
        }
    }
}
