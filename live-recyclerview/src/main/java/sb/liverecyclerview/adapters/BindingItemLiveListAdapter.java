package sb.liverecyclerview.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import sb.liverecyclerview.BR;

@SuppressWarnings("unused")
public abstract class BindingItemLiveListAdapter<T>
        extends LiveListAdapter<T, BindingItemLiveListAdapter.Holder<T>> {

    public BindingItemLiveListAdapter() {
        super();
    }

    public BindingItemLiveListAdapter(@NonNull DiffUtil.ItemCallback<T> diffCallback) {
        super(diffCallback);
    }

    public BindingItemLiveListAdapter(@NonNull AsyncDifferConfig<T> config) {
        super(config);
    }

    @Override
    public abstract int getItemViewType(int position);

    @NonNull
    @Override
    public Holder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, viewType, parent, false);
        return new Holder<>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder<T> holder, int position) {
        holder.bind(getItem(position));
    }

    @SuppressWarnings("WeakerAccess")
    public static class Holder<T> extends RecyclerView.ViewHolder {

        private final ViewDataBinding binding;

        Holder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(T item) {
            binding.setVariable(BR.item, item);
            binding.executePendingBindings();
        }
    }
}
