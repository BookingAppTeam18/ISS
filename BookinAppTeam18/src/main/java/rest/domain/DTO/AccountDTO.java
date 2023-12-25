package rest.domain.DTO;

import rest.domain.Account;
import rest.domain.enumerations.UserState;
import rest.domain.enumerations.UserTypeDTO;

public class AccountDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private String phone;
    private UserTypeDTO userType;
    private UserState userState;

    public AccountDTO(){

    }

    public AccountDTO(Account account){
        if(account.getUserType().getName().equals("ADMIN"))
            this.userType = UserTypeDTO.ADMIN;
        if(account.getUserType().getName().equals("OWNER"))
            this.userType = UserTypeDTO.OWNER;
        if(account.getUserType().getName().equals("GUEST"))
            this.userType = UserTypeDTO.GUEST;
        this.id = account.getId();
        this.firstName = account.getFirstName();
        this.lastName = account.getLastName();
        this.email = account.getEmail();
        this.address = account.getAddress();
        this.phone = account.getPhone();
        this.password = account.getPassword();
        this.userState = account.getUserState();
    }

    public AccountDTO(Long id,
                      String firstName,
                      String lastName,
                      String email,
                      String address,
                      String phone,
                      String password,
                      UserTypeDTO userType,
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserTypeDTO getUserType() {
        return userType;
    }

    public void setUserType(UserTypeDTO userType) {
        this.userType = userType;
    }

    public UserState getUserState() {
        return userState;
    }

    public void setUserState(UserState userState) {
        this.userState = userState;
    }
}
