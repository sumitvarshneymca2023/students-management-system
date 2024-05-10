package com.students.management.system.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PagePair {
    public static Integer PAGE_SIZE = 10;

    public static Integer getPage(Integer pageNo) {
        if (pageNo == 0) {
            return pageNo;
        }
        return pageNo - 1;
    }

    public static Pageable getPageable(Integer pageNo) {
        if (pageNo == -1) {
            return PageRequest.of(0, Integer.MAX_VALUE);
        }
        return PageRequest.of(getPage(pageNo), PAGE_SIZE);
    }

    private PagePair(){

    }
}
