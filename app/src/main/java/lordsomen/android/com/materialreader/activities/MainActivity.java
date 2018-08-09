package lordsomen.android.com.materialreader.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lordsomen.android.com.materialreader.R;
import lordsomen.android.com.materialreader.adapters.ReaderAdapter;
import lordsomen.android.com.materialreader.data.network.ApiClient;
import lordsomen.android.com.materialreader.data.network.ApiInterface;
import lordsomen.android.com.materialreader.pojos.Reader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ReaderAdapter.ReaderOnClickItemHandler {
    public final static String READER_DATA = "reader";
    public final static String POSITION = "position";
    private final static String TAG = MainActivity.class.getSimpleName();
    private static final String SAVED_ARRAYLIST = "saved_array_list";
    private static final String SAVED_LAYOUT_MANAGER = "layout-manager-state";
    private ApiInterface mApiInterface;
    private List<Reader> mNetworkDataList;
    @BindView(R.id.main_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.main_linear_layout)
    LinearLayout mErrorLinearLayout;
    @BindView(R.id.main_progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.toolbar_main)
    Toolbar toolbar;
    @BindView(R.id.main_reload_button)
    Button mButton;
    private ReaderAdapter mReaderAdapter;
    private Parcelable onSavedInstanceState = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (null != toolbar) {
            setSupportActionBar(toolbar);
            toolbar.setTitle(getResources().getString(R.string.app_name));
        }
        mApiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        mReaderAdapter = new ReaderAdapter(this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setAdapter(mReaderAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        // getting the data from api using retrofit interface ApiInterface
        if (savedInstanceState != null) {
            onSavedInstanceState = savedInstanceState.getParcelable(SAVED_LAYOUT_MANAGER);
            mNetworkDataList = savedInstanceState.getParcelableArrayList(SAVED_ARRAYLIST);
        }
        if (null == mNetworkDataList) {

            loadData();

            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loadData();
                }
            });
        }else {
            loadAdapter();
        }
    }

    public void loadData() {
        final Call<List<Reader>> listCall = mApiInterface.getAllReaderData();
        // now binding the data in the pojo class
        listCall.enqueue(new Callback<List<Reader>>() {
            //if data is successfully binded from json to the pojo class onResponse is called
            @Override
            public void onResponse(Call<List<Reader>> call,
                                   Response<List<Reader>> response) {

                Log.d(TAG, "Response : " + response.code());
                mNetworkDataList = response.body();
                loadAdapter();
            }

            //if data binding is not successful onFailed called
            @Override
            public void onFailure(Call<List<Reader>> call, Throwable t) {
                //cancelling the GET data request
                listCall.cancel();
                showError();
            }
        });
    }

    private void loadAdapter() {
        if (null != mNetworkDataList) {
            showReaderList();
            mReaderAdapter.ifDataChanged(mNetworkDataList);
            if (onSavedInstanceState != null) {
                mRecyclerView.getLayoutManager().onRestoreInstanceState(onSavedInstanceState);
            }
        }
    }

    /**
     * this method is for showing the error textview and making all other views gone
     */
    private void showError() {
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mErrorLinearLayout.setVisibility(View.VISIBLE);
    }

    /**
     * this method is for showing the recyclerview and making all other views gone
     */
    private void showReaderList() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mErrorLinearLayout.setVisibility(View.GONE);
    }




    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // You can change this divider to adjust the size of the poster
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_LAYOUT_MANAGER, mRecyclerView.getLayoutManager()
                .onSaveInstanceState());
        if (mNetworkDataList != null)
            outState.putParcelableArrayList(SAVED_ARRAYLIST, new ArrayList<Parcelable>(mNetworkDataList));

    }

    @Override
    public void onClickItem(int position, Reader reader, ImageView mImage, TextView mTitle) {
        // Check if we're running on Android 5.0 or higher

        Intent readerIntent = new Intent(this, ReaderDetailsActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putParcelable(READER_DATA, reader);
        mBundle.putInt(POSITION, position);
        readerIntent.putExtras(mBundle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Apply activity transition
            ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    // Now we provide a list of Pair items which contain the view we can transitioning
                    // from, and the name of the view it is transitioning to, in the launched activity
                    new Pair<View, String>(mImage,
                            ReaderDetailsActivity.VIEW_NAME_HEADER_IMAGE),
                    new Pair<View, String>(mTitle,
                            ReaderDetailsActivity.VIEW_NAME_HEADER_TITLE));
            ActivityCompat.startActivity(this, readerIntent, activityOptions.toBundle());

        } else {
            // Swap without transition
            startActivity(readerIntent);
        }

    }

}
