package app.entitites.bills_payment_system;

import javax.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {

    private int id;

    public BaseEntity() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
