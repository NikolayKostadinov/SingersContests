package bg.manhattan.singerscontests.model.pageing;

import org.springframework.data.domain.Page;

public class Paged<T> {

    private Page<T> page;

    private Paging paging;

    public Paged(Page<T> page, Paging paging) {
        this.page = page;
        this.paging = paging;
    }

    public Paged() {
    }

    public Page<T> getPage() {
        return this.page;
    }

    public Paging getPaging() {
        return this.paging;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Paged)) return false;
        final Paged<?> other = (Paged<?>) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$page = this.getPage();
        final Object other$page = other.getPage();
        if (this$page == null ? other$page != null : !this$page.equals(other$page)) return false;
        final Object this$paging = this.getPaging();
        final Object other$paging = other.getPaging();
        if (this$paging == null ? other$paging != null : !this$paging.equals(other$paging)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Paged;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $page = this.getPage();
        result = result * PRIME + ($page == null ? 43 : $page.hashCode());
        final Object $paging = this.getPaging();
        result = result * PRIME + ($paging == null ? 43 : $paging.hashCode());
        return result;
    }

    public String toString() {
        return "Paged(page=" + this.getPage() + ", paging=" + this.getPaging() + ")";
    }
}
