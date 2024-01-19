package rest.student1.controller;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import rest.controller.PriceController;
import rest.domain.DTO.PriceDTO;
import rest.service.PriceService;

import java.util.Calendar;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PriceController.class)
public class PriceControllerTest {

    @MockBean
    private PriceService priceService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = "OWNER")
    @DisplayName("Should List All Posts When making GET request to endpoint - /api/posts/")
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

        mockMvc.perform(post("http://localhost8080/api/prices",priceDTO))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].startDate", Matchers.is(mockStartDate.getTime())))
                .andExpect(jsonPath("$[0].endDate", Matchers.is(mockEndDate.getTime())))
                .andExpect(jsonPath("$[0].amount", Matchers.is(100)))
                .andExpect(jsonPath("$[0].accommodationId", Matchers.is(2)));
    }
}
