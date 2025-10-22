# Library Management Monorepo

Dieses Repository bündelt Backend (Spring Boot) und Frontend (Angular) der Bibliotheksverwaltung gemäß der Prüfungsaufgabe. Die vorhandenen Übungsprojekte wurden aufgeräumt und in eine gemeinsame Struktur überführt.

## Struktur

- `backend/` – Spring Boot Grundgerüst mit H2-Datenbankkonfiguration
- `frontend/` – Angular-Workspace als Ausgangspunkt für die UI
- `docs/` – Aufgabenstellung und zukünftig DV-Konzept sowie weitere Dokumentation
- `scripts/` – Platzhalter für Start- und Prüfscripte
- `.github/workflows/` – Platzhalter für CI-Konfigurationen

## Backend Highlights

- Domänenmodell für Publikationen, Ausleihen, Ausleiher, Schlagwörter und Publikationstypen basierend auf den JPA-Mappings der früheren Room-Management-Projekte.
- Service-Layer nach dem bekannten `RoomService`-Muster (inkl. `ServiceException`) zur Kapselung der Geschäftsregeln.
- REST-Controller unter `/api/publications`, `/api/loans` und `/api/masters` inklusive Fehlerbehandlung.
- Initialer Demodatensatz in `data.sql` (H2 In-Memory).

## Frontend Highlights

- Angular-Komponentenstruktur (Container → Liste → Formular) aus dem Repetitorium übernommen und auf Publikationen, Ausleihen und Stammdaten angepasst.
- Services für Publikationen, Ausleihen und Stammdaten mit HTTP-Anbindung an das Spring-Boot-Backend.
- Routing und Layout zur Navigation zwischen den Kernbereichen.

Weitere Funktionalitäten werden in nachfolgenden Schritten implementiert.
