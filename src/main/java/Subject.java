import java.util.HashMap;

public interface Subject {
    public void register(Observer o, String info);
    public void unregister(Observer o, String info);
    public void notifyObserver();
    public void changeAPIStrategy(Strategy strat);
}
