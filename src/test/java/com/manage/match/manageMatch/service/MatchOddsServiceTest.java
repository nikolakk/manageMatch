package com.manage.match.manageMatch.service;

import com.manage.match.manageMatch.controller.MatchOddsEntity;
import com.manage.match.manageMatch.exceptions.MatchNotFoundException;
import com.manage.match.manageMatch.model.Match;
import com.manage.match.manageMatch.model.MatchOdds;
import com.manage.match.manageMatch.model.SportEnum;
import com.manage.match.manageMatch.repository.MatchOddsRepository;
import com.manage.match.manageMatch.repository.MatchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MatchOddsServiceTest {

    @Mock
    private MatchOddsRepository matchOddsRepository;
    @Mock
    private MatchRepository matchRepository;
    @InjectMocks
    private MatchOddsService matchOddsService;

    @Test
    void whenFindById_thenReturnMatchOdds() {
        // Arrange
        Match match = new Match(1L, "OSFP-PAO", LocalDate.of(2021, 3, 31), LocalTime.of(12, 0), "OSFP", "PAO", SportEnum.FOOTBALL);
        MatchOdds matchOdds = new MatchOdds(1L, match, "X", 1.5);
        when(matchOddsRepository.findById(1L)).thenReturn(Optional.of(matchOdds));

        // Act
        MatchOdds result = matchOddsService.findById(1L);

        // Assert
        assertThat(result).isEqualTo(matchOdds);
        verify(matchOddsRepository, times(1)).findById(1L);
    }

    @Test
    void whenFindByIdNotFound_thenThrowException() {
        // Arrange
        when(matchOddsRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> matchOddsService.findById(1L))
                .isInstanceOf(MatchNotFoundException.class)
                .hasMessageContaining("MatchOdds with id 1 not found");
        verify(matchOddsRepository, times(1)).findById(1L);
    }

    @Test
    void whenSaveMatchOdds_thenReturnSavedMatchOdds() {
        // Arrange
        Match match = new Match(1L, "OSFP-PAO", LocalDate.of(2021, 3, 31), LocalTime.of(12, 0), "OSFP", "PAO", SportEnum.FOOTBALL);
        MatchOdds matchOdds = new MatchOdds(1L, match, "X", 1.5);

        MatchOddsEntity existingMatchOddsEntity = new MatchOddsEntity(1L, "X", 1.5);
        when(matchRepository.findById(1L)).thenReturn(Optional.of(match));
        when(matchOddsRepository.save(any())).thenReturn(matchOdds);

        // Act
        MatchOddsEntity result = matchOddsService.save(existingMatchOddsEntity);

        // Assert
        assertThat(result).isEqualTo(existingMatchOddsEntity);
        verify(matchOddsRepository, times(1)).save(any());
    }

    @Test
    void whenUpdateMatchOdds_thenReturnUpdatedMatchOdds() {
        // Arrange
        MatchOddsEntity existingMatchOddsEntity = new MatchOddsEntity(1L, "X", 1.5);

        Match match = new Match(1L, "OSFP-PAO", LocalDate.of(2021, 3, 31), LocalTime.of(12, 0), "OSFP", "PAO", SportEnum.FOOTBALL);
        MatchOdds existingMatchOdds = new MatchOdds(1L, match, "X", 1.5);
        MatchOdds updatedMatchOdds = new MatchOdds(1L, match, "1X", 2.0);
        when(matchOddsRepository.findById(1L)).thenReturn(Optional.of(existingMatchOdds));
        when(matchOddsRepository.save(existingMatchOdds)).thenReturn(updatedMatchOdds);

        // Act
        MatchOddsEntity result = matchOddsService.update(1L, existingMatchOddsEntity);

        // Assert
        assertThat(result.getSpecifier()).isEqualTo("1X");
        assertThat(result.getOdd()).isEqualTo(2.0);
        verify(matchOddsRepository, times(1)).findById(1L);
        verify(matchOddsRepository, times(1)).save(existingMatchOdds);
    }

    @Test
    void whenUpdateMatchOddsNotFound_thenThrowException() {
        // Arrange
        Match match = new Match(1L, "OSFP-PAO", LocalDate.of(2021, 3, 31), LocalTime.of(12, 0), "OSFP", "PAO", SportEnum.FOOTBALL);
        MatchOdds updatedMatchOdds = new MatchOdds(1L, match, "1X", 2.0);
        MatchOddsEntity matchOddsEntity = new MatchOddsEntity(1L, "X", 1.5);

        when(matchOddsRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> matchOddsService.update(1L, matchOddsEntity))
                .isInstanceOf(MatchNotFoundException.class)
                .hasMessageContaining("MatchOdds with id 1 not found");
        verify(matchOddsRepository, times(1)).findById(1L);
        verify(matchOddsRepository, never()).save(any());
    }

    @Test
    void whenDeleteMatchOdds_thenSuccess() {
        // Arrange
        when(matchOddsRepository.existsById(1L)).thenReturn(true);

        // Act
        matchOddsService.deleteById(1L);

        // Assert
        verify(matchOddsRepository, times(1)).deleteById(1L);
    }

    @Test
    void whenDeleteMatchOddsNotFound_thenThrowException() {
        // Arrange
        when(matchOddsRepository.existsById(1L)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> matchOddsService.deleteById(1L))
                .isInstanceOf(MatchNotFoundException.class)
                .hasMessageContaining("MatchOdds with id 1 does not exist");
        verify(matchOddsRepository, never()).deleteById(1L);
    }
}
