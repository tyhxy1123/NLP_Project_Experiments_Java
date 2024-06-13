package de.tu_dresden.models;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FakeMatrixNoTimeToDelete {

    private Map<Integer, Map<Integer, Set<String>>> matrix;
    private int dimension;
    private double[][] probs;

    public FakeMatrixNoTimeToDelete(int num) {
        MatrixCell[][] cells;
        dimension = num;
        matrix = new HashMap<>();
        probs = new double[num][];
        for (int i = 0; i < num; i++) {
            matrix.put(i, new HashMap<>());
            probs[i] = new double[num - i];
            for (int j = 0; j < num - i; j++) {
                matrix.get(i).put(j, new HashSet<>());
            }
        }
    }


    public void addTokenAndProb(int row, int column, String token, double prob) {
        probs[row][column] = prob;
        Map<Integer, Set<String>> innerMap = matrix.get(row);
        Set<String> innerSet = innerMap.get(column);
        innerSet.add(token);
    }

    public void addToken(int row, int column, String token) {
        matrix.get(row).get(column).add(token);

    }

    public void addProb(int row, int column, double prob) {
        probs[row][column] = prob;

    }

    public Set<String> getToken(int row, int column) {
        return matrix.get(row).get(column);
    }

    public double getProb(int row, int column) {
        return probs[row][column];
    }

}
