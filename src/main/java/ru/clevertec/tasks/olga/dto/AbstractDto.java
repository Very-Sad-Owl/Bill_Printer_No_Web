package ru.clevertec.tasks.olga.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AbstractDto {
    public String action;
    public int nodesPerPage = 0;
    public int offset = 0;
}
