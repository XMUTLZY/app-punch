package sch.project.app_punch.ui.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public UserViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
        mText.postValue("12412412");
    }

    public LiveData<String> getText() {
        return mText;
    }
}