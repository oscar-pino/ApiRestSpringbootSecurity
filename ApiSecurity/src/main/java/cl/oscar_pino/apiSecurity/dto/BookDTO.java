package cl.oscar_pino.apiSecurity.dto;

import cl.oscar_pino.apiSecurity.entities.AuthorEntity;
import cl.oscar_pino.apiSecurity.entities.CategoryEntity;
import cl.oscar_pino.apiSecurity.entities.EditorialEntity;

public class BookDTO {

	private Long id;

	private String title;

	private String isbm;

	private EditorialEntity editorial;

	private CategoryEntity category;

	private AuthorEntity author;

	private int quantity;

	public BookDTO() {
	}

	public BookDTO(String title, String isbm, int quantity, EditorialEntity editorial, CategoryEntity category,
			AuthorEntity author) {
		this.title = title;
		this.isbm = isbm;
		this.quantity = quantity;
		this.editorial = editorial;
		this.category = category;
		this.author = author;
	}

	public BookDTO(Long id, String title, String isbm, int quantity, EditorialEntity editorial, CategoryEntity category,
			AuthorEntity author) {
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

	@Override
	public String toString() {
		return "BookDTO [id=" + id + ", title=" + title + ", isbm=" + isbm + ", editorial=" + editorial + ", category="
				+ category + ", author=" + author + ", quantity=" + quantity + "]";
	}
}
