package com.yan.campusbbs.module.campusbbs.study;


import com.yan.campusbbs.repository.entity.DataMultiItem;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;

@Module
public class StudyFragmentModule {
    private List<DataMultiItem> multiItems;

    public StudyFragmentModule() {
        multiItems = new ArrayList<>();
    }

}
