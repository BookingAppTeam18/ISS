package rest.domain;

import org.apache.catalina.User;
import rest.domain.DTO.AccountDTO;
import rest.domain.enumerations.UserState;
import rest.domain.enumerations.UserType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="account_id",length = 5)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private String phone;
    private UserType userType;
    private UserState userState;

    @ElementCollection
    @CollectionTable(
            name = "account_favourite_accommodations",
            joinColumns = @JoinColumn(name = "account_id")
    )
    @Column(name = "accommodation_id")
    private List<Long> favouriteAccommodations;

    public Account(Long id, String firstName, String lastName, String email, String password, String address, String phone, UserType userType, UserState userState, List<Long> favouriteAccommodations) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.userType = userType;
        this.userState = userState;
        this.favouriteAccommodations = favouriteAccommodations;
    }

    public Account(){

    }

    public Account(AccountDTO accountDTO) {
        this(accountDTO.getId(), accountDTO.getFirstName(), accountDTO.getLastName(), accountDTO.getEmail(), accountDTO.getPassword(),accountDTO.getAddress(), accountDTO.getPhone(), accountDTO.getUserType());
    }

    public Account(Long id, String firstName, String lastName, String email, String password, String address, String phone, UserType userType) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password=password;
        this.address = address;
        this.phone = phone;
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserState getUserState() {
        return userState;
    }

    public void setUserState(UserState userState) {
        this.userState = userState;
    }

    public List<Long> getFavouriteAccommodations() {
        return favouriteAccommodations;
    }

    public void setFavouriteAccommodations(List<Long> favouriteAccommodations) {
        this.favouriteAccommodations = favouriteAccommodations;
    }

    public void copyValues(Account account){
        this.firstName = account.firstName;
        this.lastName = account.lastName;
        this.email = account.email;
        this.password = account.password;
        this.address = account.address;
        this.phone = account.phone;
        this.userType = account.userType;
        this.userState = account.userState;
        this.favouriteAccommodations = account.favouriteAccommodations;
    }
}
