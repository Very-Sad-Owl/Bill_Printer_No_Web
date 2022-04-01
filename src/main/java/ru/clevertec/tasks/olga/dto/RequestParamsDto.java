package ru.clevertec.tasks.olga.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class RequestParamsDto {
    public String category;
    public String action;
    @SerializedName("page")
    public int nodesPerPage = 0;
    public int offset = 0;
}
