package com.catinthedark.ld31.impl.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by over on 07.12.14.
 */
public class LevelMatrix2 {
    List<LevelBlock[]> matrix = Collections.synchronizedList(new ArrayList<LevelBlock[]>());

    final int rows;
    public final View view = new View();

    public LevelMatrix2(int rows, int cols, LevelBlockDisposer disposer) {
        this.rows = rows;
    }

    public class ColMapper {
        private final LevelBlock[] col;

        public ColMapper(LevelBlock[] col) {
            this.col = col;
        }

        public void setCell(int y, LevelBlock cell) {
//            LevelBlock old = col[y];
//            if (old != null)
//                disposer.dispose(old);
            col[y] = cell;
        }
    }

    public ColMapper nextCol() {
        LevelBlock[] newRow = new LevelBlock[rows];
        matrix.add(newRow);
        return new ColMapper(newRow);
    }

    public ColMapper colAt(int index) {
        return new ColMapper(matrix.get(index));
    }

    public class View implements Iterable<LevelBlock[]> {
        @Override
        public Iterator<LevelBlock[]> iterator() {
            return matrix.iterator();
        }
    }

    public void reset(){
        matrix.clear();
    }

}
