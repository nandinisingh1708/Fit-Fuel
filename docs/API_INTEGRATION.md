# FitFuel API Integration Guide

## Overview
This guide explains how to integrate the FitFuel frontend with the backend API. All endpoints are RESTful and require JWT authentication (except login/register).

## Base URL
```
http://localhost:8080/api
```

## Authentication

### Login
**Endpoint:** `POST /auth/login`

**Request:**
```json
{
  "username": "user@example.com",
  "password": "YourPassword123"
}
```

**Response (200 OK):**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "userId": 1,
  "username": "user@example.com"
}
```

**JavaScript Example:**
```javascript
async function login(username, password) {
  const response = await api.post('/auth/login', { username, password });
  api.setToken(response.accessToken);
  StorageManager.saveUser(response);
  return response;
}
```

### Register
**Endpoint:** `POST /auth/register`

**Request:**
```json
{
  "username": "newuser@example.com",
  "password": "YourPassword123",
  "email": "newuser@example.com"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "username": "newuser@example.com",
  "email": "newuser@example.com",
  "createdAt": "2024-11-30T10:00:00Z"
}
```

---

## Meals Management

### Log a Meal
**Endpoint:** `POST /meals`

**Request:**
```json
{
  "name": "Grilled Chicken Salad",
  "calories": 350,
  "protein": 45,
  "carbs": 15,
  "fat": 12,
  "mealType": "LUNCH",
  "date": "2024-11-30",
  "time": "12:30"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "userId": 1,
  "name": "Grilled Chicken Salad",
  "calories": 350,
  "protein": 45,
  "carbs": 15,
  "fat": 12,
  "mealType": "LUNCH",
  "date": "2024-11-30",
  "time": "12:30:00",
  "createdAt": "2024-11-30T10:00:00Z"
}
```

**JavaScript Example:**
```javascript
async function logMeal(mealData) {
  const meal = await api.post('/meals', {
    name: mealData.name,
    calories: mealData.calories,
    protein: mealData.protein,
    carbs: mealData.carbs,
    fat: mealData.fat,
    mealType: mealData.mealType,
    date: new Date().toISOString().split('T')[0],
    time: new Date().toLocaleTimeString('en-US', { hour12: false, hour: '2-digit', minute: '2-digit' })
  });
  
  notify.success('Meal logged successfully!');
  StorageManager.addMeal(meal);
  return meal;
}
```

### Get All Meals
**Endpoint:** `GET /meals`

**Query Parameters:**
- `date` (optional): Filter by date (YYYY-MM-DD)
- `mealType` (optional): BREAKFAST, LUNCH, DINNER, SNACK
- `page` (optional): Pagination page number (default: 0)
- `size` (optional): Page size (default: 20)

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "userId": 1,
      "name": "Grilled Chicken Salad",
      "calories": 350,
      "protein": 45,
      "carbs": 15,
      "fat": 12,
      "mealType": "LUNCH",
      "date": "2024-11-30",
      "time": "12:30:00",
      "createdAt": "2024-11-30T10:00:00Z"
    }
  ],
  "totalElements": 45,
  "totalPages": 3,
  "currentPage": 0
}
```

**JavaScript Example:**
```javascript
async function getMeals(date = null) {
  let endpoint = '/meals';
  if (date) {
    endpoint += `?date=${date}`;
  }
  const response = await api.get(endpoint);
  return response.content || [];
}
```

### Update Meal
**Endpoint:** `PUT /meals/{id}`

**Request:**
```json
{
  "name": "Grilled Chicken Salad",
  "calories": 360,
  "protein": 46,
  "carbs": 16,
  "fat": 13
}
```

**Response (200 OK):**
```json
{
  "id": 1,
  "userId": 1,
  "name": "Grilled Chicken Salad",
  "calories": 360,
  "protein": 46,
  "carbs": 16,
  "fat": 13,
  "mealType": "LUNCH",
  "date": "2024-11-30",
  "time": "12:30:00",
  "createdAt": "2024-11-30T10:00:00Z"
}
```

**JavaScript Example:**
```javascript
async function updateMeal(id, updates) {
  const updated = await api.put(`/meals/${id}`, updates);
  StorageManager.updateMeal(id, updates);
  notify.success('Meal updated!');
  return updated;
}
```

### Delete Meal
**Endpoint:** `DELETE /meals/{id}`

**Response (204 No Content)** or **200 OK**:
```json
{
  "message": "Meal deleted successfully"
}
```

**JavaScript Example:**
```javascript
async function deleteMeal(id) {
  await api.delete(`/meals/${id}`);
  StorageManager.deleteMeal(id);
  notify.success('Meal deleted!');
}
```

---

## Workouts Management

### Log Workout
**Endpoint:** `POST /api/workouts`

**Request:**
```json
{
  "name": "Running",
  "duration": 30,
  "caloriesBurned": 290,
  "intensity": "HIGH",
  "date": "2024-11-30",
  "time": "06:30",
  "notes": "Felt great during run"
}
```

**Response (201 Created):**
```json
{
  "id": 1,
  "userId": 1,
  "name": "Running",
  "duration": 30,
  "caloriesBurned": 290,
  "intensity": "HIGH",
  "date": "2024-11-30",
  "time": "06:30:00",
  "notes": "Felt great during run",
  "createdAt": "2024-11-30T10:00:00Z"
}
```

**JavaScript Example:**
```javascript
async function logWorkout(workoutData) {
  const workout = await api.post('/api/workouts', {
    name: workoutData.activity,
    duration: workoutData.duration,
    caloriesBurned: workoutData.calories,
    intensity: workoutData.intensity,
    date: workoutData.date,
    time: workoutData.time,
    notes: workoutData.notes
  });
  
  notify.success('Workout logged!');
  return workout;
}
```

### Get Workouts
**Endpoint:** `GET /api/workouts`

**Response (200 OK):**
```json
{
  "content": [
    {
      "id": 1,
      "name": "Running",
      "duration": 30,
      "caloriesBurned": 290,
      "date": "2024-11-30"
    }
  ]
}
```

### Get Daily Summary
**Endpoint:** `GET /api/workouts/daily-summary?date=2024-11-30`

**Response (200 OK):**
```json
{
  "date": "2024-11-30",
  "totalCalories": 520,
  "totalDuration": 60,
  "workoutCount": 2,
  "averageIntensity": "HIGH"
}
```

### Get Weekly Stats
**Endpoint:** `GET /api/workouts/weekly-stats`

**Response (200 OK):**
```json
{
  "weekStartDate": "2024-11-24",
  "weekEndDate": "2024-11-30",
  "totalCalories": 2100,
  "totalDuration": 210,
  "workoutCount": 7,
  "dailyBreakdown": [
    {
      "date": "2024-11-24",
      "calories": 290,
      "duration": 30,
      "activity": "Running"
    }
  ],
  "mostActiveDay": "2024-11-30",
  "averageCaloriesPerDay": 300
}
```

**JavaScript Example:**
```javascript
async function getWeeklyStats() {
  const stats = await api.get('/api/workouts/weekly-stats');
  document.getElementById('weeklyAvg').textContent = 
    `${Math.round(stats.totalCalories / 7)} cal`;
  return stats;
}
```

---

## Statistics & Analytics

### Personal Stats
**Endpoint:** `GET /api/stats/personal`

**Response (200 OK):**
```json
{
  "totalMeals": 45,
  "totalCalories": 95200,
  "averageCaloriesPerDay": 2340,
  "averageProtein": 120,
  "averageCarbs": 250,
  "averageFat": 80,
  "currentStreak": 15,
  "longestStreak": 30,
  "goalsAchieved": 12,
  "totalDaysActive": 20
}
```

### Monthly Stats
**Endpoint:** `GET /api/stats/monthly?month=11&year=2024`

**Response (200 OK):**
```json
{
  "month": 11,
  "year": 2024,
  "dailyStats": [
    {
      "date": "2024-11-01",
      "totalCalories": 2350,
      "mealCount": 4,
      "averageCaloriesPerMeal": 588
    }
  ],
  "monthlyTotal": 70500,
  "monthlyAverage": 2350,
  "highestDay": 2800,
  "lowestDay": 1900,
  "daysActive": 20
}
```

### Weekly Comparison
**Endpoint:** `GET /api/stats/weekly-comparison`

**Response (200 OK):**
```json
{
  "currentWeek": {
    "total": 16450,
    "average": 2350
  },
  "previousWeek": {
    "total": 15820,
    "average": 2260
  },
  "percentageChange": 4.0,
  "trend": "UP"
}
```

### Goal Progress
**Endpoint:** `GET /api/stats/goal-progress`

**Response (200 OK):**
```json
{
  "dailyGoal": 2450,
  "currentDayConsumed": 1850,
  "remaining": 600,
  "percentage": 75.5,
  "goalStatus": "ON_TRACK",
  "lastUpdated": "2024-11-30T20:00:00Z"
}
```

**JavaScript Example:**
```javascript
async function updateGoalProgress() {
  const progress = await api.get('/api/stats/goal-progress');
  const percentage = progress.percentage;
  document.getElementById('goalBar').style.width = percentage + '%';
  document.getElementById('goalText').textContent = `${Math.round(percentage)}% of daily goal`;
  return progress;
}
```

### Macros Breakdown
**Endpoint:** `GET /api/stats/macros`

**Response (200 OK):**
```json
{
  "protein": {
    "percentage": 30,
    "grams": 180,
    "calories": 720
  },
  "carbs": {
    "percentage": 40,
    "grams": 245,
    "calories": 980
  },
  "fat": {
    "percentage": 30,
    "grams": 82,
    "calories": 738
  },
  "totalCalories": 2438
}
```

### Calorie Balance
**Endpoint:** `GET /api/stats/calorie-balance`

**Response (200 OK):**
```json
{
  "consumed": 2100,
  "burned": 2500,
  "balance": -400,
  "status": "DEFICIT",
  "weeklyBalance": -2800
}
```

### Health Score
**Endpoint:** `GET /api/stats/health-score`

**Response (200 OK):**
```json
{
  "score": 82,
  "grade": "A",
  "breakdown": {
    "consistency": 85,
    "streak": 90,
    "goalAchievement": 75,
    "mealLogging": 80
  }
}
```

---

## Recommendations

### Get Meal Recommendations
**Endpoint:** `GET /api/recommendations/meals?goal=weight_loss&mealType=LUNCH`

**Response (200 OK):**
```json
{
  "goal": "weight_loss",
  "recommendations": [
    {
      "name": "Grilled Chicken Salad",
      "calories": 350,
      "protein": 45,
      "carbs": 15,
      "fat": 12,
      "reason": "High protein, low calorie"
    }
  ]
}
```

### Get Workout Recommendations
**Endpoint:** `GET /api/recommendations/workouts?goal=weight_loss`

**Response (200 OK):**
```json
{
  "goal": "weight_loss",
  "recommendations": [
    {
      "name": "Running",
      "description": "High-intensity cardio",
      "duration": 30,
      "estimatedCalories": 290,
      "difficulty": "HIGH"
    }
  ]
}
```

### Daily Nutrition Plan
**Endpoint:** `GET /api/recommendations/daily-plan`

**Response (200 OK):**
```json
{
  "date": "2024-11-30",
  "totalCalories": 1700,
  "meals": [
    {
      "type": "BREAKFAST",
      "calories": 350,
      "suggestions": ["Oatmeal with berries", "Scrambled eggs with toast"]
    },
    {
      "type": "LUNCH",
      "calories": 450,
      "suggestions": ["Grilled chicken salad", "Turkey sandwich"]
    },
    {
      "type": "DINNER",
      "calories": 600,
      "suggestions": ["Salmon with vegetables", "Lean beef with rice"]
    },
    {
      "type": "SNACK",
      "calories": 300,
      "suggestions": ["Greek yogurt", "Nuts and fruit"]
    }
  ]
}
```

---

## Error Handling

### Common HTTP Status Codes

| Status | Meaning | Example |
|--------|---------|---------|
| 200 | OK | Successful GET, PUT request |
| 201 | Created | Successful POST request |
| 204 | No Content | Successful DELETE request |
| 400 | Bad Request | Invalid data format |
| 401 | Unauthorized | Missing or invalid token |
| 403 | Forbidden | No permission to access |
| 404 | Not Found | Resource doesn't exist |
| 500 | Server Error | Backend error |

### Error Response Format
```json
{
  "error": "UNAUTHORIZED",
  "message": "JWT token is expired or invalid",
  "timestamp": "2024-11-30T10:00:00Z"
}
```

### JavaScript Error Handling
```javascript
async function getMeals() {
  try {
    const meals = await api.get('/meals');
    return meals;
  } catch (error) {
    if (error.message.includes('401')) {
      // Token expired - redirect to login
      window.location.href = 'index.html';
    } else {
      notify.error(`Failed to load meals: ${error.message}`);
    }
    return [];
  }
}
```

---

## Rate Limiting

The API enforces rate limiting to prevent abuse:
- **Limit:** 100 requests per minute per IP
- **Headers:** 
  - `X-RateLimit-Limit: 100`
  - `X-RateLimit-Remaining: 95`
  - `X-RateLimit-Reset: 1701348000`

When limit is exceeded, receive 429 status:
```json
{
  "error": "TOO_MANY_REQUESTS",
  "message": "Rate limit exceeded. Try again in 30 seconds",
  "retryAfter": 30
}
```

---

## Testing with cURL

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user@example.com","password":"YourPassword123"}'
```

### Log Meal
```bash
curl -X POST http://localhost:8080/api/meals \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Grilled Chicken",
    "calories": 350,
    "protein": 45,
    "carbs": 15,
    "fat": 12,
    "mealType": "LUNCH",
    "date": "2024-11-30"
  }'
```

### Get Stats
```bash
curl -X GET http://localhost:8080/api/stats/personal \
  -H "Authorization: Bearer YOUR_TOKEN"
```

---

## Frontend Integration Checklist

- [ ] Implement login form and JWT token storage
- [ ] Add meal logging form
- [ ] Create daily dashboard with stats
- [ ] Display weekly progress charts
- [ ] Add workout logging
- [ ] Show recommendations
- [ ] Implement error notifications
- [ ] Add offline support with localStorage
- [ ] Create admin analytics dashboard
- [ ] Setup automatic token refresh

---

## Support & Troubleshooting

### Common Issues

**Q: Getting 401 Unauthorized**
A: Token may be expired. Re-login and get a new token.

**Q: CORS Error**
A: Backend may not have CORS enabled for your domain. Check `WebSecurityConfig.java`.

**Q: Meals not saving**
A: Check that user is authenticated and has valid token in localStorage.

**Q: Charts not displaying**
A: Ensure Chart.js is loaded: `<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>`

---

*Last Updated: 2024-11-30*
*API Version: 1.0*
