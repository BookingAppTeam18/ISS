package rest.domain.DTO;

import rest.domain.enumerations.AccommodetionType;
import rest.domain.enumerations.Benefit;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
//@Data
public class AccommodationDTO {
    private Long id;
    private Long ownerId;
    private String name;
    private String location;
    private double activePrice;
    private HashMap<Date, Double> priceList;
    private int minNumOfGuests;
    private int maxNumOfGuests;
    private List<String> gallery;
    private List<Benefit> benefits;
    private AccommodetionType accommodetionType;

    //Trebaju seteri za liste i za enume i za mape mozda
}
