package sb.liverecyclerview.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

@SuppressWarnings("unused")
public abstract class LiveListAdapter<T, VH extends RecyclerView.ViewHolder>
        extends ListAdapter<T, VH> implements LiveAdapter<T> {

    private static final DiffUtil.ItemCallback DEFAULT_CALLBACK = new DiffUtil.ItemCallback() {

        @Override
        public boolean areItemsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
            return true;
        }
    };

    @SuppressWarnings("unchecked")
    public LiveListAdapter() {
        super(DEFAULT_CALLBACK);
    }

    public LiveListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
    }

    public LiveListAdapter(@NonNull AsyncDifferConfig<T> config) {
        super(config);
    }

    @Override
    public void onChanged(final List<T> ts) {
        if (ts != null) {
            submitList(ts);
        }
    }
}
