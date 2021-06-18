package ru.job4j.lazy;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "autos")
public class LazyAuto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "marks_id")
    private Mark mark;

    public static LazyAuto of(String name, Mark mark) {
        LazyAuto lazyAuto = new LazyAuto();
        lazyAuto.name = name;
        lazyAuto.mark = mark;
        return lazyAuto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Mark getBrand() {
        return mark;
    }

    public void setBrand(Mark mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LazyAuto lazyAuto = (LazyAuto) o;
        return id == lazyAuto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "LazyAuto{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", mark=" + mark +
                '}';
    }
}
