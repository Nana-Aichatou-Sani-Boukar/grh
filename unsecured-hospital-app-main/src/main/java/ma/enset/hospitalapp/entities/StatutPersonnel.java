package ma.enset.hospitalapp.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StatutPersonnel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Le libellé ne peut pas être vide")
    @Size(min = 5, max = 50, message = "Le libellé doit avoir entre 5 et 50 caractères")
    @Column(unique = true, nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "statutPersonnel", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Agent> agents = new ArrayList<>();

    public void addAgent(Agent agent) {
        agents.add(agent);
        agent.setStatutPersonnel(this);
    }

    public void removeAgent(Agent agent) {
        agents.remove(agent);
        agent.setStatutPersonnel(null);
    }
}
