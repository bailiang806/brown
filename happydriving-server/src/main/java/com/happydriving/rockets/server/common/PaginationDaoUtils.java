package com.happydriving.rockets.server.common;

import com.github.pagehelper.PageHelper;
import com.happydriving.rockets.server.model.PaginationInfo;

/**
 * @author mazhiqiang
 */
public class PaginationDaoUtils {

    public static <T> T executePaginationQuery(PaginationInfo paginationInfo, IPaginationMethod<T> IPaginationMethod) {
        PageHelper.startPage(paginationInfo.getPageNum(), paginationInfo.getPageSize(), true);
        return IPaginationMethod.wrapperPagination();
    }


    public static interface IPaginationMethod<T> {

        T wrapperPagination();

    }

}
