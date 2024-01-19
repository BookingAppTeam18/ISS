package rest.student1.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import rest.controller.PriceController;
import rest.domain.DTO.PriceDTO;
import rest.service.PriceService;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PriceController.class)
public class PriceControllerTest {

    @MockBean
    private PriceService priceService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    @DisplayName("Return inserted price")
    @WithUserDetails("owner")
    public void shouldCreatePost() throws Exception {
        Date mockStartDate = new Date();
        Date mockEndDate = new Date(mockStartDate.getTime());
        // Dodajemo nedelju dana na krajnji datum
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mockEndDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        mockEndDate = calendar.getTime();

        PriceDTO priceDTO = new PriceDTO(1L, mockStartDate.getTime(), mockEndDate.getTime(), 100, 2L);


        Mockito.when(priceService.insert(priceDTO)).thenReturn(priceDTO);

        String responseJson = this.mockMvc.perform(post("/api/prices")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(priceDTO)))  // Dodavanje stvarnih podataka u zahtev
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(content().string(Matchers.not(isEmptyOrNullString())))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Sada možete izvući atribute iz odgovora
        JsonNode responseNode = objectMapper.readTree(responseJson);
        Long id = responseNode.get("id").asLong();
        Long startDate = responseNode.get("startDate").asLong();
        Long endDate = responseNode.get("endDate").asLong();
        int amount = responseNode.get("amount").asInt();
        Long accommodationId = responseNode.get("accommodationId").asLong();

        // Sada možete raditi sa izvučenim vrednostima kako vam odgovara
        Assertions.assertEquals(Optional.of(1L), id);
        Assertions.assertEquals(Optional.of(mockStartDate.getTime()), startDate);
        Assertions.assertEquals(Optional.of(mockEndDate.getTime()), endDate);
        Assertions.assertEquals(100, amount);
        Assertions.assertEquals(Optional.of(2L), accommodationId);
    }
}
