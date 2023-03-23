package com.superboard.onbrd.inquiry.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.superboard.onbrd.inquiry.entity.Inquiry;

public interface InquiryRepository extends JpaRepository<Inquiry, Long>, CustomInquiryRepository {

}
