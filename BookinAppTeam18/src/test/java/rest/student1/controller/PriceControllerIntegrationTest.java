package rest.student1.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import rest.domain.DTO.PriceDTO;
import rest.domain.Price;
import rest.repository.PriceRepository;
import rest.utils.TokenUtils;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PriceRepository priceRepository;
    @Autowired
    private TokenUtils tokenUtils;
    @Test
    @DisplayName("Return inserted price")
    public void shouldCreatePriceIntegration() {
        // Priprema podataka
        Date mockStartDate = new Date();
        Date mockEndDate = new Date(mockStartDate.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mockEndDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        mockEndDate = calendar.getTime();

        PriceDTO priceDTO = new PriceDTO();
        priceDTO.setStartDate(mockStartDate);
        priceDTO.setEndDate(mockEndDate);
        priceDTO.setAmount(100.0);
        priceDTO.setAccommodationId(2L);

// Korisničko ime za koje želite generisati JWT token
        String username = "owner@gmail.com";

        String jwtToken = tokenUtils.generateToken(username);

// Postavljanje tokena u zaglavlje
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        HttpEntity<PriceDTO> request = new HttpEntity<>(priceDTO, headers);

        ResponseEntity<PriceDTO> response = restTemplate.postForEntity("/api/prices", request, PriceDTO.class);

        // Provera odgovora
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Očekivani status koda nije vraćen");
        assertNotNull(response.getBody());

        // Provera da li je cena zaista sačuvana u bazi podataka
        Long savedPriceId = response.getBody().getId();
        Price savedPrice = priceRepository.findById(savedPriceId).orElse(null);

        assertNotNull(savedPrice, "Cena nije sačuvana u bazi podataka");
        assertEquals(priceDTO.getStartDate(), savedPrice.getStart(), "Pogrešan datum početka cene");
        assertEquals(priceDTO.getEndDate(), savedPrice.getEndDate(), "Pogrešan datum kraja cene");
        assertEquals(priceDTO.getAmount(), savedPrice.getAmount(), 0.01, "Pogrešan iznos cene");
        assertEquals(priceDTO.getAccommodationId(), savedPrice.getAccommodation().getId(), "Pogrešan ID smeštaja");

        // Resetujte autentifikaciju nakon testa
        SecurityContextHolder.clearContext();
    }
}