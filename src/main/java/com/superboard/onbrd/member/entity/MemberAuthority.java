package com.superboard.onbrd.member.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MemberAuthority {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
}
