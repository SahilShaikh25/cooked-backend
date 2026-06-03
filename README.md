# 🔥 COOKED Backend

AI-powered LinkedIn profile roasting API built with Spring Boot.

The backend accepts a LinkedIn profile PDF, extracts profile information, and generates personalized roast responses using an AI model. Users can choose different roast intensities ranging from light criticism to complete annihilation.

---

## Features

* PDF upload support
* LinkedIn profile text extraction
* Multiple roast levels

  * Rare 🧊
  * Medium Rare 🔥
  * Charcoal ☠️
* AI-generated responses
* REST API architecture
* CORS support for frontend integration

---

## Tech Stack

* Java 21
* Spring Boot
* Maven
* Apache PDFBox
* REST APIs
* AI Chat Completion API

---

## Project Structure

```text
src
├── main
│   ├── java
│   │   └── com.ai.projects.cooked
│   └── resources
└── test
```

---

## API Endpoint

### Roast LinkedIn Profile

```http
POST /chat/prompt
```

### Request

Content-Type:

```text
multipart/form-data
```

Parameters:

| Name       | Type     | Required |
| ---------- | -------- | -------- |
| profile    | PDF File | Yes      |
| roastLevel | String   | Yes      |

Example roast levels:

```text
rare
medium-rare
charcoal
```

---

## Running Locally

Clone the repository:

```bash
git clone https://github.com/SahilShaikh25/cooked-backend.git
```

Navigate into the project:

```bash
cd cooked-backend
```

Build:

```bash
mvn clean package
```

Run:

```bash
mvn spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

---

## Environment Variables

Configure required secrets through environment variables.

Example:

```text
AI_API_KEY=your_api_key
```

Never commit API keys to source control.

---

## Frontend Repository

Frontend application:

https://github.com/SahilShaikh25/cooked-frontend

---

## Disclaimer

This project is intended for entertainment and educational purposes.

The generated roasts are AI-generated and should not be taken seriously.

---

## Author

Built by Sahil Shaikh
