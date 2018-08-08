package lordsomen.android.com.materialreader.pojos;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Reader implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("aspect_ratio")
    @Expose
    private Double aspectRatio;
    @SerializedName("published_date")
    @Expose
    private String publishedDate;
    public final static Parcelable.Creator<Reader> CREATOR = new Creator<Reader>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Reader createFromParcel(Parcel in) {
            return new Reader(in);
        }

        public Reader[] newArray(int size) {
            return (new Reader[size]);
        }

    }
            ;
    private final static long serialVersionUID = 3978899604196034823L;

    protected Reader(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.author = ((String) in.readValue((String.class.getClassLoader())));
        this.body = ((String) in.readValue((String.class.getClassLoader())));
        this.thumb = ((String) in.readValue((String.class.getClassLoader())));
        this.photo = ((String) in.readValue((String.class.getClassLoader())));
        this.aspectRatio = ((Double) in.readValue((Double.class.getClassLoader())));
        this.publishedDate = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Reader() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Double getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(Double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(author);
        dest.writeValue(body);
        dest.writeValue(thumb);
        dest.writeValue(photo);
        dest.writeValue(aspectRatio);
        dest.writeValue(publishedDate);
    }

    public int describeContents() {
        return 0;
    }

}
