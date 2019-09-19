package sb.liverecyclerview.adapters;

import androidx.lifecycle.Observer;

import java.util.List;

public interface LiveAdapter<T> extends Observer<List<T>> {
}
