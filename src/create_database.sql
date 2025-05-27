CREATE DATABASE IF NOT EXISTS biblioteca;
USE biblioteca;

CREATE TABLE aluno (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    matricula VARCHAR(20) UNIQUE NOT NULL,
    data_nascimento DATE
);


CREATE TABLE livro (
					 id INT AUTO_INCREMENT PRIMARY KEY,
					 titulo VARCHAR (150) NOT NULL,
					 autor VARCHAR (100),
					 ano_publicacao INT,
                     quantidade_estoque INT DEFAULT 0
);


CREATE TABLE `biblioteca`.`emprestimo` (
  										`id_emprestimo` INT NOT NULL AUTO_INCREMENT,
  										`fk_id_aluno` INT NOT NULL,
  										`fk_id_livro` INT NOT NULL,
  										`data_emprestimo` DATE NOT NULL DEFAULT CURRENT_DATE,
  										`data_devolucao` DATE NULL,
  							PRIMARY KEY (`id_emprestimo`),
  								 INDEX `fk_id_aluno_idx` (`fk_id_aluno` ASC) VISIBLE,
  								 INDEX `fk_id_livro_idx` (`fk_id_livro` ASC) VISIBLE,
  							CONSTRAINT `fk_id_aluno`
							FOREIGN KEY (`fk_id_aluno`)
							REFERENCES `biblioteca`.`aluno` (`id`)
							    ON DELETE CASCADE
							    ON UPDATE NO ACTION,
								  CONSTRAINT `fk_id_livro`
								    FOREIGN KEY (`fk_id_livro`)
								    REFERENCES `biblioteca`.`livro` (`id`)
								    ON DELETE CASCADE
								    ON UPDATE NO ACTION);