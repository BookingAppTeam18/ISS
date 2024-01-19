package rest.student1.service;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
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
@TestPropertySource(properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration,rest.student1.config.TestSecurityConfig")
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

        // Pozivanje metode koju testiramo
        PriceDTO result = priceService.insert(priceDTO);

        // Provera rezultata
        verify(priceRepository, times(1)).save(any(Price.class));
        // Dodajte dodatne provere ako je potrebno
    }
}
