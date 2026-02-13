import controller.StudentController;
import db.StudentDB;
import mapper.StudentMapper;
import model.dao.StudentDao;
import model.dao.StudentDaoImpl;
import model.service.StudentService;
import model.service.StudentServiceImpl;
import view.StudentView;

public class App {
    public static void main(String[] args) {
        StudentView studentView = new StudentView();
        StudentDB studentDB= new StudentDB();
        StudentDao dao = new StudentDaoImpl(studentDB);
        StudentMapper mapper = new StudentMapper();
        StudentService service = new StudentServiceImpl(dao,mapper);
        StudentController controller = new StudentController(studentView,service);
        controller.run();

    }
}