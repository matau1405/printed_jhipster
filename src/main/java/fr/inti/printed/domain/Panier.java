package fr.inti.printed.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Panier.
 */
@Document(collection = "panier")
public class Panier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("id_panier")
    private String idPanier;

    @DBRef
    @Field("produits")
    private Set<Produits> produits = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPanier() {
        return idPanier;
    }

    public Panier idPanier(String idPanier) {
        this.idPanier = idPanier;
        return this;
    }

    public void setIdPanier(String idPanier) {
        this.idPanier = idPanier;
    }

    public Set<Produits> getProduits() {
        return produits;
    }

    public Panier produits(Set<Produits> produits) {
        this.produits = produits;
        return this;
    }

    public Panier addProduits(Produits produits) {
        this.produits.add(produits);
        produits.setPanier(this);
        return this;
    }

    public Panier removeProduits(Produits produits) {
        this.produits.remove(produits);
        produits.setPanier(null);
        return this;
    }

    public void setProduits(Set<Produits> produits) {
        this.produits = produits;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Panier)) {
            return false;
        }
        return id != null && id.equals(((Panier) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Panier{" +
            "id=" + getId() +
            ", idPanier='" + getIdPanier() + "'" +
            "}";
    }
}
