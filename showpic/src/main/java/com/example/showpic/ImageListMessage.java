package com.example.showpic;

import java.util.List;

/**
 * Created by 524202 on 2017/8/24.
 */

public class ImageListMessage {
    private List<String> images;

    public ImageListMessage(List<String> images) {
        this.images = images;
    }

    public List<String> getImages() {
        return images;
    }
}
