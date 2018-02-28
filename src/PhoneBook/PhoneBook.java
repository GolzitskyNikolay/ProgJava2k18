package PhoneBook;

import java.util.*;


public class PhoneBook {

    private Map<String, String> phoneBookByNum = new HashMap<>();

    /**
     * @param person - name of a contact.
     * @param number - number of a contact.
     * @throws IllegalArgumentException if the format of the number isn't correct.
     * Create a new contact or add a number to the contact.
     */
    public void newContact(String person, String number) {
        if (!phoneBookByNum.containsKey(number)) {
            if (!number.matches("(\\+7|8)-((\\d|\\*)|(\\d|\\*){2}|(\\d|\\*){3})" +
                    "(-((\\d|\\*)|(\\d|\\*){2}|(\\d|\\*){3}))?#")) {
                throw new IllegalArgumentException("Неверный формат номера!");
            }
            phoneBookByNum.put(number, person);
        } else System.out.println("Такой номер уже есть у другого контакта!");
    }

    /**
     * Delete the number of your contact.
     */

    public void deleteNumber(String number) {
        phoneBookByNum.remove(number);
    }

    /**
     * Delete your contact.
     */

    public void deletePerson(String person) {
        phoneBookByNum.entrySet().removeIf(entry -> entry.getValue().equals(person));
    }

    /**
     * Searching for your contact by number.
     *
     * @return name of your contact.
     */

    public String findPerson(String number) {
        return phoneBookByNum.get(number);
    }

    /**
     * Searching for number(s) by name of your contact.
     *
     * @return number(s) of your contact.
     */

    public ArrayList<String> findNumber(String person) {
        ArrayList<String> numbers = new ArrayList<>();
        phoneBookByNum.entrySet().stream().filter(e -> e.getValue().
                equals(person)).forEach(e -> numbers.add(e.getKey()));
        Collections.sort(numbers);
        return numbers;
    }

    /**
     * @return a string containing numbers and contact names.
     */

    @Override
    public String toString() {
        return phoneBookByNum.toString();
    }
}