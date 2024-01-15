package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rest.domain.*;
import rest.domain.DTO.*;

import rest.domain.enumerations.AccommodationState;
import rest.domain.enumerations.AccommodationType;
import rest.domain.enumerations.Benefit;

import rest.domain.enumerations.ReservationStatus;

import rest.domain.enumerations.UserState;
import rest.repository.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.math.BigInteger;
import java.util.*;

@Service
public class AccountService implements IService<AccountDTO> {

    private final JavaMailSender javaMailSender;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccommodationRepository accommodationRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private AccountCommentRepository accountCommentRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    public AccountService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    @Override
    public Collection<AccountDTO> findAll(){
        List<AccountDTO> accountDTOList = new ArrayList<>();
        Collection<Account> accounts = accountRepository.findAll();
        for(Account account : accounts){
            accountDTOList.add(new AccountDTO(account));
        }
        return accountDTOList;
    }

    public AccountDTO findByEmail(String email) {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            return null;
        }
        return new AccountDTO(account);
    }

    @Override
    public AccountDTO findOne(Long id){
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new AccountDTO(account.get());
    }

    @Override
    public AccountDTO insert(AccountDTO accountDTO) throws Exception {
        UserType userType = userTypeRepository.findByName(accountDTO.getUserType().toString());
        Account account = new Account(accountDTO, userType);
        try {
            account.setUserState(UserState.CREATED);
            Account savedAccount = accountRepository.save(account);
            accountRepository.flush();
            return new AccountDTO(savedAccount);
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
            StringBuilder sb = new StringBuilder(1000);
            for (ConstraintViolation<?> error : errors) {
                sb.append(error.getMessage() + "\n");
            }
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, sb.toString());
        }
    }
    public void sendActivationEmail(String toEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("bookingappteam18@gmail.com");  // Postavi svoj email
        message.setTo(toEmail);
        message.setSubject("Aktivacija naloga");
        String link = "http://192.168.0.31:8080/api/auth/activate?email="+toEmail;

        message.setText("Kliknite na sledeći link kako biste aktivirali svoj nalog: " + link);

        javaMailSender.send(message);
    }

    public AccountDTO blockAccount(Long id) {
        AccountDTO accountDTO = findOne(id);
        UserType userType = userTypeRepository.findByName(accountDTO.getUserType().toString());
        Account account = new Account(accountDTO, userType);
        account.setUserState(UserState.BANNED);
        System.out.println(account.getUserState());

        if(account.getUserType().getName().equals("GUEST")){
            if(ReservationsExist(id)){
                Collection<Reservation> reservations = reservationRepository.findGuestReservations(id);
                for(Reservation reservation : reservations){
                    reservation.setReservationStatus(ReservationStatus.DENIED);
                    reservationRepository.save(reservation);
                    reservationRepository.flush();
                }
                //Provjeriti moze li se ovako sacuvati
//                reservationRepository.saveAll(reservations);
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Guest has reservations");
            }
        }
        if(account.getUserType().getName().equals("OWNER")){
            if(AccommodationsOwned(id))
                denyAccommodations(id);
        }

        accountRepository.save(account);
        accountRepository.flush();
        return new AccountDTO(account);
    }

    @Override
    public AccountDTO update(AccountDTO accountDTO) throws Exception {
        UserType userType = userTypeRepository.findByName(accountDTO.getUserType().toString());
        Account accountToUpdate = new Account(accountDTO, userType);
        try {
            findOne(accountDTO.getId()); // this will throw ResponseStatusException if student is not found
            accountRepository.save(accountToUpdate);
            accountRepository.flush();
            return accountDTO;
        } catch (RuntimeException ex) {
            Throwable e = ex;
            Throwable c = null;
            while ((e != null) && !((c = e.getCause()) instanceof ConstraintViolationException) ) {
                e = (RuntimeException) c;
            }
            if ((c != null) && (c instanceof ConstraintViolationException)) {
                ConstraintViolationException c2 = (ConstraintViolationException) c;
                Set<ConstraintViolation<?>> errors = c2.getConstraintViolations();
                StringBuilder sb = new StringBuilder(1000);
                for (ConstraintViolation<?> error : errors) {
                    sb.append(error.getMessage() + "\n");
                }
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, sb.toString());
            }
            throw ex;
        }
    }

    @Override
    public AccountDTO delete(Long id) {
        AccountDTO accountDTO = findOne(id);
        UserType userType = userTypeRepository.findByName(accountDTO.getUserType().toString());
        Account account = new Account(accountDTO, userType); // this will throw StudentNotFoundException if student is not found
        if(account.getUserType().getName().equals("GUEST")){
            if(ReservationsExist(id)){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Guest has reservations");
            }
        }
        if(account.getUserType().getName().equals("OWNER")){
            if(AccommodationsOwned(id))
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Owner owns accommodations");
            //Ako ne postoje rezervacije za accommodatione koje poseduje owner, izbrisati sve accommodatione
            // koje poseduje taj owner
            deleteAccommodations(id);
        }
//        accountCommentRepository.delete
        accountRepository.delete(account);
        accountRepository.flush();
        return new AccountDTO(account);
    }

    private boolean AccommodationsOwned(Long id) {
        //Vraca listu svih accommodation-a koje poseduje owner
        //Proci kroz listu i za svaki accommodation proveriti da li ima rezervacija
        Collection<Accommodation> accommodations = accommodationRepository.findAccommodationsOwned(id);
        for(Accommodation accommodation : accommodations){
            if(ReservationsExistByOwner(accommodation.getId()))
                return true;
        }
        return false;
    }

    private void denyAccommodations(Long id){
        Collection<Accommodation> accommodations = accommodationRepository.findAccommodationsOwned(id);
        for(Accommodation accommodation : accommodations){
            accommodation.setAccommodationState(AccommodationState.DECLINED);
            accommodationRepository.save(accommodation);
            accommodationRepository.flush();
        }
    }

    private boolean ReservationsExistByOwner(Long id) {
        Collection<Reservation> reservations = reservationRepository.findByAccommodationId(id);
        if(reservations.isEmpty())
            return false;
        return true;
    }

    private void deleteAccommodations(Long id){
        Collection<Accommodation> accommodations = accommodationRepository.findAccommodationsOwned(id);
        for(Accommodation accommodation : accommodations){
            accommodationRepository.delete(accommodation);
            accommodationRepository.flush();
        }
    }

    private boolean ReservationsExist(Long id) {
        Collection<Reservation> reservations = reservationRepository.findGuestReservations(id);
        if(reservations.isEmpty())
            return false;
        return true;

    }

    public Collection<AccommodationDTO> findFavourite(Long id){

        Collection<Object[]> result = accountRepository.findFavouriteAccommodationsByAccountId(id);
        Collection<Accommodation> favouriteAccommodations = new ArrayList<>();
        Collection<AccommodationDTO> favouriteAccommodationDTOs = new ArrayList<AccommodationDTO>();

        for (Object[] row : result) {
            AccommodationDTO accommodationDTO = new AccommodationDTO();

            accommodationDTO.setId(((BigInteger) row[0]).longValue());
            accommodationDTO.setAccommodationState(AccommodationState.valueOf((String) row[1]));
            accommodationDTO.setAccommodationType(AccommodationType.valueOf((String) row[2]));
            accommodationDTO.setDescription((String) row[3]);
            accommodationDTO.setAutomaticallyReserved((Boolean) row[4]);
            accommodationDTO.setLatitude((Double) row[5]);
            accommodationDTO.setLocation((String) row[6]);
            accommodationDTO.setLongitude((Double) row[7]);
            accommodationDTO.setMaxNumOfGuests((Integer) row[8]);
            accommodationDTO.setMinNumOfGuests((Integer) row[9]);
            accommodationDTO.setName((String) row[10]);
            accommodationDTO.setReservationDeadline((Integer) row[11]);
            accommodationDTO.setOwnerId(((BigInteger) row[12]).longValue());

            accommodationDTO.setGallery(new ArrayList<String>());

            ArrayList<Benefit> benefitList = new ArrayList<Benefit>();

            Collection<Object[]> benefits = accountRepository.findBenefitsByAccommodationId(accommodationDTO.getId());
            for (Object[] b : benefits){
                benefitList.add(Benefit.valueOf((String) b[0]));
            }
            accommodationDTO.setBenefits(benefitList);


            accommodationDTO.setGallery(imageService.getAccommodationImages(accommodationDTO.getId()));
//
//            // Dodajte slike koristeći dodatni upit
//            Collection<String> gallery = accommodationRepository.findGalleryByAccommodationId(accommodationDTO.getId());
//            accommodationDTO.setGallery(new ArrayList<>(gallery));

            favouriteAccommodationDTOs.add(accommodationDTO);
        }



        return favouriteAccommodationDTOs;
    }



    public AccommodationDTO addInFavourites(Long userId, AccommodationDTO accommodationDTO) {
        // Retrieve the user account from the repository based on the userId
        Account userAccount = accountRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Check if the accommodation is already in favorites to avoid duplicates
        if (!userAccount.getFavouriteAccommodations().contains(accommodationDTO.getId())) {
            // Add the accommodation to the user's favorites
            userAccount.getFavouriteAccommodations().add(accommodationDTO.getId());

            // Save the updated user account
            accountRepository.save(userAccount);
            accountRepository.flush();

            // Return the modified accommodationDTO or a confirmation DTO
            return accommodationDTO;
        } else {
            // Optionally, handle the case where the accommodation is already in favorites
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Accommodation is already in favorites");
        }
    }


    @Override
    public void deleteAll() {
        accountRepository.deleteAll();
        accountRepository.flush();
    }

    public AccountDetailsDTO findAccountDetails(Long accountId) {

        AccountDetailsDTO accountDetailsDTO = new AccountDetailsDTO();

        Account account = accountRepository.getOne(accountId);
        Collection<AccountComment> accountComments = accountCommentRepository.FindAccountComments(accountId);

        accountDetailsDTO.setAccountDTO(new AccountDTO(account));
        for (AccountComment accountComment: accountComments) {
            accountDetailsDTO.getCommentsDTO().add(new CommentDTO(accountComment));
        }

        return accountDetailsDTO;
    }
}
