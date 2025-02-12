package cl.oscar_pino.apiSecurity.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.oscar_pino.apiSecurity.entities.CustomerEntity;
import cl.oscar_pino.apiSecurity.repositories.ICustomerRepository;

@Service
public class CustomerServiceImp implements IDAO<CustomerEntity> {

	@Autowired
	private ICustomerRepository customerRepository;

	@Override
	public void create(CustomerEntity customerEntity) {

		customerRepository.save(customerEntity);
	}

	@Override
	public List<CustomerEntity> readAll() {

		return (List<CustomerEntity>) customerRepository.findAll();
	}

	public List<CustomerEntity> readAllByNationalityName(String name) {

		return (List<CustomerEntity>) customerRepository.findAllByNationalityName(name);
	}

	@Override
	public Optional<CustomerEntity> readById(Long id) {

		return customerRepository.findById(id);
	}

	public Optional<CustomerEntity> readByEmail(String email) {

		return customerRepository.findByEmail(email);
	}

	public Optional<CustomerEntity> readByPhone(String phone) {

		return customerRepository.findByPhone(phone);
	}

	@Override
	public void update(CustomerEntity customerEntity) {

		customerRepository.save(customerEntity);
	}

	@Override
	public void deleteById(Long id) {

		customerRepository.deleteById(id);
	}
}
