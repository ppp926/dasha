package com.example.sorting.controller;

import com.example.sorting.model.ExperimentResult;
import com.example.sorting.service.SortingService;
import com.example.sorting.service.SortingAlgorithm;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class ExperimentController {

    private final SortingService sortingService;

    public ExperimentController(SortingService sortingService) {
        this.sortingService = sortingService;
    }

    @GetMapping("/experiment")
    public ExperimentResult runExperiment() {
        int[] sizes = {100, 1000, 5000};
        int copies = 3; // количество повторов для усреднения
        Random rand = new Random();

        // Список алгоритмов
        Map<String, SortingAlgorithm> algorithms = new LinkedHashMap<>();
        algorithms.put("Bubble Sort", sortingService::bubbleSort);
        algorithms.put("Insertion Sort", sortingService::insertionSort);
        algorithms.put("Selection Sort", sortingService::selectionSort);
        algorithms.put("Merge Sort", sortingService::mergeSort);
        algorithms.put("Quick Sort", sortingService::quickSort);
        algorithms.put("Built-in sort", Arrays::sort); // встроенная сортировка

        Map<String, double[]> results = new LinkedHashMap<>();

        for (Map.Entry<String, SortingAlgorithm> entry : algorithms.entrySet()) {
            String name = entry.getKey();
            SortingAlgorithm algo = entry.getValue();
            double[] times = new double[sizes.length];

            for (int i = 0; i < sizes.length; i++) {
                int size = sizes[i];
                int[] arr = rand.ints(size, 0, 10001).toArray();
                double time = sortingService.measureTime(algo, arr, copies);
                times[i] = time;
            }
            results.put(name, times);
        }

        return new ExperimentResult(results, sizes);
    }
}