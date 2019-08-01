package com.tokigames.util.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageParams {
    private Integer page = 1;
    private Integer pageSize = 10;

    public Integer skip() {
        return (this.page - 1) * pageSize;
    }

    public Integer limit() {
        return this.pageSize;
    }
}
