package br.udesc.pinii.macro.model;

public class StatisticEdge implements Comparable<StatisticEdge> {

    private int score = 0;
    private String id;

    public StatisticEdge(String id) {
        this.id = id;
    }

    public void incScore(int num) {
        this.score = score + num;
    }

    public int getScore() {
        return score;
    }

    public String getId() {
        return id;
    }

    @Override
    public int compareTo(StatisticEdge o) {
        if (this.score > o.getScore()) {
            return -1;
        }
        if (this.score < o.getScore()) {
            return 1;
        }
        return 0;
    }
}
