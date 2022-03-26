package ru.clevertec.tasks.olga.model.dto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AbstractDto {
    public String action;
    public int nodesPerPage = 0;
    public int offset = 0;
}
