package com.warehouse.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.springframework.util.CollectionUtils;

import com.github.pagehelper.Page;
import com.warehouse.data.PageResult;

public class Converters {

    /**
     * 转listConverter
     * 
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Function<List<T>, List<R>> listConverter(Function<T, R> function) {
        return inList -> {
            if (CollectionUtils.isEmpty(inList)) {
                return Collections.emptyList();
            }
            List<R> outList = new ArrayList<>(inList.size());
            for (T in : inList) {
                outList.add(function.apply(in));
            }
            return outList;
        };
    }

    /**
     * 转pageConverter
     *
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Function<Page<T>, PageResult<R>> pageConverter(Integer pageNum, Integer pageSize,
        Function<T, R> function) {
        return inPage -> {
            if (inPage == null) {
                return new PageResult<>(0, 0, pageNum, pageSize, Collections.emptyList());
            }
            return new PageResult<>(inPage.getTotal(), inPage.getPages(), pageNum, pageSize,
                listConverter(function).apply(inPage.getResult()));
        };
    }

    /**
     * 转pageConverter
     *
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Function<PageResult<T>, PageResult<R>> pageConverter(Function<T, R> function) {
        return inPage -> new PageResult<>(inPage.getTotalCount(), inPage.getTotalPage(), inPage.getPageNum(),
            inPage.getPageSize(), listConverter(function).apply(inPage.getList()));
    }

}
