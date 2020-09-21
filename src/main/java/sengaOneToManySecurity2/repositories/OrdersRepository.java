package sengaOneToManySecurity2.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sengaOneToManySecurity2.entities.Orders;


@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {

	@Query(value = "select * from orders o where o.user_email = :user_email", nativeQuery = true)
	List<Orders> findAllByEmail(@Param("user_email") String user_email);

	void save(Optional<Orders> order);

}
