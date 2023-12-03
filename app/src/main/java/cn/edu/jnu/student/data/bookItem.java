package cn.edu.jnu.student.data;

public class bookItem {
    private final String title;
    private final int image;

    public bookItem(String title, int image) {
        this.title=title;
        this.image=image;
    }

    public String getTextId() {
        return title;
    }

    public int getImageId() {
        return image;
    }
}
