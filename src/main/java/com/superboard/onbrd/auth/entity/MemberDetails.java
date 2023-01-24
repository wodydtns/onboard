package com.superboard.onbrd.auth.entity;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.member.entity.MemberStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDetails implements UserDetails {
	private Long id;
	private String email;
	private String role;
	private MemberStatus status;

	public static MemberDetails from(Member member) {
		return new MemberDetails(member.getId(), member.getEmail(), member.getRole().name(), member.getStatus());
	}

	public static MemberDetails of(String email, String role) {
		return new MemberDetails(null, email, role, null);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Set.of(new SimpleGrantedAuthority(role));
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return status == MemberStatus.ACTIVE;
	}
}
