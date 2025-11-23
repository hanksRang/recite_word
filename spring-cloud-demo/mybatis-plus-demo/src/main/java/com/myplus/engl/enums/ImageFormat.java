package com.myplus.engl.enums;

public enum ImageFormat {
    PNG("png"),
    JPG("jpg"),
    JPEG("jpeg");

    private final String extension;

    ImageFormat(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    /**
     * 检查文件扩展名是否是支持的图片格式
     * @param fileExtension 文件扩展名（不包含点）
     * @return 如果是支持的格式返回true，否则false
     */
    public static boolean isSupportedFormat(String fileExtension) {
        if (fileExtension == null) {
            return false;
        }
        
        String lowerExtension = fileExtension.toLowerCase();
        for (ImageFormat format : values()) {
            if (format.getExtension().equals(lowerExtension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据文件名判断是否是支持的图片格式
     * @param fileName 文件名
     * @return 如果是支持的格式返回true，否则false
     */
    public static boolean isSupportedImage(String fileName) {
        if (fileName == null || fileName.lastIndexOf(".") == -1) {
            return false;
        }
        
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return isSupportedFormat(extension);
    }

    /**
     * 根据文件扩展名获取对应的枚举值
     * @param fileExtension 文件扩展名
     * @return 对应的枚举值，如果不是支持的格式返回null
     */
    public static ImageFormat fromExtension(String fileExtension) {
        if (fileExtension == null) {
            return null;
        }
        
        String lowerExtension = fileExtension.toLowerCase();
        for (ImageFormat format : values()) {
            if (format.getExtension().equals(lowerExtension)) {
                return format;
            }
        }
        return null;
    }
}