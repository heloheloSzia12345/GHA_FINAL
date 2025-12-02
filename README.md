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

- Autó Mapper: **CarMapper.java**
- Ügyfél Mapper: **CustomerMapper.java**
- Bérlés Mapper: **RentalMapper.java**

### RequestDTO

Erre csak azért van szükség, mivel a bérlő és a visszaadó függvények bemenetei több Entity tulajdonságát is tartalmazzák, így ezeket összevonjuk ezekbe az osztályokba, hogy egyszerűsüdjön a dolog.

- Bérlés Request DTO: **RentalRequest.java**
- Visszaadás Drop Off DTO: **DropOffRequest.java**

### Repository

A Repository-k az adatbázis elérésének felületét biztosítják, ez köti össze az Entity-vel. Spring Data JPA-val dolgozom, amely automatikus megvalósítást nyújt: örökli a JpaRepository-t, ezzel a CRUD függvényeket örökli és a
@Repository annotációval jelzem a Springnek, hogy ez egy Repository interface és a Spring automatikusan implementálja a CRUD függvényeket és az egyéb metódusokat a metódusnév alapján,
pl.: Car findByLicensePlate(String licensePlate) , ami megegyezik SELECT * FROM Car WHERE LICENSEPLATE=licensePlate lekérdezéssel (Ez a CarRepository interface-ben van).

- **CarRepository:** Autók keresése rendszém és foglaltság alapján
- **CustomerRepository:** Ügyfél keresése jogosítványszám alapján
- **RentalRepository:** Egy bérlést keressünk ügyfél alapján

### Service

A Service rétegben van az üzleti logikának a helye. A Repository réteget (adatbázis) és a Controller réteget (kliens kérések) köti össze. Ez a **CarRentalSystemService.java**-ban van implementálva. 

- **Spring elemek:** a @Service annotációval jelölöm a service osztályt a Springnek, @Autowired annotációt a Dependency Injection miatt használjuk vagyis nem nekem kell létrehoznom a Repository példányokat, hanem a Spring fogja ezt megcsinálni, a @Transactional annotáció pedig biztosítja az atomi végrehajtását a függvényeknek
- **Metódusok:** három metódusunk van, az egyik listázza az összes bérelhető autót, a második a bérlést hajtja végre, a harmadik a visszaadást hajtja végre, az kivétel kezelést megvalósítottam mindegyik függvénynél

### Controller

A Controller osztály feladata, hogy a klienstől érkező kéréseket fogadja és azt a Service-nek továbbítsa (REST API). A három metódus megvalósítása: 

| HTTP Method | Endpoint | Leírás |
|--------|----------|--------|
| GET | `/api/car/rentable` | Szabad autók listázása |
| POST | `/api/rental/renting` | Bérlés |
| POST | `/api/rental/drop` | Visszaadás |

- **Spring elemek:**
