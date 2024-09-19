package ma.enset.hospitalapp.services;

import ma.enset.hospitalapp.entities.ServiceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface ServiceService {
    Page<ServiceEntity> getServicesByKeyword(String keyword, PageRequest pageRequest);
    ServiceEntity saveService(ServiceEntity service);
    void deleteService(Long id);
    Optional<ServiceEntity> getServiceById(Long id);
}
