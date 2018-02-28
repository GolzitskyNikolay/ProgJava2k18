package PhoneBook;

import java.util.*;


public class PhoneBook {

    private Map<String, String> phoneBookByNum = new HashMap<>();
    /*
    
     */
    public void newContact(String person, String number) {
        try {
            if (!phoneBookByNum.containsKey(number)) {
                if (!number.matches("(\\+7|8)-((\\d|\\*)|(\\d|\\*){2}|(\\d|\\*){3})" +
                        "(-((\\d|\\*)|(\\d|\\*){2}|(\\d|\\*){3}))?#")) {
                    throw new IllegalArgumentException();
                }
                phoneBookByNum.put(number, person);
            } else System.out.println("Такой номер уже есть у другого контакта!");
        } catch (IllegalArgumentException e) {
            System.out.println("Неверный формат номера!");
        }
    }

    public void deleteNumber(String number) {
        phoneBookByNum.remove(number);
    }

    public void deletePerson(String person) {
        phoneBookByNum.entrySet().removeIf(entry -> entry.getValue().equals(person));
    }


    public String findPerson(String number) {
        return phoneBookByNum.get(number);
    }

    public ArrayList<String> findNumber(String person) {
        ArrayList<String> numbers = new ArrayList<>();
        phoneBookByNum.entrySet().stream().filter(e -> e.getValue().
                equals(person)).forEach(e -> numbers.add(e.getKey()));
        Collections.sort(numbers);
        return numbers;
    }


    @Override
    public String toString() {
        return phoneBookByNum.toString();
    }
}



