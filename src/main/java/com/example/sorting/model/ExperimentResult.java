package com.example.sorting.model;

import java.util.Map;

public class ExperimentResult {
    private Map<String, double[]> times; // ключ - имя алгоритма, значение - массив времён для размеров
    private int[] sizes;

    public ExperimentResult(Map<String, double[]> times, int[] sizes) {
        this.times = times;
        this.sizes = sizes;
    }

    // геттеры и сеттеры
    public Map<String, double[]> getTimes() { return times; }
    public void setTimes(Map<String, double[]> times) { this.times = times; }
    public int[] getSizes() { return sizes; }
    public void setSizes(int[] sizes) { this.sizes = sizes; }
}