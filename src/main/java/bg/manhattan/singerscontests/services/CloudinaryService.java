package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.model.enums.ResourceType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface CloudinaryService {
    String uploadFile(MultipartFile file, String fileName, ResourceType resourceType);
}
