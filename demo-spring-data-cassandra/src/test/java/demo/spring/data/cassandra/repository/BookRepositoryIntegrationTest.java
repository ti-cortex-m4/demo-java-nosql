package demo.spring.data.cassandra.repository;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import demo.spring.data.cassandra.AbstractIntegrationTest;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import demo.spring.data.cassandra.config.CassandraConfig;
import demo.spring.data.cassandra.model.Book;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.datastax.driver.core.utils.UUIDs;
import com.google.common.collect.ImmutableSet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CassandraConfig.class)
public class BookRepositoryIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeClass
    public static void beforeClass() throws Exception {
        startEmbeddedCassandra();
    }

    @AfterClass
    public static void afterClass() {
        stopEmbeddedCassandra();
    }

    @Before
    public void beforeMethod() {
        createTable();
    }

    @After
    public void afterMethod() {
        dropTable();
    }

    @Test
    public void testSaveAndFindBooks() {
        Book book1 = new Book(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        Book book2 = new Book(UUIDs.timeBased(), "Head Design Patterns", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        bookRepository.saveAll(ImmutableSet.of(book1, book2));

        Iterable<Book> books = bookRepository.findByTitleAndPublisher("Head First Java", "O'Reilly Media");

        assertThat(books, hasItem(book1));
        assertThat(books, hasItem(book2));
    }

    @Test
    public void testSaveAndDeleteBooks() {
        Book book1 = new Book(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        Book book2 = new Book(UUIDs.timeBased(), "Head Design Patterns", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        bookRepository.saveAll(ImmutableSet.of(book1, book2));

        bookRepository.delete(book1);
        bookRepository.delete(book2);

        Iterable<Book> books = bookRepository.findByTitleAndPublisher("Head First Java", "O'Reilly Media");

        assertThat(books, not(hasItem(book1)));
        assertThat(books, not(hasItem(book2)));
    }
}
