package rest.student1.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import rest.domain.Accommodation;
import rest.domain.DTO.PriceDTO;
import rest.domain.Price;
import rest.repository.AccommodationRepository;
import rest.repository.PriceRepository;
import rest.service.PriceService;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.*;

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
    public void testInsert() {
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
}
