package ru.job4j.relation;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "auto")
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "engine_id", foreignKey = @ForeignKey(name = "ENGINE_ID_FK") )
    private Engine engine;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "history_owner", joinColumns = {
            @JoinColumn(name = "driver_id", nullable = false, updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "auto_id",
                    nullable = false, updatable = false) })
    private Set<Driver> drivers;

    public Set<Driver> getDrivers() {
        return drivers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public void setDrivers(Set<Driver> drivers) {
        this.drivers = drivers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Auto auto = (Auto) o;
        return id == auto.id && Objects.equals(engine, auto.engine) && Objects.equals(drivers, auto.drivers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, engine, drivers);
    }
}
