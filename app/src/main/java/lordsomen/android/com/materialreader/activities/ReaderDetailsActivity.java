package lordsomen.android.com.materialreader.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import lordsomen.android.com.materialreader.R;
import lordsomen.android.com.materialreader.pojos.Reader;
import lordsomen.android.com.materialreader.utils.GlideApp;

public class ReaderDetailsActivity extends AppCompatActivity {

    private static final String TAG = ReaderDetailsActivity.class.getSimpleName();
    private static final String SAVED_ARRAYLIST = "saved_array_list";
    private static final String SAVED_LAYOUT_MANAGER = "layout-manager-state";
    private final static String ARTICLE_SCROLL_POSITION = "article_scroll_position";
    // View name of the header image. Used for activity scene transitions
    public static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";

    // View name of the header title. Used for activity scene transitions
    public static final String VIEW_NAME_HEADER_TITLE = "detail:header:title";

    private int position;
    private Reader reader;
    private int[] scrollPosition = null;
    @BindView(R.id.scrollView_details)
    ScrollView mScrollView;
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
    @BindView(R.id.floatingActionButton_Up)
    FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader_details);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        position=0;
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScrollView.scrollTo(0,0);
            }
        });
        ViewCompat.setTransitionName(mImageView, VIEW_NAME_HEADER_IMAGE);
        ViewCompat.setTransitionName(mTitle, VIEW_NAME_HEADER_TITLE);
        if (null != bundle) {
            position = bundle.getInt(MainActivity.POSITION);
            reader = bundle.getParcelable(MainActivity.READER_DATA);
            if(null != reader) {
                mTitle.setText(reader.getTitle());
                mPublishDate.setText(reader.getPublishedDate());
                mAuthor.setText(reader.getAuthor());
                GlideApp.with(this)
                        .load(reader.getPhoto())
                        .into(mImageView);
                mDescription.setText(reader.getBody());
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        scrollPosition = savedInstanceState.getIntArray(ARTICLE_SCROLL_POSITION);
        if (scrollPosition != null) {
            mScrollView.postDelayed(new Runnable() {
                public void run() {
                    mScrollView.scrollTo(scrollPosition[0], scrollPosition[0]);
                }
            }, 0);
        }
    }
}
