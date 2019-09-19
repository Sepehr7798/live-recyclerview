package sb.liverecyclerview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import sb.livecollection.LiveList;
import sb.liverecyclerview.adapters.LiveAdapter;

@SuppressWarnings("unused")
public class LiveRecyclerView extends RecyclerView {

    private LiveList data;

    public LiveRecyclerView(@NonNull Context context) {
        super(context);
    }

    public LiveRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LiveRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        super.setAdapter(adapter);
        bind();
    }

    public void setData(LiveList data) {
        this.data = data;
        bind();
    }

    public LiveList getData() {
        return data;
    }

    @SuppressWarnings("unchecked")
    private void bind() {
        if (data != null && getAdapter() != null && getAdapter() instanceof LiveAdapter) {
            data.observe((LifecycleOwner) getContext(), (Observer) getAdapter());
        }
    }
}
