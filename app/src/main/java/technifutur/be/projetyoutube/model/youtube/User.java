package technifutur.be.projetyoutube.model.youtube;

import android.os.Parcel;
import android.os.Parcelable;

import com.sendbird.android.OpenChannel;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by student5312 on 11/04/17.
 */

public class User extends RealmObject implements Parcelable{
    @PrimaryKey
    private String id;
    private String name;
    private String image;

    public User() {
    }


    protected User(Parcel in) {
        id = in.readString();
        name = in.readString();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
