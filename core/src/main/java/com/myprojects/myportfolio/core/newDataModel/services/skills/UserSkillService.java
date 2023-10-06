package com.myprojects.myportfolio.core.newDataModel.services.skills;

import com.myprojects.myportfolio.core.newDataModel.dao.skills.NewUserSkill;
import com.myprojects.myportfolio.core.newDataModel.dao.skills.NewUserSkillPK;
import com.myprojects.myportfolio.core.newDataModel.repositories.UserRepository;
import com.myprojects.myportfolio.core.newDataModel.repositories.skills.SkillRepository;
import com.myprojects.myportfolio.core.newDataModel.repositories.skills.UserSkillRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.Validate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Slf4j
@Service(value = "newUserSkillService")
@Transactional
public class UserSkillService implements UserSkillServiceI {

    private final UserSkillRepository userSkillRepository;

    private final UserRepository userRepository;

    private final SkillRepository skillRepository;

    public UserSkillService(UserSkillRepository userSkillRepository, UserRepository userRepository, SkillRepository skillRepository) {
        this.userSkillRepository = userSkillRepository;
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public NewUserSkill findById(NewUserSkillPK id) {
        Validate.notNull(id, "Mandatory parameter is missing: id");
        Validate.notNull(id.getUserId(), "Mandatory parameter is missing: userId");
        Validate.notNull(id.getSkillId(), "Mandatory parameter is missing: skillId");

        return userSkillRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));
    }

    @Override
    public NewUserSkill save(NewUserSkill t) {
        Validate.notNull(t, "Mandatory parameter is missing: entity");

        if (t.getId() != null && userSkillRepository.findById(t.getId()).isPresent()) {
            throw new IllegalArgumentException("Entity already exists with this id");
        }
        checkRelationsExists(t);

        return userSkillRepository.saveAndFlush(t);
    }

    @Override
    public NewUserSkill update(NewUserSkill t) {
        Validate.notNull(t, "Mandatory parameter is missing: entity");
        Validate.notNull(t.getId(), "Mandatory parameter is missing: id");
        checkIfEntityDoesNotExist(t);
        checkRelationsExists(t);

        return userSkillRepository.saveAndFlush(t);
    }

    @Override
    public void delete(NewUserSkill t) {
        Validate.notNull(t, "Mandatory parameter is missing: entity");
        Validate.notNull(t.getId(), "Mandatory parameter is missing: id");
        checkIfEntityDoesNotExist(t);

        userSkillRepository.delete(t);
    }

    private void checkIfEntityDoesNotExist(NewUserSkill t) {
        if (t.getId() == null || userSkillRepository.findById(t.getId()).isEmpty()) {
            throw new IllegalArgumentException("Entity does not exist" + (t.getId() != null ? (" with id: " + t.getId()) : "."));
        }
    }

    // check the user and the skill exists:
    private void checkRelationsExists(NewUserSkill t) {
        if (t.getUser() == null || t.getUser().getId() == null || userRepository.findById(t.getUser().getId()).isEmpty()) {
            throw new IllegalArgumentException("User does not exist with this id");
        }
        if (t.getSkill() == null || t.getSkill().getId() == null || skillRepository.findById(t.getSkill().getId()).isEmpty()) {
            throw new IllegalArgumentException("Skill does not exist with this id");
        }
    }

}
