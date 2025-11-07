-- (Optioneel) Tabel aanmaken

CREATE TABLE IF NOT EXISTS passwords (
  id            BIGSERIAL PRIMARY KEY,
  password_hash TEXT       NOT NULL,
  created_at    TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

