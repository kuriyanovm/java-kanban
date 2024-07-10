
public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        // Создание задач
        Task task1 = new Task("Уборка", "Задача для уборки");
        Task task2 = new Task("Стирка", "Задача для стирки");
        taskManager.create(task1);
        taskManager.create(task2);

        // Создание эпиков и подзадач
        Epic epic1 = new Epic("Переезд", "Переезд в другую квартиру");
        taskManager.create(epic1);

        Subtask subtask1 = new Subtask("Собрать коробки", "Сбор коробок", epic1.getId());
        Subtask subtask2 = new Subtask("Упаковать кошку", "Упаковка кошки", epic1.getId());

        taskManager.create(subtask1);
        taskManager.create(subtask2);

        Epic epic2 = new Epic("Отпуск", "Долгожданный отпуск");
        taskManager.create(epic2);
        Subtask subtask3 = new Subtask("Купить билеты", "Покупка билетов", epic2.getId());
        Subtask subtask4 = new Subtask("Забронировать отель", "Бронируем отель", epic2.getId());

        taskManager.create(subtask3);
        taskManager.create(subtask4);

        // Распечатка всех задач, эпиков и подзадач
        System.out.println("Задачи: " + taskManager.getAllTasks());
        System.out.println("Эпики: " + taskManager.getAllEpics());
        System.out.println("Подзадачи: " + taskManager.getAllSubtasks());
        System.out.println("Подзадачи для эпика " +
                epic2.getId() + " : " + taskManager.getSubtasksByEpicId(epic2.getId()));


        // Изменение статусов задач и подзадач
        task1.setStatus(Status.IN_PROGRESS);
        taskManager.update(task1);
        subtask1.setStatus(Status.DONE);
        taskManager.update(subtask1);

        // Проверка обновленных статусов
        System.out.println("Обновленные задачи: " + taskManager.getAllTasks());
        System.out.println("Обновленные эпики: " + taskManager.getAllEpics());
        System.out.println("Обновленные подзадачи: " + taskManager.getAllSubtasks());

        // Удаление задачи, эпика и подзадачи
        taskManager.deleteTask(task1.getId());
        taskManager.deleteEpic(epic1.getId());
        taskManager.deleteSubtask(subtask3.getId());

        // Проверка после удаления
        System.out.println("Остались задачи: " + taskManager.getAllTasks());
        System.out.println("Остались эпики: " + taskManager.getAllEpics());
        System.out.println("Остались подзадачи: " + taskManager.getAllSubtasks());
    }
}


