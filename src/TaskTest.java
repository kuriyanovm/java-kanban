import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TaskTest {

    private static Task task1;
    private static Task task2;


    @BeforeAll
    public static void beforeAll() {
        task1 = new Task("Тестовая задача 1", "Какое-то описание 1");
        task1.setId(1);
        task2 = new Task("Тестовое задача 2", "Какое-то описание 2");
    }


    @Test
    void shouldBeEqualIfIdsAreEqual() {
        task2.setId(1);
        assertEquals(task1, task2, "Задачи с одинаковым id должны быть равны");
    }

    @Test
    void shouldNotBeEqualIfIdsAreDifferent() {
        task2.setId(2);
        assertNotEquals(task1, task2, "Задачи с разным id не должны быть равны");
    }

}