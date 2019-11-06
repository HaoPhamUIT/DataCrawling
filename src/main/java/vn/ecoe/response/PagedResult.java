package vn.ecoe.response;

import java.util.List;

/**
 * @author locld
 *
 * @param <T>
 */
public class PagedResult<T> {
	private int page;
	private int size;
	private long totalElements;
	private long totalPages;
	private List<T> elements;

	public PagedResult(List<T> elements, long totalPages, long totalElements, int page, int size) {
		this.elements = elements;
		this.totalPages = totalPages;
		this.totalElements = totalElements;
		this.page = page;
		this.size = size;
	}

	/**
	 * @return the hasMore
	 */
	public boolean isHasMore() {
		return totalElements > (page + 1) * size;
	}

	/**
	 * @return the hasPrevious
	 */
	public boolean isHasPrevious() {
		return page > 0 && totalElements > 0;
	}

	public long getTotalElements() {
		return totalElements;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	public List<T> getElements() {
		return elements;
	}

	/**
	 * @return the totalPages
	 */
	public long getTotalPages() {
		return totalPages;
	}

}
