package com.myprojects.myportfolio.core.controllers.skills;

import com.myprojects.myportfolio.clients.general.messages.MessageResource;
import com.myprojects.myportfolio.clients.general.messages.MessageResources;
import com.myprojects.myportfolio.clients.general.views.IView;
import com.myprojects.myportfolio.core.controllers.IController;
import com.myprojects.myportfolio.core.dao.skills.NewUserSkill;
import com.myprojects.myportfolio.core.dto.groups.OnCreate;
import com.myprojects.myportfolio.core.dto.groups.OnUpdate;
import com.myprojects.myportfolio.core.dto.skills.NewUserSkillDto;
import com.myprojects.myportfolio.core.mappers.skills.UserSkillMapper;
import com.myprojects.myportfolio.core.services.skills.UserSkillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("newUserSkillController")
@RequestMapping("${core-module-basic-path}" + "/new/userSkills")
public class UserSkillController extends IController<NewUserSkillDto> {

    private final UserSkillService userSkillService;

    private final UserSkillMapper userSkillMapper;

    public UserSkillController(UserSkillService userSkillService, UserSkillMapper userSkillMapper) {
        this.userSkillService = userSkillService;
        this.userSkillMapper = userSkillMapper;
    }

    @Override
    @PostMapping()
//    @PreAuthorize("hasAnyRole(T(ApplicationUserRole).SYS_ADMIN.getName()) || @utilsService.hasId(#entity.userId)")
    protected ResponseEntity<MessageResource<NewUserSkillDto>> create(@Validated(OnCreate.class) @RequestBody NewUserSkillDto entity) throws Exception {
        Validate.notNull(entity, resourceMissing());

        NewUserSkill newEntity = userSkillService.save(userSkillMapper.mapToDao(entity));
        return this.buildSuccessResponse(userSkillMapper.mapToDto(newEntity));
    }

    @Override
    @PutMapping()
//    @PreAuthorize("hasAnyRole(T(ApplicationUserRole).SYS_ADMIN.getName()) || @utilsService.hasId(#entity.userId)")
    protected ResponseEntity<MessageResource<NewUserSkillDto>> update(Integer id, @Validated(OnUpdate.class) @RequestBody NewUserSkillDto entity) throws Exception {
        Validate.notNull(entity, resourceMissing());
        Validate.notNull(entity.getId(), fieldMissing("id"));

        NewUserSkill updatedEntity = userSkillService.update(userSkillMapper.mapToDao(entity));
        return this.buildSuccessResponse(userSkillMapper.mapToDto(updatedEntity));
    }

    @DeleteMapping(value = "/{userId}/skill/{skillId}")
//    @PreAuthorize("hasAnyRole(T(ApplicationUserRole).SYS_ADMIN.getName()) || @utilsService.hasId(#userId)")
    protected ResponseEntity<MessageResource<NewUserSkillDto>> delete(@PathVariable("userId") Integer userId, @PathVariable("skillId") Integer skillId) throws Exception {
        Validate.notNull(userId, fieldMissing("userId"));
        Validate.notNull(skillId, fieldMissing("skillId"));

        NewUserSkill entityToDelete = NewUserSkill.builder().userId(userId).skillId(skillId).build();
        userSkillService.delete(entityToDelete);
        return this.buildSuccessResponse(userSkillMapper.mapToDto(entityToDelete));
    }

    @Override
    protected ResponseEntity<MessageResources<NewUserSkillDto>> find(String filters, IView view, Pageable pageable) throws Exception {
        throw new NotImplementedException("Method not implemented");
    }

    @Override
    protected ResponseEntity<MessageResource<NewUserSkillDto>> get(Integer id, IView view) throws Exception {
        throw new NotImplementedException("Method not implemented");
    }

    @Override
    protected ResponseEntity<MessageResource<NewUserSkillDto>> delete(Integer id) throws Exception {
        throw new NotImplementedException("Method not implemented");
    }

}
