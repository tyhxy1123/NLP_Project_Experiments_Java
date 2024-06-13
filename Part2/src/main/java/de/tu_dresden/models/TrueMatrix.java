package de.tu_dresden.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TrueMatrix {

    private int dim;
    private List<List<MatrixCell>> cells;

    public TrueMatrix(int dim) {
        this.dim = dim;
        cells = new ArrayList<>();
        for (int i = 0; i < dim; i++) {
            List<MatrixCell> l = new ArrayList<>();
            for (int j = 0; j < dim - i; j++) {
                l.add(new MatrixCell());
            }
            cells.add(l);
        }
    }


    public void addCell(int x, int y, String cellContent) {
        cells.get(x).get(y).add(cellContent);
    }

    public void addAllCell(int x, int y, Set<String> cellContent) {
        cells.get(x).get(y).addAll(cellContent);
    }

    public Set<String> getCellContent(int x, int y) {
        return cells.get(x).get(y).getTokens();
    }


    public int getDimension() {
        return dim;
    }

    public MatrixCell getCell(int x, int y) {
        return cells.get(x).get(y);
    }

    public Map<String, List<String>> getRelations(int x, int y){
        return this.cells.get(x).get(y).getRelations();
    }

    public Map<String, Double> getAppranceProbs(int x, int y){
        return this.cells.get(x).get(y).getTagAppearanceProbs();
    }
}
