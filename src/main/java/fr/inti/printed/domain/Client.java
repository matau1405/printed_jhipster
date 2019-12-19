package fr.inti.printed.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Client.
 */
@Document(collection = "client")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("id_client")
    private String idClient;

    @Field("nom_client")
    private String nomClient;

    @Field("prenom_client")
    private String prenomClient;

    @Field("date_naissance_client")
    private String dateNaissanceClient;

    @NotNull
    @Size(max = 100)
    @Field("adresse_client")
    private String adresseClient;

    @Field("ville_client")
    private String villeClient;

    @Field("pays_client")
    private String paysClient;

    @Field("email_client")
    private String emailClient;

    @Field("list_commande")
    private String listCommande;

    @DBRef
    @Field("panier")
    private Panier panier;

    @DBRef
    @Field("paiement")
    private Authentification paiement;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdClient() {
        return idClient;
    }

    public Client idClient(String idClient) {
        this.idClient = idClient;
        return this;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getNomClient() {
        return nomClient;
    }

    public Client nomClient(String nomClient) {
        this.nomClient = nomClient;
        return this;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getPrenomClient() {
        return prenomClient;
    }

    public Client prenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
        return this;
    }

    public void setPrenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
    }

    public String getDateNaissanceClient() {
        return dateNaissanceClient;
    }

    public Client dateNaissanceClient(String dateNaissanceClient) {
        this.dateNaissanceClient = dateNaissanceClient;
        return this;
    }

    public void setDateNaissanceClient(String dateNaissanceClient) {
        this.dateNaissanceClient = dateNaissanceClient;
    }

    public String getAdresseClient() {
        return adresseClient;
    }

    public Client adresseClient(String adresseClient) {
        this.adresseClient = adresseClient;
        return this;
    }

    public void setAdresseClient(String adresseClient) {
        this.adresseClient = adresseClient;
    }

    public String getVilleClient() {
        return villeClient;
    }

    public Client villeClient(String villeClient) {
        this.villeClient = villeClient;
        return this;
    }

    public void setVilleClient(String villeClient) {
        this.villeClient = villeClient;
    }

    public String getPaysClient() {
        return paysClient;
    }

    public Client paysClient(String paysClient) {
        this.paysClient = paysClient;
        return this;
    }

    public void setPaysClient(String paysClient) {
        this.paysClient = paysClient;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public Client emailClient(String emailClient) {
        this.emailClient = emailClient;
        return this;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public String getListCommande() {
        return listCommande;
    }

    public Client listCommande(String listCommande) {
        this.listCommande = listCommande;
        return this;
    }

    public void setListCommande(String listCommande) {
        this.listCommande = listCommande;
    }

    public Panier getPanier() {
        return panier;
    }

    public Client panier(Panier panier) {
        this.panier = panier;
        return this;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    public Authentification getPaiement() {
        return paiement;
    }

    public Client paiement(Authentification authentification) {
        this.paiement = authentification;
        return this;
    }

    public void setPaiement(Authentification authentification) {
        this.paiement = authentification;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", idClient='" + getIdClient() + "'" +
            ", nomClient='" + getNomClient() + "'" +
            ", prenomClient='" + getPrenomClient() + "'" +
            ", dateNaissanceClient='" + getDateNaissanceClient() + "'" +
            ", adresseClient='" + getAdresseClient() + "'" +
            ", villeClient='" + getVilleClient() + "'" +
            ", paysClient='" + getPaysClient() + "'" +
            ", emailClient='" + getEmailClient() + "'" +
            ", listCommande='" + getListCommande() + "'" +
            "}";
    }
}
