package technifutur.be.projetyoutube.model.youtube;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by student5312 on 18/05/17.
 */

public class Achievement extends RealmObject{
    @PrimaryKey
    private String name;
    private int image;
    private int count;

    public Achievement(String name, int image) {
        this.name = name;
        this.image = image;
        this.count = 0;
    }

    public Achievement() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
