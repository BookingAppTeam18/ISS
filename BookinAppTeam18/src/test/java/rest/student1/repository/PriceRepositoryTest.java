package rest.student1.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import rest.domain.Accommodation;
import rest.domain.Price;
import rest.repository.AccommodationRepository;
import rest.repository.PriceRepository;

import javax.persistence.NonUniqueResultException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.AssertJUnit.fail;

@DataJpaTest
@ActiveProfiles("test")  // Postavljanje profila na "test"
public class PriceRepositoryTest {

    @Autowired
    private PriceRepository priceRepository;


    @Autowired
    private AccommodationRepository accommodationRepository;

    @Test
    public void shouldSavePrice() {
        // Priprema podataka
        Date mockStartDate = new Date();
        Date mockEndDate = new Date(mockStartDate.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mockEndDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        mockEndDate = calendar.getTime();

        Accommodation accommodation = new Accommodation();
        accommodation.setId(2L);
        Accommodation savedAccommodation = accommodationRepository.save(accommodation);

        Price price = new Price(mockStartDate, mockEndDate, 100);
        price.setAccommodation(savedAccommodation);
        Price savedPrice = priceRepository.save(price);

        // Provera rezultata
        assertThat(savedPrice).usingRecursiveComparison().ignoringFields("userId").isEqualTo(price);
    }

    @Test
    public void shouldNotSavePriceWithoutAccommodation() {
        // Priprema podataka
        Accommodation accommodation = new Accommodation();
        accommodation.setId(2L);
        Date mockStartDate = new Date();
        Date mockEndDate = new Date(mockStartDate.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mockEndDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        mockEndDate = calendar.getTime();

        Price price = new Price(mockStartDate, mockEndDate, 100);
        price.setAccommodation(accommodation);

        // Pokušaj čuvanja cene bez smeštaja, očekujemo da će baciti izuzetak

        try {
            // Pokušaj čuvanja cene bez smeštaja
            priceRepository.save(price);
            // Ako dođemo do ovde, test treba da propadne
            fail("Expected an exception, but none was thrown.");
        } catch (Exception e) {
            // Provera da li je izuzetak onaj koji očekujemo
            assertThat(e).isInstanceOf(org.springframework.dao.DataIntegrityViolationException.class)
                    .hasCauseInstanceOf(org.hibernate.exception.ConstraintViolationException.class);
        }
    }

    @Test
    public void shouldFindPricesForAccommodation() {
        // Priprema podataka
        Accommodation accommodation = new Accommodation();
        accommodation.setName("Test Accommodation");
        Accommodation savedAccommodation = accommodationRepository.save(accommodation);

        Date mockStartDate = new Date();
        Date mockEndDate = new Date(mockStartDate.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mockEndDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        mockEndDate = calendar.getTime();

        Price price = new Price(mockStartDate, mockEndDate, 100);
        price.setAccommodation(savedAccommodation);
        Price savedPrice = priceRepository.save(price);

        // Pozivanje metode koju testiramo
        Collection<Price> foundPrices = priceRepository.findPricesForAccommodation(savedAccommodation.getId());

        // Provera rezultata
        assertThat(foundPrices).isNotNull();
        assertThat(foundPrices).contains(savedPrice);
    }


    @Test
    public void shouldNotFindPricesForNonExistingAccommodation() {
        // Pokušaj pronalaženja cena za smeštaj koji ne postoji
        Long nonExistingAccommodationId = 999L;

        // Dobijanje rezultata upita
        Collection<Price> foundPrices = priceRepository.findPricesForAccommodation(nonExistingAccommodationId);

        // Provera da li je rezultat prazna kolekcija
        assertThat(foundPrices).isEqualTo(Collections.emptyList());
    }

    @Test
    public void shouldFindNextPriceForAccommodation() {
        // Priprema podataka
        Accommodation accommodation = new Accommodation();
        accommodation.setName("Test Accommodation");
        Accommodation savedAccommodation = accommodationRepository.save(accommodation);

        // Cena u prošlosti
        Price pastPrice = createPrice(savedAccommodation, -1);
        priceRepository.save(pastPrice);

        // Cena u budućnosti
        Price futurePrice1 = createPrice(savedAccommodation, 1);
        Price futurePrice2 = createPrice(savedAccommodation, 2);
        priceRepository.saveAll(List.of(futurePrice1, futurePrice2));

        // Pozivanje metode koju testiramo
        try {
            // Koristimo Pageable objekat direktno
            List<Price> nextPrices = priceRepository.findNextPriceForAccommodation(savedAccommodation.getId(), Pageable.ofSize(1));

            assertThat(nextPrices.get(0)).isNotNull();
            // Provera rezultata
            // Dodajte ostale provere prema potrebi
        } catch (NonUniqueResultException ex) {
            // Obrada situacije kada ima više od jedne sledeće cene
            fail("Expected a single result, but got multiple results");
        }
    }

    private Price createPrice(Accommodation accommodation, int daysToAdd) {
        Date startDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd);
        Date endDate = calendar.getTime();
        return new Price(startDate, endDate, 100, accommodation);
    }

    @Test
    void testDeletePricesByAccommodationId() {
        // Postavljanje testnih podataka
        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        Accommodation savedAccommodation = accommodationRepository.save(accommodation);

        // Dodavanje cena u bazu
        Price price1 = createPrice(savedAccommodation, 1);
        Price price2 = createPrice(savedAccommodation, 2);
        priceRepository.saveAll(List.of(price1, price2));

        // Poziv metode koja se testira
        priceRepository.deletePricesByAccommodationId(savedAccommodation.getId());

        // Verifikacija da su cene izbrisane
        assertThat(priceRepository.findPricesForAccommodation(savedAccommodation.getId())).isEmpty();
    }

}
