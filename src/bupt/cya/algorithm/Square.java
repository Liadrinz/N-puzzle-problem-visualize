package bupt.cya.algorithm;

import java.util.ArrayList;
import java.util.List;

public class Square {
    public static int num = 16;
    public static int side = 4;
    public static float factor1 = 1;
    public static float factor2 = 0;
    private byte[] data;
    private final byte[] standard = new byte[num];
    private int level = 0;
    private Square parent = null;
    private List<Square> children = new ArrayList<>();

    public Square(byte[] data) {
        this.data = data;
        int k = 0;
        for (byte i = 1; i < num; ++i) {
            standard[k++] = i;
        }
        standard[k] = (byte)0;
    }

    public boolean isInvalid(int row, int col) {
        return row < 0 || row >= side || col < 0 || col >= side;
    }

    public byte get(int row, int col) {
        if (isInvalid(row, col)) return -1;
        return data[row * side + col];
    }

    public void set(int row, int col, byte value) {
        if (isInvalid(row, col)) return;
        data[row * side + col] = value;
    }

    public Square move(int row, int col, char direction) {
        int row2 = 0, col2 = 0;
        switch (direction) {
            case 'u':
                row2 = row - 1;
                col2 = col;
                break;
            case 'd':
                row2 = row + 1;
                col2 = col;
                break;
            case 'l':
                row2 = row;
                col2 = col - 1;
                break;
            case 'r':
                row2 = row;
                col2 = col + 1;
                break;
        }
        byte target = get(row2, col2);
        if (target != 0) return null;
        Square child = clone();
        child.set(row2, col2, get(row, col));
        child.set(row, col, (byte)0);
        child.parent = this;
        this.children.add(child);
        child.level = this.level + 1;
        return child;
    }

    public List<Square> getPossibleChildren() {
        int i;
        for (i = 0; i < num; ++i)
            if (data[i] == (byte)0) break;
        int r = i / side;
        int c = i % side;
        List<Square> result = new ArrayList<>();
        for (int j = -1; j < 2; j += 2) {
            if (!isInvalid(r + j, c))
                result.add(move(r + j, c, j < 0 ? 'd' : 'u'));
            if (!isInvalid(r, c + j))
                result.add(move(r, c + j, j < 0 ? 'r' : 'l'));
        }
        return result;
    }

    public int manhattanByValue(byte value) {
        int p1 = 0, p2 = 0;
        for (int p = 0; p < num; ++p) {
            if (data[p] == value) p1 = p;
            if (standard[p] == value) p2 = p;
        }
        return Math.abs(p1 / side - p2 / side) + Math.abs(p1 % side - p2 % side);
    }

    public int manhattan() {
        int distance = 0;
        for (byte i = 1; i < num; ++i) {
            distance += manhattanByValue(i);
        }
        return distance;
    }

    public double cost() {

        return factor1 * manhattan() + factor2 * level;
    }

    public boolean isStandard() {
        return manhattan() == 0;
    }

    public boolean canSolve() {
        int inv1 = 0, inv2 = 0;
        for (int i = 0; i < num; ++i) {
            for (int j = i + 1; j < num; ++j) {
                inv1 += data[i] > data[j] ? 1 : 0;
                inv2 += standard[i] > standard[j] ? 1 : 0;
            }
        }
        inv1 += manhattanByValue((byte)0);
        return (inv1 % 2) == (inv2 % 2);
    }

    @Override
    protected Square clone() {
        return new Square(this.data.clone());
    }

    public boolean equals(Square square) {
        for (int i = 0; i < num; ++i) {
            if (data[i] != square.data[i]) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < side; ++i) {
            for (int j = 0; j < side; ++j) {
                result += String.format("%3d", (int)get(i, j));
            }
            if (i < side - 1)
                result += "\n";
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        Square other = (Square)o;
        for (int i = 0; i < num; ++i) {
            if (other.data[i] != this.data[i]) return false;
        }
        return true;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public byte[] getStandard() {
        return standard;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Square getParent() {
        return parent;
    }

    public void setParent(Square parent) {
        this.parent = parent;
    }

    public List<Square> getChildren() {
        return children;
    }

    public void setChildren(List<Square> children) {
        this.children = children;
    }
}
