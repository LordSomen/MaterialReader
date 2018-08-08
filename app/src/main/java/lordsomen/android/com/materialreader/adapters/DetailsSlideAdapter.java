package lordsomen.android.com.materialreader.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import lordsomen.android.com.materialreader.fragments.DetailsFragment;
import lordsomen.android.com.materialreader.pojos.Reader;

public class DetailsSlideAdapter extends FragmentStatePagerAdapter {

    private List<Reader> mReaderList;

    public DetailsSlideAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return DetailsFragment.init(mReaderList.get(position));
    }

    @Override
    public int getCount() {
        return mReaderList.size();
    }

    public void setData(List<Reader> reader){
        if(null!=reader){
            mReaderList = reader;
            notifyDataSetChanged();
        }
    }
}
