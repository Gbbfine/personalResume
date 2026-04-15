USE personal_resume;

ALTER TABLE profile
  ADD COLUMN IF NOT EXISTS is_active TINYINT(1) NOT NULL DEFAULT 0;

-- Optional: keep one active profile for frontend display
UPDATE profile
SET is_active = 0;

UPDATE profile p
JOIN (
  SELECT id
  FROM profile
  ORDER BY updated_at DESC, id DESC
  LIMIT 1
) latest ON latest.id = p.id
SET p.is_active = 1;