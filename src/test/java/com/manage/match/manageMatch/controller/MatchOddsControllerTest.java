package com.manage.match.manageMatch.controller;

import com.manage.match.manageMatch.exceptions.MatchNotFoundException;
import com.manage.match.manageMatch.model.Match;
import com.manage.match.manageMatch.model.MatchOdds;
import com.manage.match.manageMatch.model.SportEnum;
import com.manage.match.manageMatch.service.MatchOddsService;
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

@WebMvcTest(MatchOddsController.class)
public class MatchOddsControllerTest {

    @MockBean
    private MatchOddsService matchOddsService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenGetAllMatchOdds_thenReturnMatchOddsList() throws Exception {
        // Arrange
        Match match = new Match(1L, "OSFP-PAO", LocalDate.of(2021, 3, 31), LocalTime.of(12, 0), "OSFP", "PAO", SportEnum.FOOTBALL);
        MatchOdds matchOdds1 = new MatchOdds(1L, match, "X", 1.5);
        MatchOdds matchOdds2 = new MatchOdds(2L, match, "1", 2.0);
        when(matchOddsService.getAllMatchOdds()).thenReturn(List.of(matchOdds1, matchOdds2));

        // Act & Assert
        mockMvc.perform(get("/api/match-odds/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].specifier").value("X"))
                .andExpect(jsonPath("$[1].specifier").value("1"));
    }

    @Test
    void whenGetMatchOddsById_thenReturnMatchOdds() throws Exception {
        // Arrange
        Match match = new Match(1L, "OSFP-PAO", LocalDate.of(2021, 3, 31), LocalTime.of(12, 0), "OSFP", "PAO", SportEnum.FOOTBALL);
        MatchOdds matchOdds = new MatchOdds(1L, match, "X", 1.5);
        when(matchOddsService.findById(1L)).thenReturn(matchOdds);

        // Act & Assert
        mockMvc.perform(get("/api/match-odds/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specifier").value("X"));
    }

    @Test
    void whenGetMatchOddsByIdNotFound_thenReturnNotFound() throws Exception {
        // Arrange
        when(matchOddsService.findById(99L)).thenThrow(new MatchNotFoundException("MatchOdds with id 99 not found"));

        // Act & Assert
        mockMvc.perform(get("/api/match-odds/get/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenCreateMatchOdds_thenReturnCreatedMatchOdds() throws Exception {
        // Arrange
        MatchOddsEntity matchOddsEntity = new MatchOddsEntity(1L, "1X", 1.5);
        when(matchOddsService.save(any())).thenReturn(matchOddsEntity);

        // Act & Assert
        mockMvc.perform(post("/api/match-odds/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "match": {
                              "description": "OSFP-PAO",
                              "matchDate": "2021-03-31",
                              "matchTime": "12:00:00",
                              "teamA": "OSFP",
                              "teamB": "PAO",
                              "sport": "FOOTBALL"
                          },
                          "specifier": "1X",
                          "odd": 0
                        }
                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.specifier").value("1X"));
    }

    @Test
    void whenUpdateMatchOdds_thenReturnUpdatedMatchOdds() throws Exception {
        // Arrange
        MatchOddsEntity matchOddsEntity = new MatchOddsEntity(1L, "1X", 1.5);

        // Mocking the service update method
        when(matchOddsService.update(any(), any())).thenReturn(matchOddsEntity);

        // Act & Assert
        mockMvc.perform(put("/api/match-odds/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                            "id": 1,
                            "match": {
                                "id": 2,
                                "description": "OSFP-PAO",
                                "matchDate": "2022-03-31",
                                "matchTime": "12:00:00",
                                "teamA": "OSFP",
                                "teamB": "PAO",
                                "sport": "FOOTBALL"
                            },
                            "specifier": "string",
                            "odd": 0.0
                        }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specifier").value("1X"))  // Check the specifier
                .andExpect(jsonPath("$.odd").value(1.5));
    }

    @Test
    void whenUpdateMatchOddsNotFound_thenReturnNotFound() throws Exception {
        // Arrange
        Match match = new Match(1L, "OSFP-PAO", LocalDate.of(2021, 3, 31), LocalTime.of(12, 0), "OSFP", "PAO", SportEnum.FOOTBALL);
        when(matchOddsService.update(any(), any())).thenThrow(new MatchNotFoundException("MatchOdds with id 99 not found"));

        // Act & Assert
        mockMvc.perform(put("/api/match-odds/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                            "id": 1,
                            "match": {
                                "id": 2,
                                "description": "OSFP-PAO",
                                "matchDate": "2022-03-31",
                                "matchTime": "12:00:00",
                                "teamA": "OSFP",
                                "teamB": "PAO",
                                "sport": "FOOTBALL"
                            },
                            "specifier": "string",
                            "odd": 0.0
                        }
                    """))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenDeleteMatchOdds_thenReturnNoContent() throws Exception {
        // Arrange
        doNothing().when(matchOddsService).deleteById(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/match-odds/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void whenDeleteMatchOddsNotFound_thenReturnNotFound() throws Exception {
        // Arrange
        doThrow(new MatchNotFoundException("MatchOdds with id 99 does not exist"))
                .when(matchOddsService).deleteById(99L);

        // Act & Assert
        mockMvc.perform(delete("/api/match-odds/delete/99"))
                .andExpect(status().isNotFound());
    }
}
