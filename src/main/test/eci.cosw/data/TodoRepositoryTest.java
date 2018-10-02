package eci.cosw.data;

import eci.cosw.data.model.Todo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoRepositoryTest {

    @Autowired
    TodoRepository todoRepository;

    @Before
    public void init() {
        todoRepository.deleteAll();

        todoRepository.save(new Todo("travel to Galapagos", 10, "Jan 10 - 1860", "charles@natural.com", "pending"));
        todoRepository.save(new Todo("travel to Galapagos", 10, "June 1 - 1870", "whatever@natural.com", "pending"));
        todoRepository.save(new Todo("travel to Galapagos", 10, "Feb 10 - 1890", "whatever2@natural.com", "pending"));
        todoRepository.save(new Todo("travel to wherever", 2, "Oct 10 - 1900", "charles@natural.com", "pending"));
    }

    @Test
    public void itShouldListTwo() {
        List<Todo> todos = todoRepository.findResponsible("charles@natural.com");
        assertEquals(2,todos.size());
    }

    @Test
    public void itShouldListZero() {
        List<Todo> todos = todoRepository.findResponsible("nobody@natural.com");
        assertEquals(0,todos.size());
    }

}
