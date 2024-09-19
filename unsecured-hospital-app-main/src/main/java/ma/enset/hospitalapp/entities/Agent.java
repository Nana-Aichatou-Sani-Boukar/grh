package ma.enset.hospitalapp.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String matricule;
    private int numSS;

    @NotEmpty
    @Size(min = 4, max = 20)
    private String nom;

    @NotEmpty
    @Size(min = 4, max = 20)
    private String prenom;

    // Autres attributs...
    
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    private String lieuNaissance;
    private String nationalite;
    private String sexe;
    private String situationMatrimoniale;
    
    @Pattern(regexp = "\\d{10}", message = "Le numéro de téléphone doit comporter 10 chiffres")
    private String tel;

    @Email(message = "Email non valide")
    private String mail;

    private String residence;
    private String personneAprevenir;

    @Temporal(TemporalType.DATE)
    private Date dateEmbauche;
    
    private String diplome;
    private String niveauDiplome;

    // Relation Many-to-One avec Gestion
    @ManyToOne
    @JoinColumn(name = "gestion_id")  // Clé étrangère
    private Gestion gestion;

    @ManyToOne
    @JoinColumn(name = "ville_id")  // Clé étrangère
    private Ville ville;

    @ManyToOne
    @JoinColumn(name = "statutPersonnel_id")  // Clé étrangère
    private StatutPersonnel statutPersonnel;

    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Conjoint> conjoints = new ArrayList<>();

    public void addConjoint(Conjoint conjoint) {
        conjoints.add(conjoint);
        conjoint.setAgent(this);
    }

    public void removeConjoint(Conjoint conjoint) {
        conjoints.remove(conjoint);
        conjoint.setAgent(null);
    }

    @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Enfant> enfants = new ArrayList<>();

    public void addEnfant(Enfant enfant) {
        enfants.add(enfant);
        enfant.setAgent(this);
    }

    public void removeEnfant(Enfant enfant) {
        enfants.remove(enfant);
        enfant.setAgent(null);
    }
}

