package PhoneBook;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneBookTest {
    @Test
    public void newContactOrAddNumber() {
        PhoneBook book = new PhoneBook();
        book.newContactOrAddNumber("Вася", "+7-12#");
        book.newContactOrAddNumber("Тепя", "8-523#");
        book.newContactOrAddNumber("Sema", "8-5*3-1#");
        assertEquals("{8-523#=Тепя, 8-5*3-1#=Sema, +7-12#=Вася}", book.toString());
        try {
            book.newContactOrAddNumber("", "8-5*3#");
            fail("Exception expected");
        } catch (NullPointerException e) {
            assertEquals("Имя контакта введено не корректно!", e.getMessage());
        }
    }

    @Test
    public void numberIsCorrect() {
        PhoneBook book = new PhoneBook();
        try {
            book.newContactOrAddNumber("MAMA", null);
            fail("Exception expected");
        } catch (NullPointerException e) {
            assertEquals("Номер введён не корректно!", e.getMessage());
        }
        book.newContactOrAddNumber("Вася", "+7-12#");
        assertTrue(true);
        try {
            book.newContactOrAddNumber("MAMA2", "");
            fail("Exception expected");
        } catch (NullPointerException e) {
            assertEquals("Номер введён не корректно!", e.getMessage());
        }
        try {
            book.newContactOrAddNumber("MAMA", "     ");
            fail("Exception expected");
        } catch (NullPointerException e) {
            assertEquals("Номер введён не корректно!", e.getMessage());
        }
    }

    @Test
    public void personIsCorrect() {
        PhoneBook book = new PhoneBook();
        try {
            book.newContactOrAddNumber(null, "8-12#");
            fail("Exception expected");
        } catch (NullPointerException e) {
            assertEquals("Имя контакта введено не корректно!", e.getMessage());
        }
        book.newContactOrAddNumber("Вася 3", "+7-142#");
        assertTrue(true);
        try {
            book.newContactOrAddNumber("", "8-15#");
            fail("Exception expected");
        } catch (NullPointerException e) {
            assertEquals("Имя контакта введено не корректно!", e.getMessage());
        }
        try {
            book.newContactOrAddNumber(" ", "8-14#");
            fail("Exception expected");
        } catch (NullPointerException e) {
            assertEquals("Имя контакта введено не корректно!", e.getMessage());
        }
    }

    @Test
    public void testOfNumber() {
        PhoneBook book = new PhoneBook();
        try {
            book.newContactOrAddNumber("Alena", "8-5---*3-1#");
            fail("Exception 1 expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Неверный формат номера!", e.getMessage());
        }
        book.newContactOrAddNumber("Ilia", "8-57#");
        assertTrue(true);
        try {
            book.newContactOrAddNumber("Seva", "8-57#");
            fail("Exception 2 expected");
        } catch (IllegalArgumentException e) {
            assertEquals("Такой номер уже есть у другого контакта!", e.getMessage());
        }
        try {
            book.newContactOrAddNumber("Vasya", null);
            fail("Exception 3 expected");
        } catch (NullPointerException e) {
            assertEquals("Номер введён не корректно!", e.getMessage());
        }
    }

    @Test
    public void deleteNumber() {
        PhoneBook book = new PhoneBook();
        book.newContactOrAddNumber("Вася", "+7-12#");
        book.newContactOrAddNumber("Тепя", "8-523#");
        book.deleteNumber("+7-12#");
        assertEquals("{8-523#=Тепя}", book.toString());
        book.deleteNumber(null);
        assertEquals("{8-523#=Тепя}", book.toString());

    }

    @Test
    public void deletePerson() {
        PhoneBook book = new PhoneBook();
        book.newContactOrAddNumber("Вася", "+7-12#");
        book.newContactOrAddNumber("Тепя", "8-523#");
        book.deletePerson("Вася");
        assertEquals("{8-523#=Тепя}", book.toString());
    }

    @Test
    public void findPerson() {
        PhoneBook book = new PhoneBook();
        book.newContactOrAddNumber("Вася", "+7-12#");
        book.newContactOrAddNumber("Тепя", "8-523#");
        assertEquals("Тепя", book.findPerson("8-523#"));
        assertEquals("Вася", book.findPerson("+7-12#"));
        assertEquals(null, book.findPerson("8-8#"));

    }


    @Test
    public void findNumber() {
        PhoneBook book = new PhoneBook();
        book.newContactOrAddNumber("Вася", "+7-12#");
        book.newContactOrAddNumber("Вася", "+7-123#");
        book.newContactOrAddNumber("Вася", "8-*64-5#");
        book.newContactOrAddNumber("Тепя", "+7-1#");
        assertArrayEquals(new String[]{"+7-1#"}, book.findNumber("Тепя").toArray());
        assertArrayEquals(new String[]{"+7-123#", "+7-12#", "8-*64-5#"}, book.findNumber("Вася").toArray());
    }
}