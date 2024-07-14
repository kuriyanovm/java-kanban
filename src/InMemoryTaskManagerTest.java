import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private static TaskManager manager;


    @BeforeEach //создаем чистый экземпляр менеджера задач, чтобы не волноваться что данные сохраняются между тестами
    public void beforeEach() {
        manager = Managers.getDefault();
    }
    @Test
    void canCreateAndFindTask() {

        Task task = new Task("Новая задача", "Какое-то описание");
        manager.create(task);
        Task foundTask = manager.getTask(task.getId());
        assertNotNull(foundTask, "Задачу можно найти по id");
        assertEquals(task, foundTask, "Найденная задача должна быть равна созданной");
    }


    @Test
    void canCreateAndFindEpic() {
        Epic epic = new Epic("Новый эпик", "Какое-то описание");
        manager.create(epic);
        Epic foundEpic = manager.getEpic(epic.getId());
        assertNotNull(foundEpic, "Эпик можно найти по id");
        assertEquals(epic, foundEpic, "Найденный эпик должен быть равен созданному");
    }

    @Test
    void canCreateAndFindSubtask() {
        Epic epic = new Epic("Новый эпик", "Какое-то описание");
        manager.create(epic);

        Subtask subtask = new Subtask("Новая подзадача", "Какое-то описание", epic.getId());
        manager.create(subtask);

        Subtask foundSubtask = manager.getSubtask(subtask.getId());
        assertNotNull(foundSubtask, "Подзадачу можно найти по id");
        assertEquals(subtask, foundSubtask, "Найденная подзадача должна быть равна созданной");
    }

    @Test
    void shouldNotConflictGeneratedAndSetIds() {
        Task task1 = new Task("Задача 1", "Какое-то описание");
        task1.setId(1);
        Task task2 = new Task("Задача 2", "Какое-то описание");
        task2.setId(2);

        manager.create(task1);
        manager.create(task2);

        assertEquals(2, manager.getAllTasks().size(), "Должно быть две задачи с разными id");
    }

    @Test
    void shouldNotUseAlreadyExistingIdWhenCreate() {
        Task task1 = new Task("Задача 1", "Какое-то описание");
        manager.create(task1);

        // Ручное присвоение нового id, имитируя потенциальный конфликт
        int manualId = task1.getId() + 1;
        task1.setId(manualId);
        manager.create(task1);

        // Убедиться, что следующий автоматически сгенерированный id не совпадает с вручную установленным id
        Task task2 = new Task("Задача 2", "Какое-то описание");
        manager.create(task2);

        assertNotEquals(manualId, task2.getId(), "Автоматически сгенерированный id не должен совпадать с вручную установленным id");
    }

    @Test
    void shouldNotChangeTaskFieldsAfterAddingToManager() {
        Task task = new Task("Задача 3", "Какое-то описание");
        manager.create(task);
        Task createdTask = manager.getTask(task.getId());
        assertEquals(task.getName(), createdTask.getName(), "Имя задачи не должно меняться");
        assertEquals(task.getDescription(), createdTask.getDescription(), "Описание задачи не должно меняться");
        assertEquals(task.getStatus(), createdTask.getStatus(), "Статус задачи не должен меняться");
    }

    @Test
    void testHistoryManagerStoresPreviousVersions() {
        Task task = new Task("Новая задача 4", "Какое-то описание");
        manager.create(task);
        manager.getTask(task.getId());

        List<Task> history = manager.getHistory();
        assertNotNull(history, "История не должна быть null");
        assertEquals(1, history.size(), "История должна содержать одну задачу");

        Task taskFromHistory = history.get(0);
        assertEquals(task.getId(), taskFromHistory.getId(), "Должны совпадать id");
        assertEquals(task.getName(), taskFromHistory.getName(), "Должно совпадать имя задачи");
        assertEquals(task.getDescription(), taskFromHistory.getDescription(), "Должно совпадать описание задачи");
        assertEquals(task.getStatus(), taskFromHistory.getStatus(), "Должнен совпадать статус задачи");
    }

    @Test
    void shouldDeleteTask() {
        Task task = new Task("Задача 1", "Описание задачи 1");
        manager.create(task);
        assertNotNull(manager.getTask(task.getId()), "Задача должна быть создана");
        manager.deleteTask(task.getId());
        assertNull(manager.getTask(task.getId()), "Задача должна быть удалена");
        assertTrue(manager.getAllTasks().isEmpty(), "Список задач должен быть пуст");
    }

    @Test
    void shouldDeleteEpicAndSubtasks() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        manager.create(epic);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", epic.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", epic.getId());
        manager.create(subtask1);
        manager.create(subtask2);

        assertNotNull(manager.getEpic(epic.getId()), "Эпик должен быть создан");
        assertNotNull(manager.getSubtask(subtask1.getId()), "Подзадача 1 должна быть создана");
        assertNotNull(manager.getSubtask(subtask2.getId()), "Подзадача 2 должна быть создана");

        manager.deleteEpic(epic.getId());

        assertNull(manager.getEpic(epic.getId()), "Эпик должен быть удален");
        assertNull(manager.getSubtask(subtask1.getId()), "Подзадача 1 должна быть удалена");
        assertNull(manager.getSubtask(subtask2.getId()), "Подзадача 2 должна быть удалена");
        assertTrue(manager.getAllEpics().isEmpty(), "Список эпиков должен быть пуст");
        assertTrue(manager.getAllSubtasks().isEmpty(), "Список подзадач должен быть пуст");
    }

    @Test
    void shouldDeleteSubtask() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        manager.create(epic);

        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", epic.getId());
        manager.create(subtask);

        assertNotNull(manager.getSubtask(subtask.getId()), "Подзадача должна быть создана");

        manager.deleteSubtask(subtask.getId());

        assertNull(manager.getSubtask(subtask.getId()), "Подзадача должна быть удалена");
        assertTrue(manager.getAllSubtasks().isEmpty(), "Список подзадач должен быть пуст");
    }

    @Test
    void shouldUpdateEpicStatusToDoneWhenAllSubtasksAreDone() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        manager.create(epic);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", epic.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", epic.getId());
        manager.create(subtask1);
        manager.create(subtask2);

        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.DONE);

        manager.update(subtask1);
        manager.update(subtask2);

        Epic updatedEpic = manager.getEpic(epic.getId());

        assertNotNull(updatedEpic, "Эпик должен быть найден");
        assertEquals(Status.DONE, updatedEpic.getStatus(), "Статус эпика должен быть DONE, когда все подзадачи выполнены");
    }

    @Test
    void shouldDeleteAllTasks() {
        Task task = new Task("Задача 1", "Описание задачи 1");
        manager.create(task);

        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        manager.create(epic);

        Subtask subtask = new Subtask("Подзадача 1", "Описание подзадачи 1", epic.getId());
        manager.create(subtask);

        manager.deleteAllTasks();

        assertTrue(manager.getAllTasks().isEmpty(), "Все задачи должны быть удалены");
        assertTrue(manager.getAllEpics().isEmpty(), "Все эпики должны быть удалены");
        assertTrue(manager.getAllSubtasks().isEmpty(), "Все подзадачи должны быть удалены");
    }

    @Test
    void shouldReturnAllTasks() {
        Task task1 = new Task("Задача 1", "Описание задачи 1");
        Task task2 = new Task("Задача 2", "Описание задачи 2");
        manager.create(task1);
        manager.create(task2);

        ArrayList<Task> allTasks = manager.getAllTasks();
        assertEquals(2, allTasks.size(), "Должны возвращаться все задачи");
        assertTrue(allTasks.contains(task1), "Должна возвращаться задача 1");
        assertTrue(allTasks.contains(task2), "Должна возвращаться задача 2");
    }

    @Test
    void shouldReturnAllEpics() {
        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        manager.create(epic1);
        manager.create(epic2);

        ArrayList<Epic> allEpics = manager.getAllEpics();
        assertEquals(2, allEpics.size(), "Должны возвращаться все эпики");
        assertTrue(allEpics.contains(epic1), "Должен возвращаться эпик 1");
        assertTrue(allEpics.contains(epic2), "Должен возвращаться эпик 2");
    }

    @Test
    void shouldReturnAllSubtasks() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        manager.create(epic);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", epic.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", epic.getId());
        manager.create(subtask1);
        manager.create(subtask2);

        ArrayList<Subtask> allSubtasks = manager.getAllSubtasks();
        assertEquals(2, allSubtasks.size(), "Должны возвращаться все подзадачи");
        assertTrue(allSubtasks.contains(subtask1), "Должна возвращаться подзадача 1");
        assertTrue(allSubtasks.contains(subtask2), "Должна возвращаться подзадача 2");
    }
    @Test
    void shouldReturnSubtasksByEpicId() {
        Epic epic = new Epic("Эпик 1", "Описание эпика 1");
        manager.create(epic);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", epic.getId());
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", epic.getId());
        manager.create(subtask1);
        manager.create(subtask2);

        ArrayList<Subtask> subtasksByEpic = manager.getSubtasksByEpicId(epic.getId());
        assertEquals(2, subtasksByEpic.size(), "Должны возвращаться все подзадачи эпика");
        assertTrue(subtasksByEpic.contains(subtask1), "Должна возвращаться подзадача 1 эпика");
        assertTrue(subtasksByEpic.contains(subtask2), "Должна возвращаться подзадача 2 эпика");
    }



}