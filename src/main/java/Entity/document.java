package Entity;

/**
 * Created by yamengwenjing on 2017-04-02.
 */
public class document {
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public document(String content) {
        this.content = content;
    }

    String content;
    int category;

}
