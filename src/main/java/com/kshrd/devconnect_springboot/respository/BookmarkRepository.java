package com.kshrd.devconnect_springboot.respository;

import com.kshrd.devconnect_springboot.model.entity.Bookmark;
import com.kshrd.devconnect_springboot.model.entity.Hackathon;
import com.kshrd.devconnect_springboot.model.entity.Jobs;
import com.kshrd.devconnect_springboot.model.entity.Project;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.UUID;

@Mapper
public interface BookmarkRepository {
    @Results(id = "baseMapper", value = {
            @Result(property = "targetId", column = "target_id"),
            @Result(property = "targetType", column = "target_type"),
            @Result(property = "bookmarkBy", column = "bookmark_by")
    })
    @Select("""
        INSERT INTO bookmarks VALUES (DEFAULT, #{targetId}, #{targetType}, #{bookmarkBy})
        RETURNING *;
    """)
    Bookmark createBookmark(UUID targetId, String targetType, UUID bookmarkBy);

    @Result(property = "projectId", column = "project_id")
    @Result(property = "isOpen", column = "is_open")
    @Result(property = "createdAt", column = "created_at")
    @Result(property = "owner", column = "user_id", one = @One(select = "com.kshrd.devconnect_springboot.respository.AppUserRepository.getUserById"))
    @Result(property = "skills", column = "project_id", many = @Many(select = "com.kshrd.devconnect_springboot.respository.ProjectSkillRepository.getSkillByProjectId"))
    @Result(property = "positions", column = "project_id", many = @Many(select = "com.kshrd.devconnect_springboot.respository.ProjectPositionRepository.getAllProjectPositionById"))
    @Select("""
        SELECT p.* FROM projects p INNER JOIN bookmarks b ON p.project_id = b.target_id WHERE bookmark_by = #{bookmarkBy}
        OFFSET #{page} LIMIT #{size}
    """)
    List<Project> getAllBookmarkProject(UUID bookmarkBy, Integer page, Integer size);

    @Result(property = "jobId", column = "job_id")
    @Result(property = "jobBoard", column = "job_board", typeHandler = com.kshrd.devconnect_springboot.config.JobBoardTypeHandler.class)
    @Result(property = "jobType", column = "job_type" , one = @One(select = "com.kshrd.devconnect_springboot.respository.JobsRepository.selectJobTypeById"))
    @Result(property = "postedDate", column = "posted_date")
    @Result(property = "creator", column = "creator_id" , one = @One(select = "com.kshrd.devconnect_springboot.respository.AppUserRepository.getUserById"))
    @Select("""
        SELECT j.* FROM jobs j INNER JOIN bookmarks b ON j.job_id = b.target_id WHERE bookmark_by = #{bookmarkBy};
        OFFSET #{page} LIMIT #{size}
    """)
    List<Jobs> getAllBookmarkJob(UUID bookmarkBy, Integer page, Integer size);


    List<Hackathon> getAllBookmarkHackathon(UUID bookmarkBy, Integer page, Integer size);
//    SELECT h.* FROM hackathons h INNER JOIN bookmarks b ON h.hackathon_id = b.target_id WHERE bookmark_by = '';

//    SELECT r.* FROM recruiter_profiles r INNER JOIN bookmarks b ON b.target_id = r.recruiter_id WHERE bookmark_by = '';
//    List<Recruiter> getAllBookmarkRecruiter(UUID bookmarkBy, Integer page, Integer size);

//    SELECT d.* FROM developer_profiles d INNER JOIN bookmarks b ON  b.target_id = d.developer_id WHERE bookmark_by = '';
//    List<Developer> getAllBookmarkDeveloper(UUID bookmarkBy, Integer page, Integer size);

}
