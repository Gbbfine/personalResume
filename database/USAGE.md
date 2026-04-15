# Database Usage

## 1) Initialize Database

```bash
mysql -u root -p < database/schema.sql
mysql -u root -p < database/seed.sql
```

## 2) Verify Tables

```sql
USE personal_resume;
SHOW TABLES;
```

Expected tables:
- admin_user
- profile
- skill
- project_experience
- work_experience
- education_experience
- honor
- contact

## 3) Core Query Pattern

All child tables are linked to one profile by `profile_id`.

```sql
SELECT *
FROM skill
WHERE profile_id = 1
  AND is_visible = 1
ORDER BY sort_order ASC, updated_at DESC;
```

The same pattern applies to:
- `project_experience`
- `work_experience`
- `education_experience`
- `honor`
- `contact`

## 4) Common Operations

### Get current public profile

```sql
SELECT *
FROM profile
WHERE is_active = 1
ORDER BY updated_at DESC
LIMIT 1;
```

### Switch active profile

```sql
UPDATE profile SET is_active = 0;
UPDATE profile SET is_active = 1 WHERE id = 1;
```

### Insert one skill

```sql
INSERT INTO skill(profile_id, name, sort_order, is_visible)
VALUES (1, 'Redis', 10, 1);
```

### Insert one honor

```sql
INSERT INTO honor(profile_id, title, issuer, award_date, description, sort_order, is_visible)
VALUES (1, 'Scholarship', 'University', '2024-11-01', 'Award description', 1, 1);
```