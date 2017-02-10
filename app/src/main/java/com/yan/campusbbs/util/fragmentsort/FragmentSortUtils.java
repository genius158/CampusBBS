package com.yan.campusbbs.util.fragmentsort;

import android.support.v4.app.Fragment;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/2/9.
 */

public class FragmentSortUtils {

    public static void fragmentsSort(List<Fragment> followViews) {
        Collections.sort(followViews, (view1, view2) -> {
            if (((FragmentSort) view1).getIndex()
                    > ((FragmentSort) view2).getIndex())
                return 1;
            else if (((FragmentSort) view1).getIndex()
                    < ((FragmentSort) view2).getIndex())
                return -1;
            else return 0;
        });
    }
}
