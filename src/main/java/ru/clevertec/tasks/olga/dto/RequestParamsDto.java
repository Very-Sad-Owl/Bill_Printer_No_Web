package ru.clevertec.tasks.olga.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class RequestParamsDto {
    public String category;
    public String action;
    @SerializedName("limit")
    public int nodesPerPage = 0;
    @SerializedName("page")
    public int offset = 0;
}
