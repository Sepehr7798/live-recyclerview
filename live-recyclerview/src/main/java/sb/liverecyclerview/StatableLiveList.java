package sb.liverecyclerview;

import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import sb.livecollection.LiveList;

@SuppressWarnings({"unused", "WeakerAccess"})
public class StatableLiveList<T> extends LiveList<T> {

    private static final String TAG = "StatableLiveList";

    private final MutableLiveData<State> state;

    public StatableLiveList() {
        state = new MutableLiveData<>();
    }

    @SafeVarargs
    public StatableLiveList(T... items) {
        super(items);
        this.state = new MutableLiveData<>();
        setState(State.RESULT);
    }

    public void load(final Loader<T> loader) {
        setState(State.LOADING);
        List<T> data = loader.load();
        if (data == null) {
            setState(State.ERROR);
        } else if (data.isEmpty()) {
            setState(State.EMPTY);
        } else {
            setState(State.RESULT);
        }

        clear();
        if (data != null) addAll(data);
    }

    public LiveData<State> getState() {
        return state;
    }

    public void setState(State state) {
        if (state.equals(this.state.getValue())) return;

        Log.d(TAG, "setState: " + state);
        if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
            this.state.setValue(state);
        } else {
            this.state.postValue(state);
        }
    }


    public enum State {
        LOADING,
        RESULT,
        EMPTY,
        ERROR
    }

    public interface Loader<T> {
        List<T> load();
    }
}
