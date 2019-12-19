package fr.inti.printed.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;

/**
 * A Authentification.
 */
@Document(collection = "authentification")
public class Authentification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("login")
    private String login;

    @Field("passeword")
    private String passeword;

    @DBRef
    @Field("paiement")
    private Paiement paiement;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public Authentification login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasseword() {
        return passeword;
    }

    public Authentification passeword(String passeword) {
        this.passeword = passeword;
        return this;
    }

    public void setPasseword(String passeword) {
        this.passeword = passeword;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public Authentification paiement(Paiement paiement) {
        this.paiement = paiement;
        return this;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Authentification)) {
            return false;
        }
        return id != null && id.equals(((Authentification) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Authentification{" +
            "id=" + getId() +
            ", login='" + getLogin() + "'" +
            ", passeword='" + getPasseword() + "'" +
            "}";
    }
}
