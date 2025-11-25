package org.example.backend.requestdto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DropOffRequest {
    private String licenseNum;
    private String name;
    private LocalDate dropOffDate;
}
