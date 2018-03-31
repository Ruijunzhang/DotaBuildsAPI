package models;

public class Match {

    private Hero myHero;
    private boolean isWon;
    private long kill;
    private long death;
    private long gpm;
    private long assist;
    private long matchId;

    public Hero getMyHero() {
        return myHero;
    }

    public void setMyHero(Hero myHero) {
        this.myHero = myHero;
    }

    public boolean isWon() {
        return isWon;
    }

    public void setWon(boolean won) {
        isWon = won;
    }

    public long getKill() {
        return kill;
    }

    public void setKill(long kill) {
        this.kill = kill;
    }

    public long getDeath() {
        return death;
    }

    public void setDeath(long death) {
        this.death = death;
    }

    public long getGpm() {
        return gpm;
    }

    public void setGpm(long gpm) {
        this.gpm = gpm;
    }

    public long getAssist() {
        return assist;
    }

    public void setAssist(long assist) {
        this.assist = assist;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }
}
