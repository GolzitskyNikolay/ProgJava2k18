package golzitsky.HomeTasks;

import java.util.*;


public class Sorts {

    public static void main(String[] args) {
    }

    private static void printlnArray(int[] array) {
        System.out.println(Arrays.toString(array));
    }

    private static void turtleSort(int[] array) {
        int min = array[0];
        int max = array[0];
        for (int e : array) {
            if (e > max) max = e;
            if (e < min) min = e;
        }
        int[] newArray = new int[max - min + 1];
        for (int i : array) {
            newArray[i - min]++;
        }
        int newArrayLength = newArray.length;
        int j = 0;
        for (int i = 0; i < newArrayLength; i++) {
            int count = newArray[i];
            while (count != 0) {
                array[j++] = i + min;
                count--;
            }
        }
    }


    private static void ourBubbleSort(int[] ourArray) {
        for (int k = ourArray.length - 1; k > 0; k--) {
            boolean swap = false;
            for (int i = 0; i < k; i++) {
                if (ourArray[i] >= ourArray[i + 1]) {
                    int element = ourArray[i];
                    ourArray[i] = ourArray[i + 1];
                    ourArray[i + 1] = element;
                    swap = true;
                }
            }
            if (!swap) break;
        }
    }

    private static void selectSort(int[] ourArray) {
        for (int i = 0; i < ourArray.length; i++) {
            int min_element = ourArray[i];
            int min_index = i;
            for (int j = i + 1; j < ourArray.length; j++) {
                if (ourArray[j] < min_element) {
                    min_element = ourArray[j];
                    min_index = j;
                }
            }
            if (i != min_index) {
                int k = ourArray[i];
                ourArray[i] = ourArray[min_index];
                ourArray[min_index] = k;
            }
        }
    }

    private static void insertSort(int[] array) {
        int arrayLength = array.length;
        int i = 1;
        while (i < arrayLength) {
            int i2 = i;
            for (int j = i - 1; j >= 0; j--) {
                if (array[i2] < array[j]) {
                    int g = array[j];
                    array[j] = array[i2];
                    array[i2--] = g;
                }
            }
            i++;
        }
    }

    private static void mergeSort(int[] array) {
        forMergeSort(array, 0, array.length);
    }

    private static void forMergeSort(int[] array, int first, int last) {
        if (last - first <= 1) return;
        int middle = (first + last) / 2;
        forMergeSort(array, first, middle);
        forMergeSort(array, middle, last);
        int[] left = Arrays.copyOfRange(array, first, middle);
        int[] right = Arrays.copyOfRange(array, middle, last);
        int l = 0, r = 0;
        for (int i = first; i < last; i++) {
            if (l < left.length && (r == right.length || left[l] <= right[r])) {
                array[i] = left[l++];
            } else {
                array[i] = right[r++];
            }
        }
    }


    private static void quickSort(int[] array) {
        forQuickSort(array, 0, array.length - 1);
    }

    private static void forQuickSort(int[] array, int min, int max) {
        if (min < max) {
            Random random = new Random();
            int x = array[min + random.nextInt(max - min + 1)];
            int left = min, right = max;
            while (left <= right) {
                while (array[left] < x) {
                    left++;
                }
                while (array[right] > x) {
                    right--;
                }
                if (left <= right) {
                    int temp = array[left];
                    array[left] = array[right];
                    array[right] = temp;
                    left++;
                    right--;
                }
            }
            int border = right;
            forQuickSort(array, min, border);
            forQuickSort(array, border + 1, max);
        }
    }
}
