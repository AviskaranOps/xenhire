package xenhire.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import xenhire.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByUsernameOrEmail(String username, String email);

	User findByUsername(String username);

	@Query(value="select username, id from user u left join user_role ur on u.id=ur.user_id where role_id=1", nativeQuery=true)
	List<UserIdName> getAllUsers();
	
	public interface UserIdName{
		public long getid();
		public String getusername();
	}

	User findByEmail(String email);

	User findByEmailOrUsername(String email, String username);

	Optional<User> findByIdAndOtp(long userId, long otp);

	Optional<User> findByEmailAndOtp(String email, long otp);

	@Query(value="select * from user u left join user_role ur on u.id=ur.user_id where ur.role_id=3", nativeQuery=true)
	Page<User> getCandidates(Pageable page);

}
