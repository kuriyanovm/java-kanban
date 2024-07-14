import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    private static Epic epic1;
    private static Epic epic2;


    @BeforeAll
    public static void beforeAll() {
        epic1 = new Epic("Тестовая задача 1", "Какое-то описание 1");
        epic1.setId(1);
        epic2 = new Epic("Тестовое задача 2", "Какое-то описание 2");
    }


    @Test
    void shouldBeEqualIfIdsAreEqual() {
        epic2.setId(1);
        assertEquals(epic1, epic2, "Задачи с одинаковым id должны быть равны");
    }

    @Test
    void shouldNotBeEqualIfIdsAreDifferent() {
        epic2.setId(2);
        assertNotEquals(epic1, epic2, "Задачи с разным id не должны быть равны");
    }

    @Test
    void shouldNotAddItselfAsSubTask() {
        epic1.addSubtaskId(epic1.getId());
        assertFalse(epic1.getSubtaskIds().contains(epic1.getId()), "Объект Epic нельзя добавить в самого себя в виде подзадачи");
    }
}


