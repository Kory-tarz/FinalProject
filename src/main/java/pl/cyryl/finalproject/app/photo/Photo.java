package pl.cyryl.finalproject.app.photo;

public abstract class Photo {

    public abstract long getId();
    public abstract String getPath();

    public String getImagePath(){
        return getId() + "/" + getPath();
    }
}
