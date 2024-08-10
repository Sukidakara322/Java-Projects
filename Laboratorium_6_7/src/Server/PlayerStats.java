package Server;

import java.util.concurrent.atomic.AtomicInteger;

public class PlayerStats {
    private AtomicInteger wins = new AtomicInteger(0);
    private AtomicInteger losses = new AtomicInteger(0);
    private AtomicInteger draws = new AtomicInteger(0);

    public void increment_wins(){
        wins.incrementAndGet();
    }
    public void increment_losses(){
        losses.incrementAndGet();
    }
    public void increment_draws(){
        draws.incrementAndGet();
    }
    @Override
    public String toString(){
        return "Wins: " +  wins.get() + ", Losses: " + losses.get() + ", Draws: " + draws.get();
    }
}
