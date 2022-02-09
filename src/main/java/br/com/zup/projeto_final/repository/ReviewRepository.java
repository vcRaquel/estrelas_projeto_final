package br.com.zup.projeto_final.repository;

import br.com.zup.projeto_final.model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
}
