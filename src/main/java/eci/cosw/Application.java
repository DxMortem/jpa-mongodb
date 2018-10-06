package eci.cosw;

import com.mongodb.BasicDBObject;
import eci.cosw.data.TodoRepository;
import eci.cosw.data.UserRepository;
import eci.cosw.data.model.Customer;
import eci.cosw.data.model.Todo;
import eci.cosw.data.model.User;
import org.bson.BasicBSONDecoder;
import org.bson.BasicBSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class Application implements CommandLineRunner {


    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        MongoOperations mongoOperation = (MongoOperations) applicationContext.getBean("mongoTemplate");

        // --- Create mocked users ---

        userRepository.deleteAll();

        userRepository.save(new User("Kutxi Romero", "KutxiRomero@example.com"));
        userRepository.save(new User("Mick Jagger", "MickJagger@example.com"));
        userRepository.save(new User("Elvis Presley", "ElvisPresley@example.com"));
        userRepository.save(new User("Axl Rose", "AxlRose@example.com"));
        userRepository.save(new User("Jim Morrison", "JimMorrison@example.com"));
        userRepository.save(new User("Alice Smith", "AliceSmith@example.com"));
        userRepository.save(new User("Bob Marley", "BobMarley@example.com"));
        userRepository.save(new User("Jimmy Page", "JimmyPage@example.com"));
        userRepository.save(new User("Freddy Mercury", "FreddyMercury@example.com"));
        userRepository.save(new User("Michael Jackson", "MichaelJackson@example.com"));


        // --- Create mocked To-Dos ---

        todoRepository.deleteAll();

        todoRepository.save(new Todo("Description",1,"Jan 10 - 1860","MichaelJackson@example.com","Pending"));
        todoRepository.save(new Todo("Description",2,"Jan 10 - 1860","MichaelJackson@example.com","Pending"));
        todoRepository.save(new Todo("Description",2,"Jan 10 - 1860","MickJagger@example.com","Pending"));
        todoRepository.save(new Todo("Description",3,"Jan 10 - 1860","MickJagger@example.com","Pending"));
        todoRepository.save(new Todo("Description",3,"Jan 10 - 1860","FreddyMercury@example.com","Pending"));
        todoRepository.save(new Todo("Description",3,"Jan 10 - 1860","FreddyMercury@example.com","Pending"));
        todoRepository.save(new Todo("Description",4,"Jan 10 - 1860","ElvisPresley@example.com","Pending"));
        todoRepository.save(new Todo("Description",4,"Jan 10 - 1860","ElvisPresley@example.com","Pending"));
        todoRepository.save(new Todo("Description",4,"Jan 10 - 1860","AxlRose@example.com","Pending"));
        todoRepository.save(new Todo("Description",5,"Jan 10 - 1860","AxlRose@example.com","Pending"));
        todoRepository.save(new Todo("Description",5,"Jan 10 - 1860","JimmyPage@example.com","Pending"));
        todoRepository.save(new Todo("Description",5,"Jan 10 - 1860","JimmyPage@example.com","Pending"));
        todoRepository.save(new Todo("Description",6,"Jan 10 - 1860","BobMarley@example.com","Pending"));
        todoRepository.save(new Todo("Description",6,"Jan 10 - 1860","BobMarley@example.com","Pending"));
        todoRepository.save(new Todo("Description",6,"Jan 10 - 1860","JimMorrison@example.com","Pending"));
        todoRepository.save(new Todo("Description",7,"Jan 10 - 1860","JimMorrison@example.com","Pending"));
        todoRepository.save(new Todo("Description",7,"Jan 10 - 1860","AliceSmith@example.com","Pending"));
        todoRepository.save(new Todo("Description",7,"Jan 10 - 1860","AliceSmith@example.com","Pending"));
        todoRepository.save(new Todo("Description",7,"Jan 10 - 1860","AliceSmith@example.com","Pending"));
        todoRepository.save(new Todo("Description",7,"Jan 10 - 1860","AliceSmith@example.com","Pending"));
        todoRepository.save(new Todo("Description",10,"Jan 10 - 1860","KutxiRomero@example.com","Pending"));
        todoRepository.save(new Todo("Description",10,"Jan 10 - 1860","KutxiRomero@example.com","Pending"));
        todoRepository.save(new Todo("Description",10,"Jan 10 - 1860","KutxiRomero@example.com","Pending"));
        todoRepository.save(new Todo("Description",10,"Jan 10 - 1860","KutxiRomero@example.com","Pending"));
        todoRepository.save(new Todo("Description",10,"Jan 10 - 1860","KutxiRomero@example.com","Pending"));


        // --- First Query: Todos that the DueDate has expire. ---
        Query query = new Query();
        query.addCriteria(Criteria.where("dueDate").lt(new SimpleDateFormat("MMM d - yyyy").format(new Date())));
        List<Todo> todos = mongoOperation.find(query,Todo.class);
        System.out.print("\n--- Expired Todos: ---\n");
        for (Todo todo: todos) {
            System.out.println(todo);
        }
        System.out.print("\n------\n");

        // --- Second Query: Todos that are assigned to given user and have priority greater equal to 5. ---
        String responsible = "KutxiRomero@example.com";
        query = new Query();
        query.addCriteria(Criteria.where("responsible").is(responsible).and("priority").gte(5));
        todos = mongoOperation.find(query,Todo.class);
        System.out.print("--- Todos of "+responsible+" and priority greater equal to 5: ---\n");
        for (Todo todo: todos) {
            System.out.println(todo);
        }
        System.out.print("\n------\n");

        // --- Third Query: List users that have assigned more than 2 Todos. ---
//        Aggregation agg = Aggregation.newAggregation(
//                Aggregation.lookup("t odo","email","responsible","email"),
//                Aggregation.group("email").count().as("quantity"),
//                Aggregation.match(Criteria.where("quantity").gt(2)),
//                Aggregation.project("name","email","quantity")
//        );
//
//        List<BasicDBObject> users = mongoOperation.aggregate(agg,"user",BasicDBObject.class).getMappedResults();
//        System.out.print("--- List of users that have assigned more than 2 Todos ---\n");
//        for (BasicDBObject user: users) {
//            System.out.println(user.toString());
//        }
//        System.out.print("\n------\n");

        // --- Fourth Query: Todos that contains the description with a length greater than 30 characters. ---
//        Aggregation agg = Aggregation.newAggregation(
//                Aggregation.project().and("description").,
//                Aggregation.match(Criteria.where("descriptionChars").gt(30))
//        );
//        AggregationResults<To do> aggregationResults = mongoOperation.aggregate(agg,"to do",To do.class);
//        mongoOperation.
//        System.out.print("\n--- Todos with description larger than 30 chars: ---\n");
//        System.out.println(aggregationResults.getRawResults());
//        System.out.print("\n------\n");
    }

}