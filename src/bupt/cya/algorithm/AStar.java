package bupt.cya.algorithm;

public class AStar {
    private int iterNum = 0;
    private final Table open = new Table(true);
    private final Table close  = new Table(false);
    public Square solve(Square initSquare) {
        open.insert(initSquare);
        while (open.length() > 0) {
            iterNum++;
            Square square = open.getMinCostSquare();
            if (square.isStandard()) return square;
            for (Square child : square.getPossibleChildren()) {
                if (!open.contains(child) && !close.contains(child)) {
                    open.insert(child);
                }
            }
            open.delete(square);
            close.insert(square);
        }
        return null;
    }
    public int getIterNum() {
        return iterNum;
    }
}
