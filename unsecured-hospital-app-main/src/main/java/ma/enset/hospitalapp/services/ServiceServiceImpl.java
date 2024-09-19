package ma.enset.hospitalapp.services;

import ma.enset.hospitalapp.entities.ServiceEntity;
import ma.enset.hospitalapp.repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceServiceImpl implements ServiceService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public Page<ServiceEntity> getServicesByKeyword(String keyword, PageRequest pageRequest) {
        return serviceRepository.findByLibelleContains(keyword, pageRequest);
    }

    @Override
    public ServiceEntity saveService(ServiceEntity service) {
        return serviceRepository.save(service);
    }

    @Override
    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }

    @Override
    public Optional<ServiceEntity> getServiceById(Long id) {
        return serviceRepository.findById(id);
    }
}
