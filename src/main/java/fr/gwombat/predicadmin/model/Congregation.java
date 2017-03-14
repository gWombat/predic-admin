package fr.gwombat.predicadmin.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by gWombat.
 *
 * @since 14/03/2017
 */
@Entity
@Table(name = "congregations")
public class Congregation implements Serializable {

    private static final long serialVersionUID = -6757555507970624970L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "congregation_id")
    private Long id;

    @NotBlank
    @Column(name = "identifier", nullable = false)
    private String identifier = EntityIdGenerator.generateIdentifier();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
