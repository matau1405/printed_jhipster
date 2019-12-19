package fr.inti.printed.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * A Paiement.
 */
@Document(collection = "paiement")
public class Paiement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("posseseur_carte")
    private String posseseurCarte;

    @Field("type_carte")
    private String typeCarte;

    @Field("numero_carte")
    private Long numeroCarte;

    @Field("date_exp")
    private String dateExp;

    @Field("crypotogramme")
    private Long crypotogramme;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosseseurCarte() {
        return posseseurCarte;
    }

    public Paiement posseseurCarte(String posseseurCarte) {
        this.posseseurCarte = posseseurCarte;
        return this;
    }

    public void setPosseseurCarte(String posseseurCarte) {
        this.posseseurCarte = posseseurCarte;
    }

    public String getTypeCarte() {
        return typeCarte;
    }

    public Paiement typeCarte(String typeCarte) {
        this.typeCarte = typeCarte;
        return this;
    }

    public void setTypeCarte(String typeCarte) {
        this.typeCarte = typeCarte;
    }

    public Long getNumeroCarte() {
        return numeroCarte;
    }

    public Paiement numeroCarte(Long numeroCarte) {
        this.numeroCarte = numeroCarte;
        return this;
    }

    public void setNumeroCarte(Long numeroCarte) {
        this.numeroCarte = numeroCarte;
    }

    public String getDateExp() {
        return dateExp;
    }

    public Paiement dateExp(String dateExp) {
        this.dateExp = dateExp;
        return this;
    }

    public void setDateExp(String dateExp) {
        this.dateExp = dateExp;
    }

    public Long getCrypotogramme() {
        return crypotogramme;
    }

    public Paiement crypotogramme(Long crypotogramme) {
        this.crypotogramme = crypotogramme;
        return this;
    }

    public void setCrypotogramme(Long crypotogramme) {
        this.crypotogramme = crypotogramme;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Paiement)) {
            return false;
        }
        return id != null && id.equals(((Paiement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Paiement{" +
            "id=" + getId() +
            ", posseseurCarte='" + getPosseseurCarte() + "'" +
            ", typeCarte='" + getTypeCarte() + "'" +
            ", numeroCarte=" + getNumeroCarte() +
            ", dateExp='" + getDateExp() + "'" +
            ", crypotogramme=" + getCrypotogramme() +
            "}";
    }
}
