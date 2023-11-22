package rest.domain.enumerations;

public enum AccommodationState {
    APPROVED,
    PENDING,
    DECLINED;

    @Override
    public String toString() {
        switch(this.ordinal()){
            case 0:
                return "Approved";
            case 1:
                return "Pending";
            case 2:
                return "Declined";
            default:
                return "";
        }
    }
}
