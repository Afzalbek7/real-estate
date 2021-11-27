package maroqand.uz.real_estate.repository;

import maroqand.uz.real_estate.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUserName(String user);

    @Query(value = "select * from real_estate_user where user_name = :userName", nativeQuery = true )
    User findByLogin(@Param("userName") String userName);

    User findByUserName(String username);
}
