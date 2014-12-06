package com.catinthedark.ld31.impl.level;

import java.lang.reflect.Array;
import java.util.Iterator;

/**
 * Created by kirill on 06.12.14.
 */
public class LevelMatrix {
    private final LevelBlock[][] matrix;
    //pointer for addition
    private int ptr = 0;
    //pointer for iteration
    private int beginPtr = 0;
    //count of already added blocks
    private int added = 0;
    private final int cols;
    private final LevelBlockDisposer disposer;
    public final View view = new View();

    public LevelMatrix(int rows, int cols, LevelBlockDisposer disposer) {
        matrix = (LevelBlock[][]) Array.newInstance(LevelBlock.class, cols, rows);
        this.cols = cols;
        this.disposer = disposer;
    }

    /**
     * Use view for data query ONLY! NOT for modifications!
     * FIXME: buggy iterator! Works if level matrix full filled only :(
     */
    public class View implements Iterable<LevelBlock[]> {
        @Override
        public Iterator<LevelBlock[]> iterator() {
            return new Iterator<LevelBlock[]>() {
                private int _ptr;
                private int _iterated = 0;

                @Override
                public boolean hasNext() {
                    int widowSize = added < cols ? added : cols;
                    return _iterated != widowSize;
                }

                @Override
                public LevelBlock[] next() {
                    LevelBlock[] ret = matrix[_ptr];
                    _ptr++;
                    _ptr = _ptr % cols;
                    _iterated++;
                    return ret;
                }

                Iterator<LevelBlock[]> conf(int p) {
                    this._ptr = p;
                    return this;
                }
            }.conf(beginPtr % cols);
        }
    }


    public class ColMapper {
        private final LevelBlock[] col;

        public ColMapper(LevelBlock[] col) {
            this.col = col;
        }

        public void setCell(int y, LevelBlock cell) {
            LevelBlock old = col[y];
            if (old != null)
                disposer.dispose(old);
            col[y] = cell;
        }
    }

    public ColMapper nextCol() {
        added++;
        ptr++;
        if (ptr > cols)
            beginPtr++;
        return new ColMapper(matrix[ptr % cols]);
    }

}
