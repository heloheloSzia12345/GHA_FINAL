package org.example.kliens.restclientapi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.kliens.dto.CarDTO;
import org.example.kliens.dto.RentalDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestClientApi {
    private final String BASE_URL = "http://localhost:8080/api"; // A Backend API alap URL-je
    private final HttpClient httpClient; // A HTTP kérések küldését segíti
    private final ObjectMapper objectMapper; // Szerializáció, deszerializáció

    public RestClientApi(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        // Regisztrálja a JavaTimeModule-t, ez a dátumok JSON-ben levő kezeléséhez szükséges
        this.objectMapper.registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());
    }

    public List<CarDTO> getAllCars() throws Exception {
        // GET kérés létrehozása
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/car/rentable")).header("Content-Type", "application/json").GET().build();
        // String típusú válasz
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        // Deszerializáció
        return objectMapper.readValue(response.body(), new TypeReference<>() {
        }); // .body()->JSON String
    }

    public RentalDTO renting(String licensePlate, String licenseNum, String name, LocalDate dateOfBirth, LocalDate pickUpDate, LocalDate deadline) throws Exception {
        Map<String, Object> jsonMap = new HashMap<>(); // Létrehozok egy Map-ot a JSON objektumhoz
        jsonMap.put("licensePlate", licensePlate);
        jsonMap.put("licenseNum", licenseNum);
        jsonMap.put("name", name);
        jsonMap.put("dateOfBirth", dateOfBirth);
        jsonMap.put("pickUpDate", pickUpDate);
        jsonMap.put("deadline", deadline);
        String json = objectMapper.writeValueAsString(jsonMap); // JSON String létrehozása, szerializáció
        // Post belsejébe a JSON Stirng
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/rental/renting")).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(json)).build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), RentalDTO.class);
    }

    // Ugyanaz mint az előző függvénynél
    public RentalDTO dropOffCar(String licenseNum, String name, LocalDate dropOffDate) throws Exception {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("licenseNum", licenseNum);
        jsonMap.put("name", name);
        jsonMap.put("dropOffDate", dropOffDate);
        String json = objectMapper.writeValueAsString(jsonMap);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/rental/drop")).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(json)).build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return objectMapper.readValue(response.body(), RentalDTO.class);
    }
}
