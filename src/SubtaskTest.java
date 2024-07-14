import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    private static Subtask subtask1;
    private static Subtask subtask2;
    private static Epic epic;

    @BeforeEach ///создаем чистые экземпляры объектов, чтобы не волноваться что данные сохраняются между тестами
    public  void beforeEach() {
        epic = new Epic("Эпик", "Какое-то описание");
        epic.setId(3);
        subtask1 = new Subtask("Тестовая подзадача 1", "Какое-то описание 1", 3);
        subtask1.setId(1);
        subtask2 = new Subtask("Тестовая подзадача 2", "Какое-то описание 2", 3);
    }


    @Test
    void shouldBeEqualIfIdsAreEqual () {
        subtask2.setId(1);
        assertEquals(subtask1, subtask2, "Подзадачи с одинаковым id должны быть равны");
    }

    @Test
    void shouldNotBeEqualIfIdsAreDifferent () {
        subtask2.setId(2);
        assertNotEquals(subtask1, subtask2, "Подзадачи с разным id не должны быть равны");
    }

    @Test
    void shouldNotAddItselfAsEpic() {
        subtask1.setId(3);
        assertFalse(epic.getSubtaskIds().contains(epic.getId()), "Объект Epic нельзя добавить в самого себя в виде подзадачи");
    }




}