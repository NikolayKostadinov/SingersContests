package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.model.enums.ResourceType;
import bg.manhattan.singerscontests.services.CloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinaryConfig;

    public CloudinaryServiceImpl(Cloudinary cloudinaryConfig) {
        this.cloudinaryConfig = cloudinaryConfig;
    }

    @Override
    public String uploadFile(MultipartFile file, String fileName, ResourceType resourceType) {
        try {
            File uploadedFile = convertMultiPartToFile(file);
            Map params = ObjectUtils.asMap(
                    "public_id", fileName,
                    "overwrite", true,
                    "resource_type", resourceType
            );
            Map uploadResult = cloudinaryConfig.uploader().upload(uploadedFile, params);
            boolean isDeleted = uploadedFile.delete();

            if (isDeleted){
                System.out.println("File successfully deleted");
            }else
                System.out.println("File doesn't exist");
            return  uploadResult.get("url").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
