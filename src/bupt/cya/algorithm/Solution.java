package bupt.cya.algorithm;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    public class Output {
        public List<Square> trace;
        public int iterNum;
        public int stepNum;
        public Output(List<Square> trace, int iterNum, int stepNum) {
            this.trace = trace;
            this.iterNum = iterNum;
            this.stepNum = stepNum;
        }
    }
    public Output solve(Square square) {
        List<Square> trace = new ArrayList<>();
        AStar aStar = new AStar();
        if (!square.canSolve()) return null;
        Square result = aStar.solve(square);
        trace.add(result);
        while (result.getParent() != null) {
            trace.add(0, result.getParent());
            result = result.getParent();
        }
        return new Output(trace, aStar.getIterNum(), trace.size());
    }
}
