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
public class Direction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Le code ne peut pas être vide")
    @Size(min = 3, max = 5, message = "Le code doit avoir entre 3 et 5 caractères")
    @Column(unique = true, nullable = false)
    private String code;

    @NotEmpty(message = "Le libellé ne peut pas être vide")
    @Size(min = 5, max = 255, message = "Le libellé doit avoir entre 5 et 255 caractères")
    @Column(unique = true, nullable = false)
    private String libelle;

    @OneToMany(mappedBy = "direction", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ServiceEntity> services = new ArrayList<>();

    public void addService(ServiceEntity service) {
        services.add(service);
        service.setDirection(this);
    }

    public void removeService(ServiceEntity service) {
        services.remove(service);
        service.setDirection(null);
    }
}
