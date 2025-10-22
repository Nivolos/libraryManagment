# AGENTS.md — Restructure & Bootstrap für **libraryManagment** (Angular + Spring Boot + Hibernate)

**Repo:** [github.com/Nivolos/libraryManagment](https://github.com/Nivolos/libraryManagment)  
**Arbeitsbranch:** `Nivolos-gulpV1` (alle PRs gegen diesen Branch erstellen)
>*Branch:* [github.com/Nivolos/libraryManagment/Nivolos-gulpV1](https://github.com/Nivolos/libraryManagment/Nivolos-gulpV1)

## 📄 Referenz

Die vollständige Prüfungsaufgabe befindet sich unter:  
[`docs/Pruefungsaufgabe.md`](./docs/Pruefungsaufgabe.md)

Diese Datei beschreibt die offiziellen Anforderungen,  
Use-Cases, Musskriterien und Abgabebedingungen der Hausarbeit.  
Der Agent soll sich strikt an diesen Inhalt halten.

## 🎯 Ziel

Aus den hochgeladenen **Roommanagement**-Projekten ein konsistentes Full-Stack-System für die **Bibliotheksverwaltung** erstellen — mit **minimalen neuen Abhängigkeiten** und **maximaler Wiederverwendung** bestehender Strukturen.

- **Backend:** Spring Boot + JPA/Hibernate, REST unter `/api/**`
- **Frontend:** Angular CLI (bestehende Room-Pattern: Container → List → Form)
- **Domäne:** Publications, Loans, Borrowers, Keywords, PublicationTypes

## ⚙️ Minimalprinzip (sehr wichtig)

- **So wenig Neues wie möglich.**
  - Keine zusätzlichen Frameworks/Libs außer zwingend notwendigen:
    - `spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `h2`, optional `lombok`
  - Frontend nur mit Angular-Basics: `HttpClient`, `Router`, `Forms`
  - Keine neuen UI-Frameworks (z. B. Material, PrimeNG)
- **Nur so viel Code wie nötig.**
  - Bestehende Komponenten/Services **umbenennen & anpassen** statt neu schreiben
  - Doppelte Funktionalität **vereinigen**, keine Parallelstrukturen
- **Kein Struts2**, nur Spring Boot REST
- **Iteratives Vorgehen in kleinen PRs**, jeder PR build-bar

## 📁 Endlayout (nach Restrukturierung)

- `/backend` # Spring Boot App (REST + JPA)
- `/frontend` # Angular CLI App
- `/docs` # DV-Konzept, Install, UML/Testfälle
- `/scripts` # Dev/Verify Helper
- `/.github/workflows` # CI (Maven + Angular Build)


## 📦 Quellen im Repo

- `angular-roommgmt-master/`
- `roommanagement-main/`  
  - inkl. `backend/`, `angular/src/main/client/`
- `roommanagement-orm-main/`
- `angular-repetitorium-main/`
- `hibernate-examples-master/`
- `serialisation-master/` (nicht benötigt)
- diverse `README-*.md`

> ZIP-Dateien sind bereits entpackt; der Agent soll nur mit den vorhandenen Ordnern arbeiten.


## 🧭 Regeln & Leitplanken

1. **Bereinigen & Ignorieren**
   - Entferne: `*.zip`, `/target`, `/dist`, `.idea`, `.classpath`, `.project`, `*.iml`, `.DS_Store`
   - `.gitignore` anlegen/ergänzen (Java/Node/IDE)

2. **Mono-Layout erzeugen**
   - `/backend`, `/frontend`, `/docs`, `/scripts`, `/.github/workflows` erstellen

3. **Namens-/Paketkonvention**
   - Java-Package: `de.nordakademie.iaa.library`
   - REST-Pfade: `/api/publications`, `/api/loans`, `/api/borrowers`, `/api/keywords`, `/api/pubtypes`

4. **Transaktionen & Schichten**
   - Persistenz nur in Repos
   - Business-Regeln in Services (`@Transactional`)
   - Controller schlank (nur Mapping/DTO)

5. **Keine neuen großen Libs**
   - Backend: nur Boot Web, Data JPA, H2, optional Lombok  
   - Frontend: Angular-Basisbibliotheken, keine UI-Frameworks

6. **Tests/Builds müssen grün sein**  
   - Maven + Angular Build in CI

7. **Kleine, saubere PRs**
   - Jeder PR → klarer Teilabschnitt, vollständig funktionsfähig


## 🧱 Konkrete Aufgaben

### A) Cleanup (PR1)
- Entferne überflüssige Ordner/Artefakte
- Lege Zielstruktur an
- Keine inhaltlichen Codeänderungen außer Moves

### B) Backend migrieren (PR2)
- Basis aus `roommanagement-main/backend/**` → `/backend/**`
- Passe `pom.xml` an:
  - Parent: Spring Boot
  - Dependencies: `spring-boot-starter-web`, `spring-boot-starter-data-jpa`, `h2`
- `application.properties` minimal:

server.port=8080
spring.datasource.url=jdbc:h2:mem:libdb;MODE=PostgreSQL;DB_CLOSE_DELAY=-1
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

- **Domänenmodell** (neu/angepasst):
    - `Publication(id, title, authors, publishedAt, publisher, type, isbn?, stock, keywords*)`
    - `Loan(id, publication, borrower, issuedAt, dueAt, returnedAt?)`
    - `Borrower(id, matriculation, firstName, lastName, email)`
    - `Keyword(id, name)`
    - `PublicationType(id, name)`
    - vorhandene Mappings aus `roommanagement-orm-main`/**`hibernate-examples-master`** bevorzugt übernehmen
- **Repositories:** `JpaRepository`-Interfaces
- **Service (minimal):**
    - `borrow(publicationId, borrowerId)` prüft Bestand
    - `return(loanId)` markiert Rückgabe
- **Controller:**
    - `PublicationController`: CRUD + Suche
    - `LoanController`: Borrow/Return/Listen
    - `MasterDataController`: CRUD für Stammdaten
- **Initialdaten:** `data.sql` mit wenigen Demo-Objekten

### C) Frontend migrieren (PR3)
- Basis: `angular-roommgmt-master/` → `/frontend/`
- Projektname in `package.json`: `"library-mgmt"`
- `proxy.conf.json`: 
`{ "/api": { "target": "http://localhost:8080", "secure": false, "changeOrigin": true } }`

- **Komponentenstruktur:**
	- /publications: Container + List/Form
	- /loans: Container + List/Aktionen
	- /masters: Borrower/Keyword/Pubtype CRUD
- **Services:**
	- PublicationService
	- LoanService
	- MasterDataService
- Forms: einfache Validierungen (Pflichtfelder)
- Tabellen: zunächst clientseitig sortierbar

### D)Dunning/Mahnwesen (PR4, optional)
- Service-Methoden für Mahnstufen (level1/2/3)
- Bei Level 3: publication.stock--, Status „LOST“
- UI: einfache Buttons, keine neuen Libs

### E) Docs (PR5)
- `/docs/README.md`: Install in < 5 Minuten
- `/docs/DV-Konzept.md`: UML, Dialogmodell, Testfälle
- Inhalte aus vorhandenen READMEs konsolidieren

### F) Scripts (PR6)
- `/scripts/dev.sh`: Backend + Frontend starten
- `/scripts/verify.sh`: Build-Smoke-Test
----
## CI (ein PR, wenn gewünscht)
- `/.github/workflows/ci.yml` — nur Maven-Build für `/backend` und Angular-Build für `/frontend`. Keine zusätzlichen Aktionen.

## 🧩 PR-Plan
1. **PR1** – Cleanup & Zielstruktur  
2. **PR2** – Backend CRUD Publications  
3. **PR3** – Frontend Publications  
4. **PR4** – Loans + Borrow/Return  
5. **PR5** – Stammdaten (Borrowers/Keywords/Pubtypes)  
6. **PR6** – Dunning (optional)  
7. **PR7** – Docs & Scripts

> Jeder PR muss eigenständig builden, grün und minimal sein.

## ✅ Done-Kriterien

- **Backend:**  
  `mvn -q -DskipTests package` läuft in `/backend`  
  `GET /api/publications` → 200/JSON

- **Frontend:**  
  `npm ci && npm run build` läuft in `/frontend`  
  `ng serve` + Proxy → CRUD funktional

- **Keine neuen Drittlibs** (außer minimalen Deps)
- **Installationszeit:** < 5 Minuten

## 🚫 No-Go

- Struts2-Artefakte oder -Config übernehmen  
- UI-Frameworks wie Material/Prime/Bootstrap hinzufügen  
- Doppelte Services/DTOs behalten  
- Große Refactorings ohne PR-Schritte  
- Neue externe APIs oder Build-Tools (z. B. Gulp, Vite, Webpack-Modifikationen)

## 🤖 Hinweise für den Agent

- **Umbenennen & anpassen** statt neu schreiben  
- Wenn doppelte Implementierungen existieren → **vereinheitlichen**
- Bei Unsicherheit → kleinste Änderung, keine neuen Abhängigkeiten  
- Fokus: Minimal funktionsfähig, stabil, testbar

## 📎 Snippets

# Build & Output
`target/`
`dist/`
`node_modules/`

# IDE & System
`.idea/`
`*.iml`
`.classpath`
`.project`
`.DS_Store`

# Archives
`*.zip`

### `scripts/dev.sh`

#!/usr/bin/env bash
set -euo pipefail

# Backend bauen & starten
(  
  `cd backend`  
  `mvn -q -DskipTests package`  
  `nohup mvn spring-boot:run` > ..`/.dev-backend.log 2>&1 &`  
  `echo $!` > ..`/.dev-backend.pid`  
)

# Frontend starten (mit Proxy auf :8080)
(  
    `cd frontend`  
    `npm ci`  
    `npm start`  
)

`{ "/api": { "target": "http://localhost:8080", "secure": false, "changeOrigin": true } }`