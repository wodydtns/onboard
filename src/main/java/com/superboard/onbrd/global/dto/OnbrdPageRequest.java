package com.superboard.onbrd.global.dto;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.superboard.onbrd.global.exception.OnbrdAssert;

import lombok.Getter;

@Getter
public class OnbrdPageRequest implements Pageable {
	private int page;
	private int size;

	protected OnbrdPageRequest(int page, int size) {
		OnbrdAssert.isTrue(page > 1, "Page index must not be less than one");
		OnbrdAssert.isTrue(size > 1, "Page index must not be less than one");

		this.page = page;
		this.size = size;
	}

	public void rebasePageNumberToZero() {
		page--;
	}

	@Override
	public int getPageNumber() {
		return page;
	}

	@Override
	public int getPageSize() {
		return size;
	}

	@Override
	public long getOffset() {
		return (long)getPageNumber() * getPageSize();
	}

	@Override
	public Sort getSort() {
		return null;
	}

	@Override
	public Pageable next() {
		return new OnbrdPageRequest(getPageNumber() + 1, getPageSize());
	}

	@Override
	public Pageable previousOrFirst() {
		return hasPrevious() ? previous() : first();
	}

	public Pageable previous() {
		return getPageNumber() == 0 ? this : new OnbrdPageRequest(getPageNumber() - 1, getPageSize());
	}

	@Override
	public Pageable first() {
		return new OnbrdPageRequest(0, getPageSize());
	}

	@Override
	public Pageable withPage(int pageNumber) {
		return new OnbrdPageRequest(pageNumber, getPageSize());
	}

	@Override
	public boolean hasPrevious() {
		return getPageSize() > 0;
	}
}
