package com.turinghealth.turing.health.utils.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class PaginationResponse<T> {
    private List<T> content;
    private Long totalElements;
    private Integer size;
    private Integer totalPage;
    private Integer page;

    public PaginationResponse(Page<T> page) {
        this.content = page.getContent();
        this.totalElements = page.getTotalElements();
        this.size = page.getSize();
        this.totalPage = page.getTotalPages();
        this.page = page.getNumber();
    }
}
