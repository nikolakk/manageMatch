package com.manage.match.manageMatch.service;

import com.manage.match.manageMatch.controller.MatchEntity;
import com.manage.match.manageMatch.exceptions.MatchNotFoundException;
import com.manage.match.manageMatch.model.Match;
import com.manage.match.manageMatch.model.SportEnum;
import com.manage.match.manageMatch.repository.MatchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MatchServiceTest {

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private MatchService matchService;

    @Test
    void whenGetAllMatches_thenReturnMatchList() {
        // Arrange
        Match match1 = new Match(1L, "OSFP-PAO", LocalDate.of(2021, 3, 31), LocalTime.of(12, 0), "OSFP", "PAO", SportEnum.FOOTBALL);
        Match match2 = new Match(2L, "PAO-ARIS", LocalDate.of(2021, 4, 1), LocalTime.of(18, 0), "PAO", "ARIS", SportEnum.FOOTBALL);
        when(matchRepository.findAll()).thenReturn(List.of(match1, match2));

        // Act
        List<MatchEntity> matches = matchService.getAllMatches();

        // Assert
        assertThat(matches).hasSize(2);
        verify(matchRepository, times(1)).findAll();
    }

    @Test
    void whenFindById_thenReturnMatch() {
        // Arrange
        Match match = new Match(1L, "OSFP-PAO", LocalDate.of(2021, 3, 31), LocalTime.of(12, 0), "OSFP", "PAO", SportEnum.FOOTBALL);
        when(matchRepository.findById(1L)).thenReturn(Optional.of(match));

        // Act
        MatchEntity foundMatch = matchService.findById(1L);

        // Assert
        assertThat(foundMatch).isNotNull();
        assertThat(foundMatch.getDescription()).isEqualTo("OSFP-PAO");
        verify(matchRepository, times(1)).findById(1L);
    }

    @Test
    void whenFindByIdNotFound_thenThrowException() {
        // Arrange
        when(matchRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> matchService.findById(1L))
                .isInstanceOf(MatchNotFoundException.class)
                .hasMessageContaining("Match with id 1 not found");
        verify(matchRepository, times(1)).findById(1L);
    }

    @Test
    void whenSaveValidMatch_thenReturnSavedMatch() {
        // Arrange
        Match match = new Match(null, "OSFP-PAO", LocalDate.of(2021, 3, 31), LocalTime.of(12, 0), "OSFP", "PAO", SportEnum.FOOTBALL);
        when(matchRepository.save(match)).thenReturn(match);

        // Act
        MatchEntity savedMatch = matchService.save(match);

        // Assert
        assertThat(savedMatch).isNotNull();
        verify(matchRepository, times(1)).save(match);
    }

    @Test
    void whenSaveInvalidMatch_thenThrowException() {
        // Arrange
        Match invalidMatch = new Match(null, "OSFP-PAO", LocalDate.of(2021, 3, 31), LocalTime.of(12, 0), null, null, SportEnum.FOOTBALL);

        // Act & Assert
        assertThatThrownBy(() -> matchService.save(invalidMatch))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Team A cannot be null or empty");
        verify(matchRepository, never()).save(any());
    }

    @Test
    void whenUpdateMatch_thenReturnUpdatedMatch() {
        // Arrange
        Match existingMatch = new Match(1L, "OSFP-PAO", LocalDate.of(2021, 3, 31), LocalTime.of(12, 0), "OSFP", "PAO", SportEnum.FOOTBALL);
        Match updatedMatch = new Match(null, "OSFP-AEK", LocalDate.of(2021, 4, 1), LocalTime.of(16, 0), "OSFP", "AEK", SportEnum.BASKETBALL);

        when(matchRepository.findById(1L)).thenReturn(Optional.of(existingMatch));
        when(matchRepository.save(existingMatch)).thenReturn(existingMatch);

        // Act
        MatchEntity result = matchService.update(1L, updatedMatch);

        // Assert
        assertThat(result.getDescription()).isEqualTo("OSFP-AEK");
        assertThat(result.getSport()).isEqualTo(SportEnum.BASKETBALL.name());
        verify(matchRepository, times(1)).findById(1L);
        verify(matchRepository, times(1)).save(existingMatch);
    }

    @Test
    void whenDeleteById_thenDeleteMatch() {
        // Arrange
        when(matchRepository.existsById(1L)).thenReturn(true);

        // Act
        matchService.deleteById(1L);

        // Assert
        verify(matchRepository, times(1)).deleteById(1L);
    }

    @Test
    void whenDeleteByIdNotFound_thenThrowException() {
        // Arrange
        when(matchRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> matchService.deleteById(1L))
                .isInstanceOf(MatchNotFoundException.class)
                .hasMessageContaining("Match with id 1 does not exist");
        verify(matchRepository, never()).deleteById(1L);
    }
}