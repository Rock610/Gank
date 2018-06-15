package com.rock.android.gank.ui.contentmvvm;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.rock.android.gank.Model.Module;

public class TestWrapper extends BaseObservable{

    private Module module;

    public TestWrapper(Module module) {
        this.module = module;
    }

    @Bindable
    public String get_id() {
        return module.get_id();
    }

    public void set_id(String _id) {
        module.set_id(_id);
        notifyPropertyChanged(BR._id);
    }

    //....等等的getter setter

}
