package lt.ca.javau10.Receptai;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lt.ca.javau10.Receptai.entities.ERole;
import lt.ca.javau10.Receptai.entities.Role;
import lt.ca.javau10.Receptai.repositories.RoleRepository;


@Component
public class RoleDBSeeder implements CommandLineRunner{
	private static final Logger logger = LoggerFactory.getLogger(RoleDBSeeder.class);
	
	    private RoleRepository roleRepository;
	    
	    RoleDBSeeder(RoleRepository roleRepository){
	    	this.roleRepository=roleRepository;
	    }
//jei nėra ROLE_ADMIN arba ROLE_USER jas prideda į duomenų bazę
	    @Override
	    public void run(String... args) throws Exception {
	        if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
	            roleRepository.save(new Role(ERole.ROLE_ADMIN));
	            logger.info("ROLE_ADMIN added.");
	        }
	        
	        if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()) {
	            roleRepository.save(new Role(ERole.ROLE_USER));
	            logger.info("ROLE_USER added.");
	        }
	    }

}
