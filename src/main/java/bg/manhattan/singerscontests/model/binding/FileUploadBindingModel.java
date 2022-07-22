package bg.manhattan.singerscontests.model.binding;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FileUploadBindingModel {
    @NotNull()
    private MultipartFile file;
    @NotBlank
    private String title;

    public MultipartFile getFile() {
        return file;
    }

    public FileUploadBindingModel setFile(MultipartFile file) {
        this.file = file;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public FileUploadBindingModel setTitle(String title) {
        this.title = title;
        return this;
    }
}
