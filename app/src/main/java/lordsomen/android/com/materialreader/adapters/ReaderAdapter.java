package lordsomen.android.com.materialreader.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lordsomen.android.com.materialreader.R;
import lordsomen.android.com.materialreader.pojos.Reader;
import lordsomen.android.com.materialreader.utils.GlideApp;

public class ReaderAdapter extends RecyclerView.Adapter<ReaderAdapter.ReaderAdapterViewHolder> {
    private List<Reader> readerDataList;
    private Context mContext;
    private ReaderOnClickItemHandler mClickHandler;

    public ReaderAdapter(Context context, ReaderOnClickItemHandler click) {

        mContext = context;
        mClickHandler = click;
        setHasStableIds(true);

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ReaderAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int itemId = R.layout.reader_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParent = false;
        View movieViewObject = inflater.inflate(itemId, viewGroup, attachToParent);
        return new ReaderAdapterViewHolder(movieViewObject);
    }

    @Override
    public void onBindViewHolder(ReaderAdapterViewHolder holder, int position) {
        if(readerDataList != null){
            Reader reader = readerDataList.get(position);
            String imgUrl = reader.getThumb();
            GlideApp.with(mContext)
                    .load(imgUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.readerItemImageView);
            holder.mItemName.setText(reader.getTitle());
        }

    }

    @Override
    public int getItemCount() {
        if (readerDataList == null) {
            return 0;
        } else {
            return readerDataList.size();
        }
    }

    public void ifDataChanged(List<Reader> readerData) {
        if (readerData != null)
            readerDataList = readerData;
        notifyDataSetChanged();
    }

    public interface ReaderOnClickItemHandler {
        void onClickItem(int position,Reader reader,ImageView mImage,TextView mTitle);
    }

    public class ReaderAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.main_item_thumbnail_image)
        public ImageView readerItemImageView;
        @BindView(R.id.main_item_name)
        public TextView mItemName;

        public ReaderAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onClickItem(getAdapterPosition(),readerDataList.get(getAdapterPosition())
            ,readerItemImageView,mItemName);
        }
    }
}


