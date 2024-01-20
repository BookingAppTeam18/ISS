package rest.student1.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import rest.domain.DTO.PriceDTO;
import rest.utils.TokenUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.testng.AssertJUnit.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PriceControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TokenUtils tokenUtils;
    @Test
    @DisplayName("Return inserted price")
    public void shouldCreatePrice() {
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

    @DisplayName("Prices with same date")
    public void shouldntCreatePricesWithSameDate() {
        // Priprema podataka
        Date mockStartDate = new Date();
        Date mockEndDate = new Date(mockStartDate.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mockEndDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        mockEndDate = calendar.getTime();


        PriceDTO priceDTO2 = new PriceDTO();
        priceDTO2.setStartDate(mockStartDate);
        priceDTO2.setEndDate(mockEndDate);
        priceDTO2.setAmount(100.0);
        priceDTO2.setAccommodationId(2L);

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

        ArrayList<PriceDTO> prices = new ArrayList<>();
        prices.add(priceDTO);
        prices.add(priceDTO2);

        HttpEntity<ArrayList<PriceDTO>> request = new HttpEntity<>(prices, headers);
        ParameterizedTypeReference<ArrayList<PriceDTO>> responseType = new ParameterizedTypeReference<ArrayList<PriceDTO>>() {};

        ResponseEntity<ArrayList<PriceDTO>> response = restTemplate.exchange("/api/prices/addMultiple",
                HttpMethod.POST, request, responseType);

        // Provera odgovora
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Očekivani status koda nije vraćen");
        assertNull(response.getBody());
        // Resetujte autentifikaciju nakon testa
        SecurityContextHolder.clearContext();
    }


    @Test
    @DisplayName("Prices with different date")
    public void shouldCreatePricesWithDifferentDates() {
        // Priprema podataka
        Date mockStartDate = new Date();
        Date mockEndDate = new Date(mockStartDate.getTime() + (7L * 24L * 60L * 60L * 1000L));

        PriceDTO priceDTO2 = new PriceDTO();
        priceDTO2.setStartDate(mockStartDate);
        priceDTO2.setEndDate(mockEndDate);
        priceDTO2.setAmount(100.0);
        priceDTO2.setAccommodationId(2L);

        PriceDTO priceDTO = new PriceDTO();
        priceDTO.setStartDate(new Date(mockStartDate.getTime() + (14L * 24L * 60L * 60L * 1000L)));
        priceDTO.setEndDate(new Date(mockEndDate.getTime() + (14L * 24L * 60L * 60L * 1000L)));
        priceDTO.setAmount(100.0);
        priceDTO.setAccommodationId(2L);

// Korisničko ime za koje želite generisati JWT token
        String username = "owner@gmail.com";

        String jwtToken = tokenUtils.generateToken(username);

// Postavljanje tokena u zaglavlje
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        ArrayList<PriceDTO> prices = new ArrayList<>();
        prices.add(priceDTO);
        prices.add(priceDTO2);

        HttpEntity<ArrayList<PriceDTO>> request = new HttpEntity<>(prices, headers);
        ParameterizedTypeReference<ArrayList<PriceDTO>> responseType = new ParameterizedTypeReference<ArrayList<PriceDTO>>() {};

        ResponseEntity<ArrayList<PriceDTO>> response = restTemplate.exchange("/api/prices/addMultiple",
                HttpMethod.POST, request, responseType);

        // Provera odgovora
        assertEquals(HttpStatus.CREATED, response.getStatusCode(), "Očekivani status koda nije vraćen");
        assertNotNull(response.getBody());

        ArrayList<PriceDTO> savedPrices = response.getBody();

        assertNotNull(savedPrices, "Cena nije sačuvana u bazi podataka");
        assertEquals(priceDTO.getStartDate(), savedPrices.get(0).getStartDate(), "Pogrešan datum početka cene");
        assertEquals(priceDTO.getEndDate(), savedPrices.get(0).getEndDate(), "Pogrešan datum kraja cene");
        assertEquals(priceDTO.getAmount(), savedPrices.get(0).getAmount(), 0.01, "Pogrešan iznos cene");
        assertEquals(priceDTO.getAccommodationId(), savedPrices.get(0).getAccommodationId(), "Pogrešan ID smeštaja");
        assertEquals(priceDTO2.getStartDate(), savedPrices.get(1).getStartDate(), "Pogrešan datum početka cene");
        assertEquals(priceDTO2.getEndDate(), savedPrices.get(1).getEndDate(), "Pogrešan datum kraja cene");
        assertEquals(priceDTO2.getAmount(), savedPrices.get(1).getAmount(), 0.01, "Pogrešan iznos cene");
        assertEquals(priceDTO2.getAccommodationId(), savedPrices.get(1).getAccommodationId(), "Pogrešan ID smeštaja");

        // Resetujte autentifikaciju nakon testa
        SecurityContextHolder.clearContext();
    }


    @Test
    @DisplayName("Prices with accommodation that doesn't exist")
    public void shouldntCreatePriceNonExistedAccommodation() {
        // Priprema podataka
        Date mockStartDate = new Date();
        Date mockEndDate = new Date(mockStartDate.getTime() + (7L * 24L * 60L * 60L * 1000L));

        PriceDTO priceDTO = new PriceDTO();
        priceDTO.setStartDate(mockStartDate);
        priceDTO.setEndDate(mockEndDate);
        priceDTO.setAmount(100.0);
        priceDTO.setAccommodationId(10L);

// Korisničko ime za koje želite generisati JWT token
        String username = "owner@gmail.com";

        String jwtToken = tokenUtils.generateToken(username);

// Postavljanje tokena u zaglavlje
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);


        HttpEntity<PriceDTO> request = new HttpEntity<>(priceDTO, headers);
        ParameterizedTypeReference<PriceDTO> responseType = new ParameterizedTypeReference<PriceDTO>() {};

        ResponseEntity<PriceDTO> response = restTemplate.exchange("/api/prices",
                HttpMethod.POST, request, responseType);

        // Provera odgovora
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Očekivani status koda nije vraćen");

        // Resetujte autentifikaciju nakon testa
        SecurityContextHolder.clearContext();
    }
}