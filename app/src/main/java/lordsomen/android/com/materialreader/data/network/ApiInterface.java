package lordsomen.android.com.materialreader.data.network;

import java.util.List;

import lordsomen.android.com.materialreader.pojos.Reader;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("58c5d68f_xyz-reader/xyz-reader.json")
    Call<List<Reader>> getAllReaderData();
}
