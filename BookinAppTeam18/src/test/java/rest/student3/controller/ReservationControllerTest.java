package rest.student3.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import rest.controller.ReservationController;
import rest.domain.DTO.ReservationDTO;
import rest.domain.enumerations.ReservationStatus;
import rest.service.ReservationService;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReservationController.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationService reservationService;

    @Test
    @WithMockUser(authorities = "GUEST")
    @DisplayName("Should create reservation when making POST request to endpoint - /api/reservations")
    public void testCreateReservation() throws Exception {
        Date mockStartDate = new Date(2024, 01, 20);
        Date mockEndDate = new Date(2024, 01, 25);

        ReservationDTO inputReservationDTO = new ReservationDTO(1L, mockStartDate, mockEndDate, 4000, null, null, 2, ReservationStatus.CREATED);
        ReservationDTO mockedReturnValue = new ReservationDTO(1L, mockStartDate, mockEndDate, 4000, null, null, 2, ReservationStatus.CREATED);
        Mockito.when(reservationService.insert(inputReservationDTO)).thenReturn(inputReservationDTO);

        String reservationJson = objectMapper.writeValueAsString(inputReservationDTO);

        mockMvc.perform(post("/api/reservations", 1L)
                        .content(objectMapper.writeValueAsString(reservationJson))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

}


