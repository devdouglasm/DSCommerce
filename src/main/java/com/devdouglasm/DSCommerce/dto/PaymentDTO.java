package com.devdouglasm.DSCommerce.dto;

import com.devdouglasm.DSCommerce.entities.Payment;

import java.time.Instant;

public class PaymentDTO {

    private long id;
    private Instant moment;

    public PaymentDTO(long id, Instant moment) {
        this.id = id;
        this.moment = moment;
    }

    public PaymentDTO(Payment entity) {
        id = entity.getId();
        moment = entity.getMoment();
    }

    public long getId() {
        return id;
    }

    public Instant getMoment() {
        return moment;
    }
}
