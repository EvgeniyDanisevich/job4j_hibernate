package ru.job4j.integration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OrdersStoreTest {
    private final BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/update_001.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @After
    public void deleteTable() {
        try {
            pool.getConnection().prepareStatement("drop table orders").executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);

        store.save(Order.of("name1", "description1"));

        List<Order> all = (List<Order>) store.findAll();

        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenSaveOrderAndFindById() {
        OrdersStore store = new OrdersStore(pool);

        store.save(Order.of("name1", "description1"));

        Order order = store.findById(1);

        assertEquals(order.getId(), 1);
        assertEquals(order.getName(), "name1");
        assertEquals(order.getDescription(), "description1");
    }

    @Test
    public void whenSaveOrderAndFindByName() {
        OrdersStore store = new OrdersStore(pool);

        store.save(Order.of("name1", "description1"));
        store.save(Order.of("name1", "description2"));

        List<Order> orders = store.findByName("name1");
        assertEquals(orders.get(0).getName(), "name1");
        assertEquals(orders.get(1).getName(), "name1");
    }

    @Test
    public void whenSaveOrderAndUpdate() {
        OrdersStore store = new OrdersStore(pool);

        Order order1 = Order.of("name1", "description1");
        Order order2 = Order.of("updatedName", "updatedDescription");

        store.save(order1);
        store.update(1, order2);

        assertEquals(store.findById(1).getName(), "updatedName");
        assertEquals(store.findById(1).getDescription(), "updatedDescription");
    }
}