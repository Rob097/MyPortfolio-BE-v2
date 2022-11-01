package com.myprojects.myportfolio.core.user;

import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.clients.general.views.Normal;
import com.myprojects.myportfolio.clients.general.views.Verbose;
import com.myprojects.myportfolio.core.diary.Diary;
import com.myprojects.myportfolio.core.diary.DiaryRepository;
import com.myprojects.myportfolio.core.education.Education;
import com.myprojects.myportfolio.core.education.EducationRepository;
import com.myprojects.myportfolio.core.experience.Experience;
import com.myprojects.myportfolio.core.experience.ExperienceRepository;
import com.myprojects.myportfolio.core.project.Project;
import com.myprojects.myportfolio.core.project.ProjectRepository;
import com.myprojects.myportfolio.core.skill.Skill;
import com.myprojects.myportfolio.core.skill.SkillRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value="userService")
@Transactional
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private EducationRepository educationRepository;

    @Autowired
    private ExperienceRepository experienceRepository;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private DiaryRepository diaryRepository;

    public List<User> getAllUsers(){
        List<User> all = this.userRepository.findAll();
        return all;
    }

    public User findById(Integer id) {
        Validate.notNull(id, "Mandatory parameter is missing: id.");

        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new NoSuchElementException("Impossible to found any user with id: " + id));
    }

    public User findFirstById(Integer id, IView view) {
        Validate.notNull(id, "Mandatory parameter is missing: id.");

        UserProjection userProjection = this.userRepository.findFirstById(id);

        User user = new User(userProjection);
        user = this.fetchRelations(user, view);

        return user;
    }

    public User save(User u){
        Validate.notNull(u, "Mandatory parameter is missing: user.");

        if(u.getId()!=null) {
            Optional<User> actual = this.userRepository.findById(u.getId());
            Validate.isTrue(!actual.isPresent(), "It already exists an application user with id: " + u.getId());
        }

        User user = this.userRepository.save(u);
        return user;
    }

    public User update(User userToUpdate){
        Validate.notNull(userToUpdate, "Mandatory parameter is missing: user.");
        Validate.notNull(userToUpdate.getId(), "Mandatory parameter is missinge: id user.");

        return this.userRepository.save(userToUpdate);
    }

    public void delete(User userToDelete){
        Validate.notNull(userToDelete, "Mandatory parameter is missing: user.");

        this.userRepository.delete(userToDelete);
    }

    /**Method used to check if an id is the same as the one of the current logged-in user*/
    public boolean hasId(Integer id){
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByEmail(username);
        return user.getId().equals(id);
    }

    private User fetchRelations(User user, IView view){
        if(user==null || view==null){
            return user;
        }

        if(view.isExactly(Normal.value)){

            this.projectRepository.findAllIdsByUserId(user.getId()).ifPresentOrElse(
                    (projectsId) -> user.setProjects(projectsId.stream().map(el -> Project.builder().id(el).build()).collect(Collectors.toList())),
                    () -> { log.warn("No projects found for user: " + user.getId()); }
            );

            this.educationRepository.findAllIdsByUserId(user.getId()).ifPresentOrElse(
                    (educationsId) -> user.setEducations(educationsId.stream().map(el -> Education.builder().id(el).build()).collect(Collectors.toList())),
                    () -> { log.warn("No educations found for user: " + user.getId()); }
            );

            this.experienceRepository.findAllIdsByUserId(user.getId()).ifPresentOrElse(
                    (experiencesId) -> user.setExperiences(experiencesId.stream().map(el -> Experience.builder().id(el).build()).collect(Collectors.toList())),
                    () -> { log.warn("No experiences found for user: " + user.getId()); }
            );

            this.skillRepository.findAllIdsByUserId(user.getId()).ifPresentOrElse(
                    (skillsId) -> user.setSkills(skillsId.stream().map(el -> Skill.builder().id(el).build()).collect(Collectors.toSet())),
                    () -> { log.warn("No skills found for user: " + user.getId()); }
            );

            this.diaryRepository.findIdByUserId(user.getId()).ifPresentOrElse(
                    (diaryId) -> user.setDiary(Diary.builder().id(diaryId).build()),
                    () -> { log.warn("No diary found for user: " + user.getId()); }
            );

        }

        if(view.isExactly(Verbose.value)){

            this.projectRepository.findAllByUserId(user.getId()).ifPresentOrElse(
                    (projects) -> user.setProjects(projects.stream().map(el -> new Project(el)).collect(Collectors.toList())),
                    () -> { log.warn("No projects found for user: " + user.getId()); }
            );

            this.educationRepository.findAllByUserId(user.getId()).ifPresentOrElse(
                    (educations) -> user.setEducations(educations.stream().map(el -> new Education(el)).collect(Collectors.toList())),
                    () -> { log.warn("No educations found for user: " + user.getId()); }
            );

            this.experienceRepository.findAllByUserId(user.getId()).ifPresentOrElse(
                    (experiences) -> user.setExperiences(experiences.stream().map(el -> new Experience(el)).collect(Collectors.toList())),
                    () -> { log.warn("No experiences found for user: " + user.getId()); }
            );

            this.skillRepository.findAllByUserId(user.getId()).ifPresentOrElse(
                    (skills) -> user.setSkills(skills),
                    () -> { log.warn("No skills found for user: " + user.getId()); }
            );

            this.diaryRepository.findByUserId(user.getId()).ifPresentOrElse(
                    (diary) -> user.setDiary(new Diary(diary)),
                    () -> { log.warn("No diary found for user: " + user.getId()); }
            );

        }

        return user;

    }

}

