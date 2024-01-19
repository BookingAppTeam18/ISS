package rest.student1.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import rest.controller.PriceController;
import rest.domain.DTO.PriceDTO;
import rest.service.PriceService;

import java.util.Calendar;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PriceController.class)
@ActiveProfiles("MyTest")  // Postavljanje profila na "test"
public class PriceControllerTest {

    @MockBean
    private PriceService priceService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        // Postavljanje Mockito kada je potrebno
    }
    @Test
    @DisplayName("Return inserted price")
    @WithUserDetails("owner")
    public void ShouldCreatePrice() throws Exception {
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
        // Dodajte potrebne informacije u priceDTO

        // Postavljanje Mockito kada je potrebno
        Mockito.when(priceService.insert(Mockito.any(PriceDTO.class))).thenReturn(priceDTO);

        // Simulacija HTTP POST zahteva
        mockMvc.perform(post("/api/prices")
                        .content(objectMapper.writeValueAsString(priceDTO))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.startDate").value(priceDTO.getStartDate().getTime()))
                .andExpect(jsonPath("$.endDate").value(priceDTO.getEndDate().getTime()))
                .andExpect(jsonPath("$.amount").value(priceDTO.getAmount()))
                .andExpect(jsonPath("$.accommodationId").value(priceDTO.getAccommodationId()));


        // Provera poziva metode u priceService
        Mockito.verify(priceService, Mockito.times(1)).insert(Mockito.any(PriceDTO.class));
    }
}
