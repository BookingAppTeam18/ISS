package rest.student1.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import rest.domain.DTO.PriceDTO;
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

        PriceDTO savedPrice = response.getBody();
        assertNotNull(savedPrice, "Cena nije sačuvana u bazi podataka");
        assertEquals(priceDTO.getStartDate(), savedPrice.getStartDate(), "Pogrešan datum početka cene");
        assertEquals(priceDTO.getEndDate(), savedPrice.getEndDate(), "Pogrešan datum kraja cene");
        assertEquals(priceDTO.getAmount(), savedPrice.getAmount(), 0.01, "Pogrešan iznos cene");
        assertEquals(priceDTO.getAccommodationId(), savedPrice.getAccommodationId(), "Pogrešan ID smeštaja");

        // Resetujte autentifikaciju nakon testa
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Cancel Price")
    public void shouldCancelSecondPriceIntegration() {
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

        PriceDTO savedPrice = response.getBody();
        assertNotNull(savedPrice, "Cena nije sačuvana u bazi podataka");
        assertEquals(priceDTO.getStartDate(), savedPrice.getStartDate(), "Pogrešan datum početka cene");
        assertEquals(priceDTO.getEndDate(), savedPrice.getEndDate(), "Pogrešan datum kraja cene");
        assertEquals(priceDTO.getAmount(), savedPrice.getAmount(), 0.01, "Pogrešan iznos cene");
        assertEquals(priceDTO.getAccommodationId(), savedPrice.getAccommodationId(), "Pogrešan ID smeštaja");


        ResponseEntity<PriceDTO> response2 = restTemplate.postForEntity("/api/prices", request, PriceDTO.class);

        PriceDTO savedPrice2 = response2.getBody();
        assertNotNull(savedPrice2, "Cena nije sačuvana u bazi podataka");
        assertEquals(priceDTO.getStartDate(), savedPrice2.getStartDate(), "Pogrešan datum početka cene");
        assertEquals(priceDTO.getEndDate(), savedPrice2.getEndDate(), "Pogrešan datum kraja cene");
        assertEquals(priceDTO.getAmount(), savedPrice2.getAmount(), 0.01, "Pogrešan iznos cene");
        assertEquals(priceDTO.getAccommodationId(), savedPrice2.getAccommodationId(), "Pogrešan ID smeštaja");

        // Resetujte autentifikaciju nakon testa
        SecurityContextHolder.clearContext();
    }
}