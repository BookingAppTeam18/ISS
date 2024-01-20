package rest.student1.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;
import rest.domain.Accommodation;
import rest.domain.DTO.PriceDTO;
import rest.domain.Price;
import rest.repository.AccommodationRepository;
import rest.repository.PriceRepository;
import rest.service.PriceService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class PriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private AccommodationRepository accommodationRepository;

    @InjectMocks
    private PriceService priceService;


    @Test
    @DisplayName("Should insert new Price")
    public void testValidInsert() {
        // Priprema podataka
        Date mockStartDate = new Date();
        Date mockEndDate = new Date(mockStartDate.getTime() + 7 * 24 * 60 * 60 * 1000); // Dodajemo nedelju dana

        PriceDTO priceDTO = new PriceDTO();
        priceDTO.setStartDate(mockStartDate);
        priceDTO.setEndDate(mockEndDate);
        priceDTO.setAmount(100.0);
        priceDTO.setAccommodationId(1L);

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);

        when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));
        when(priceRepository.save(any(Price.class))).thenAnswer(invocation -> {
            // Ovde možete postaviti ponašanje koje želite da se dogodi kada se pozove save metoda
            Price savedPrice = invocation.getArgument(0);
            savedPrice.setId(123L); // Postavljanje neke vrednosti za ID, samo kao primer
            return savedPrice;
        });

        // Pozivanje metode koju testiramo
        PriceDTO result = priceService.insert(priceDTO);

        // Provera rezultata
        verify(priceRepository, times(1)).save(any(Price.class));
        // Dodajte dodatne provere ako je potrebno
    }


    @Test
    @DisplayName("Should Throw Exception if Accommodation ID doesn't exist")
    public void shouldNotFoundAccommodationThatDoesntExist() {
        // Priprema podataka
        Date mockStartDate = new Date();
        Date mockEndDate = new Date(mockStartDate.getTime() + 7 * 24 * 60 * 60 * 1000); // Dodajemo nedelju dana

        PriceDTO priceDTO = new PriceDTO();
        priceDTO.setStartDate(mockStartDate);
        priceDTO.setEndDate(mockEndDate);
        priceDTO.setAmount(100.0);
        priceDTO.setAccommodationId(100L);

        // Simuliranje povratne vrednosti Optional.empty() kada se pozove findById
        when(accommodationRepository.findById(100L)).thenReturn(Optional.empty());

        // Pozivanje metode koju testiramo i očekujemo izuzetak
        assertThrows(ResponseStatusException.class, () -> priceService.insert(priceDTO));

        // Provera da li je findById metoda pozvana tačno 1 put sa odgovarajućim argumentom
        verify(accommodationRepository, times(1)).findById(100L);
        // Provera da li je save metoda pozvana tačno 0 puta
        verify(priceRepository, times(0)).save(any(Price.class));
        // Dodajte dodatne provere ako je potrebno
    }

    @Test
    @DisplayName("Update Prices with Valid Prices")
    void updatePrices_validPrices_shouldUpdatePrices() throws Exception {
        // Priprema podataka
        Long accommodationId = 1L;
        Accommodation accommodation = new Accommodation();
        accommodation.setId(accommodationId);

        ArrayList<PriceDTO> newValidPrices = prepareValidPricesDTO();
        ArrayList<Price> oldPrices = oldPrices();

        Price savedPrice1 = new Price(newValidPrices.get(0));
        savedPrice1.setAccommodation(accommodation);
        Price savedPrice2 = new Price(newValidPrices.get(1));
        savedPrice2.setAccommodation(accommodation);


        when(priceRepository.findPricesForAccommodation(accommodationId)).thenReturn(oldPrices);
        when(accommodationRepository.findById(accommodationId)).thenReturn(Optional.of(accommodation));
        when(priceRepository.save(new Price(newValidPrices.get(0)))).thenReturn(savedPrice1);
        when(priceRepository.save(new Price(newValidPrices.get(1)))).thenReturn(savedPrice2);
        // Pozivanje metode koju testiramo
        ArrayList<PriceDTO> result =new ArrayList<>( priceService.updatePrices(newValidPrices, accommodationId));

        // Provera rezultata
        assertNotNull(result);
        assertEquals(result.get(0).getAmount(),300);
        assertEquals(result.get(1).getAmount(),500);
        // Dodajte dodatne provere ako je potrebno
    }

    @Test
    @DisplayName("Update Prices with Colliding Dates")
    void updatePrices_collidingDates_shouldThrowException() {
        // Priprema podataka
        Long accommodationId = 1L;

        Collection<PriceDTO> collidingPrices = prepareCollidingPrices();

        // Pozivanje metode koju testiramo i očekujemo izuzetak
        assertThrows(ResponseStatusException.class, () -> priceService.updatePrices(collidingPrices, accommodationId));

        // Provera da li je metoda findPricesForAccommodation pozvana tačno 1 put
        verify(priceRepository, times(0)).findPricesForAccommodation(accommodationId);
        // Provera da li je save metoda pozvana tačno 0 puta
        verify(priceRepository, times(0)).save(any(Price.class));
        // Dodajte dodatne provere ako je potrebno
    }

    // Pomoćne metode za pripremu podataka

    private ArrayList<PriceDTO> prepareValidPricesDTO() {
        ArrayList<PriceDTO> validPrices = new ArrayList<>();


        Date mockStartDate = new Date();
        Date mockEndDate = new Date(mockStartDate.getTime() + (7L * 24L * 60L * 60L * 1000L));

        PriceDTO priceDTO = new PriceDTO();
        priceDTO.setId(1L);
        priceDTO.setStartDate(new Date(mockStartDate.getTime() + (14L * 24L * 60L * 60L * 1000L)));
        priceDTO.setEndDate(new Date(mockEndDate.getTime() + (14L * 24L * 60L * 60L * 1000L)));
        priceDTO.setAmount(300.0);
        priceDTO.setAccommodationId(1L);

        PriceDTO priceDTO2 = new PriceDTO();
        priceDTO2.setId(2L);
        priceDTO2.setStartDate(mockStartDate);
        priceDTO2.setEndDate(mockEndDate);
        priceDTO2.setAmount(500.0);
        priceDTO2.setAccommodationId(1L);


        validPrices.add(priceDTO);
        validPrices.add(priceDTO2);

        return validPrices;
    }

    private ArrayList<Price> oldPrices() {
        ArrayList<Price> validPrices = new ArrayList<>();


        Date mockStartDate = new Date();
        Date mockEndDate = new Date(mockStartDate.getTime() + (7L * 24L * 60L * 60L * 1000L));

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        Price price = new Price();
        price.setId(1L);
        price.setStart(mockStartDate);
        price.setEndDate(mockEndDate);
        price.setAmount(100.0);
        price.setAccommodation(accommodation);

        Price price2 = new Price();
        price2.setId(2L);
        price2.setStart(new Date(mockStartDate.getTime() + (14L * 24L * 60L * 60L * 1000L)));
        price2.setEndDate(new Date(mockEndDate.getTime() + (14L * 24L * 60L * 60L * 1000L)));
        price2.setAmount(100.0);
        price2.setAccommodation(accommodation);

        validPrices.add(price);
        validPrices.add(price2);

        return validPrices;
    }

    private Collection<PriceDTO> prepareCollidingPrices() {
        ArrayList<PriceDTO> collidingPrices = new ArrayList<>();


        Date mockStartDate = new Date();
        Date mockEndDate = new Date(mockStartDate.getTime() + (7L * 24L * 60L * 60L * 1000L));

        PriceDTO priceDTO2 = new PriceDTO();
        priceDTO2.setStartDate(mockStartDate);
        priceDTO2.setEndDate(mockEndDate);
        priceDTO2.setAmount(100.0);
        priceDTO2.setAccommodationId(1L);

        PriceDTO priceDTO = new PriceDTO();
        priceDTO.setStartDate(new Date(mockStartDate.getTime() ));
        priceDTO.setEndDate(new Date(mockEndDate.getTime() ));
        priceDTO.setAmount(100.0);
        priceDTO.setAccommodationId(1L);

        collidingPrices.add(priceDTO);
        collidingPrices.add(priceDTO2);

        return collidingPrices;
    }

    private Collection<Price> newCollidingPrices() {
        // Implementirajte logiku za pripremu kolekcije originalnih cena sa poklapanjem datuma
        return null;
    }
}
