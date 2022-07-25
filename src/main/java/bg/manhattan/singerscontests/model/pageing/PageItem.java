package bg.manhattan.singerscontests.model.pageing;

public class PageItem {

    private PageItemType pageItemType;

    private int index;

    private boolean active;

    public PageItem(PageItemType pageItemType, int index, boolean active) {
        this.pageItemType = pageItemType;
        this.index = index;
        this.active = active;
    }

    public PageItem() {
    }

    public static PageItemBuilder builder() {
        return new PageItemBuilder();
    }


    public PageItemType getPageItemType() {
        return pageItemType;
    }

    public PageItem setPageItemType(PageItemType pageItemType) {
        this.pageItemType = pageItemType;
        return this;
    }

    public int getIndex() {
        return index;
    }

    public PageItem setIndex(int index) {
        this.index = index;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public PageItem setActive(boolean active) {
        this.active = active;
        return this;
    }

    public static class PageItemBuilder {
        private PageItemType pageItemType;
        private int index;
        private boolean active;

        PageItemBuilder() {
        }

        public PageItemBuilder pageItemType(PageItemType pageItemType) {
            this.pageItemType = pageItemType;
            return this;
        }

        public PageItemBuilder index(int index) {
            this.index = index;
            return this;
        }

        public PageItemBuilder active(boolean active) {
            this.active = active;
            return this;
        }

        public PageItem build() {
            return new PageItem(pageItemType, index, active);
        }

        public String toString() {
            return "PageItem.PageItemBuilder(pageItemType=" + this.pageItemType + ", index=" + this.index + ", active=" + this.active + ")";
        }
    }
}
