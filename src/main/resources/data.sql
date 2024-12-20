INSERT INTO match (id, description, match_date, match_time, team_a, team_b, sport)
VALUES
    (1, 'OSFP-PAO', '2021-03-31', '12:00:00', 'OSFP', 'PAO', 1),
    (2, 'Lakers-Warriors', '2024-05-12', '18:30:00', 'Lakers', 'Warriors', 2),
    (3, 'Barcelona-Real Madrid', '2024-12-15', '21:00:00', 'Barcelona', 'Real Madrid', 1);

-- Insert initial data into the MatchOdds table
INSERT INTO match_odds (id, match_id, specifier, odd)
VALUES
    (1, 1, '1', 1.5),
    (2, 1, 'X', 3.2),
    (3, 1, '2', 4.5),
    (4, 2, '1', 1.8),
    (5, 2, 'X', 2.9),
    (6, 2, '2', 2.2),
    (7, 3, '1', 2.0),
    (8, 3, 'X', 3.0),
    (9, 3, '2', 3.5);