package lordsomen.android.com.materialreader.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import lordsomen.android.com.materialreader.R;
import lordsomen.android.com.materialreader.pojos.Reader;
import lordsomen.android.com.materialreader.utils.GlideApp;

public class DetailsFragment extends Fragment {

    public static final String VAL = "val";
    public Reader reader;
    @BindView(R.id.details_fragment_title)
    TextView mTitle;
    @BindView(R.id.imageView_details)
    ImageView mImageView;
    @BindView(R.id.textView_author_details)
    TextView mAuthor;
    @BindView(R.id.textView_published_date)
    TextView mPublishDate;
    @BindView(R.id.textView_description)
    TextView mDescription;

    public static DetailsFragment init(Reader reader) {

        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(VAL, reader);
        detailsFragment.setArguments(args);
        return detailsFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments())
            reader = getArguments().getParcelable(VAL);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, rootView);
        if(null != reader) {
            mTitle.setText(reader.getTitle());
            mPublishDate.setText(reader.getPublishedDate());
            mAuthor.setText(reader.getAuthor());
            GlideApp.with(getActivity().getApplicationContext())
                    .load(reader.getPhoto())
                    .into(mImageView);
            mDescription.setText(reader.getBody());
        }
        return rootView;
    }

}
