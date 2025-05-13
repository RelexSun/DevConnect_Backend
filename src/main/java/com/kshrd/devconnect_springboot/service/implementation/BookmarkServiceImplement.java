package com.kshrd.devconnect_springboot.service.implementation;

import com.kshrd.devconnect_springboot.model.entity.*;
import com.kshrd.devconnect_springboot.model.enums.BookmarkEnum;
import com.kshrd.devconnect_springboot.respository.*;
import com.kshrd.devconnect_springboot.service.BookmarkService;
import com.kshrd.devconnect_springboot.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookmarkServiceImplement implements BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    @Override
    public Bookmark createBookmark(UUID targetId, String targetType) {
        return bookmarkRepository.createBookmark(targetId, targetType, CurrentUser.appUserId);
    }

    @Override
    public List<?> bookmarkByType(BookmarkEnum targetType, Integer page, Integer size) {
        page = (page - 1) * size;
        switch (targetType) {
            case PROJECTS -> {
                return bookmarkRepository.getAllBookmarkProject(CurrentUser.appUserId, page, size);
            }
            case JOBS -> {
                return bookmarkRepository.getAllBookmarkJob(CurrentUser.appUserId, page, size);
            }
            case HACKATHONS -> {
                return bookmarkRepository.getAllBookmarkHackathon(CurrentUser.appUserId, page, size);
            }
            default -> {
                return new ArrayList<>();
            }
        }
    }


}
