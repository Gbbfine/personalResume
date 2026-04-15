# API Document

Base URL: `http://localhost:8080`

## Unified Response

```json
{
  "code": 0,
  "message": "success",
  "data": {}
}
```

## Auth

### POST /api/auth/login

Request:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

Response data:

```json
{
  "token": "<jwt-token>"
}
```

## Public APIs

- `GET /api/public/profile`
- `GET /api/public/skills`
- `GET /api/public/projects`
- `GET /api/public/work-experiences`
- `GET /api/public/educations`
- `GET /api/public/honors`
- `GET /api/public/contacts`

Rules:
- Public APIs always use the active profile (`profile.is_active = 1`).
- If no active profile exists, backend falls back to latest updated profile.
- Child data is filtered by `profile_id` + `is_visible = 1` and sorted by `sort_order ASC, updated_at DESC`.

## Admin APIs

Requires `Authorization: Bearer <token>`.

- Profile:
  - `GET /api/admin/profiles`
  - `GET /api/admin/profiles/{id}`
  - `POST /api/admin/profiles`
  - `PUT /api/admin/profiles/{id}`
  - `PUT /api/admin/profiles/{id}/activate`
  - `DELETE /api/admin/profiles/{id}`

- Skills: `GET/POST/PUT/DELETE /api/admin/skills`
- Projects: `GET/POST/PUT/DELETE /api/admin/projects`
- Work: `GET/POST/PUT/DELETE /api/admin/work-experiences`
- Education: `GET/POST/PUT/DELETE /api/admin/educations`
- Honors: `GET/POST/PUT/DELETE /api/admin/honors`
- Contact: `GET/POST/PUT/DELETE /api/admin/contacts`

Notes:
- Child modules support optional `profileId` in request body.
- If `profileId` is omitted, backend auto binds to active profile.
- Portfolio module has been removed.