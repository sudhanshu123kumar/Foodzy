package com.EcommerceWeb.Foodzy.Payload;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T> {

    private List<T> productData;

    private Integer pageNumber;

    private Integer pageSize;

    private Long totalElements;

    private Integer totalPages;

    private Boolean lastPage;

}
