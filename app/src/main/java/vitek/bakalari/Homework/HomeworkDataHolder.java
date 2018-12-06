package vitek.bakalari.Homework;

import java.util.ArrayList;

import vitek.bakalari.Homework.Adapters.DoneHomeworkAdapter;
import vitek.bakalari.Homework.Adapters.ToDoHomeworkAdapter;

public class HomeworkDataHolder {
    private static HomeworkDataHolder instance;
    private final ArrayList<Homework> dataSet;
    private final ArrayList<Homework> doneHomework;
    private final ArrayList<Homework> toDoHomework;
    private final ArrayList<Homework> archiveHomework;
    private DoneHomeworkAdapter mDoneHomeworkAdapter;
    private ToDoHomeworkAdapter mToDoHomeworkAdapter;

    private HomeworkDataHolder(ArrayList<Homework> dataSet) {
        this.dataSet = dataSet;
        doneHomework = new ArrayList<Homework>();
        toDoHomework = new ArrayList<Homework>();
        archiveHomework = new ArrayList<Homework>();
        for (Homework homework : dataSet) {
            if (!homework.getDone()) toDoHomework.add(homework);
        }
        for (Homework homework : dataSet) {
            if (homework.getDone() && !homework.getInArchive()) doneHomework.add(homework);
        }
        for (Homework homework : dataSet) {
            if (homework.getInArchive()) archiveHomework.add(homework);
        }
    }

    public static void init(ArrayList<Homework> dataSet) {
        if (instance == null) {
            instance = new HomeworkDataHolder(dataSet);
        }
    }

    public static HomeworkDataHolder getInstance() {
        return instance;
    }

    public ArrayList<Homework> getDataSet() {
        return dataSet;
    }

    public Homework getDoneItem(int posittion) {
        return doneHomework.get(posittion);
    }

    public Homework getArchivedItem(int position) {
        return archiveHomework.get(position);
    }

    public Homework getToDoItem(int position) {
        return toDoHomework.get(position);
    }

    public void addArchivedItem(Homework homework) {
        archiveHomework.add(homework);
    }

    public void removeArchivedItem(int position) {
        archiveHomework.remove(position);
    }

    public void addDoneItem(Homework homework) {
        doneHomework.add(homework);
    }

    public void addDoneItem(int position, Homework homework){
        doneHomework.add(position, homework);
    }

    public void removeDoneItem(int position) {
        doneHomework.remove(position);
    }

    public void addToDoItem(Homework homework) {
        toDoHomework.add(homework);
    }

    public void removeToDoItem(int position) {
        toDoHomework.remove(position);
    }

    public ArrayList<Homework> getAllDone () {
        return doneHomework;
    }

    public ArrayList<Homework> getAllToDo () {
        return toDoHomework;
    }

    public ArrayList<Homework> getAllArchived () {
        return archiveHomework;
    }

    public DoneHomeworkAdapter getDoneHomeworkAdapter() {
        return mDoneHomeworkAdapter;
    }

    public void setDoneHomeworkAdapter(DoneHomeworkAdapter doneHomeworkAdapter) {
        mDoneHomeworkAdapter = doneHomeworkAdapter;
    }

    public ToDoHomeworkAdapter getToDoHomeworkAdapter() {
        return mToDoHomeworkAdapter;
    }

    public void setToDoHomeworkAdapter(ToDoHomeworkAdapter toDoHomeworkAdapter) {
        mToDoHomeworkAdapter = toDoHomeworkAdapter;
    }
}
