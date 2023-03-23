package com.superboard.onbrd.report.entity;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.superboard.onbrd.global.converter.ReportTypeConverter;
import com.superboard.onbrd.global.entity.BaseEntity;
import com.superboard.onbrd.member.entity.Member;
import com.superboard.onbrd.report.dto.ReportCreateCommand;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Report extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	@Convert(converter = ReportTypeConverter.class)
	private ReportType type;
	@Column(nullable = false)
	private Long postId;
	@Column(name = "resolved_yn", nullable = false)
	private Boolean isResolved = false;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "whistleblower_id")
	private Member whistleblower;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resolver_id")
	private Member resolver;

	public static Report of(Member whistleblower, ReportCreateCommand command) {
		Report report = new Report();

		report.type = command.getType();
		report.postId = command.getPostId();
		report.whistleblower = whistleblower;

		return report;
	}
}
