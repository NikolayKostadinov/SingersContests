package bg.manhattan.singerscontests.model.enums;

public enum ResourceType {
    image, video, raw;

    //public getResourceType()

    public static ResourceType getType(String contentType) {
        return switch (contentType){
            case "image/avif",
                    "image/bmp",
                    "image/gif",
                    "image/vnd.microsoft.icon",
                    "image/jpeg",
                    "image/png",
                    "image/svg+xml",
                    "image/tiff",
                    "image/webp"-> image;
            case "video/x-msvideo",
                    "video/mp4",
                    "video/mpeg",
                    "video/ogg",
                    "video/webm"-> video;
            default -> raw;
        };
    }
}
