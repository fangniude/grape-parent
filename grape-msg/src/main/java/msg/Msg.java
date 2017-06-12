package msg;

public final class Msg {
    public final String destination;
    public final String title;
    public final String content;

    public Msg(String destination, String title, String content) {
        this.destination = destination;
        this.title = title;
        this.content = content;
    }
}
