package com.yan.campusbbs.util.sort;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/2/9.
 */

public class SortUtils {

    public static <T> void sort(List<T> tList) {
        Collections.sort(tList, (sort1, sort2) -> {
            if (((Sort) sort1).getIndex()
                    > ((Sort) sort2).getIndex())
                return 1;
            else if (((Sort) sort1).getIndex()
                    < ((Sort) sort2).getIndex())
                return -1;
            else return 0;
        });
    }
}
