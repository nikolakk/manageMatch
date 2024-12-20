package com.manage.match.manageMatch.controller;

import com.manage.match.manageMatch.exceptions.MatchNotFoundException;
import com.manage.match.manageMatch.model.Match;
import com.manage.match.manageMatch.model.SportEnum;
import com.manage.match.manageMatch.service.MatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MatchController.class)
public class MatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatchService matchService;

    @Test
    void whenGetAllMatches_thenReturnMatchList() throws Exception {
        // Arrange
        MatchEntity match1 = new MatchEntity("OSFP-PAO", LocalDate.of(2021, 3, 31), LocalTime.of(12, 0), "OSFP", "PAO", SportEnum.FOOTBALL.name());
        MatchEntity match2 = new MatchEntity("PAO-ARIS", LocalDate.of(2021, 4, 1), LocalTime.of(18, 0), "PAO", "ARIS", SportEnum.FOOTBALL.name());

        when(matchService.getAllMatches()).thenReturn(List.of(match1, match2));

        // Act & Assert
        mockMvc.perform(get("/api/matches/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].description").value("OSFP-PAO"));
    }

    @Test
    void whenGetMatchById_thenReturnMatch() throws Exception {
        // Arrange
        MatchEntity match = new MatchEntity("OSFP-PAO", LocalDate.of(2021, 3, 31), LocalTime.of(12, 0), "OSFP", "PAO", SportEnum.FOOTBALL.name());
        when(matchService.findById(any())).thenReturn(match);

        // Act & Assert
        mockMvc.perform(get("/api/matches/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("OSFP-PAO"));
    }

    @Test
    void whenCreateMatch_thenReturnCreatedMatch() throws Exception {
        // Arrange
        MatchEntity match = new MatchEntity("OSFP-PAO", LocalDate.of(2021, 3, 31), LocalTime.of(12, 0), "OSFP", "PAO", SportEnum.FOOTBALL.name());
        when(matchService.save(any())).thenReturn(match);

        // Act & Assert
        mockMvc.perform(post("/api/matches/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "description": "OSFP-PAO",
                        "matchDate": "2021-03-31",
                        "matchTime": "12:00",
                        "teamA": "OSFP",
                        "teamB": "PAO",
                        "sport": "FOOTBALL"
                    }
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description").value("OSFP-PAO"));
    }

    @Test
    void whenUpdateMatch_thenReturnUpdatedMatch() throws Exception {
        // Arrange
        MatchEntity updatedMatch = new MatchEntity("OSFP-AEK", LocalDate.of(2021, 4, 1), LocalTime.of(16, 0), "OSFP", "AEK", SportEnum.BASKETBALL.name());

        when(matchService.update(eq(1L), any(Match.class))).thenReturn(updatedMatch);

        // Act & Assert
        mockMvc.perform(put("/api/matches/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "description": "OSFP-AEK",
                    "matchDate": "2021-04-01",
                    "matchTime": "16:00",
                    "teamA": "OSFP",
                    "teamB": "AEK",
                    "sport": "BASKETBALL"
                }
            """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").value("OSFP-AEK"));

        verify(matchService, times(1)).update(eq(1L), any(Match.class));
    }

    @Test
    void whenUpdateMatchNotFound_thenReturnNotFound() throws Exception {
        // Arrange
        when(matchService.update(eq(1L), any(Match.class)))
                .thenThrow(new MatchNotFoundException("Match with id 1 not found"));

        // Act & Assert
        mockMvc.perform(put("/api/matches/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "description": "OSFP-AEK",
                    "matchDate": "2021-04-01",
                    "matchTime": "16:00",
                    "teamA": "OSFP",
                    "teamB": "AEK",
                    "sport": "BASKETBALL"
                }
            """))
                .andExpect(status().isNotFound());

        verify(matchService, times(1)).update(eq(1L), any(Match.class));
    }

    @Test
    void whenDeleteMatch_thenReturnNoContent() throws Exception {
        // Arrange
        doNothing().when(matchService).deleteById(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/matches/delete/1"))
                .andExpect(status().isNoContent());

        verify(matchService, times(1)).deleteById(1L);
    }

    @Test
    void whenDeleteMatchNotFound_thenReturnNotFound() throws Exception {
        // Arrange
        doThrow(new MatchNotFoundException("Match with id 1 does not exist"))
                .when(matchService).deleteById(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/matches/delete/1"))
                .andExpect(status().isNotFound());

        verify(matchService, times(1)).deleteById(1L);
    }
}