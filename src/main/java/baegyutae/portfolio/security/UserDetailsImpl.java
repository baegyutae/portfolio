package baegyutae.portfolio.security;

import baegyutae.portfolio.entity.User;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {

    private User user;

    // 커스텀 UserDetails에 사용자 엔티티를 포함시키는 생성자
    public UserDetailsImpl(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자의 권한 반환
        return Collections.emptyList(); // 예시로 비어 있는 리스트 반환
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정이 만료되지 않았음을 나타냄
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정이 잠기지 않았음을 나타냄
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 인증 정보(비밀번호)가 만료되지 않았음을 나타냄
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정이 활성화되었음을 나타냄
    }
}

