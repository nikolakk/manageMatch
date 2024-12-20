CREATE TABLE match (
                       id SERIAL PRIMARY KEY,
                       description VARCHAR(255) NOT NULL,
                       match_date DATE NOT NULL,
                       match_time TIME NOT NULL,
                       team_a VARCHAR(100) NOT NULL,
                       team_b VARCHAR(100) NOT NULL,
                       sport INTEGER NOT NULL CHECK (sport IN (1, 2)) -- 1 for Football, 2 for Basketball
);

-- Create MatchOdds table
CREATE TABLE match_odds (
                            id SERIAL PRIMARY KEY,
                            match_id INTEGER NOT NULL REFERENCES match (id) ON DELETE CASCADE,
                            specifier VARCHAR(10) NOT NULL, -- "1", "X", or "2"
                            odd DECIMAL(5, 2) NOT NULL
);