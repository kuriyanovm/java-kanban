import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    void create(Task task);

    void create(Epic epic);

    void create(Subtask subtask);

    ArrayList<Task> getAllTasks();

    ArrayList<Epic> getAllEpics();

    ArrayList<Subtask> getAllSubtasks();

    ArrayList<Subtask> getSubtasksByEpicId(int epicId);

    void deleteAllTasks();

    Task getTask(int id);

    Subtask getSubtask(int id);

    Epic getEpic(int id);

    void update(Task task);

    void update(Epic epic);

    void update(Subtask subtask);

    void deleteTask(int id);

    void deleteEpic(int id);

    void deleteSubtask(int id);

    void updateEpicStatus(Epic epic);

    List<Task> getHistory();

    int generateUniqueId();

}
