package com.karan.prometheus_grafana.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateBookRequestDTO {
        private String name;
        private String author;
        private String publisher;
        private String description;
    }
