package bupt.cya.algorithm;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private boolean mostQuery;
    private final List<Square> data = new ArrayList<>();
    public Table(boolean mostQuery) {
        this.mostQuery = mostQuery;
    }
    public void insert(Square square) {
        if (mostQuery) {
            double cost = square.cost();
            int l = 0, r = data.size() - 1;
            int m = (l + r) / 2;
            while (l < r - 1) {
                if (cost > data.get(m).cost())
                    l = m;
                else r = m;
                m = (l + r) / 2;
            }
            data.add(m, square);
        } else
            data.add(square);
    }
    public void delete(Square square) {
        data.remove(square);
    }
    public Square getMinCostSquare() {
        if (mostQuery)
            return data.get(0);
        else {
            double minCost = -1;
            Square minCostSquare = null;
            for (Square datum : data) {
                double cost = datum.cost();
                if (minCost == -1 || cost < minCost) {
                    minCost = cost;
                    minCostSquare = datum;
                }
            }
            return minCostSquare;
        }
    }
    public int length() {
        return data.size();
    }
    public boolean contains(Square square) {
        for (Square datum : data) {
            if (datum.equals(square)) return true;
        }
        return false;
    }
}
