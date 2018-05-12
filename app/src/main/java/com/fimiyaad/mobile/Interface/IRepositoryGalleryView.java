package com.fimiyaad.mobile.Interface;

import com.fimiyaad.mobile.Model.GalleryModel;

import java.util.HashMap;
import java.util.List;

/**
 * Created by rohan
 * on 9/4/17.
 */

public interface IRepositoryGalleryView {
    void showRepositories(List<GalleryModel.Album> repo);
}