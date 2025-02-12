package cl.oscar_pino.apiSecurity.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import cl.oscar_pino.apiSecurity.entities.LoanEntity;
import cl.oscar_pino.apiSecurity.repositories.ILoanRepository;

@Service
public class LoanServiceImp implements IDAO<LoanEntity> {
	
	@Autowired
	private ILoanRepository loanRepository;

	@Override
	public void create(LoanEntity loanEntity) {
		
		loanRepository.save(loanEntity);
	}
	
	@Override
	public List<LoanEntity> readAll() {
		
		return (List<LoanEntity>) loanRepository.findAll();
	}
	
	public List<LoanEntity> readAllByFirstAndLastNameCustomer(String firstName, String lastName){
		
		return (List<LoanEntity>) loanRepository.findAllByFirstAndLastNameCustomer(firstName, lastName);
	}
	
	public List<LoanEntity> readAllByLoanDate(@Param("loanDate") LocalDate loanDate){
		
		return (List<LoanEntity>) loanRepository.findByLoanDate(loanDate);
	}
	
	@Override
	public Optional<LoanEntity> readById(Long id) {
		
		return loanRepository.findById(id);
	}	
	
	@Override
	public void update(LoanEntity loanEntity) {
		
		loanRepository.save(loanEntity);
	}

	@Override
	public void deleteById(Long id) {		

		loanRepository.deleteById(id);
	}
}
