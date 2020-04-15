import java.io.FileNotFoundException;

public interface Game {
    void play() throws FileNotFoundException;
    int getName();
}
