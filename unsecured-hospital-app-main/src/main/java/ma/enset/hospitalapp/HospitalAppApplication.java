package ma.enset.hospitalapp;

// import ma.enset.hospitalapp.entities.Direction;
// import ma.enset.hospitalapp.repository.DirectionRepository;
// import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class HospitalAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospitalAppApplication.class, args);
    }

    // @Bean
    // CommandLineRunner start(DirectionRepository directionRepository){
    //     return args -> {
    //         directionRepository.save(new Direction(null,"DRC", "Direction de Recouvrement et de Cotisation"));
    //         directionRepository.save(new Direction(null,"DBI", "Direction du Budget et des Impots "));
    //     };
    // }
}
