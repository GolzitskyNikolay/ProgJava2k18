package PhoneBook;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneBookTest {

    @Test
    public void newContactOrAddNumber() {
        PhoneBook book = new PhoneBook();
        book.newContactOrAddNumber("Вася", "*100#");
        book.newContactOrAddNumber("Вася", "*101#");
        assertEquals(book.findNumber("Вася").toString(), "[*100#, *101#]");
        assertEquals(book.findPerson("*100#").toString(), Optional.of("Вася").toString());
    }

    @TestFactory
    public Collection<DynamicTest> numberIsCorrect_Exception_DynamicTests() {
        PhoneBook book = new PhoneBook();
        book.newContactOrAddNumber("Джованни", "234567");
        List<String> numbers = new ArrayList<>(Arrays.asList("88j", "99#", "*111111", null, "", " ", "234567"));
        Collection<DynamicTest> dynamicTests = new ArrayList<>();
        for (int i = 0; i < numbers.size(); i++) {
            String num = numbers.get(i);
            Executable exec = () -> assertThrows(IllegalArgumentException.class,
                    () -> book.newContactOrAddNumber("name", num));
            String testName = " Test " + num;
            DynamicTest dTest = DynamicTest.dynamicTest(testName, exec);
            dynamicTests.add(dTest);
        }
        return dynamicTests;
    }

    @Test
    public void personIsCorrect_InvalidContactNameFormat_1() {
        PhoneBook book = new PhoneBook();
        try {
            book.newContactOrAddNumber("", "8-15987658-999");
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid contact name format!", e.getMessage());
        }
    }

    @Test
    public void personIsCorrect_InvalidContactNameFormat_2() {
        PhoneBook book = new PhoneBook();
        try {
            book.newContactOrAddNumber(null, "8-146745476");
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid contact name format!", e.getMessage());
        }
    }

    @Test
    public void numberIsCorrect_InvalidNumberFormat_1() {
        PhoneBook book = new PhoneBook();
        try {
            book.newContactOrAddNumber("Schaverma", "8-5---*3-1#");
            fail("Exception 1 expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid number format!", e.getMessage());
        }
    }

    @Test
    public void numberIsCorrect_InvalidNumberFormat_2() {
        PhoneBook book = new PhoneBook();
        try {
            book.newContactOrAddNumber("Mizim", "");
            fail("Exception 1 expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid number format!", e.getMessage());
        }
    }

    @Test
    public void numberIsCorrect_InvalidNumberFormat_3() {
        PhoneBook book = new PhoneBook();
        try {
            book.newContactOrAddNumber("Glicin", null);
            fail("Exception 1 expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid number format!", e.getMessage());
        }
    }

    @Test
    public void numberIsCorrect_ThisNumberAlreadyHasAnotherContact() {
        PhoneBook book = new PhoneBook();
        book.newContactOrAddNumber("Noshpa", "809991");
        try {
            book.newContactOrAddNumber("Korvalol", "809991");
            fail("Exception 2 expected");
        } catch (IllegalArgumentException e) {
            assertEquals("This number already has another contact!", e.getMessage());
        }
    }

    @Test
    public void deleteNumber_True() {
        PhoneBook book = new PhoneBook();
        book.newContactOrAddNumber("Вася", "+7-12");
        book.newContactOrAddNumber("Тепя", "8-523");
        assertTrue(book.deleteNumber("+712"));
        assertFalse(book.deleteNumber("+712"));
    }

    @Test
    public void deleteNumber_False_1() {
        PhoneBook book = new PhoneBook();
        book.newContactOrAddNumber("Тепя", "8-523");
        assertFalse(book.deleteNumber(null));
    }


    @Test
    public void deleteNumber_False_2() {
        PhoneBook book = new PhoneBook();
        book.newContactOrAddNumber("Тепя", "8-523");
        assertFalse(book.deleteNumber("888888"));
    }

    @Test
    public void deletePerson_True() {
        PhoneBook book = new PhoneBook();
        book.newContactOrAddNumber("Вася", "+7-12");
        book.newContactOrAddNumber("Тепя", "8-523");
        assertTrue(book.deletePerson("Вася"));
    }

    @Test
    public void deletePerson_False_1() {
        PhoneBook book = new PhoneBook();
        book.newContactOrAddNumber("Тепя", "8-523");
        assertFalse(book.deletePerson("Вася"));
    }

    @Test
    public void deletePerson_False_2() {
        PhoneBook book = new PhoneBook();
        book.newContactOrAddNumber("Тепя", "8-523");
        assertFalse(book.deletePerson(null));
    }

    @Test
    public void findPerson_Ok() {
        PhoneBook book = new PhoneBook();
        book.newContactOrAddNumber("Вася", "+7-12");
        book.newContactOrAddNumber("Тепя", "8-523");
        assertEquals(Optional.of("Тепя"), book.findPerson("8523"));
        assertEquals(Optional.of("Вася"), book.findPerson("+712"));
    }

    @Test
    public void findPerson_Empty() {
        PhoneBook book = new PhoneBook();
        book.newContactOrAddNumber("Вася", "+7-12");
        assertEquals(Optional.empty(), book.findPerson("8-8"));
    }

    @Test
    public void findPerson_Null() {
        PhoneBook book = new PhoneBook();
        book.newContactOrAddNumber("Вася", "+7-12");
        assertEquals(Optional.empty(), book.findPerson(null));
    }

    @Test
    public void findNumber() {
        PhoneBook book = new PhoneBook();
        book.newContactOrAddNumber("Вася", "+7-12");
        book.newContactOrAddNumber("Вася", "+7-123");
        book.newContactOrAddNumber("Вася", "8-64-5");
        book.newContactOrAddNumber("Тепя", "+7-1");
        assertArrayEquals(new String[]{"+71"}, book.findNumber("Тепя").toArray());
        assertArrayEquals(new String[]{"+7123", "8645", "+712"}, book.findNumber("Вася").toArray());
    }
}