package bupt.cya.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SquareFactory {
    public static void config(String N, String factor1, String factor2) {
        Square.num = Integer.parseInt(N) + 1;
        if (Square.num > 100) Square.num = 100;
        Square.side = (int)Math.sqrt(Square.num);
        Square.num = Square.side * Square.side;
        Square.factor1 = Float.parseFloat(factor1);
        Square.factor2 = Float.parseFloat(factor2);
    }
    public static byte[] toArray(List<Byte> list) {
        byte[] array = new byte[list.size()];
        for (int i = 0; i < list.size(); ++i) {
            array[i] = list.get(i);
        }
        return array;
    }
    public static Square getSolvableSquare() {
        List<Byte> data = new ArrayList<>();
        for (byte i = 0; i < Square.num; ++i) data.add(i);
        Collections.shuffle(data);
        Square square = new Square(toArray(data));
        while (!square.canSolve()) {
            Collections.shuffle(data);
            square.setData(toArray(data));
        }
        return square;
    }
}
