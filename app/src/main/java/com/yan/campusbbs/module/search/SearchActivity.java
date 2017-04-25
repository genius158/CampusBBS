package com.yan.campusbbs.module.search;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.yan.campusbbs.module.search.action.ActionChangeSearchText;
import com.yan.campusbbs.module.search.adapter.SearchAdapter;
import com.yan.campusbbs.module.search.data.SearchData;
import com.yan.campusbbs.module.selfcenter.adapter.SelfCenterMultiItemAdapter;
import com.yan.campusbbs.module.setting.ImageControl;
import com.yan.campusbbs.module.setting.SettingHelper;
import com.yan.campusbbs.module.setting.SettingModule;
import com.yan.campusbbs.repository.entity.DataMultiItem;
import com.yan.campusbbs.rxbusaction.ActionChangeSkin;
import com.yan.campusbbs.util.RxBus;
import com.yan.campusbbs.util.SPUtils;
import com.yan.campusbbs.util.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by yan on 2017/2/15.
 */

public class SearchActivity extends BaseActivity implements SearchContract.View {

    @Inject
    SettingHelper changeSkinHelper;
    @Inject
    SearchPresenter presenter;
    @Inject
    RxBus rxBus;
    @Inject
    ToastUtils toastUtils;
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

    private List<SearchData> searchItems;

    private int pageNum = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs_search);
        ButterKnife.bind(this);
        daggerInject();
        imageControl.frescoInit();
        init();
        RxActionInit();
    }

    private void daggerInject() {
        DaggerSearchComponent.builder().applicationComponent(
                ((ApplicationCampusBBS) getApplication()).getApplicationComponent()
        ).searchModule(new SearchModule(this))
                .settingModule(new SettingModule(this, compositeDisposable))
                .build().inject(this);
    }

    private void init() {
        searchItems = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.setAdapter(searchAdapter);

        etSearch.setOnEditorActionListener(onEditorActionListener);
        etSearch.addTextChangedListener(watcher);

        getSearchData();
        showSearchData();
        etSearch.requestFocus();
    }

    @OnClick(R.id.arrow_back)
    public void onClick() {
        finish();
    }

    private TextView.OnEditorActionListener onEditorActionListener = (v, actionId, event) -> {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
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
            boolean isModify = false;
            for (SearchData searchData : searchItems) {
                if (searchData.data.equals(etSearch.getText().toString())) {
                    searchItems.remove(searchData);
                    searchItems.add(new SearchData(etSearch.getText().toString()));
                    isModify = true;
                    break;
                }
            }
            if (!isModify) {
                searchItems.add(new SearchData(etSearch.getText().toString()));
            }
            requestData();
            saveSearchData();
        }
    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            dataMultiDisplayItems.clear();

            if (TextUtils.isEmpty(s)) {
                dataMultiDisplayItems.addAll(searchItems);
            } else {
                if (searchItems != null && !searchItems.isEmpty()) {
                    List<SearchData> historyShow = new ArrayList<>();
                    for (SearchData searchData : searchItems) {
                        if (searchData.data.contains(s)) {
                            historyShow.add(searchData);
                        }
                    }
                    dataMultiDisplayItems.addAll(historyShow);
                }
            }
            Collections.reverse(dataMultiDisplayItems);
            searchAdapter.notifyDataSetChanged();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void requestData() {
        pageNum = 1;
        presenter.getTopicList(String.valueOf(pageNum)
                , etSearch.getText().toString(), 1);
    }

    private void showSearchData() {
        if (!searchItems.isEmpty()) {
            dataMultiDisplayItems.clear();
            dataMultiDisplayItems.addAll(searchItems);
            Collections.reverse(dataMultiDisplayItems);
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

    private void RxActionInit() {
        addDisposable(rxBus.getEvent(ActionChangeSearchText.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(actionChangeSearchText -> {
                    etSearch.setText(actionChangeSearchText.searchData.data);
                    etSearch.setSelection(etSearch.getText().toString().length());
                    Observable.timer(200, TimeUnit.MILLISECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(aLong -> {
                                etSearch.requestFocus();
                                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                inputMethodManager.showSoftInput(etSearch, 0);
                            });
                }));
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

    @Override
    public void netError() {
        toastUtils.showShort("网络错误");
    }

    @Override
    public void setTopic(List<DataMultiItem> dataMultiItems) {
        if (pageNum == 1) {
            this.dataMultiDisplayItems.clear();
            this.dataMultiDisplayItems.addAll(dataMultiItems);
            searchAdapter.notifyDataSetChanged();
            if (dataMultiDisplayItems.size() > 4) {
                searchAdapter.setOnLoadMoreListener(() -> {
                    presenter.getTopicList(String.valueOf(++pageNum)
                            , etSearch.getText().toString(), 1);
                });
                searchAdapter.setEnableLoadMore(true);
            }
        } else {
            int tempSize = this.dataMultiDisplayItems.size();
            this.dataMultiDisplayItems.addAll(dataMultiItems);
            searchAdapter.notifyItemRangeInserted(tempSize, this.dataMultiDisplayItems.size() - tempSize);
            searchAdapter.loadMoreComplete();
        }
    }

    @Override
    public void error(String msg) {

    }
}
