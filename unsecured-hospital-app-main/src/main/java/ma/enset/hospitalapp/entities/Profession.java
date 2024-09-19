package ma.enset.hospitalapp.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Le libellé ne peut pas être vide")
    @Size(min = 5, max = 50, message = "Le libellé doit avoir entre 5 et 50 caractères")
    @Column(unique = true, nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "profession", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Conjoint> conjoints = new ArrayList<>();

    public void addconjoint(Conjoint conjoint) {
        conjoints.add(conjoint);
        conjoint.setProfession(this);
    }

    public void removeconjoint(Conjoint conjoint) {
        conjoints.remove(conjoint);
        conjoint.setProfession(null);
    }
}
