package com.example.appvideoclub.View;

import com.example.appvideoclub.Controller.VideoClubController;

public class PadreController {
    protected VideoClubController vc;

    public VideoClubController getVc() {
        return vc;
    }

    public void setVc(VideoClubController vc) {
        this.vc = vc;
    }
}
