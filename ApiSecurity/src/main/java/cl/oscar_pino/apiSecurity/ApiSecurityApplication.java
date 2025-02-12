package cl.oscar_pino.apiSecurity;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import cl.oscar_pino.apiSecurity.entities.AuthorEntity;
import cl.oscar_pino.apiSecurity.entities.BookEntity;
import cl.oscar_pino.apiSecurity.entities.CategoryEntity;
import cl.oscar_pino.apiSecurity.entities.CustomerEntity;
import cl.oscar_pino.apiSecurity.entities.EditorialEntity;
import cl.oscar_pino.apiSecurity.entities.LoanEntity;
import cl.oscar_pino.apiSecurity.entities.NationalityEntity;
import cl.oscar_pino.apiSecurity.entities.PermissionEntity;
import cl.oscar_pino.apiSecurity.entities.ReturnEntity;
import cl.oscar_pino.apiSecurity.entities.RoleEntity;
import cl.oscar_pino.apiSecurity.entities.UserEntity;
import cl.oscar_pino.apiSecurity.entities.enums.PermissionEnum;
import cl.oscar_pino.apiSecurity.entities.enums.RoleEnum;
import cl.oscar_pino.apiSecurity.services.AuthorServiceImp;
import cl.oscar_pino.apiSecurity.services.BookServiceImp;
import cl.oscar_pino.apiSecurity.services.CategoryServiceImp;
import cl.oscar_pino.apiSecurity.services.CustomerServiceImp;
import cl.oscar_pino.apiSecurity.services.EditorialServiceImp;
import cl.oscar_pino.apiSecurity.services.LoanServiceImp;
import cl.oscar_pino.apiSecurity.services.NationalityServiceImp;
import cl.oscar_pino.apiSecurity.services.PermissionServiceImp;
import cl.oscar_pino.apiSecurity.services.ReturnServiceImp;
import cl.oscar_pino.apiSecurity.services.UserServiceImp;

@SpringBootApplication
public class ApiSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiSecurityApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserServiceImp userServiceImp;

	@Autowired
	private NationalityServiceImp nationalityServiceImp;

	@Autowired
	private PermissionServiceImp permissionServiceImp;

	@Autowired
	private CategoryServiceImp categoryServiceImp;

	@Autowired
	private BookServiceImp bookServiceImp;

	@Autowired
	private EditorialServiceImp editorialServiceImp;

	@Autowired
	private AuthorServiceImp authorServiceImp;

	@Autowired
	private CustomerServiceImp customerServiceImp;

	@Autowired
	private LoanServiceImp loanServiceImp;

	@Autowired
	private ReturnServiceImp returnServiceImp;

	@Bean
	CommandLineRunner init() {

		return args -> {

			Set<NationalityEntity> nationalities = nationalityServiceImp.readAll().stream().collect(Collectors.toSet());

			Set<PermissionEntity> permissions = permissionServiceImp.readAll().stream().collect(Collectors.toSet());

			Set<UserEntity> users = userServiceImp.readAll().stream().collect(Collectors.toSet());

			Set<CategoryEntity> categories = categoryServiceImp.readAll().stream().collect(Collectors.toSet());

			Set<BookEntity> books = bookServiceImp.readAll().stream().collect(Collectors.toSet());

			Set<EditorialEntity> editorials = editorialServiceImp.readAll().stream().collect(Collectors.toSet());

			Set<AuthorEntity> authors = authorServiceImp.readAll().stream().collect(Collectors.toSet());

			Set<CustomerEntity> customers = customerServiceImp.readAll().stream().collect(Collectors.toSet());

			Set<LoanEntity> loans = loanServiceImp.readAll().stream().collect(Collectors.toSet());

			Set<ReturnEntity> returns = returnServiceImp.readAll().stream().collect(Collectors.toSet());

			if (nationalities.isEmpty()) {

				nationalities.add(new NationalityEntity("chile", "español"));
				nationalities.add(new NationalityEntity("peru", "español"));
				nationalities.add(new NationalityEntity("ee.uu", "ingles"));
				nationalities.add(new NationalityEntity("francia", "frances"));
				nationalities.add(new NationalityEntity("brasil", "portugues"));
				nationalities.add(new NationalityEntity("españa", "español"));
				nationalities.add(new NationalityEntity("inglaterra", "ingles"));

				nationalities.stream().forEach(n -> nationalityServiceImp.create(n));
			}

			if (authors.isEmpty()) {

				authors.add(
						new AuthorEntity("Jhon", "Tolkien", nationalities.stream().collect(Collectors.toList()).get(6),
								"www.tolkien.com", "tolkiene@anillos.com"));
				authors.add(new AuthorEntity("Miguel", "Cervantes",
						nationalities.stream().collect(Collectors.toList()).get(5), "www.cervantes.com",
						"cervante@quijote.com"));
				authors.add(new AuthorEntity("George", "Lucas",
						nationalities.stream().collect(Collectors.toList()).get(6), "www.lucas.com", "george@gg.com"));

				authors.stream().forEach(a -> authorServiceImp.create(a));
			}

			if (editorials.isEmpty()) {

				editorials.add(new EditorialEntity("George Allen & Unwin", "calle Tolkien", "91455363",
						"www.anillos.com", "el@hobby.com", LocalDate.of(1937, 9, 21)));
				editorials.add(new EditorialEntity("Agustín Jubera", "calle Cervantes", "98761233", "www.quijote.com",
						"don@quijote.com", LocalDate.of(1605, 1, 16)));
				editorials.add(new EditorialEntity("Alan Dean Foster", "calle george", "98715213", "www.lucasfilm.com",
						"gl@galaxias.com", LocalDate.of(1976, 11, 12)));

				editorials.stream().forEach(e -> editorialServiceImp.create(e));
			}

			if (categories.isEmpty()) {

				categories.add(new CategoryEntity("Ficción", "Trata sobre hechos irreales e imaginarios."));
				categories.add(new CategoryEntity("Novela", "Acerca del comportamiento diario de la humanidad."));
				categories.add(new CategoryEntity("Terror", "Describe historias en donde se busca asustar al lector."));
				categories.add(new CategoryEntity("Fabulas", "Relata historias en donde los animales pueden hablar."));
				categories.add(new CategoryEntity("Cuento",
						"Relatos más cortos, generalmente con una única trama o incidente"));

				categories.stream().forEach(c -> categoryServiceImp.create(c));

			}

			if (books.isEmpty()) {

				books.add(new BookEntity("El Hobbit", "l100", 100,
						editorials.stream().collect(Collectors.toList()).get(0),
						categories.stream().collect(Collectors.toList()).get(0),
						authors.stream().collect(Collectors.toList()).get(0)));

				books.add(new BookEntity("Don Quijote", "l101", 100,
						editorials.stream().collect(Collectors.toList()).get(1),
						categories.stream().collect(Collectors.toList()).get(1),
						authors.stream().collect(Collectors.toList()).get(1)));

				books.add(new BookEntity("La guerra de las galaxias", "l102", 100,
						editorials.stream().collect(Collectors.toList()).get(2),
						categories.stream().collect(Collectors.toList()).get(0),
						authors.stream().collect(Collectors.toList()).get(2)));

				books.stream().forEach(b -> bookServiceImp.create(b));
			}

			if (returns.isEmpty()) {

				returns.add(new ReturnEntity(LocalDate.now().plusDays(10l), 0f, 0)); // LocalDate returnDate, Float
																						// penalty, int daysLate
				returns.add(new ReturnEntity(LocalDate.now().plusDays(1l), 0f, 0));
				returns.add(new ReturnEntity(LocalDate.now().plusDays(3l), 0f, 0));
				returns.add(new ReturnEntity(LocalDate.now().plusDays(24l), 0f, 0));

				returns.stream().forEach(r -> returnServiceImp.create(r));
			}

			if (customers.isEmpty()) {

				customers.add(new CustomerEntity("julio", "parra", nationalities.stream().toList().get(0),
						"calle prat 101", "julio@parra.com", "54123478"));
				customers.add(new CustomerEntity("pedro", "torres", nationalities.stream().toList().get(0),
						"las parcelas 201", "pedro@torres.com", "59813478"));
				customers.add(new CustomerEntity("pablo", "perez", nationalities.stream().toList().get(2),
						"lo herrera 123", "pablo@perez.com", "54162018"));
				customers.add(new CustomerEntity("mario", "garrido", nationalities.stream().toList().get(4),
						"sarmientos 41", "mario@garrido.com", "74192178"));

				customers.stream().forEach(c -> customerServiceImp.create(c));
			}

			if (loans.isEmpty()) {

				LoanEntity[] loansArray = new LoanEntity[3];
				loansArray[0] = new LoanEntity(Set.of(books.stream().toList().get(0), books.stream().toList().get(1)),
						customers.stream().toList().get(0), LocalDate.now());
				loansArray[1] = new LoanEntity(Set.of(books.stream().toList().get(0), books.stream().toList().get(1),
						books.stream().toList().get(2)), customers.stream().toList().get(1), LocalDate.now());
				loansArray[2] = new LoanEntity(Set.of(books.stream().toList().get(1), books.stream().toList().get(2)),
						customers.stream().toList().get(2), LocalDate.now());

				loansArray[0].setReturnEntity(returns.stream().toList().get(0));
				loansArray[1].setReturnEntity(returns.stream().toList().get(1));
				loansArray[2].setReturnEntity(returns.stream().toList().get(2));

				loans.add(loansArray[0]);
				loans.add(loansArray[1]);
				loans.add(loansArray[2]);

				loans.stream().forEach(l -> loanServiceImp.create(l));
			}

			if (permissions.isEmpty()) {

				/* Create PERMISSIONS */
				PermissionEntity createPermission = new PermissionEntity(PermissionEnum.CREATE);
				PermissionEntity readPermission = new PermissionEntity(PermissionEnum.READ);
				PermissionEntity updatePermission = new PermissionEntity(PermissionEnum.UPDATE);
				PermissionEntity deletePermission = new PermissionEntity(PermissionEnum.DELETE);

				/* Create ROLES */
				RoleEntity adminRole = new RoleEntity(RoleEnum.ADMIN,
						Set.of(createPermission, readPermission, updatePermission, deletePermission));
				RoleEntity developerRole = new RoleEntity(RoleEnum.DEVELOPER,
						Set.of(createPermission, readPermission, updatePermission));
				RoleEntity inviteRole = new RoleEntity(RoleEnum.INVITED, Set.of(readPermission));
				RoleEntity userRole = new RoleEntity(RoleEnum.USER, Set.of(createPermission, readPermission));

				if (users.isEmpty()) {

					UserEntity oscar = new UserEntity("Oscar", passwordEncoder.encode("1234"), Set.of(adminRole));
					UserEntity luis = new UserEntity("Luis", passwordEncoder.encode("1234"), Set.of(developerRole));
					UserEntity jose = new UserEntity("Jose", passwordEncoder.encode("1234"), Set.of(inviteRole));
					UserEntity pedro = new UserEntity("Pedro", passwordEncoder.encode("1234"), Set.of(userRole));

					userServiceImp.createAll(Set.of(oscar, luis, jose, pedro));

				}
			}

		};
	}
}
