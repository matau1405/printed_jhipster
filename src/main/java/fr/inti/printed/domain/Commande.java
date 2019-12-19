package fr.inti.printed.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;

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
    private String dateCmd;

    @Field("montant_cmd")
    private Float montantCmd;

    @Field("delai_livraison_cmd")
    private Double delaiLivraisonCmd;

    @Field("etat_livraison_cmd")
    private String etatLivraisonCmd;

    @Field("lieu_livraison_cmd")
    private String lieuLivraisonCmd;

    @Field("mode_livraison_cmd")
    private String modeLivraisonCmd;

    @Field("prix_total_cmd")
    private Float prixTotalCmd;

    @Field("mode_paiement")
    private String modePaiement;

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

    public String getDateCmd() {
        return dateCmd;
    }

    public Commande dateCmd(String dateCmd) {
        this.dateCmd = dateCmd;
        return this;
    }

    public void setDateCmd(String dateCmd) {
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

    public String getModeLivraisonCmd() {
        return modeLivraisonCmd;
    }

    public Commande modeLivraisonCmd(String modeLivraisonCmd) {
        this.modeLivraisonCmd = modeLivraisonCmd;
        return this;
    }

    public void setModeLivraisonCmd(String modeLivraisonCmd) {
        this.modeLivraisonCmd = modeLivraisonCmd;
    }

    public Float getPrixTotalCmd() {
        return prixTotalCmd;
    }

    public Commande prixTotalCmd(Float prixTotalCmd) {
        this.prixTotalCmd = prixTotalCmd;
        return this;
    }

    public void setPrixTotalCmd(Float prixTotalCmd) {
        this.prixTotalCmd = prixTotalCmd;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public Commande modePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
        return this;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
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
            ", prixTotalCmd=" + getPrixTotalCmd() +
            ", modePaiement='" + getModePaiement() + "'" +
            "}";
    }
}
