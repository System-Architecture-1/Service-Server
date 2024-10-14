package umc.todaynan.repository;

import org.springframework.data.repository.CrudRepository;
import umc.todaynan.domain.entity.RefreshToken;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
    Boolean existsRefreshTokenByEmail(String email);

    RefreshToken findRefreshTokenByEmail(String email);
}
