package bg.manhattan.singerscontests.model.binding;

public class EditionDeleteBindingModel {
    private Long id;
    private Long contentId;

    public Long getId() {
        return id;
    }

    public EditionDeleteBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getContentId() {
        return contentId;
    }

    public EditionDeleteBindingModel setContentId(Long contentId) {
        this.contentId = contentId;
        return this;
    }
}
