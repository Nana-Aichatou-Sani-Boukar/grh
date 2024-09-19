package ma.enset.hospitalapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Conjoint {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[A-Z0-9]{5,20}$", message = "Le matricule doit contenir entre 5 et 20 caractères alphanumériques en majuscules.")
    private String matricule;

    @NotEmpty(message = "Le nom du conjoint ne peut pas être vide")
    @Size(min = 2, max = 100, message = "Le nom du conjoint doit avoir entre 2 et 100 caractères")
    private String nom;

    @NotEmpty(message = "Le prénom du conjoint ne peut pas être vide")
    @Size(min = 2, max = 100, message = "Le prénom du conjoint doit avoir entre 2 et 100 caractères")
    private String prenom;

    @Past(message = "La date de naissance doit être une date passée")
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    @Past(message = "La date de mariage doit être une date passée")
    @Temporal(TemporalType.DATE)
    private Date dateMariage;

    @Size(max = 100, message = "Le lieu de naissance ne peut pas dépasser 100 caractères")
    private String lieuNaissance;

    @Size(max = 50, message = "La nationalité ne peut pas dépasser 50 caractères")
    private String nationalite;

    @Pattern(regexp = "^(M|F)$", message = "Le sexe doit être 'M' pour masculin ou 'F' pour féminin")
    private String sexe;

    @Size(max = 100, message = "La référence de l'acte de mariage ne peut pas dépasser 100 caractères")
    private String referenceActeDeMariage;

    @Size(max = 100, message = "La référence de l'acte de naissance ne peut pas dépasser 100 caractères")
    private String referenceActeDeNaissance;

    @Size(max = 100, message = "L'employeur ne peut pas dépasser 100 caractères")
    private String employeur;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    @ManyToOne
    @JoinColumn(name = "profession_id", nullable = false)
    private Profession profession;
}

