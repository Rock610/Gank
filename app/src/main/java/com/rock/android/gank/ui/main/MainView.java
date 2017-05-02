package com.rock.android.gank.ui.main;

import com.rock.android.gank.Model.Module;
import com.rock.android.gank.base.BaseView;

import java.util.ArrayList;

/**
 * Created by rock on 2017/4/28.
 */

public interface MainView extends BaseView {
    boolean isHasMore();

    void setRefreshing(boolean b);

    int getPage();

    void setHasMore(boolean b);

    void clearList();

    void refreshDataModule(ArrayList<Module> module,boolean fromDB);

    ArrayList<Module> getCurrentDataList();
}
