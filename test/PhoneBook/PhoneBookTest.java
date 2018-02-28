package PhoneBook;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneBookTest {
    @Test
    public void newContact() {
        PhoneBook book = new PhoneBook();
        book.newContact("Вася", "+7-12#");
        book.newContact("Тепя", "8-523#");
        book.newContact("Sema", "8-5*3-1#");
        assertEquals("{8-523#=Тепя, 8-5*3-1#=Sema, +7-12#=Вася}", book.toString());
        try {
            book.newContact("Лена", "8-5---*3-1#");
            fail("Exception expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Неверный формат номера!", e.getMessage());
        }
    }

    @Test
    public void deleteNumber() {
        PhoneBook book = new PhoneBook();
        book.newContact("Вася", "+7-12#");
        book.newContact("Тепя", "8-523#");
        book.deleteNumber("+7-12#");
        assertEquals("{8-523#=Тепя}", book.toString());
    }

    @Test
    public void deletePerson() {
        PhoneBook book = new PhoneBook();
        book.newContact("Вася", "+7-12#");
        book.newContact("Тепя", "8-523#");
        book.deletePerson("Вася");
        assertEquals("{8-523#=Тепя}", book.toString());
    }

    @Test
    public void findPerson() {
        PhoneBook book = new PhoneBook();
        book.newContact("Вася", "+7-12#");
        book.newContact("Тепя", "8-523#");
        assertEquals("Тепя", book.findPerson("8-523#"));
        assertEquals("Вася", book.findPerson("+7-12#"));
        assertEquals(null, book.findPerson("8-8#"));
    }


    @Test
    public void findNumber() {
        PhoneBook book = new PhoneBook();
        book.newContact("Вася", "+7-12#");
        book.newContact("Вася", "+7-123#");
        book.newContact("Вася", "8-*64-5#");
        book.newContact("Тепя", "+7-1#");
        assertArrayEquals(new String[]{"+7-1#"}, book.findNumber("Тепя").toArray());
        assertArrayEquals(new String[]{"+7-12#", "+7-123#", "8-*64-5#"}, book.findNumber("Вася").toArray());
    }
}