
package com.jacky.launcher.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.DisplayMetrics;

import com.jacky.launcher.R;
import com.jacky.launcher.app.AppCardPresenter;
import com.jacky.launcher.app.AppDataManage;
import com.jacky.launcher.app.AppModel;

import java.util.ArrayList;

public class MainActivity extends Activity {

    protected BrowseFragment mBrowseFragment;
    private ArrayObjectAdapter rowsAdapter;
    private Context mContext;
    private static final int COW_COUNT = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        mBrowseFragment = (BrowseFragment) getFragmentManager().findFragmentById(R.id.browse_fragment);

        mBrowseFragment.setHeadersState(BrowseFragment.HEADERS_DISABLED);

        prepareBackgroundManager();
        buildRowsAdapter();
    }

    private void prepareBackgroundManager() {
        BackgroundManager mBackgroundManager = BackgroundManager.getInstance(this);
        mBackgroundManager.attach(this.getWindow());
        DisplayMetrics mMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void buildRowsAdapter() {
        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        addAppRow();
        addGameRow();

        mBrowseFragment.setAdapter(rowsAdapter);
        mBrowseFragment.setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                if (item instanceof AppModel) {
                    AppModel appBean = (AppModel) item;
                    Intent launchIntent = mContext.getPackageManager().getLaunchIntentForPackage(
                            appBean.getPackageName());
                    if (launchIntent != null) {
                        mContext.startActivity(launchIntent);
                    }
                }
            }
        });
    }

    private void addAppRow() {
        ArrayList<AppModel> appDataList = new AppDataManage(mContext).getLaunchAppList();
        int cardCount = appDataList.size();

        for (int r = 0; r < ((cardCount+COW_COUNT-1)/COW_COUNT); r++) {
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new AppCardPresenter());
            for (int c = (r*COW_COUNT); ((c < cardCount) && (c < (r*COW_COUNT+COW_COUNT))); c++) {
                listRowAdapter.add(appDataList.get(c));
            }
            rowsAdapter.add(new ListRow(listRowAdapter));
        }
    }

    private void addGameRow() {
        ArrayList<AppModel> appDataList = new AppDataManage(mContext).getLaunchGameList();
        int cardCount = appDataList.size();

        for (int r = 0; r < ((cardCount+COW_COUNT-1)/COW_COUNT); r++) {
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new AppCardPresenter());
            for (int c = (r*COW_COUNT); ((c < cardCount) && (c < (r*COW_COUNT+COW_COUNT))); c++) {
                listRowAdapter.add(appDataList.get(c));
            }
            rowsAdapter.add(new ListRow(listRowAdapter));
        }
    }
}
