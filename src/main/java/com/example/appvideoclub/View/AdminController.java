package com.example.appvideoclub.View;

import com.example.appvideoclub.Controller.VideoClubController;

public class AdminController {
    private VideoClubController vc;

    public VideoClubController getVc() {
        return vc;
    }

    public void setVc(VideoClubController vc) {
        this.vc = vc;
    }
}