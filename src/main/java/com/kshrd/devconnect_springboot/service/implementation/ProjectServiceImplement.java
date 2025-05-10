package com.kshrd.devconnect_springboot.service.implementation;

import com.kshrd.devconnect_springboot.exception.BadRequestException;
import com.kshrd.devconnect_springboot.exception.NotFoundException;
import com.kshrd.devconnect_springboot.model.dto.request.ProjectPositionRequest;
import com.kshrd.devconnect_springboot.model.dto.request.ProjectRequest;
import com.kshrd.devconnect_springboot.model.entity.AppUser;
import com.kshrd.devconnect_springboot.model.entity.JoinProject;
import com.kshrd.devconnect_springboot.model.entity.Project;
import com.kshrd.devconnect_springboot.model.entity.ProjectPosition;
import com.kshrd.devconnect_springboot.respository.*;
import com.kshrd.devconnect_springboot.service.ProjectService;
import com.kshrd.devconnect_springboot.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectServiceImplement implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectPositionRepository projectPositionRepository;
    private final ProjectSkillRepository projectSkillRepository;
    private final PositionRepository positionRepository;
    private final SkillRepository skillRepository;

    @Override
    public List<Project> getAllProject(Integer page, Integer size) {
        page = (page - 1) * size;
        return projectRepository.getAllProject(page, size);
    }

    @Override
    public Project getProjectById(UUID projectId) {
        Project project = projectRepository.getProjectById(projectId);
        if(project == null) {
            throw new NotFoundException("Project not found");
        }
        return project;
    }

    @Override
    public List<Project> getAllProjectByUser(Integer page, Integer size) {
        page = (page - 1) * size;
        return projectRepository.getAllProjectByUser(CurrentUser.appUserId, page, size);
    }

    @Override
    public Project getProjectByIdAndUser(UUID projectId) {
        Project project = projectRepository.getProjectByIdAndUser(CurrentUser.appUserId, projectId);
        if(project == null) {
            throw new NotFoundException("Project not found");
        }
        return project;
    }

    @Override
    public Project createProject(ProjectRequest projectRequest) {
        Project project = projectRepository.createProjectByUser(CurrentUser.appUserId, projectRequest);
        for (UUID s : projectRequest.getSkills()) {
            if (skillRepository.getSkillById(s) == null) {
                throw new NotFoundException("Skill not found");
            }
            projectSkillRepository.createProjectSkill(project.getProjectId(), s);
        }
        for (ProjectPositionRequest p : projectRequest.getPositions()) {
            if(positionRepository.getPositionById(p.getPositionId()) == null) {
                throw new NotFoundException("Position not found");
            }
            projectPositionRepository.createProjectPosition(p.getMaxMembers(), project.getProjectId(), p.getPositionId());
        }
        return getProjectById(project.getProjectId());
    }

    @Override
    public Project updateProject(UUID projectId, ProjectRequest projectRequest) {
        if(getProjectById(projectId) == null) {
            throw new NotFoundException("Project not found");
        }
        Project project = projectRepository.updateProject(projectId, projectRequest);
        projectSkillRepository.deleteAllProjectSkill(projectId);
        projectPositionRepository.deleteAllProjectPosition(projectId);
        for (UUID s : projectRequest.getSkills()) {
            if (skillRepository.getSkillById(s) == null) {
                throw new NotFoundException("Skill not found");
            }
            projectSkillRepository.createProjectSkill(project.getProjectId(), s);
        }
        for (ProjectPositionRequest p : projectRequest.getPositions()) {
            if(positionRepository.getPositionById(p.getPositionId()) == null) {
                throw new NotFoundException("Position not found");
            }
            projectPositionRepository.createProjectPosition(p.getMaxMembers(), project.getProjectId(), p.getPositionId());
        }
        return getProjectById(project.getProjectId());
    }

    @Override
    public void deleteProject(UUID projectId) {
        Project project = projectRepository.getProjectByIdAndUser(CurrentUser.appUserId, projectId);
        if(project == null) {
            throw new NotFoundException("Project not found");
        }
        projectRepository.deleteProject(CurrentUser.appUserId, projectId);
    }

    @Override
    public JoinProject createJoinProject(JoinProject joinProject) {
        return null;
    }
}
