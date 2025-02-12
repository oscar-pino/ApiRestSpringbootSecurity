package cl.oscar_pino.apiSecurity.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "books")
public class BookEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;	
	
	@Column(nullable = false, unique = true)	
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(min = 3, max = 50, message = "ingrese 3 caracteres como mínimo y 50 como máximo")
	private String title;
	
	@Column(nullable = false, unique = true)
	@NotBlank(message = "el campo no debe ser null o solo contener espacios en blanco")
	@Size(min = 4, max = 10, message = "ingrese 4 caracteres como mínimo y 10 como máximo")
	private String isbm;

	@ManyToOne
	@JoinColumn(name = "editorial_id", nullable = false)
	private EditorialEntity editorial;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private CategoryEntity category;

	@ManyToOne
	@JoinColumn(name = "author_id", nullable = false)
	private AuthorEntity author;
	
	@Column(nullable = false)	
	@Min(value=0, message = "la cantidad no puede ser menor que cero")
	private int quantity;
	
	public BookEntity() {
	}

	public BookEntity(String title, String isbm, int quantity, EditorialEntity editorial, CategoryEntity category, AuthorEntity author) {
	this.title = title;
	this.isbm = isbm;
	this.quantity = quantity;
	this.editorial = editorial;
	this.category = category;
	this.author = author;
	}
	
	public BookEntity(Long id, String title, String isbm, int quantity, EditorialEntity editorial, CategoryEntity category, AuthorEntity author) {
		this(title, isbm, quantity, editorial, category, author);
		this.id = id;
		}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbm() {
		return isbm;
	}

	public void setIsbm(String isbm) {
		this.isbm = isbm;
	}

	public Long getId() {
		return id;
	}

	public EditorialEntity getEditorial() {
		return editorial;
	}

	public void setEditorial(EditorialEntity editorial) {
		this.editorial = editorial;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

	public AuthorEntity getAuthor() {
		return author;
	}

	public void setAuthor(AuthorEntity author) {
		this.author = author;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}	
}
