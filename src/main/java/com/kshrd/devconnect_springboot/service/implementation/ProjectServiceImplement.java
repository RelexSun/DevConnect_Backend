package com.kshrd.devconnect_springboot.service.implementation;

import com.kshrd.devconnect_springboot.exception.BadRequestException;
import com.kshrd.devconnect_springboot.exception.NotFoundException;
import com.kshrd.devconnect_springboot.model.dto.request.JoinProjectRequest;
import com.kshrd.devconnect_springboot.model.dto.request.ProjectPositionRequest;
import com.kshrd.devconnect_springboot.model.dto.request.ProjectRequest;
import com.kshrd.devconnect_springboot.model.dto.response.AppUserResponse;
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
    private final JoinProjectRepository joinProjectRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public List<Project> getAllProject(Integer page, Integer size) {
        page = (page - 1) * size;
        return projectRepository.getAllProject(page, size);
    }

    @Override
    public Project getProjectById(UUID projectId) {
        Project project = projectRepository.getProjectById(projectId);
        if (project == null) {
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
        if (project == null) {
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
            if (positionRepository.getPositionById(p.getPositionId()) == null) {
                throw new NotFoundException("Position not found");
            }
            projectPositionRepository.createProjectPosition(p.getMaxMembers(), project.getProjectId(), p.getPositionId());
        }
        return getProjectById(project.getProjectId());
    }

    @Override
    public Project updateProject(UUID projectId, ProjectRequest projectRequest) {
        if (getProjectById(projectId) == null) {
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
            if (positionRepository.getPositionById(p.getPositionId()) == null) {
                throw new NotFoundException("Position not found");
            }
            projectPositionRepository.createProjectPosition(p.getMaxMembers(), project.getProjectId(), p.getPositionId());
        }
        return getProjectById(project.getProjectId());
    }

    @Override
    public void deleteProject(UUID projectId) {
        Project project = projectRepository.getProjectByIdAndUser(CurrentUser.appUserId, projectId);
        if (project == null) {
            throw new NotFoundException("Project not found");
        }
        projectRepository.deleteProject(CurrentUser.appUserId, projectId);
    }

    @Override
    public JoinProject createJoinProject(JoinProjectRequest joinProject) {
        Project project = projectRepository.getProjectById(joinProject.getProjectId());
        if (project == null) {
            throw new NotFoundException("Project not found");
        }
        if (positionRepository.getPositionById(joinProject.getPositionId()) == null) throw new NotFoundException("Position not found");
        if (projectPositionRepository.getPositionByProject(project.getProjectId(), joinProject.getPositionId()) == null) throw new NotFoundException("Position don't exist in the project");
        if (appUserRepository.getUserById(joinProject.getDeveloperId()) == null) throw new NotFoundException("Developer not found");
        if (appUserRepository.getUserById(joinProject.getDeveloperId()).getIsRecruiter()) throw new BadRequestException("Only developer allow to join");

//        if maxMember < approved join should throw error
        for (ProjectPosition p : project.getPositions()) {
            if (joinProjectRepository.getApprovedCount(project.getProjectId(), p.getPositionId()) > joinProjectRepository.getApprovedCount(project.getProjectId(), p.getPositionId())) {
                throw new BadRequestException("Project position already full");
            }
        }
        return joinProjectRepository.createJoinProject(joinProject);
    }

    @Override
    public List<ProjectPosition> getAllPositionByProjectId(UUID projectId) {
        Project project = projectRepository.getProjectById(projectId);
        if (project == null) {
            throw new NotFoundException("Project not found");
        }
        return projectPositionRepository.getAllPositionByProjectId(projectId);
    }

    @Override
    public void updateProjectStatus(Boolean status, UUID projectId) {
        Project project = projectRepository.getProjectById(projectId);
        if (project == null) {
            throw new NotFoundException("Project not found");
        }
        projectRepository.updateProjectStatus(status, projectId, CurrentUser.appUserId);
    }

    @Override
    public void updateProjectApproval(Boolean status, UUID projectId, UUID developerId) {
        Project project = projectRepository.getProjectById(projectId);
        if (project == null) {
            throw new NotFoundException("Project not found");
        }
        AppUserResponse appUser = appUserRepository.getUserById(developerId);
        if (appUser == null) {
            throw new NotFoundException("Developer not found");
        }
        if (joinProjectRepository.getJoinProjectByDeveloperId(developerId) == null) {
            throw new NotFoundException("Developer didn't apply to this project");
        }
        if (project.getOwner().getUserId() != CurrentUser.appUserId) {
            throw new BadRequestException("You are not the owner of this project");
        }
        joinProjectRepository.updateApprovalStatus(status, projectId, developerId);
    }

}
