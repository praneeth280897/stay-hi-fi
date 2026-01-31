package com.example.stay_hi_fi.response;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class Meta {

    private int page;
    private long totalRecords;
    private long totalPages;
    private long pageSize;

    public Meta(Page<?> page) {
        this.page = page.getNumber();
        this.totalRecords = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.pageSize = page.getSize();

    }
}
