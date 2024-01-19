package rest.student2.contoller;

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
import org.springframework.test.web.servlet.MockMvc;
import rest.controller.ReservationController;
import rest.domain.DTO.PriceDTO;
import rest.domain.DTO.ReservationDTO;
import rest.domain.enumerations.ReservationStatus;
import rest.service.PriceService;
import rest.service.ReservationService;

import java.util.Calendar;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReservationController.class)
public class ReservationControllerTest {

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Should change reservation status")
    @WithUserDetails("owner")
    public void shouldChangeReservationStatus() throws Exception {
        Date mockStartDate = new Date();
        Date mockEndDate = new Date(mockStartDate.getTime());

        // Dodajte nedelju dana na datum završetka
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mockEndDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        mockEndDate = calendar.getTime();

        ReservationDTO reservationDTO = new ReservationDTO(1L, mockStartDate, mockEndDate, 100.0, 1L, 1L, 1, ReservationStatus.CREATED);

        ReservationDTO updatedReservationDTO = new ReservationDTO(1L, mockStartDate, mockEndDate, 100.0, 1L, 1L, 1, ReservationStatus.APPROVED);

        // Postavite ponašanje za findOne i update
        Mockito.when(reservationService.findOne(reservationDTO.getId())).thenReturn(reservationDTO);
        Mockito.when(reservationService.update(Mockito.any(ReservationDTO.class))).thenReturn(updatedReservationDTO);

        // Ispitivanje ažuriranja
        mockMvc.perform(put("/api/reservations/{reservationId}", reservationDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.startDate").value(mockStartDate))
                .andExpect(jsonPath("$.endDate").value(mockEndDate))
                .andExpect(jsonPath("$.price").value(100.0))
                .andExpect(jsonPath("$.accountId").value(1L))
                .andExpect(jsonPath("$.accommodationId").value(1L))
                .andExpect(jsonPath("$.numberOfGuests").value(1))
                .andExpect(jsonPath("$.reservationStatus").value(ReservationStatus.APPROVED.toString()));
    }
}
