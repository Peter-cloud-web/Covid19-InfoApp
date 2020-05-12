package seanpetersgichia.covid19_inforapp.ui.country;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CountryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CountryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}