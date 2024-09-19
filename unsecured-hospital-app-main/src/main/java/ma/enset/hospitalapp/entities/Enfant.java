package ma.enset.hospitalapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enfant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Le nom du conjoint ne peut pas être vide")
    @Size(min = 2, max = 100, message = "Le nom du conjoint doit avoir entre 2 et 100 caractères")
    private String nom;

    @NotEmpty(message = "Le prénom du conjoint ne peut pas être vide")
    @Size(min = 2, max = 100, message = "Le prénom du conjoint doit avoir entre 2 et 100 caractères")
    private String prenom;

    @Past(message = "La date de naissance doit être une date passée")
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;

    @Size(max = 100, message = "Le lieu de naissance ne peut pas dépasser 100 caractères")
    private String lieuNaissance;

    @Size(max = 50, message = "La nationalité ne peut pas dépasser 50 caractères")
    private String nationalite;

    @Size(max = 100, message = "La référence de l'acte de naissance ne peut pas dépasser 100 caractères")
    private String referenceActeDeNaissance;

    @ManyToOne
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

}
