# Auto Kölcsönzés Rendszer - Car Rental System

## Nagyházi Áttekintés

Ez egy **JavaFX + Spring Boot** alapú autó kölcsönzés rendszer, amely lehetővé teszi az ügyfeleknek az autók bérléseit, visszaadásait egy grafikus felületen keresztül. A felhasználói felület és az üzleti logika szét van választva és a felhasználó nem lát be bele az 
üzleti logikába és más felhasználók adataiba.

**Technológiai megvalósítás:**
- **IDE:** IntelliJ IDEA
- **Backend:** Spring Boot 3.3.5 keretrendszer
- **Frontend:** JavaFX keretrendszer
- **Adatbázis:** PostgreSQL
- **Build Tool:** Gradle
- **Architecture Pattern:** 3 Layer Architecture

---

## Főbb Funkciók

### Bérlés (renting)
- Autó kiválasztása elérhető listából, vagyis a bérelhető autók közül
- Felhasználó megadja adatait
- Születési dátum ellenőrzés (18+ korhatár)
- Elviteli és határidő dátum megadása
- Dátumok helyességének ellenőrzése
- Adathiány ellenőrzése

### Visszaadás (dropOff)
- Ügyfél keresés a megadott adatok alapján
- Aktív bérlés lezárása, vagyis visszahozta az autót
- Ár kiszámítása (alapár + büntetés)
- Díjak megjelenítése a felhasználónak

---

## Adatbázis

### Séma

- **CAR**(CARID(pk),LICENSEPLATE, RENTABLE, BRAND, CARTYPE, COLOR)
- **CUSTOMER**(CUSTOMERID(pk),NAME, LICENSENUM, DATEOFBIRTH)
- **RENTAL**(RENTALID(fk),CARID(fk), CUSTOMERID, PICKUPDATE, DROPOFFDATE, DEADLINE,PREIS)

A CARID és a CUSTOMERID elsődleges kulcsok, viszont a RENTAL táblába idegen kulcsok. A CARID és a CUSTOMERID auto increment-elve van.

### Egyéb tulajdonságok

- Ha egy új ügyfél bérel autót, akkor azt elmenti az adatbázisba, a visszatérőeket nem
- Előre kiválasztott autókat lehet bérelni, amiket az adatbázisban lehet létrehozni (új autó) és törölni (kivont autó)
- Minden bérlést elmentünk az adatbázisba

### Technikai megvalósítás

A PostgreSQL adatbáziskezelőt alkalmaztam, ahol kezelni tudom az adatbázist és az adatokat az applikációtól teljesen elkülönítve. Az applikáció erre csatlakozik rá és az itt tárolt adatok segítségével dolgozik.

## Backend

### Alap információk

A backend-et Spring Boot keretrendszerben valósítottam meg, amibe különböző dependency-ket injektáltam: PostgreSQL, Lombok, Spring Data JPA. Ezt egy szerverként kell felfogni ami a klienssel REST API-n keresztül kommunikál a klienssel HTTPS kérések, JSON fájlok
segítségével. A jobb megértéshez a fájlokat, package-ket mutatom be.

### Entity

Az Entity-k a relációs adatbázis tábláinak az objektum-orientált reprezentációja (ORM). A JPA annotációk segítségével a perzisztenciát definiáltam (@Entity), és hogy melyik tulajdonság az elsődleges kulcs stb. A kapcsolatot a Rental táblában létrehoztam szintén
annotációk segítségével. A JPA persistence-t a Hibernate segítségével valósítottam meg. A settereket, gettereket, konstruktorokat a Lombok annotációk segítségével definiáltam.

- Autó entitás: **Car.java**
- Ügyfél entitás: **Customer.java**
- Bérlés entitás: **Rental.java**

### DTO, Mapper

A DTO-k az adatok átvitelére szolgálnak a kliens és a szerver között, amik minden szükséges információt tartalmaznak. Entity helyett DTO-kat küldünk, hogy ne az Entity-ket kelljen. A DTO-k az Entity-k objektumokra való leképzése ami a Mapper-ekkel történik.
A Mapper az Entity-t leképzi DTO-ra és fordítva is. 

- Autó DTO: **CarDTO.java**
- Ügyfél DTO: **Customer.java**
- Bérlés DTO: **Rental.java**

---

- Autó Mapper: **CarMapper.java**
- Ügyfél Mapper: **CustomerMapper.java**
- Bérlés Mapper: **RentalMapper.java**

### RequestDTO




| Method | Endpoint | Leírás |
|--------|----------|--------|
| GET | `/api/car/rentable` | Szabad autók listája |
| POST | `/api/rental/renting` | Új bérlés létrehozása |
| POST | `/api/rental/drop` | Bérlés lezárása |
| GET | `/api/rental/statistics` | Statisztikák |
