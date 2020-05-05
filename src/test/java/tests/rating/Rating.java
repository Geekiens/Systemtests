package tests.rating;

public class Rating {
    private int score;
    private String title;
    private String content;

    public Rating() {
    }

    public Rating(int score, String title, String content) {
        this.score = score;
        this.title = title;
        this.content = content;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
