package maroqand.uz.real_estate.repository;

import maroqand.uz.real_estate.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);

    boolean existsByName(String name);

    @Query(value = "select id from customers where name = :userName", nativeQuery = true)
    Long findCustomerIdByUserName(@Param("userName") String userName);

    @Query(value = "select * from real_estate_user where userName = :userName", nativeQuery = true )
    User findByLogin(@Param("userName") String userName);
}
