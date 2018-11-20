package golzitsky.HomeTasks;


import java.util.*;

public class MyHashtable<K, V> extends Hashtable<K, V> {
    private ArrayList<Pair<K, V>> array[];
    private int countOfElements;

    MyHashtable(int size) {
        this.countOfElements = size;
        array = new ArrayList[countOfElements];
    }

    private int hashcode(Object key) {
        return (key.toString().length() + 5) % countOfElements;
    }

    public V put(K key, V value) {
        int hashcode = hashcode(key);
        if (!contains(key)) {
            if (array[hashcode] == null) {
                array[hashcode] = new ArrayList<>();
                array[hashcode].add(new Pair<>(key, value));
                return null;
            } else {
                array[hashcode].add(new Pair<>(key, value));
                return null;
            }
        } else {
            V oldValue = get(key);
            remove(new Pair<>(key, get(key)));
            put(key, value);
            return oldValue;
        }
    }

    public boolean contains(Object key) {
        return (get(key) != null);
    }

    public V get(Object key) {
        int hashcode = hashcode(key);
        if (array[hashcode] != null) {
            for (int i = 0; i < array[hashcode].size(); i++) {
                if (array[hashcode].get(i).getKey() == key) {
                    return array[hashcode].get(i).getValue();
                }
            }
        }
        return null;
    }

    public void print() {
        for (ArrayList<Pair<K, V>> pairs : array) {
            if (pairs != null) {
                for (Pair<K, V> pair : pairs) {
                    System.out.print(pair.getKey() + " <--> ");
                    System.out.println(pair.getValue() + " ");
                }
            }
        }
    }

    public Pair<K, V> remove(Pair<K, V> pair) {
        int hashcode = hashcode(pair.getKey());
        if (array[hashcode] != null && contains(pair.getKey())) {
            for (int i = 0; i < array[hashcode].size(); i++) {
                if (array[hashcode].get(i).getKey() == pair.getKey()) {
                    Pair<K, V> pair1 = array[hashcode].get(i);
                    array[hashcode].remove(i);
                    return pair1;
                }
            }
        }
        return null;
    }
}


class MyHashtable2<K, V> extends Hashtable<K, V> {   //с  открытой адресацией
    private Pair<K, V>[] array;
    private int countOfElements;
    private int countOfElementsInArray = 0;

    MyHashtable2(int size) {
        this.countOfElements = size;
        array = new Pair[countOfElements];
    }

    private int hashcode(Object key) {
        return (key.toString().length() + 5) % countOfElements;
    }

    public V get(Object key) {
        int hashcode = hashcode(key);
        if (array[hashcode] != null) {
            if (array[hashcode].getKey() == key) {
                return array[hashcode].getValue();
            }
        }
        return null;
    }

    void print() {
        System.out.println("\n");
        for (Pair<K, V> pair : array) {
            if (pair != null && !pair.isWasDeleted()) {
                System.out.print("(Хэшкод: " + hashcode(pair.getKey()) + ", ");
                System.out.print("Индекс: " + findIndex(pair) + ", ");
                System.out.print("Пара: " + pair.getKey() + " <--> ");
                System.out.print(pair.getValue() + ") " + "\n");
            }
        }
    }

    private int findIndex(Pair<K, V> pair) {
        int index = hashcode(pair.getKey());
        while (true) {
            if (array[index].getKey().equals(pair.getKey()) &&
                    array[index].getValue().equals(pair.getValue())) return index;
            else {
                index = (index + 1) % countOfElements;
            }
        }
    }

    public boolean contains(Object key) {
        return (get(key) != null);
    }

    public V put(K key, V value) {
        int hashcode = hashcode(key);
        if (array[hashcode] == null || array[hashcode].isWasDeleted()) {
            array[hashcode] = new Pair<>(key, value);
            countOfElementsInArray++;
            return null;
        } else if (array[hashcode].getKey() == key) {
            V oldValue = array[hashcode].getValue();
            array[hashcode] = new Pair<>(key, value);
            countOfElementsInArray++;
            return oldValue;
        } else if (countOfElementsInArray < countOfElements) {
            int index = (hashcode + 1) % countOfElements;
            while (countOfElementsInArray < countOfElements) {
                if (array[index] == null || array[hashcode].getKey() == key || array[hashcode].isWasDeleted()) {
                    V oldValue = get(key);
                    array[index] = new Pair<>(key, value);
                    countOfElementsInArray++;
                    return oldValue;
                } else {
                    index = (index + 1) % countOfElements;
                }
            }
        } else throw new StackOverflowError("Места нет!!! Хэштаблицу надо расширять!!!");
        return value;
    }

    public V remove(Object key) {
        int hashcode = hashcode(key);
        if (array[hashcode] != null) {
            int index = hashcode;
            while (countOfElementsInArray > 0) {
                if (array[index].getKey() == key) {
                    array[index].setWasDeleted(true);
                    return array[index].getValue();
                } else {
                    index = (index + 1) % countOfElements;
                }
            }
        }
        return null;
    }
}

class MyClass {
    public static void main(String[] args) {
        MyHashtable2 myHashtable2 = new MyHashtable2(4);
        Object s = myHashtable2.put(1, "Один");
        myHashtable2.print();
        System.out.println("s = " + s);
        s = myHashtable2.put(2, "Два");
        myHashtable2.print();
        System.out.println("s = " + s);
        s = myHashtable2.put(3, "Три");
        myHashtable2.print();
        System.out.println("s = " + s);
        s = myHashtable2.put("Четыре", 4);
        myHashtable2.print();
        System.out.println("s = " + s);
        s = myHashtable2.put("Пять", 5);
        myHashtable2.print();
        System.out.println("s = " + s);
        s = myHashtable2.remove(1);
        myHashtable2.print();
        System.out.println("s = " + s);
    }
}