# 🎰 Simple Gacha Simulator API

A RESTful API for simulating a gacha (loot box) system. Built with role-based access, prize/rarity management, inventory tracking, and JWT-authenticated endpoints.

## 🌐 Base URL

```
http://localhost:8080/api/v1
```

> Replace `localhost:8080` with your deployed host if applicable.

---

## 📦 Features

- ✅ User registration, login, and JWT auth
- 🎁 Gacha pull simulation (1 or 10 pulls)
- 🏆 Prize & tier CRUD (admin only)
- 🎒 User inventory system
- 🔐 Role-based access (USER & ADMIN)

---

## 🔐 Authentication

- Uses **JWT (Bearer token)** in the `Authorization` header
- Acquire tokens via `POST /auth/login` or `POST /auth/register`

Example:
```http
Authorization: Bearer <accessToken>
```

---

## 🚀 API Endpoints

### 🧑 Auth
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/register` | Register new user |
| POST | `/auth/login` | Login and get tokens |
| POST | `/refresh-token/refresh` | Refresh JWT using refresh token |

---

### 👤 Profile
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/profile` | Get current user profile |
| GET | `/profile/inventory` | View own gacha inventory |

---

### 🎰 Gacha
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/gacha` | Roll once |
| POST | `/gacha/10` | Roll 10 times |

---

### 🧍 Users (Admin only)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/users` | Get all users |
| GET | `/users/{id}` | Get user by ID |
| PUT | `/users/{id}` | Update user |
| DELETE | `/users/{id}` | Delete user |

---

### 🧱 Tiers (Admin only)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/tiers` | List all tiers |
| GET | `/tiers/{id}` | Get tier by ID |
| POST | `/tiers` | Create tier |
| PUT | `/tiers/{id}` | Update tier |
| DELETE | `/tiers/{id}` | Delete tier |

---

### 🏆 Prizes (Admin only)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/prizes` | List all prizes |
| GET | `/prizes/{id}` | Get prize by ID |
| POST | `/prizes` | Create prize |
| PUT | `/prizes/{id}` | Update prize |
| DELETE | `/prizes/{id}` | Delete prize |

---

### 🎒 Inventory (Admin only)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/inventories` | Get all inventories |
| GET | `/inventories/{id}` | Get inventory by ID |

---

## 📄 Sample Gacha Response

```json
{
  "id": 5,
  "name": "Legendary Staff",
  "description": "A staff with immense magical power.",
  "imageUrl": "https://...",
  "tierName": "Legendary",
  "dropRate": 0.1
}
```

For 10-pull:
```json
{
  "content": [ { /* item */ }, { /* item */ }, ... ],
  "page": {
    "size": 20,
    "number": 0,
    "totalElements": 10,
    "totalPages": 1
  }
}
```

---

## 📥 Environment Variables

Use these variables in Postman or `.env`:

| Key | Default |
|-----|---------|
| `url` | `http://localhost:8080` |
| `token` | _(set dynamically)_ |
| `refreshToken` | _(set dynamically)_ |

---

## 🧪 Postman Collection

[📬 View API Docs on Postman](https://documenter.getpostman.com/view/20277894/2sA3s7iofW)

Or import manually via `Gacha Simulator.postman_collection.json`.

---

## 🛡️ Notes

- Admins are required to manage users, prizes, and tiers.
- Inventory is auto-filled by gacha rolls.
- All endpoints return structured JSON responses.

---

## 🧑 Author

**Zul Fahri Baihaqi**  
GitHub: [@Zurihaqi](https://github.com/Zurihaqi)

---
