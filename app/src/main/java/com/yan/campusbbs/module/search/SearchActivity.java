package com.yan.campusbbs.module.search;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yan.campusbbs.ApplicationCampusBBS;
import com.yan.campusbbs.R;
import com.yan.campusbbs.base.BaseActivity;
import com.yan.campusbbs.config.SharedPreferenceConfig;
import com.yan.campusbbs.module.search.adapter.SearchAdapter;
import com.yan.campusbbs.module.search.data.SearchData;
import com.yan.campusbbs.module.selfcenter.data.SelfDynamic;
import com.yan.campusbbs.module.setting.ImageControl;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by yan on 2017/2/15.
 */

public class SearchActivity extends BaseActivity implements SearchContract.View {

    @Inject
    SettingHelper changeSkinHelper;
    @Inject
    SPUtils spUtils;
    @Inject
    ImageControl imageControl;
    @Inject
    SearchAdapter searchAdapter;

    @Inject
    List<DataMultiItem> dataMultiDisplayItems;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.common_app_bar)
    CardView commonAppBar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.et_search)
    EditText etSearch;

    private List<DataMultiItem> dataMultiItems;
    private List<SearchData> searchItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs_search);
        ButterKnife.bind(this);
        daggerInject();
        imageControl.frescoInit();
        init();
    }

    private void daggerInject() {
        DaggerSearchComponent.builder().applicationComponent(
                ((ApplicationCampusBBS) getApplication()).getApplicationComponent()
        ).searchModule(new SearchModule(this))
                .settingModule(new SettingModule(this, compositeDisposable))
                .build().inject(this);
    }

    private void init() {
        dataMultiItems = new ArrayList<>();
        searchItems = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(searchAdapter);

        etSearch.setOnEditorActionListener(onEditorActionListener);

        getSearchData();

        showSearchData();
    }

    @Override
    protected SPUtils sPUtils() {
        return spUtils;
    }

    @Override
    public void changeSkin(ActionChangeSkin actionChangeSkin) {
        super.changeSkin(actionChangeSkin);
        title.setText("搜索");
        commonAppBar.setCardBackgroundColor(
                ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
        );
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            commonAppBar.setBackgroundColor(
                    ContextCompat.getColor(this, actionChangeSkin.getColorPrimaryId())
            );
        }
    }

    @OnClick(R.id.arrow_back)
    public void onClick() {
        finish();
    }

    private TextView.OnEditorActionListener onEditorActionListener = (v, actionId, event) -> {
        if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_SEND
                || actionId == EditorInfo.IME_ACTION_DONE
                || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode()
                && KeyEvent.ACTION_DOWN == event.getAction())) {
            searchGo();

        }
        return false;
    };


    private void searchGo() {
        if (etSearch.getText() != null && !etSearch.getText().toString().trim().equals("")) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);

            if (searchItems.size() >= 11) {
                searchItems.remove(0);
            }

            if (searchItems.contains(etSearch.getText().toString())) {
                searchItems.remove(etSearch.getText().toString());
                searchItems.add(new SearchData(etSearch.getText().toString()));
            } else {
                searchItems.add(new SearchData(etSearch.getText().toString()));
            }
            requestData();
            saveSearchData();
        } else {

        }
    }

    private void requestData() {
    }

    private void showSearchData() {
        if (!searchItems.isEmpty()) {
            dataMultiDisplayItems.clear();
            dataMultiDisplayItems.addAll(searchItems);
            searchAdapter.notifyDataSetChanged();
        }
    }

    private void getSearchData() {
        String searchHistory = spUtils.getString(MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE, SharedPreferenceConfig.SEARCH_DATA);
        if (!TextUtils.isEmpty(searchHistory)) {
            List<SearchData> strings = new Gson().fromJson(searchHistory, new TypeToken<List<SearchData>>() {
            }.getType());

            searchItems.addAll(strings);
        }
    }


    private void saveSearchData() {
        String searchData = new Gson().toJson(searchItems);
        spUtils.putString(MODE_PRIVATE, SharedPreferenceConfig.SHARED_PREFERENCE, SharedPreferenceConfig.SEARCH_DATA, searchData);
    }

}
