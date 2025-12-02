package ifrn.pi.locadora.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity 
public class Filme {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		private String nome;
		private String genero;
		private String descricao;
		private String capa;
		private String nota;
		private String preco;
		
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getNome() {
			return nome;
		}
		public void setNome(String nome) {
			this.nome = nome;
		}
		public String getGenero() {
			return genero;
		}
		public void setGenero(String genero) {
			this.genero = genero;
		}
		public String getDescricao() {
			return descricao;
		}
		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
		public String getCapa() {
			return capa;
		}
		public void setCapa(String capa) {
			this.capa = capa;
		}
		public String getNota() {
			return nota;
		}
		public void setNota(String nota) {
			this.nota = nota;
		}
		
		public String getPreco() {
			return preco;
		}
		public void setPreco(String preco) {
			this.preco = preco;
		}
		@Override
		public String toString() {
			return "Filme [id=" + id + ", nome=" + nome + ", genero=" + genero + ", descricao=" + descricao + ", nota="
					+ nota + ", preco=" + preco + "]";
		}
		
}
