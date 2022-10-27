package com.ifuture.AcountService.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
 * class Account is domain-entity comment object.
 *
 * @author Kirill Soklakov
 * @since 2022-10-26
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Table(name = "accounts")
public class Account {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "amount")
    private Long amount;
}