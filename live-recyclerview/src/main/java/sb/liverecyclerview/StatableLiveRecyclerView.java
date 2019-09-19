package sb.liverecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

@SuppressWarnings("unused")
public class StatableLiveRecyclerView extends LiveRecyclerView {

    private final LayoutManager stateLayoutManager;
    private boolean isStateAdapterSetting = false;
    private Adapter adapter;
    private LayoutManager layoutManager;

    private View loadingView;
    private View errorView;
    private View emptyView;

    public StatableLiveRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public StatableLiveRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatableLiveRecyclerView(
            @NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        stateLayoutManager = new LinearLayoutManager(context, VERTICAL, false);

        loadingView = LayoutInflater.from(context).inflate(R.layout.lrv_loading_view, this, false);
        errorView = new View(context);
        emptyView = new View(context);

        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StatableLiveRecyclerView,
                0,
                0);
        for (int i = 0; i < typedArray.length(); i++) {
            int attr = typedArray.getIndex(i);

            if (attr == R.styleable.StatableLiveRecyclerView_lrv_loading_view_visible) {
                boolean isVisible = typedArray.getBoolean(attr, false);
                loadingView.setVisibility(isVisible ? VISIBLE : GONE);
            } else if (attr == R.styleable.StatableLiveRecyclerView_lrv_loading_view_layout) {
                loadingView = initView(typedArray, attr, StatableLiveList.State.LOADING);
            } else if (attr == R.styleable.StatableLiveRecyclerView_lrv_empty_view_layout) {
                emptyView = initView(typedArray, attr, StatableLiveList.State.EMPTY);
            } else if (attr == R.styleable.StatableLiveRecyclerView_lrv_error_view_layout) {
                errorView = initView(typedArray, attr, StatableLiveList.State.ERROR);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void setData(final StatableLiveList data) {
        super.setData(data);

        data.getState().observe((LifecycleOwner) getContext(), new Observer<StatableLiveList.State>() {
            @Override
            public void onChanged(StatableLiveList.State state) {
                switch (state) {
                    case LOADING:
                        setStateAdapter(loadingView);
                        break;
                    case ERROR:
                        setStateAdapter(errorView);
                        break;
                    case EMPTY:
                        setStateAdapter(emptyView);
                        break;
                    case RESULT:
                        setDataAdapter();
                        break;
                }
            }
        });
    }

    @Override
    public StatableLiveList getData() {
        return (StatableLiveList) super.getData();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if (!isStateAdapterSetting) {
            this.adapter = adapter;
        }
    }

    @Override
    public void setLayoutManager(LayoutManager layoutManager) {
        super.setLayoutManager(layoutManager);

        if (!isStateAdapterSetting) {
            this.layoutManager = layoutManager;
        }
    }

    public void setLoadingView(View loadingView) {
        this.loadingView = loadingView;
    }

    public void setErrorView(View errorView) {
        this.errorView = errorView;
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    public View getLoadingView() {
        return loadingView;
    }

    public View getErrorView() {
        return errorView;
    }

    public View getEmptyView() {
        return emptyView;
    }

    private View initView(TypedArray typedArray, int attr, StatableLiveList.State state) {
        int resId = typedArray.getResourceId(attr, 0);
        View view = LayoutInflater.from(getContext()).inflate(resId, this, false);

        if (getData() != null && getData().getState().getValue() == state) {
            setStateAdapter(view);
        }

        return view;
    }

    private void setStateAdapter(View view) {
        isStateAdapterSetting = true;
        setAdapter(new StateAdapter(view));
        setLayoutManager(stateLayoutManager);
        isStateAdapterSetting = false;
    }

    private void setDataAdapter() {
        setAdapter(adapter);
        setLayoutManager(layoutManager);
    }

    private static class StateAdapter extends Adapter<ViewHolder> {

        private final View view;

        StateAdapter(View view) {
            this.view = view;
        }

        @Override
        public int getItemCount() {
            return 1;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(view) {
            };
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        }
    }
}
