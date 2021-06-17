package it.rdev.blog.api.dao;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.sun.istack.Nullable;

import it.rdev.blog.api.dao.entity.Articolo;

@Repository
public interface ArticoloDAOTest extends JpaRepository<Articolo, Long>, JpaSpecificationExecutor<Articolo>{

	@EntityGraph(
			type = EntityGraphType.FETCH,
			attributePaths = {
					
			}
		)
	
	List<Articolo> findAll(@Nullable Specification<Articolo> spec);
	
}
