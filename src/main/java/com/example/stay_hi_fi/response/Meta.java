package com.example.stay_hi_fi.response;

import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class Meta {

    private int page;
    private int numberOfElements;
    private long totalElements;

    public Meta(Page<?> page) {
        this.page = page.getNumber();
        this.numberOfElements = page.getNumberOfElements();
        this.totalElements = page.getTotalElements();
    }
}
