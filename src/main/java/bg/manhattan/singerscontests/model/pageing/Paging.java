package bg.manhattan.singerscontests.model.pageing;

import java.util.ArrayList;
import java.util.List;

public class Paging {
    private static final int PAGINATION_STEP = 3;

    private boolean nextEnabled;
    private boolean prevEnabled;
    private int pageSize;
    private int pageNumber;
    private List<PageItem> items = new ArrayList<>();

    public Paging(boolean nextEnabled, boolean prevEnabled, int pageSize, int pageNumber, List<PageItem> items) {
        this.nextEnabled = nextEnabled;
        this.prevEnabled = prevEnabled;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.items = items;
    }

    public Paging() {
    }

    public static PagingBuilder builder() {
        return new PagingBuilder();
    }


    public void addPageItems(int from, int to, int pageNumber) {
        for (int i = from; i < to; i++) {
            items.add(PageItem.builder()
                    .active(pageNumber != i)
                    .index(i)
                    .pageItemType(PageItemType.PAGE)
                    .build());
        }
    }

    public void last(int pageSize) {
        items.add(PageItem.builder()
                .active(false)
                .pageItemType(PageItemType.DOTS)
                .build());

        items.add(PageItem.builder()
                .active(true)
                .index(pageSize)
                .pageItemType(PageItemType.PAGE)
                .build());
    }

    public void first(int pageNumber) {
        items.add(PageItem.builder()
                .active(pageNumber != 1)
                .index(1)
                .pageItemType(PageItemType.PAGE)
                .build());

        items.add(PageItem.builder()
                .active(false)
                .pageItemType(PageItemType.DOTS)
                .build());
    }

    public static Paging of(int totalPages, int pageNumber, int pageSize) {
        Paging paging = new Paging();
        paging.setPageSize(pageSize);
        paging.setNextEnabled(pageNumber != totalPages);
        paging.setPrevEnabled(pageNumber != 1);
        paging.setPageNumber(pageNumber);

        if (totalPages < PAGINATION_STEP * 2 + 6) {
            paging.addPageItems(1, totalPages + 1, pageNumber);

        } else if (pageNumber < PAGINATION_STEP * 2 + 1) {
            paging.addPageItems(1, PAGINATION_STEP * 2 + 4, pageNumber);
            paging.last(totalPages);

        } else if (pageNumber > totalPages - PAGINATION_STEP * 2) {
            paging.first(pageNumber);
            paging.addPageItems(totalPages - PAGINATION_STEP * 2 - 2, totalPages + 1, pageNumber);

        } else {
            paging.first(pageNumber);
            paging.addPageItems(pageNumber - PAGINATION_STEP, pageNumber + PAGINATION_STEP + 1, pageNumber);
            paging.last(totalPages);
        }

        return paging;
    }

    public boolean isNextEnabled() {
        return this.nextEnabled;
    }

    public boolean isPrevEnabled() {
        return this.prevEnabled;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public List<PageItem> getItems() {
        return this.items;
    }

    public void setNextEnabled(boolean nextEnabled) {
        this.nextEnabled = nextEnabled;
    }

    public void setPrevEnabled(boolean prevEnabled) {
        this.prevEnabled = prevEnabled;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setItems(List<PageItem> items) {
        this.items = items;
    }

    public static class PagingBuilder {
        private boolean nextEnabled;
        private boolean prevEnabled;
        private int pageSize;
        private int pageNumber;
        private List<PageItem> items;

        PagingBuilder() {
        }

        public PagingBuilder nextEnabled(boolean nextEnabled) {
            this.nextEnabled = nextEnabled;
            return this;
        }

        public PagingBuilder prevEnabled(boolean prevEnabled) {
            this.prevEnabled = prevEnabled;
            return this;
        }

        public PagingBuilder pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public PagingBuilder pageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
            return this;
        }

        public PagingBuilder items(List<PageItem> items) {
            this.items = items;
            return this;
        }

        public Paging build() {
            return new Paging(nextEnabled, prevEnabled, pageSize, pageNumber, items);
        }

        public String toString() {
            return "Paging.PagingBuilder(nextEnabled=" + this.nextEnabled + ", prevEnabled=" + this.prevEnabled + ", pageSize=" + this.pageSize + ", pageNumber=" + this.pageNumber + ", items=" + this.items + ")";
        }
    }
}
