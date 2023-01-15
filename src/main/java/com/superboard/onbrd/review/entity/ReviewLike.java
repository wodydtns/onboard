package com.superboard.onbrd.review.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ReviewLike {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
}
