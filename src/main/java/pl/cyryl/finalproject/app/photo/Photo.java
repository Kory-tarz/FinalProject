package pl.cyryl.finalproject.app.photo;

public abstract class Photo {

    public abstract long getId();
    public abstract String getPath();
    public abstract void setPath(String path);

    public String getImagePath(){
        return getId() + "/" + getPath();
    }
}
