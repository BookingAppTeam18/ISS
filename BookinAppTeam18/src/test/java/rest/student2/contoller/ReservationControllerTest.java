package rest.student2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import rest.controller.ReservationController;
import rest.domain.DTO.ReservationDTO;
import rest.domain.enumerations.ReservationStatus;
import rest.service.ReservationService;

import java.util.Calendar;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ReservationController.class)
public class ReservationControllerTest {

    @MockBean
    private ReservationService reservationService;

    // Remove unnecessary @Autowired annotation
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should change reservation status")
    public void shouldChangeReservationStatus() throws Exception {
        Date mockStartDate = new Date();
        Date mockEndDate = new Date(mockStartDate.getTime());

        // Add a week to the end date
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mockEndDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        mockEndDate = calendar.getTime();

        ReservationDTO reservationDTO = new ReservationDTO(1L, mockStartDate, mockEndDate, 100.0, 1L, 1L, 1, ReservationStatus.CREATED);

        ReservationDTO updatedReservationDTO = new ReservationDTO(1L, mockStartDate, mockEndDate, 100.0, 1L, 1L, 1, ReservationStatus.APPROVED);

        if (reservationService != null) {
            Mockito.when(reservationService.findOne(reservationDTO.getId())).thenReturn(reservationDTO);
            Mockito.when(reservationService.update(reservationDTO)).thenReturn(updatedReservationDTO);
        }

        mockMvc.perform(put("/api/reservations/{reservationId}", reservationDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationDTO)))
                .andExpect(status().is(200))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", Matchers.is(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$[0].startDate", Matchers.is(mockStartDate.getTime())))
                .andExpect(jsonPath("$[0].endDate", Matchers.is(mockEndDate.getTime())))
                .andExpect(jsonPath("$[0].price", Matchers.is(100.0)))
                .andExpect(jsonPath("$[0].accountId", Matchers.is(1L)))
                .andExpect(jsonPath("$[0].accommodationId", Matchers.is(1L)))
                .andExpect(jsonPath("$[0].numberOfGuests", Matchers.is(1)))
                .andExpect(jsonPath("$[0].reservationStatus", Matchers.is(ReservationStatus.APPROVED)));
    }
}
