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
        if (numberIsCorrect(number) && personIsCorrect(person)) {
            number = number.replace("-", "");
            phoneBookByNum.put(number, person);
        }
    }

    /**
     * Your number must have at least 1 digit. Before this digit,
     * your number can contain a plus and a digit. Also you can use the special format of the number:
     * "*"(1 or more digit)"#".
     * @param number can't have a null.
     * @return true, if number is a correct.
     * @throws IllegalArgumentException if the format of the number isn't correct
     * or other contact already has this number.
     */
    private boolean numberIsCorrect(String number) {
        if (number == null || !number.matches(
                "((\\+\\d)?(-)*\\d((\\d)*(-)*)*|(\\*)(-)*(\\d)((\\d)*(-)*)*(#))")) {
            throw new IllegalArgumentException("Invalid number format!");
        }
        if (phoneBookByNum.containsKey(number)) {
            throw new IllegalArgumentException("This number already has another contact!");
        }
        return true;
    }

    /**
     * @param person can't have a null.
     * @return true, if person is a correct.
     * @throws IllegalArgumentException if the format of the contact isn't correct.
     */
    private boolean personIsCorrect(String person) {
        if (person == null || person.matches(" *")) {
            throw new IllegalArgumentException("Invalid contact name format!");
        }
        return true;
    }

    /**
     * Delete the number of your contact.
     * @return true, if number is deleted.
     */
    public boolean deleteNumber(String number) {
        if (phoneBookByNum.containsKey(number)) {
            phoneBookByNum.remove(number);
            return true;
        }
        return false;

    }

    /**
     * Delete your contact.
     * @return true, if person is deleted.
     */
    public boolean deletePerson(String person) {
        if (phoneBookByNum.containsValue(person)) {
            phoneBookByNum.entrySet().removeIf(entry -> entry.getValue().equals(person));
            return true;
        }
        return false;
    }

    /**
     * Searching for your contact by number.
     * @return name of your contact.
     */
    public Optional<String> findPerson(String number) {
        if (phoneBookByNum.containsKey(number)) {
            return Optional.ofNullable(phoneBookByNum.get(number));
        }
        return Optional.empty();
    }

    /**
     * Searching for number(s) by name of your contact.
     * @return number(s) of your contact.
     */
    public ArrayList<String> findNumber(String person) {
        ArrayList<String> numbers = new ArrayList<>();
        phoneBookByNum.entrySet().stream().filter(e -> e.getValue().
                equals(person)).forEach(e -> numbers.add(e.getKey()));
        return numbers;
    }
}