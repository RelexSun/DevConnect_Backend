package com.kshrd.devconnect_springboot.service;

import com.kshrd.devconnect_springboot.model.entity.*;
import com.kshrd.devconnect_springboot.model.enums.BookmarkEnum;

import java.util.List;
import java.util.UUID;

public interface BookmarkService {
    Bookmark createBookmark(UUID targetId, String targetType);
    List<?> bookmarkByType(BookmarkEnum targetType, Integer page, Integer size);

}
