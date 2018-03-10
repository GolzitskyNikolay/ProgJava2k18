package PhoneBook;

import java.util.*;

public class PhoneBook {

    private Map<String, String> phoneBookByNum = new HashMap<>();

    /**
     * Create a new contact or add a number to the contact.
     * @param person - name of a contact.
     * @param number - number of a contact.
     */
    public void newContactOrAddNumber(String person, String number) {
        if (testOfNumber(number) && personIsCorrect(person)) phoneBookByNum.put(number, person);
    }

    /**
     * The number must match the following pattern:
     * "+7" or "8" then "-". Then can be "*" or digit. At the end of the number must be "#".
     * After each 3 symbols must be "-", except for the case where after 3 symbols is inserted "#".
     * The number must contain at least 1 digit or "*".
     * For example: +7-2*1-345# or 8-111-7# or 8-9#.
     * @return true, if the number is correct and not null.
     * @throws IllegalArgumentException if the format of the number isn't correct.
     */
    private boolean testOfNumber(String number) {
        if (numberIsCorrect(number) && phoneBookByNum.containsKey(number)) {
            throw new IllegalArgumentException("Такой номер уже есть у другого контакта!");
        }
        if (!number.matches("(\\+7|8)-((\\d|\\*)|(\\d|\\*){2}|(\\d|\\*){3})" +
                "(-((\\d|\\*)|(\\d|\\*){2}|(\\d|\\*){3}))?#")) {
            throw new IllegalArgumentException("Неверный формат номера!");
        }
        return true;
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
        String result = null;
        if (numberIsCorrect(number)) {
            result = phoneBookByNum.get(number);
        }
        return result;
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
        return numbers;
    }

    /**
     * @param number can't have a null.
     * @return true, if number is a correct.
     */
    private boolean numberIsCorrect(String number) {
        if (number == null || number.matches(" *"))
            throw new NullPointerException("Номер введён не корректно!");
        return true;
    }

    /**
     * @param person can't have a null.
     * @return true, if person is a correct.
     */
    private boolean personIsCorrect(String person) {
        if (person == null || person.matches(" *"))
            throw new NullPointerException("Имя контакта введено не корректно!");
        return true;
    }

    /**
     * @return a string containing numbers and contact names.
     */
    @Override
    public String toString() {
        return phoneBookByNum.toString();
    }
}
