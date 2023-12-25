package rest.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rest.domain.DTO.AccountDTO;
import rest.domain.enumerations.UserState;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="accounts")
public class Account implements UserDetails {
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_role")
    private UserType userType;
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "user_state")
    private UserState userState;
    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

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

    public Account(AccountDTO accountDTO, UserType userType) {
        this.userType = userType;
        this.id = accountDTO.getId();
        this.firstName = accountDTO.getFirstName();
        this.lastName = accountDTO.getLastName();
        this.email = accountDTO.getEmail();
        this.address = accountDTO.getAddress();
        this.phone = accountDTO.getPhone();
        this.password = accountDTO.getPassword();
        this.userState = accountDTO.getUserState();
    }

    public Account(Long id,
                   String firstName,
                   String lastName,
                   String email,
                   String address,
                   String phone,
                   String password,
                   UserType userType,
                   UserState userState) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.password = password;
        this.userType = userType;
        this.userState = userState;
    }

    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<UserType> authorities = new ArrayList<UserType>();
        authorities.add(this.userType);
        return  authorities;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userState == UserState.ACTIVE;
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
