package com.example.stay_hi_fi.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponseDTO<T> {

    private List<T> data;
    private Meta meta;

    public PaginationResponseDTO(Page<T> page) {
        this.data = page.getContent();
        this.meta = new Meta(page);
    }

    public List<T> getData() {
        return data;
    }

    public Meta getMeta() {
        return meta;
    }
}
