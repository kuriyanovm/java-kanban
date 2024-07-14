import java.util.List;

public class Main {
    public static void main(String[] args) {


        TaskManager manager = Managers.getDefault();
        // Создание задач
        Task task1 = new Task("Уборка", "Задача для уборки");
        Task task2 = new Task("Стирка", "Задача для стирки");
        manager.create(task1);
        manager.create(task2);

        // Создание эпиков и подзадач
        Epic epic1 = new Epic("Переезд", "Переезд в другую квартиру");
        manager.create(epic1);

        Subtask subtask1 = new Subtask("Собрать коробки", "Сбор коробок", epic1.getId());
        Subtask subtask2 = new Subtask("Упаковать кошку", "Упаковка кошки", epic1.getId());

        manager.create(subtask1);
        manager.create(subtask2);

        Epic epic2 = new Epic("Отпуск", "Долгожданный отпуск");
        manager.create(epic2);
        Subtask subtask3 = new Subtask("Купить билеты", "Покупка билетов", epic2.getId());
        Subtask subtask4 = new Subtask("Забронировать отель", "Бронируем отель", epic2.getId());

        manager.create(subtask3);
        manager.create(subtask4);

        // Получение задач и проверка истории
        manager.getTask(task1.getId());
        manager.getTask(task2.getId());
        manager.getEpic(epic1.getId());
        manager.getSubtask(subtask1.getId());
        manager.getSubtask(subtask2.getId());

        // Вывод истории задач
        List<Task> history = manager.getHistory();
        System.out.println("История просмотров:");
        for (Task task : history) {
            System.out.println(task);
        }


    }
}


