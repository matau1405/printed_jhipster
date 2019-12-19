package fr.inti.printed.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import fr.inti.printed.domain.enumeration.ModeDeLivraison;

import fr.inti.printed.domain.enumeration.StatusCommande;

/**
 * A Commande.
 */
@Document(collection = "commande")
public class Commande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("id_cmd")
    private String idCmd;

    @Field("date_cmd")
    private Instant dateCmd;

    @Field("montant_cmd")
    private Float montantCmd;

    @Field("delai_livraison_cmd")
    private Double delaiLivraisonCmd;

    @Field("etat_livraison_cmd")
    private String etatLivraisonCmd;

    @Field("lieu_livraison_cmd")
    private String lieuLivraisonCmd;

    @Field("mode_livraison_cmd")
    private ModeDeLivraison modeLivraisonCmd;

    @NotNull
    @Field("status")
    private StatusCommande status;

    @DBRef
    @Field("panier")
    private Panier panier;

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

    public String getIdCmd() {
        return idCmd;
    }

    public Commande idCmd(String idCmd) {
        this.idCmd = idCmd;
        return this;
    }

    public void setIdCmd(String idCmd) {
        this.idCmd = idCmd;
    }

    public Instant getDateCmd() {
        return dateCmd;
    }

    public Commande dateCmd(Instant dateCmd) {
        this.dateCmd = dateCmd;
        return this;
    }

    public void setDateCmd(Instant dateCmd) {
        this.dateCmd = dateCmd;
    }

    public Float getMontantCmd() {
        return montantCmd;
    }

    public Commande montantCmd(Float montantCmd) {
        this.montantCmd = montantCmd;
        return this;
    }

    public void setMontantCmd(Float montantCmd) {
        this.montantCmd = montantCmd;
    }

    public Double getDelaiLivraisonCmd() {
        return delaiLivraisonCmd;
    }

    public Commande delaiLivraisonCmd(Double delaiLivraisonCmd) {
        this.delaiLivraisonCmd = delaiLivraisonCmd;
        return this;
    }

    public void setDelaiLivraisonCmd(Double delaiLivraisonCmd) {
        this.delaiLivraisonCmd = delaiLivraisonCmd;
    }

    public String getEtatLivraisonCmd() {
        return etatLivraisonCmd;
    }

    public Commande etatLivraisonCmd(String etatLivraisonCmd) {
        this.etatLivraisonCmd = etatLivraisonCmd;
        return this;
    }

    public void setEtatLivraisonCmd(String etatLivraisonCmd) {
        this.etatLivraisonCmd = etatLivraisonCmd;
    }

    public String getLieuLivraisonCmd() {
        return lieuLivraisonCmd;
    }

    public Commande lieuLivraisonCmd(String lieuLivraisonCmd) {
        this.lieuLivraisonCmd = lieuLivraisonCmd;
        return this;
    }

    public void setLieuLivraisonCmd(String lieuLivraisonCmd) {
        this.lieuLivraisonCmd = lieuLivraisonCmd;
    }

    public ModeDeLivraison getModeLivraisonCmd() {
        return modeLivraisonCmd;
    }

    public Commande modeLivraisonCmd(ModeDeLivraison modeLivraisonCmd) {
        this.modeLivraisonCmd = modeLivraisonCmd;
        return this;
    }

    public void setModeLivraisonCmd(ModeDeLivraison modeLivraisonCmd) {
        this.modeLivraisonCmd = modeLivraisonCmd;
    }

    public StatusCommande getStatus() {
        return status;
    }

    public Commande status(StatusCommande status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusCommande status) {
        this.status = status;
    }

    public Panier getPanier() {
        return panier;
    }

    public Commande panier(Panier panier) {
        this.panier = panier;
        return this;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public Commande paiement(Paiement paiement) {
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
        if (!(o instanceof Commande)) {
            return false;
        }
        return id != null && id.equals(((Commande) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Commande{" +
            "id=" + getId() +
            ", idCmd='" + getIdCmd() + "'" +
            ", dateCmd='" + getDateCmd() + "'" +
            ", montantCmd=" + getMontantCmd() +
            ", delaiLivraisonCmd=" + getDelaiLivraisonCmd() +
            ", etatLivraisonCmd='" + getEtatLivraisonCmd() + "'" +
            ", lieuLivraisonCmd='" + getLieuLivraisonCmd() + "'" +
            ", modeLivraisonCmd='" + getModeLivraisonCmd() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
