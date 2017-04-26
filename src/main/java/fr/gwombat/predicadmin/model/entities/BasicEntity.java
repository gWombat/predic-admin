package fr.gwombat.predicadmin.model.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotBlank;

@MappedSuperclass
class BasicEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Column(name = "identifier", unique = true, nullable = false, length = 32)
    protected String          identifier       = EntityIdGenerator.generateIdentifier();

    protected BasicEntity() {
    }

    @Override
    public int hashCode() {
        final HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        hashCodeBuilder.append(identifier);
        return hashCodeBuilder.toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (obj instanceof BasicEntity) {
            final BasicEntity other = (BasicEntity) obj;
            final EqualsBuilder equalsBuilder = new EqualsBuilder();
            equalsBuilder.append(identifier, other.identifier);
            return equalsBuilder.build();
        }

        return false;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

}
