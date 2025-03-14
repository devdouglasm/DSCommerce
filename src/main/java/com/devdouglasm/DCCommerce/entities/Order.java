package com.devdouglasm.DCCommerce.entities;

import jakarta.persistence.*;

import java.time.Instant;

@Entity // marking as an entity
@Table(name = "tb_order") // creating a table in db with a name "tb_order"
public class Order {

    @Id// marking as an ID
    @GeneratedValue(strategy = GenerationType.IDENTITY) // automatic incrementation the value of ID
    private Long id;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE") // to be saved with a UTC time
    private Instant moment;
    private OrderStatus status;

    @ManyToOne // many to one - one client could have a lot of orders
    @JoinColumn(name = "client_id") // creating a new column with a foreign key "client_id"
    // the name has to be the same of attribute bellow
    private User client;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    public Order(Long id, Instant moment, OrderStatus status, User client, Payment payment) {
        this.id = id;
        this.moment = moment;
        this.status = status;
        this.client = client;
        this.payment = payment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }
}
